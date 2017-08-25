package itx.examples.rabbitmq.common.receiver;

public interface MessageListener {

    void onMessage(String message);

}
