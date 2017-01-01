package itx.java.examples.sshclient;

import java.nio.file.Path;

/**
 * Created by gergej on 27.12.2016.
 */
public interface SshClientController extends AutoCloseable {

    /**
     * connect to server
     * @throws SshControllerException
     */
    public void connect() throws SshControllerException;

    /**
     * execute command synchronously
     * @param command
     *    commmand to execute
     * @return
     *    result of executed command
     * @throws SshControllerException
     */
    public CommandResult exec(String command) throws SshControllerException;

    /**
     * copy single file on server
     * @param sourceFile
     *    local source file
     * @param destinationPath
     *    remote destination (target) directory
     * @throws SshControllerException
     */
    public void copyFile(Path sourceFile, String destinationPath) throws SshControllerException;

    /**
     * copy directory recursively on server
     * @param sourceDirectory
     *    local source directory
     * @param destinationPath
     *    remote destination (target) directory
     * @throws SshControllerException
     */
    public void copyDirectory(Path sourceDirectory, String destinationPath) throws SshControllerException;

    /**
     * disconnect from server
     */
    public void disconnect();

    /**
     * get builder
     * @return instance of builder
     */
    public static SshClientControllerBuilder builder() {
        return new SshClientControllerBuilder();
    }

}
