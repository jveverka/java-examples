package itx.examples.rabbitmq.common.receiver;

import itx.examples.rabbitmq.common.MessageData;

public interface MessageListener {

    void onMessage(MessageData message);

}
