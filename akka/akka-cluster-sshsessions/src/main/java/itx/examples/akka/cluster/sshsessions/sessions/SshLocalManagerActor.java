package itx.examples.akka.cluster.sshsessions.sessions;

import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.cluster.Cluster;
import itx.examples.akka.cluster.sshsessions.Utils;
import itx.examples.akka.cluster.sshsessions.dto.SessionCreateRequest;
import itx.examples.akka.cluster.sshsessions.dto.SessionCreateResponse;
import itx.examples.akka.cluster.sshsessions.mock.SshSession;
import itx.examples.akka.cluster.sshsessions.mock.SshSessionImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

/**
 * Created by juraj on 3/19/17.
 */
public class SshLocalManagerActor extends UntypedActor {

    private static final Logger LOG = LoggerFactory.getLogger(SshLocalManagerActor.class);

    @Override
    public void onReceive(Object message) throws Throwable {
        if (message instanceof SessionCreateRequest) {
            SessionCreateRequest sessionCreateRequest = (SessionCreateRequest)message;
            LOG.info("create session from: " + sessionCreateRequest.getClientActorAddress());
            String sessionId = UUID.randomUUID().toString();

            //1. create session objects and actor
            SshSession sshSession = new SshSessionImpl(sessionId);
            context().system().actorOf(Props.create(new SshSessionActorCreator(sshSession, sessionCreateRequest.getClientActorAddress())),
                    Utils.generateSessionActorName(sessionId));

            //2. notify creator about ssh session creation result
            String nodeAddress = Cluster.get(context().system()).selfAddress().toString();
            String selfAddress = Utils.getSshSessionAddress(nodeAddress, sshSession.getId());
            SessionCreateResponse sessionCreateResponse = new SessionCreateResponse(sshSession.getId(), sessionCreateRequest.getClientId(), selfAddress);
            sender().tell(sessionCreateResponse, self());
        } else {
            LOG.info("onReceive: " + message.getClass().getName());
        }
    }

}
