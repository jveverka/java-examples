package itx.examples.rabbitmq.common.dispatcher;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import itx.examples.rabbitmq.common.MessageData;
import itx.examples.rabbitmq.common.MessageUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class MessageDispatcherImpl implements MessageDispatcher {

    private String serverHostName;
    private String queueName;
    private Connection connection;
    private Channel channel;

    public MessageDispatcherImpl(String serverHostName, String queueName) {
        this.serverHostName = serverHostName;
        this.queueName = queueName;
    }

    @Override
    public void start() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(serverHostName);
        connection = factory.newConnection();
        channel = connection.createChannel();
        channel.queueDeclare(queueName, false, false, false, null);
    }

    @Override
    public void sendMessage(MessageData message) throws IOException {
        channel.basicPublish("", queueName, null, MessageUtils.getBytes(message));
    }

    @Override
    public void shutdown() throws IOException, TimeoutException {
        channel.close();
        connection.close();
    }

}
