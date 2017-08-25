package itx.examples.rabbitmq.common.dispatcher;

import itx.examples.rabbitmq.common.MessageData;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public interface MessageDispatcher {

    void start() throws IOException, TimeoutException;

    void sendMessage(MessageData message) throws IOException;

    void shutdown() throws IOException, TimeoutException;

}
