package itx.examples.akka.cluster.sshsessions.cluster;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import itx.examples.akka.cluster.sshsessions.Utils;
import itx.examples.akka.cluster.sshsessions.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by juraj on 3/18/17.
 */
public class SshClusterManager {

    private static final Logger LOG = LoggerFactory.getLogger(SshClusterManager.class);

    private String leaderNodeAddress;
    private String selfNodeAddress;
    private Map<String, MemberInfo> members;  //indexed by memberAddress
    private AtomicBoolean isLeader;
    private ActorSystem actorSystem;
    private ActorRef selfRef;
    private Map<String, SessionCreateInfo> pendingCreateSessionRequests; //indexed by clientId
    private Map<String, SessionCreateInfo> pendingCloseSessionRequests; //indexed by clientId

    public SshClusterManager() {
        this.members = new ConcurrentHashMap<>();
        this.pendingCreateSessionRequests = new ConcurrentHashMap<>();
        this.pendingCloseSessionRequests = new ConcurrentHashMap<>();
        this.isLeader = new AtomicBoolean(false);
    }

    public void setContext(ActorSystem actorSystem, ActorRef selfRef, String selfNodeAddress) {
        this.selfNodeAddress = selfNodeAddress;
        this.selfRef = selfRef;
        this.actorSystem = actorSystem;
    }

    public void setLeaderAddress(String leaderAddress) {
        this.leaderNodeAddress = leaderAddress;
        if (this.selfNodeAddress.equals(leaderAddress)) {
            isLeader.set(true);
            pingAllMemberSessions();
        } else {
            isLeader.set(false);
        }
        printMembers();
    }

    public void addMember(String memberAddress, String status) {
        LOG.info("addMember: " + selfNodeAddress + " " + memberAddress + " " + status);
        if (members.get(memberAddress) == null ) {
            members.put(memberAddress, new MemberInfo(memberAddress, 0));
            printMembers();
            if (isLeader.get()) {
                pingMemberSessions(memberAddress);
            }
        }
    }

    public void removeMember(String memberAddress, String status) {
        LOG.info("removeMember: " + selfNodeAddress + " " + memberAddress + " " + status);
        members.remove(memberAddress);
        printMembers();
    }

    public void onPongMessage(SessionPongMessage sessionPongMessage) {
        if (isLeader.get()) {
            LOG.info("Pong from: " + sessionPongMessage.getSessionId());
            members.get(sessionPongMessage.getMemberAddress()).incrementSessionCount();
        }
    }

    public void onSessionCreateRequest(SessionCreateRequest sessionCreateRequest) {
        if (isLeader.get()) {
            String memberAddress = selectClusterMemberForNewSession();
            ActorSelection actorSelection = actorSystem.actorSelection(Utils.getSshSessionCreatorAddress(memberAddress));
            actorSelection.tell(sessionCreateRequest, selfRef);
            //TODO: set timeout (circuit breaker)
            SessionCreateInfo sessionCreateInfo =
                    new SessionCreateInfo(sessionCreateRequest.getClientId(), memberAddress, sessionCreateRequest.getClientActorAddress());
            pendingCreateSessionRequests.put(sessionCreateRequest.getClientId(), sessionCreateInfo);
        }
    }

    public void onSessionCreateResponse(SessionCreateResponse sessionCreateResponse) {
        if (isLeader.get()) {
            LOG.info("session created response: " + sessionCreateResponse.getSessionId());
            SessionCreateInfo sessionCreateInfo = pendingCreateSessionRequests.remove(sessionCreateResponse.getClientId());
            if (sessionCreateInfo != null) {
                members.get(sessionCreateInfo.getMemberAddress()).incrementSessionCount();
                actorSystem.actorSelection(sessionCreateInfo.getClientActorAddress()).tell(sessionCreateResponse, selfRef);
            }
        }
    }

    public void onSessionCloseRequest(SessionCloseRequest sessionCloseRequest) {
        if (isLeader.get()) {
            LOG.info("session close request: " + sessionCloseRequest.getSessionActorAddress());
            for (MemberInfo memberInfo: members.values()) {
                if (sessionCloseRequest.getSessionActorAddress().startsWith(memberInfo.getMemberAddress())) {
                    memberInfo.decrementSessionCount();
                    break;
                }
            }
            ActorSelection actorSelection = actorSystem.actorSelection(sessionCloseRequest.getSessionActorAddress());
            actorSelection.tell(sessionCloseRequest, selfRef);
        }
    }

    public void onSessionCloseResponse(SessionCloseResponse sessionCloseResponse) {
        if (isLeader.get()) {
            LOG.info("session close response: " + sessionCloseResponse.getSessionId());
            ActorSelection actorSelection = actorSystem.actorSelection(sessionCloseResponse.getClientActorAddress());
            actorSelection.tell(sessionCloseResponse, selfRef);
        }
    }

    public String getSshClusterManagerLeaderAddress() {
        return Utils.getSshClusterManagerAddress(leaderNodeAddress);
    }

    public String getSelfNodeAddress() {
        return selfNodeAddress;
    }

    private void pingAllMemberSessions() {
        members.forEach( (memberAddress, memberInfo) -> {
            pingMemberSessions(memberAddress);
        });
    }

    private void pingMemberSessions(String memberAddress) {
        LOG.info("scanning " + memberAddress + " for ssh sessions");
        String path = Utils.getSshSessionsSelectionAddress(memberAddress);
        actorSystem.actorSelection(path).tell(new SessionPingMessage(), selfRef);
    }

    private String selectClusterMemberForNewSession() {
        //select cluster node with minimum ssh sessions
        String memberAddress = null;
        int minSessions = Integer.MAX_VALUE;
        for (MemberInfo memberInfo: members.values()) {
            if (minSessions > memberInfo.getSessions()) {
                minSessions = memberInfo.getSessions();
                memberAddress = memberInfo.getMemberAddress();
            }
        }
        LOG.info("selected node [" + minSessions + "] " + memberAddress);
        return memberAddress;
    }

    private void printMembers() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        members.forEach( (k,v) -> {
           sb.append("  ");
           sb.append(k.equals(selfNodeAddress)?"->":"  ");
           sb.append(k.equals(leaderNodeAddress)?"LEADER":"follow");
           sb.append(" [");
           sb.append(v.getSessions());
           sb.append("] ");
           sb.append(k);
           sb.append("\n");
        } );
        LOG.info(sb.toString());
    }

}
