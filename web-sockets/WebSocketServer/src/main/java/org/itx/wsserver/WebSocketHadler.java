package org.itx.wsserver;

import io.undertow.websockets.WebSocketConnectionCallback;
import io.undertow.websockets.core.WebSocketChannel;
import io.undertow.websockets.spi.WebSocketHttpExchange;

public class WebSocketHadler implements WebSocketConnectionCallback {
	
	private WSReceiveListener receiveListener;
	
	public WebSocketHadler(WSReceiveListener receiveListener) {
		this.receiveListener = receiveListener;
	}

	@Override
	public void onConnect(WebSocketHttpExchange exchange, WebSocketChannel channel) {
		channel.getReceiveSetter().set(receiveListener);
		channel.resumeReceives();
	}

}
