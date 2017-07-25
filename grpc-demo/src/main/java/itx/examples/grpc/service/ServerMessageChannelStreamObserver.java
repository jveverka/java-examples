package itx.examples.grpc.service;

import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerMessageChannelStreamObserver<V extends DataMessage> implements StreamObserver<V> {

    final private static Logger LOG = LoggerFactory.getLogger(ServerMessageChannelStreamObserver.class);

    private StreamObserver<DataMessage> responseObserver;

    public ServerMessageChannelStreamObserver(StreamObserver<DataMessage> responseObserver) {
        this.responseObserver = responseObserver;
    }

    @Override
    public void onNext(V value) {
        LOG.info("onNext: {}", value.getMessage());
        DataMessage dataMessage = DataMessage.newBuilder().setMessage("from server").build();
        responseObserver.onNext(dataMessage);
        responseObserver.onCompleted();
    }

    @Override
    public void onError(Throwable t) {
        LOG.info("onError");
    }

    @Override
    public void onCompleted() {
        LOG.info("onCompleted");
    }

}
