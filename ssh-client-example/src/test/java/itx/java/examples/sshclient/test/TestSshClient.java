package itx.java.examples.sshclient.test;

import itx.java.examples.sshclient.CommandResult;
import itx.java.examples.sshclient.SshClientController;
import itx.java.examples.sshclient.SshControllerException;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.nio.file.Paths;

/**
 * Created by gergej on 27.12.2016.
 */
public class TestSshClient {

    @Test
    public void testSshClient() throws SshControllerException {
        SshClientController sshClientController = SshClientController.builder()
                .setHostName("192.168.56.101")
                .setPort(22)
                .setUserName("root")
                .setPassword("gergej")
                .build();

        sshClientController.connect();
        sshClientController.disconnect();

        sshClientController.connect();
        CommandResult result = sshClientController.exec("ls -la");
        Assert.assertNotNull(result);
        Assert.assertTrue(result.getReturnCode() == 0);
        sshClientController.disconnect();
    }

    @Test
    public void testSshCommands() throws SshControllerException {
        SshClientController sshClientController = SshClientController.builder()
                .setHostName("192.168.56.101")
                .setPort(22)
                .setUserName("root")
                .setPassword("gergej")
                .build();

        sshClientController.connect();

        CommandResult result = sshClientController.exec("ls -la");
        Assert.assertNotNull(result);
        Assert.assertTrue(result.getReturnCode() == 0);

        result = sshClientController.exec("not-existing");
        Assert.assertNotNull(result);
        Assert.assertTrue(result.getReturnCode() != 0);

        sshClientController.disconnect();
    }

    @Test
    public void testScpFile() throws SshControllerException {
        SshClientController sshClientController = SshClientController.builder()
                .setHostName("192.168.56.101")
                .setPort(22)
                .setUserName("root")
                .setPassword("gergej")
                .build();

        sshClientController.connect();
        sshClientController.copyFile(Paths.get("/tmp/testFile.txt"), "/tmp/");
        sshClientController.disconnect();
    }

    @Test
    public void testScpDirectory() throws SshControllerException {
        SshClientController sshClientController = SshClientController.builder()
                .setHostName("192.168.56.101")
                .setPort(22)
                .setUserName("root")
                .setPassword("gergej")
                .build();

        sshClientController.connect();
        sshClientController.copyDirectory(Paths.get("/tmp/testdata"), "/tmp/");
        sshClientController.disconnect();
    }

}