package itx.examples.grpc.service.test;

import io.grpc.stub.StreamObserver;
import itx.examples.grpc.service.DataMessage;
import itx.examples.grpc.service.SimpleClient;
import itx.examples.grpc.service.SimpleServer;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class RpcBidirectionalTest {

    final private static Logger LOG = LoggerFactory.getLogger(RpcBidirectionalTest.class);
    private static final int SERVER_PORT = 50051;
    private static final String SERVER_HOST = "localhost";

    @Test
    public void rpcBidirectionalTest() throws IOException, InterruptedException {
        SimpleServer server = new SimpleServer(SERVER_PORT);
        server.start();
        SimpleClient client = new SimpleClient(SERVER_HOST, SERVER_PORT);

        TestMessageChannelStreamObserver testMessageChannelStreamObserver = new TestMessageChannelStreamObserver<DataMessage>();
        StreamObserver<DataMessage> messageChannel = client.getMessageChannel(testMessageChannelStreamObserver);

        DataMessage dataMessage = DataMessage.newBuilder().setMessage("from client").build();
        messageChannel.onNext(dataMessage);
        messageChannel.onCompleted();

        testMessageChannelStreamObserver.await();

        DataMessage lastMessage = testMessageChannelStreamObserver.getLastMessage();
        Assert.assertNotNull(lastMessage);
        Assert.assertEquals("from server", lastMessage.getMessage());

        server.stop();
        server.blockUntilShutdown();
    }

}
