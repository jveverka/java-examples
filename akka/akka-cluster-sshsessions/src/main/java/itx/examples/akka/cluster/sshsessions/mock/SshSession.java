package itx.examples.akka.cluster.sshsessions.mock;

import itx.examples.akka.cluster.sshsessions.dto.SessionDataRequest;
import itx.examples.akka.cluster.sshsessions.dto.SessionDataResponse;

/**
 * Created by juraj on 3/18/17.
 */
public interface SshSession extends AutoCloseable {

    public String getId();

    public SessionDataResponse processData(SessionDataRequest sessionDataRequest);

}
