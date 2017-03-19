package itx.examples.akka.cluster.sshsessions.mock;

import itx.examples.akka.cluster.sshsessions.dto.SessionDataRequest;
import itx.examples.akka.cluster.sshsessions.dto.SessionDataResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by juraj on 3/18/17.
 */
public class SshSessionImpl implements SshSession {

    private static final Logger LOG = LoggerFactory.getLogger(SshSessionImpl.class);

    private String id;

    public SshSessionImpl(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public SessionDataResponse processData(SessionDataRequest sessionDataRequest) {
        LOG.info("processData: " + sessionDataRequest.getData());
        return new SessionDataResponse(sessionDataRequest.getData().toUpperCase());
    }

    @Override
    public void close() throws Exception {
        LOG.info("close: " + id);
    }

}
