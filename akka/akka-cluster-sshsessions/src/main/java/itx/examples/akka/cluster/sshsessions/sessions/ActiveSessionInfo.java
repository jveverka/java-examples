package itx.examples.akka.cluster.sshsessions.sessions;

import akka.actor.ActorRef;

/**
 * Created by gergej on 25.3.2017.
 */
public class ActiveSessionInfo {

    private String sessionId;
    private ActorRef sessionActor;
    private String clientId;
    private String clientSessionActorAddress;

    public ActiveSessionInfo(String sessionId, ActorRef sessionActor,
                             String clientId, String clientSessionActorAddress) {
        this.sessionId = sessionId;
        this.sessionActor = sessionActor;
        this.clientId = clientId;
        this.clientSessionActorAddress = clientSessionActorAddress;
    }

    public String getSessionId() {
        return sessionId;
    }

    public ActorRef getSessionActor() {
        return sessionActor;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientSessionActorAddress() {
        return clientSessionActorAddress;
    }

}
