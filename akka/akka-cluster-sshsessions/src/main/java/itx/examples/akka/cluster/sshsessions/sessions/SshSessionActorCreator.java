package itx.examples.akka.cluster.sshsessions.sessions;

import akka.japi.Creator;
import itx.examples.akka.cluster.sshsessions.mock.SshSession;

/**
 * Created by juraj on 3/18/17.
 */
public class SshSessionActorCreator implements Creator<SshSessionActor> {

    private SshSession sshSession;
    private String clientActorAddress;

    public SshSessionActorCreator(SshSession sshSession, String clientActorAddress) {
        this.sshSession = sshSession;
        this.clientActorAddress = clientActorAddress;
    }

    @Override
    public SshSessionActor create() throws Exception {
        return new SshSessionActor(sshSession, clientActorAddress);
    }

}
