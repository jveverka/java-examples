package itx.examples.akka.cluster.sshsessions.sessions;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.cluster.Cluster;
import itx.examples.akka.cluster.sshsessions.Utils;
import itx.examples.akka.cluster.sshsessions.dto.*;
import itx.examples.akka.cluster.sshsessions.mock.SshSessionImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

/**
 * Created by juraj on 3/19/17.
 */
public class SshLocalManagerActor extends UntypedActor {

    private static final Logger LOG = LoggerFactory.getLogger(SshLocalManagerActor.class);

    private SshLocalManagerImpl localManager;
    private String nodeAddress;

    public SshLocalManagerActor(SshLocalManagerImpl localManager) {
        this.localManager = localManager;
    }

    @Override
    public void preStart() {
        nodeAddress = Cluster.get(context().system()).selfAddress().toString();
        localManager.setNodeAddress(nodeAddress);
    }

    @Override
    public void onReceive(Object message) throws Throwable {
        if (message instanceof SessionCreateRequest) {
            SessionCreateRequest sessionCreateRequest = (SessionCreateRequest) message;
            String sessionId = UUID.randomUUID().toString();

            //1. create session objects and actor
            SshSessionImpl sshSession = new SshSessionImpl(sessionId, null);
            ActorRef sshSessionActorRef = context().system().actorOf(Props.create(
                    SshSessionActor.class, sshSession, sessionCreateRequest.getClientActorAddress()),
                    Utils.generateSessionActorName(sessionId));

            //2. notify creator about ssh session creation result
            String sessionActorAddress = Utils.getSshSessionAddress(nodeAddress, sshSession.getId());
            SessionCreateResponse sessionCreateResponse =
                    new SessionCreateResponse(sshSession.getId(),
                            sessionCreateRequest.getClientId(), sessionActorAddress,
                            Utils.getSshLocalManagerActorAddress(nodeAddress));
            sender().tell(sessionCreateResponse, self());
            localManager.onSessionCreateResquest(sshSession, sshSessionActorRef, sessionCreateRequest);
        } else if (message instanceof SessionCloseRequest) {
            SessionCloseRequest sessionCloseRequest = (SessionCloseRequest)message;
            localManager.onSessionCloseRequest(self(), sender(), sessionCloseRequest);
        } else if (message instanceof SessionCloseResponse) {
            SessionCloseResponse sessionCloseResponse = (SessionCloseResponse)message;
            localManager.onSessionCloseResponse(self(), sessionCloseResponse);
        } else if (message instanceof GetActiveSessionsRequest) {
            GetActiveSessionsResponse activeSessionsResponse = localManager.onGetActiveSessionsRequest();
            sender().tell(activeSessionsResponse, self());
        } else {
            LOG.info("onReceive: " + message.getClass().getName());
        }
    }

}
