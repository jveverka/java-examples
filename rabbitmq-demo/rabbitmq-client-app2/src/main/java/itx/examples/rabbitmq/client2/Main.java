package itx.examples.rabbitmq.client2;

import itx.examples.rabbitmq.common.Setup;
import itx.examples.rabbitmq.common.dispatcher.MessageDispatcher;
import itx.examples.rabbitmq.common.dispatcher.MessageDispatcherImpl;
import itx.examples.rabbitmq.common.receiver.MessageListener;
import itx.examples.rabbitmq.common.receiver.MessageReceiver;
import itx.examples.rabbitmq.common.receiver.MessageReceiverImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Main {

    final private static Logger LOG = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws IOException, TimeoutException {
        LOG.info("rabbitMQ client 02 starter");
        MessageDispatcher messageDispatcher = new MessageDispatcherImpl(Setup.CLIENT_RESPONSE_QUEUE);
        messageDispatcher.start();

        MessageListener messageListener = new MessageListenerImpl(messageDispatcher);
        MessageReceiver messageReceiver = new MessageReceiverImpl(Setup.CLIENT_REQUEST_QUEUE, messageListener);
        messageReceiver.startBlocking();

        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                try {
                    messageReceiver.shutdown();
                    messageDispatcher.shutdown();
                } catch (TimeoutException | IOException e) {
                    LOG.error("shutdown error: ", e);
                }
            }
        });

    }

}
