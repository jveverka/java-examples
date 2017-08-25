package itx.examples.rabbitmq.client2;

import itx.examples.rabbitmq.common.MessageData;
import itx.examples.rabbitmq.common.dispatcher.MessageDispatcher;
import itx.examples.rabbitmq.common.receiver.MessageListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class MessageListenerImpl implements MessageListener {

    final private static Logger LOG = LoggerFactory.getLogger(MessageListenerImpl.class);

    private MessageDispatcher messageDispatcher;

    public MessageListenerImpl(MessageDispatcher messageDispatcher) {
        this.messageDispatcher = messageDispatcher;
    }

    @Override
    public void onMessage(MessageData message) {
        try {
            messageDispatcher.sendMessage(new MessageData(message));
        } catch (IOException e) {
            LOG.error("Send message failed: ", e);
        }
    }

}
