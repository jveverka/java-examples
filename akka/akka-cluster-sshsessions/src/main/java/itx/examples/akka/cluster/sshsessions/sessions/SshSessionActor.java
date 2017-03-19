package itx.examples.akka.cluster.sshsessions.sessions;

import akka.actor.PoisonPill;
import akka.actor.UntypedActor;
import akka.cluster.Cluster;
import itx.examples.akka.cluster.sshsessions.dto.*;
import itx.examples.akka.cluster.sshsessions.mock.SshSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by juraj on 3/18/17.
 */
public class SshSessionActor extends UntypedActor {

    private static final Logger LOG = LoggerFactory.getLogger(SshSessionActor.class);

    private SshSession sshSession;
    private String clientActorAddress;

    public SshSessionActor(SshSession sshSession, String clientActorAddress) {
        this.sshSession = sshSession;
        this.clientActorAddress = clientActorAddress;
    }

    @Override
    public void preStart() {
    }

    @Override
    public void onReceive(Object message) throws Throwable {
        if (message instanceof SessionDataRequest) {
            SessionDataRequest sessionDataRequest = (SessionDataRequest) message;
            SessionDataResponse sessionDataResponse = sshSession.processData(sessionDataRequest);
            context().sender().tell(sessionDataResponse, self());
        } else if (message instanceof SessionPingMessage) {
            String memberAddress = Cluster.get(context().system()).selfAddress().toString();
            context().sender().tell(new SessionPongMessage(memberAddress, sshSession.getId()), self());
        } else if (message instanceof SessionDataRequest) {
            SessionDataRequest sessionDataRequest = (SessionDataRequest)message;
            SessionDataResponse sessionDataResponse = sshSession.processData(sessionDataRequest);
            sender().tell(sessionDataResponse, self());
        } else if (message instanceof SessionCloseRequest) {
            sshSession.close();
            SessionCloseResponse sessionCloseResponse = new SessionCloseResponse(sshSession.getId(), clientActorAddress);
            sender().tell(sessionCloseResponse, null);
            self().tell(PoisonPill.getInstance(), self());
        } else {
            LOG.info("onReceive: " + message.getClass().getName());
            context().sender().tell(new SessionError("unsupported message"), self());
        }
    }

    @Override
    public void postStop() {
        LOG.info("ssh session actor: bye");
    }

}
