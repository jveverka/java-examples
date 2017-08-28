package itx.examples.dropwizard.server.ws;

import itx.examples.dropwizard.server.services.DataService;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeRequest;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeResponse;
import org.eclipse.jetty.websocket.servlet.WebSocketCreator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WSCreator implements WebSocketCreator {

    private static final Logger LOG = LoggerFactory.getLogger(WSCreator.class);

    private DataService dataService;

    public WSCreator(DataService dataService) {
        this.dataService = dataService;
    }

    @Override
    public Object createWebSocket(ServletUpgradeRequest req, ServletUpgradeResponse resp) {
        LOG.info("createWebSocket");
        return new WSocket(dataService);
    }
}
