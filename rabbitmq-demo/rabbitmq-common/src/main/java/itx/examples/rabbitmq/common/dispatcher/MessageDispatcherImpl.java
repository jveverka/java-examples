package itx.examples.rabbitmq.common.dispatcher;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import itx.examples.rabbitmq.common.Setup;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class MessageDispatcherImpl implements MessageDispatcher {

    private String queueName;
    private Connection connection;
    private Channel channel;

    public MessageDispatcherImpl(String queueName) {
        this.queueName = queueName;
    }

    @Override
    public void start() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(Setup.SERVER_HOST);
        connection = factory.newConnection();
        channel = connection.createChannel();
        channel.queueDeclare(queueName, false, false, false, null);
    }

    @Override
    public void sendMessage(String message) throws IOException {
        channel.basicPublish("", queueName, null, message.getBytes());
    }

    @Override
    public void shutdown() throws IOException, TimeoutException {
        channel.close();
        connection.close();
    }

}
