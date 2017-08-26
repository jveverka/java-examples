package itx.examples.rabbitmq.common.receiver;

import com.rabbitmq.client.*;
import itx.examples.rabbitmq.common.MessageData;
import itx.examples.rabbitmq.common.MessageUtils;
import itx.examples.rabbitmq.common.Setup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class MessageReceiverImpl implements MessageReceiver {

    final private static Logger LOG = LoggerFactory.getLogger(MessageReceiverImpl.class);

    private String serverHostName;
    private String queueName;
    private MessageListener messageListener;
    private Connection connection;
    private Channel channel;

    public MessageReceiverImpl(String serverHostName, String queueName, MessageListener messageListener) {
        this.serverHostName = serverHostName;
        this.queueName = queueName;
        this.messageListener = messageListener;
    }

    @Override
    public void startBlocking() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(serverHostName);
        connection = factory.newConnection();
        channel = connection.createChannel();
        channel.queueDeclare(queueName, false, false, false, null);
        LOG.info(" [*] Waiting for messages. To exit press CTRL+C");

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                MessageData messageData = MessageUtils.fromByteArray(body);
                LOG.trace(" [x] Received '{}'", messageData);
                messageListener.onMessage(messageData);
            }
        };
        channel.basicConsume(queueName, true, consumer);
    }

    @Override
    public void start() throws IOException, TimeoutException {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    startBlocking();
                } catch (TimeoutException | IOException e) {
                    LOG.error("Receiver start error: ", e);
                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

    @Override
    public void shutdown() throws IOException, TimeoutException {
        channel.close();
        connection.close();
    }

}
