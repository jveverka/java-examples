package itx.examples.akka.cluster.sshsessions.sessions;

import itx.examples.akka.cluster.sshsessions.client.SshClientSession;
import itx.examples.akka.cluster.sshsessions.client.SshClientSessionListener;

/**
 * Created by gergej on 25.3.2017.
 */
public interface SshSessionFactory {

    SshClientSession createSshSession(String sessionId, SshClientSessionListener sshClientSessionListener);

}
