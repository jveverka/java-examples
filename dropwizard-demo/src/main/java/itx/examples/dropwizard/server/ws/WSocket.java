package itx.examples.dropwizard.server.ws;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.util.concurrent.ListenableFuture;
import itx.examples.dropwizard.server.dto.DataRequest;
import itx.examples.dropwizard.server.dto.DataResponse;
import itx.examples.dropwizard.server.services.DataService;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class WSocket extends WebSocketAdapter {

    private static final Logger LOG = LoggerFactory.getLogger(WSocket.class);

    private DataService dataService;
    private ObjectMapper objectMapper;
    private Session session;

    public WSocket(DataService dataService) {
        this.dataService = dataService;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public void onWebSocketConnect(Session session) {
        LOG.info("onConnect");
        this.session = session;
    }

    @Override
    public void onWebSocketClose(int statusCode, String reason) {
        LOG.info("onClose");
    }

    @Override
    public void onWebSocketError(Throwable cause) {
        LOG.info("onError");
    }

    @Override
    public void onWebSocketText(String message) {
        try {
            LOG.info("onTextMessage");
            DataRequest dataRequest = objectMapper.readValue(message, DataRequest.class);
            ListenableFuture<DataResponse> data = dataService.getData(dataRequest);
            String response = objectMapper.writeValueAsString(data.get());
            session.getRemote().sendString(response);
        } catch (Exception e) {
            LOG.error("IO Error:", e);
        }
    }

    @Override
    public void onWebSocketBinary(byte[] payload, int offset, int len) {
        LOG.info("onBinaryMessage");
        throw new UnsupportedOperationException("binary messages are not supported");
    }

}
