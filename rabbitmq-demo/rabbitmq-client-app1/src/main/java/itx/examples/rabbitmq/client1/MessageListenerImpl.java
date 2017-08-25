package itx.examples.rabbitmq.client1;

import itx.examples.rabbitmq.common.receiver.MessageListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageListenerImpl implements MessageListener {

    final private static Logger LOG = LoggerFactory.getLogger(MessageListenerImpl.class);

    @Override
    public void onMessage(String message) {
        LOG.info("received: {}" , message);
    }

}
