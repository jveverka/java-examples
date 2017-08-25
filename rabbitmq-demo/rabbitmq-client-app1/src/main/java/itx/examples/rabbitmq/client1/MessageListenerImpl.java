package itx.examples.rabbitmq.client1;

import itx.examples.rabbitmq.common.MessageData;
import itx.examples.rabbitmq.common.receiver.MessageListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

public class MessageListenerImpl implements MessageListener {

    final private static Logger LOG = LoggerFactory.getLogger(MessageListenerImpl.class);

    private MessageData[] messages;
    private CountDownLatch countDownLatch;

    @Override
    public void onMessage(MessageData message) {
        LOG.trace("received: {}" , message);
        messages[((int)message.getContextId())] = message;
        countDownLatch.countDown();
    }

    public void await() throws InterruptedException {
        countDownLatch.await();
    }

    public void init(MessageData[] messages) {
        this.messages = messages;
        this.countDownLatch = new CountDownLatch(messages.length);
    }

}
