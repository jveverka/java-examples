package itx.examples.akka.cluster.sshsessions.dto;

import java.io.Serializable;

/**
 * Created by juraj on 3/18/17.
 */
public class SessionCreateRequest implements Serializable {

    private String clientId;
    private String clientActorAddress;

    public SessionCreateRequest(String clientId, String clientActorAddress) {
        this.clientId = clientId;
        this.clientActorAddress = clientActorAddress;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientActorAddress() {
        return clientActorAddress;
    }

}
