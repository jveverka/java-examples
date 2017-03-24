package itx.examples.akka.cluster.sshsessions.cluster;

/**
 * Created by juraj on 3/24/17.
 */
public class SessionInfo {

    private String clientSessionActorAddress;
    private String clientId;
    private String sshSessionActorAddress;
    private String sshSessionId;

    public SessionInfo(String clientSessionActorAddress, String clientId, String sshSessionActorAddress, String sshSessionId) {
        this.clientSessionActorAddress = clientSessionActorAddress;
        this.clientId = clientId;
        this.sshSessionActorAddress = sshSessionActorAddress;
        this.sshSessionId = sshSessionId;
    }

    public String getClientSessionActorAddress() {
        return clientSessionActorAddress;
    }

    public String getClientId() {
        return clientId;
    }

    public String getSshSessionActorAddress() {
        return sshSessionActorAddress;
    }

    public String getSshSessionId() {
        return sshSessionId;
    }
}
