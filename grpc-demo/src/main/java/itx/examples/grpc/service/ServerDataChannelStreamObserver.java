package itx.examples.grpc.service;

import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerDataChannelStreamObserver<V extends DataMessage> implements StreamObserver<V> {

    final private static Logger LOG = LoggerFactory.getLogger(ServerDataChannelStreamObserver.class);
    final private static String CLOSE_COMMAND = "CLOSE";

    private StreamObserver<DataMessage> responseObserver;

    public ServerDataChannelStreamObserver(StreamObserver<DataMessage> responseObserver) {
        LOG.info("server observer started.");
        this.responseObserver = responseObserver;
    }

    @Override
    public void onNext(V value) {
        LOG.debug("onNext: {} {}", value.getIndex(), value.getMessage());
        DataMessage dataMessage = DataMessage.newBuilder().mergeFrom(value).build();
        responseObserver.onNext(dataMessage);
        if (CLOSE_COMMAND.equals(value.getMessage())) {
            responseObserver.onCompleted();
            LOG.info("closing server observer ...");
        }
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
