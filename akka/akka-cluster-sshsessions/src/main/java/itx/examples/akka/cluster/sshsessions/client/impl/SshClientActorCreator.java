package itx.examples.akka.cluster.sshsessions.client.impl;

import akka.japi.Creator;
import itx.examples.akka.cluster.sshsessions.client.SshClientServiceImpl;

import java.util.concurrent.TimeUnit;

/**
 * Created by juraj on 3/18/17.
 */
public class SshClientActorCreator implements Creator<SshClientActor> {

    private SshClientServiceImpl sshClientService;
    private String clientId;
    private long timeout;
    private TimeUnit timeUnit;

    public SshClientActorCreator(SshClientServiceImpl sshClientService, String clientId, long timeout, TimeUnit timeUnit) {
        this.sshClientService = sshClientService;
        this.clientId = clientId;
        this.timeout = timeout;
        this.timeUnit = timeUnit;
    }

    @Override
    public SshClientActor create() throws Exception {
        return new SshClientActor(sshClientService, clientId, timeout, timeUnit);
    }

}
