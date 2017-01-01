package itx.java.examples.sshclient;

/**
 * Created by gergej on 27.12.2016.
 */
public class SshClientControllerBuilder {

    private String hostName;
    private int port;
    private String userName;
    private String password;

    public SshClientControllerBuilder() {
        this.port = 22;
    }

    public SshClientControllerBuilder setHostName(String hostName) {
        this.hostName = hostName;
        return this;
    }

    public SshClientControllerBuilder setPort(int port) {
        this.port = port;
        return this;
    }

    public SshClientControllerBuilder setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public SshClientControllerBuilder setPassword(String password) {
        this.password = password;
        return this;
    }

    public SshClientController build() {
        return new SshClientControllerImpl(hostName, port, userName, password);
    }

}
