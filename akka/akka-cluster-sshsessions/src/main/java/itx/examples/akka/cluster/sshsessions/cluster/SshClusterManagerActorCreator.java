package itx.examples.akka.cluster.sshsessions.cluster;

import akka.japi.Creator;

/**
 * Created by juraj on 3/18/17.
 */
public class SshClusterManagerActorCreator implements Creator<SshClusterManagerActor> {

    private SshClusterManager sshClusterManager;

    public SshClusterManagerActorCreator(SshClusterManager sshClusterManager) {
        this.sshClusterManager = sshClusterManager;
    }

    @Override
    public SshClusterManagerActor create() throws Exception {
        return new SshClusterManagerActor(sshClusterManager);
    }

}
