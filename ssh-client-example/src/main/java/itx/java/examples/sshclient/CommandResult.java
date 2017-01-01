package itx.java.examples.sshclient;

import java.io.ByteArrayOutputStream;

/**
 * Created by gergej on 27.12.2016.
 */
public interface CommandResult {

    public int getReturnCode();

    public ByteArrayOutputStream getOut();

    public ByteArrayOutputStream getErr();

}
