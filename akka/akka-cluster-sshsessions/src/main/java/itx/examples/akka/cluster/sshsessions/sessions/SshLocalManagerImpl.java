package itx.examples.akka.cluster.sshsessions.sessions;

import akka.actor.ActorRef;
import itx.examples.akka.cluster.sshsessions.dto.*;
import itx.examples.akka.cluster.sshsessions.mock.SshSessionImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by gergej on 25.3.2017.
 */
public class SshLocalManagerImpl implements SshLocalManager {

    private static final Logger LOG = LoggerFactory.getLogger(SshLocalManagerImpl.class);

    private String nodeAddress;
    private Map<String, ActiveSessionData> activeSessions;
    private Map<String, ActorRef> pendingSessionCloseRequests;

    public SshLocalManagerImpl() {
        activeSessions = new ConcurrentHashMap<>();
        pendingSessionCloseRequests = new ConcurrentHashMap<>();
    }

    public void setNodeAddress(String nodeAddress) {
        this.nodeAddress = nodeAddress;
    }

    public void onSessionCreateResquest(SshSessionImpl sshSession, ActorRef sshSessionActorRef, SessionCreateRequest sessionCreateRequest) {
        LOG.info("create session from: " + sessionCreateRequest.getClientActorAddress());
        ActiveSessionData activeSessionInfo = new ActiveSessionData(
                sessionCreateRequest.getClientId(), sshSessionActorRef,
                sshSession.getId(), sessionCreateRequest.getClientActorAddress(), sshSession);
        activeSessions.put(sshSession.getId(), activeSessionInfo);
    }

    public void onSessionCloseRequest(ActorRef self, ActorRef sender, SessionCloseRequest sessionCloseRequest) {
        ActiveSessionData activeSessionInfo = activeSessions.get(sessionCloseRequest.getSshSessionId());
        if (activeSessionInfo != null) {
            activeSessionInfo.getSessionActor().tell(sessionCloseRequest, self);
            pendingSessionCloseRequests.put(sessionCloseRequest.getSshSessionId(), sender);
        }
    }

    public void onSessionCloseResponse(ActorRef self, SessionCloseResponse sessionCloseResponse) {
        activeSessions.remove(sessionCloseResponse.getSessionId());
        ActorRef sessionRemoveRequestor = pendingSessionCloseRequests.remove(sessionCloseResponse.getSessionId());
        if (sessionRemoveRequestor != null) {
            sessionRemoveRequestor.tell(sessionCloseResponse, self);
        }
    }

    public GetActiveSessionsResponse onGetActiveSessionsRequest() {
        GetActiveSessionsResponse activeSessionsResponse =
                new GetActiveSessionsResponse(nodeAddress, getActiveSessions());
        return activeSessionsResponse;
    }

    @Override
    public List<ActiveSession> getActiveSessions() {
        List<ActiveSession> activeSessionList = new ArrayList<>();
        activeSessions.values().forEach( as -> {
            ActiveSession activeSession = new ActiveSession(as.getSessionId(),
                    as.getClientId(), as.getClientSessionActorAddress());
            activeSessionList.add(activeSession);
        });
        return activeSessionList;
    }

}
