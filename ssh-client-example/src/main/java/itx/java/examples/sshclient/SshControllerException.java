package itx.java.examples.sshclient;

/**
 * Created by gergej on 27.12.2016.
 */
public class SshControllerException extends Exception {

    public SshControllerException() {
        super();
    }

    public SshControllerException(String name) {
        super(name);
    }

    public SshControllerException(Exception e) {
        super(e);
    }

}
