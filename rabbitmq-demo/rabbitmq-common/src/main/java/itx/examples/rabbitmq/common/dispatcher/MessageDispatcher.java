package itx.examples.rabbitmq.common.dispatcher;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public interface MessageDispatcher {

    void start() throws IOException, TimeoutException;

    void sendMessage(String message) throws IOException;

    void shutdown() throws IOException, TimeoutException;

}
