package itx.examples.akka.cluster.sshsessions.sessions;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.cluster.Cluster;
import itx.examples.akka.cluster.sshsessions.Utils;
import itx.examples.akka.cluster.sshsessions.dto.*;
import itx.examples.akka.cluster.sshsessions.mock.SshSession;
import itx.examples.akka.cluster.sshsessions.mock.SshSessionImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by juraj on 3/19/17.
 */
public class SshLocalManagerActor extends UntypedActor {

    private static final Logger LOG = LoggerFactory.getLogger(SshLocalManagerActor.class);

    private String nodeAddress;
    private Map<String, ActiveSessionInfo> activeSessions;
    private Map<String, ActorRef> pendingSessionCloseRequests;

    public SshLocalManagerActor() {
        activeSessions = new ConcurrentHashMap<>();
        pendingSessionCloseRequests = new ConcurrentHashMap<>();
    }

    @Override
    public void preStart() {
        nodeAddress = Cluster.get(context().system()).selfAddress().toString();
    }

    @Override
    public void onReceive(Object message) throws Throwable {
        if (message instanceof SessionCreateRequest) {
            SessionCreateRequest sessionCreateRequest = (SessionCreateRequest) message;
            LOG.info("create session from: " + sessionCreateRequest.getClientActorAddress());
            String sessionId = UUID.randomUUID().toString();

            //1. create session objects and actor
            SshSession sshSession = new SshSessionImpl(sessionId);
            ActorRef sshSessionActorRef = context().system().actorOf(Props.create(
                    new SshSessionActorCreator(sshSession, sessionCreateRequest.getClientActorAddress())),
                    Utils.generateSessionActorName(sessionId));

            //2. notify creator about ssh session creation result
            nodeAddress = Cluster.get(context().system()).selfAddress().toString();
            String sessionActorAddress = Utils.getSshSessionAddress(nodeAddress, sshSession.getId());
            SessionCreateResponse sessionCreateResponse =
                    new SessionCreateResponse(sshSession.getId(),
                            sessionCreateRequest.getClientId(), sessionActorAddress,
                            Utils.getSshLocalManagerActorAddress(nodeAddress));
            sender().tell(sessionCreateResponse, self());

            ActiveSessionInfo activeSessionInfo = new ActiveSessionInfo(
                    sessionCreateRequest.getClientId(), sshSessionActorRef,
                    sessionId, sessionCreateRequest.getClientActorAddress());
            activeSessions.put(sessionId, activeSessionInfo);
        } else if (message instanceof SessionCloseRequest) {
            SessionCloseRequest sessionCloseRequest = (SessionCloseRequest)message;
            ActiveSessionInfo activeSessionInfo = activeSessions.get(sessionCloseRequest.getSshSessionId());
            if (activeSessionInfo != null) {
                activeSessionInfo.getSessionActor().tell(sessionCloseRequest, self());
                pendingSessionCloseRequests.put(sessionCloseRequest.getSshSessionId(), sender());
            }
        } else if (message instanceof SessionCloseResponse) {
            SessionCloseResponse sessionCloseResponse = (SessionCloseResponse)message;
            activeSessions.remove(sessionCloseResponse.getSessionId());
            ActorRef sessionRemoveRequestor = pendingSessionCloseRequests.remove(sessionCloseResponse.getSessionId());
            if (sessionRemoveRequestor != null) {
                sessionRemoveRequestor.tell(sessionCloseResponse, self());
            }
        } else if (message instanceof GetActiveSessionsRequest) {
            List<ActiveSession> activeSessionList = new ArrayList<>();
            activeSessions.values().forEach( as -> {
                ActiveSession activeSession = new ActiveSession(as.getSessionId(),
                        as.getClientId(), as.getClientSessionActorAddress());
                activeSessionList.add(activeSession);
            });
            GetActiveSessionsResponse activeSessionsResponse = new GetActiveSessionsResponse(nodeAddress, activeSessionList);
            sender().tell(activeSessionsResponse, self());
        } else {
            LOG.info("onReceive: " + message.getClass().getName());
        }
    }

}
