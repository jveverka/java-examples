package itx.examples.akka.cluster.sshsessions.dto;

import java.io.Serializable;

/**
 * Created by juraj on 3/18/17.
 */
public class SessionCloseResponse implements Serializable {

    private String sessionId;
    private String clientActorAddress;

    public SessionCloseResponse(String sessionId, String clientActorAddress) {
        this.sessionId = sessionId;
        this.clientActorAddress = clientActorAddress;
    }

    public String getSessionId() {
        return sessionId;
    }

    public String getClientActorAddress() {
        return clientActorAddress;
    }

}
