package itx.examples.rabbitmq.common.receiver;

import com.rabbitmq.client.*;
import itx.examples.rabbitmq.common.Setup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class MessageReceiverImpl implements MessageReceiver {

    final private static Logger LOG = LoggerFactory.getLogger(MessageReceiverImpl.class);

    private String queueName;
    private MessageListener messageListener;
    private Connection connection;
    private Channel channel;

    public MessageReceiverImpl(String queueName, MessageListener messageListener) {
        this.queueName = queueName;
        this.messageListener = messageListener;
    }

    @Override
    public void startBlocking() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(Setup.SERVER_HOST);
        connection = factory.newConnection();
        channel = connection.createChannel();
        channel.queueDeclare(queueName, false, false, false, null);
        LOG.info(" [*] Waiting for messages. To exit press CTRL+C");

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                String message = new String(body, "UTF-8");
                LOG.info(" [x] Received '{}'", message);
                messageListener.onMessage(message);
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
