package itx.java.examples.sshclient;

import java.io.ByteArrayOutputStream;

/**
 * Created by gergej on 27.12.2016.
 */
public class CommandResultImpl implements CommandResult {

    private int retCode;
    private ByteArrayOutputStream out;
    private ByteArrayOutputStream err;

    public CommandResultImpl(int retCode, ByteArrayOutputStream out, ByteArrayOutputStream err) {
        this.retCode = retCode;
        this.out = out;
        this.err = err;
    }

    @Override
    public int getReturnCode() {
        return retCode;
    }

    @Override
    public ByteArrayOutputStream getOut() {
        return out;
    }

    @Override
    public ByteArrayOutputStream getErr() {
        return err;
    }

}
