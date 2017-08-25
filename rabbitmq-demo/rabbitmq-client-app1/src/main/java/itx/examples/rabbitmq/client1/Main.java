package itx.examples.rabbitmq.client1;

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
        MessageDispatcher messageDispatcher = new MessageDispatcherImpl(Setup.CLIENT_OUTBOUND_QUEUE);
        messageDispatcher.start();

        MessageListener messageListener = new MessageListenerImpl();
        MessageReceiver messageReceiver = new MessageReceiverImpl(Setup.CLIENT_INBOUND_QUEUE, messageListener);
        messageReceiver.start();

        for (int i=0; i<10; i++) {
            String message = "message-" + i;
            LOG.info(" [i] Sent '{}'", message );
            messageDispatcher.sendMessage(message);
        }
        messageDispatcher.shutdown();
        messageReceiver.shutdown();
    }

}
