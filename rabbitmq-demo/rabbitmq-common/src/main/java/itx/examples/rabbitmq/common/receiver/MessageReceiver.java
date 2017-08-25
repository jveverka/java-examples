package itx.examples.rabbitmq.common.receiver;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public interface MessageReceiver {

    public void startBlocking() throws IOException, TimeoutException;

    public void start() throws IOException, TimeoutException;

    public void shutdown() throws IOException, TimeoutException;

}
