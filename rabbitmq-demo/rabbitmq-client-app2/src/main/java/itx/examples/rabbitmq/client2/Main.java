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
        final String serverHostname = Setup.resolveServerHost(args);
        LOG.info("RabbitMQ client 02 starter {}", serverHostname);
        MessageDispatcher messageDispatcher = new MessageDispatcherImpl(serverHostname, Setup.CLIENT_RESPONSE_QUEUE);
        messageDispatcher.start();

        MessageListener messageListener = new MessageListenerImpl(messageDispatcher);
        MessageReceiver messageReceiver
                = new MessageReceiverImpl(serverHostname, Setup.CLIENT_REQUEST_QUEUE, messageListener);
        messageReceiver.startBlocking();

        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                try {
                    LOG.info("Shutting down ...");
                    messageReceiver.shutdown();
                    messageDispatcher.shutdown();
                    LOG.info("bye.");
                } catch (TimeoutException | IOException e) {
                    LOG.error("shutdown error: ", e);
                }
            }
        });

    }

}
