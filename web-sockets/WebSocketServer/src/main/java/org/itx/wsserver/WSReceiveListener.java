package org.itx.wsserver;

import java.util.logging.Logger;

import io.undertow.websockets.core.AbstractReceiveListener;
import io.undertow.websockets.core.BufferedTextMessage;
import io.undertow.websockets.core.WebSocketChannel;
import io.undertow.websockets.core.WebSockets;

public class WSReceiveListener extends AbstractReceiveListener {
	
	private final static Logger logger = Logger.getLogger(WSReceiveListener.class.getName());

    @Override
    protected void onFullTextMessage(WebSocketChannel channel, BufferedTextMessage message) {
        final String messageData = message.getData();
        for (WebSocketChannel session : channel.getPeerConnections()) {
        	//logger.info("got message: " + messageData);
            WebSockets.sendText("Pong", session, null);
        }
    }

}
