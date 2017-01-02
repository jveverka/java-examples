package itx.java.examples.sshclient;

import org.apache.sshd.client.SshClient;
import org.apache.sshd.client.channel.ClientChannel;
import org.apache.sshd.client.channel.ClientChannelEvent;
import org.apache.sshd.client.scp.ScpClient;
import org.apache.sshd.client.session.ClientSession;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.EnumSet;

/**
 * Created by gergej on 27.12.2016.
 */
public class SshClientControllerImpl implements SshClientController {

    private String hostName;
    private int port;
    private String userName;
    private String password;
    private SshClient client;
    private ClientSession session;


    public SshClientControllerImpl(String hostName, int port, String userName, String password) {
        this.hostName = hostName;
        this.port = port;
        this.userName = userName;
        this.password = password;
    }

    public static SshClientControllerBuilder builder() {
        return new SshClientControllerBuilder();
    }

    @Override
    public void connect() throws SshControllerException {
        try {
            client = SshClient.setUpDefaultClient();
            client.start();
            session = client.connect(userName, hostName, port).verify().getSession();
            session.addPasswordIdentity(password);
            session.auth().await();
        } catch (IOException e) {
            throw new SshControllerException(e);
        }
    }

    @Override
    public CommandResult exec(String command) throws SshControllerException {
        try {
            ClientChannel execChannel = session.createExecChannel(command);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ByteArrayOutputStream err = new ByteArrayOutputStream();
            execChannel.setOut(out);
            execChannel.setErr(err);
            execChannel.open().await(6000);
            execChannel.waitFor(EnumSet.of(ClientChannelEvent.CLOSED), 6000L);
            execChannel.close();
            return new CommandResultImpl(execChannel.getExitStatus(), out, err);
        } catch (IOException e) {
            throw new SshControllerException(e);
        }
    }

    @Override
    public void copyFile(Path sourceFile, String destinationPath) throws SshControllerException {
        try {
            ScpClient scpClient = session.createScpClient();
            scpClient.upload(sourceFile, destinationPath, ScpClient.Option.PreserveAttributes, ScpClient.Option.TargetIsDirectory);
        } catch (IOException e) {
            throw new SshControllerException(e);
        }
    }

    @Override
    public void copyDirectory(Path sourceDirectory, String destinationPath) throws SshControllerException {
        try {
            ScpClient scpClient = session.createScpClient();
            scpClient.upload(sourceDirectory, destinationPath, ScpClient.Option.PreserveAttributes, ScpClient.Option.TargetIsDirectory, ScpClient.Option.Recursive);
        } catch (IOException e) {
            throw new SshControllerException(e);
        }
    }

    @Override
    public void disconnect() {
        try {
            close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() throws Exception {
        session.close();
        client.close();
    }

}
