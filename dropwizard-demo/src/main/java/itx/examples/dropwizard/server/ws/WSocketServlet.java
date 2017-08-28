package itx.examples.dropwizard.server.ws;

import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

public class WSocketServlet extends WebSocketServlet {

    private WSCreator wsCreator;

    public WSocketServlet(WSCreator wsCreator) {
        this.wsCreator = wsCreator;
    }

    @Override
    public void configure(WebSocketServletFactory factory) {
        factory.setCreator(wsCreator);
    }

}
