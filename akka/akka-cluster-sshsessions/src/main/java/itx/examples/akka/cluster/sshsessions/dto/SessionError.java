package itx.examples.akka.cluster.sshsessions.dto;

import java.io.Serializable;

/**
 * Created by juraj on 3/18/17.
 */
public class SessionError implements Serializable {

    private String error;

    public SessionError(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

}
