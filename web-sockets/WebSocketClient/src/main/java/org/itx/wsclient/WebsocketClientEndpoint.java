package org.itx.wsclient;


import java.io.IOException;
import java.net.URI;

import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

@ClientEndpoint
public class WebsocketClientEndpoint {
    private Session userSession = null;
    private MessageHandler messageHandler;
    private WebSocketContainer container;

    public WebsocketClientEndpoint(URI endpointURI) {
        try {
            container = ContainerProvider.getWebSocketContainer();
            container.connectToServer(this, endpointURI);
        } catch (IOException e) {
           throw new RuntimeException(e);
        } catch (DeploymentException e) {
        	throw new RuntimeException(e);
		}
    }

    /**
     * Callback hook for Connection open events.
     *
     * @param userSession the userSession which is opened.
     */
    @OnOpen
    public void onOpen(Session userSession) {
        this.userSession = userSession;
    }

    /**
     * Callback hook for Connection close events.
     *
     * @param userSession the userSession which is getting closed.
     * @param reason the reason for connection close
     */
    @OnClose
    public void onClose(Session userSession, CloseReason reason) {
        this.userSession = null;
    }

    /**
     * Callback hook for Message Events. This method will be invoked when a client send a message.
     *
     * @param message The text message
     */
    @OnMessage
    public void onMessage(String message) {
        if (this.messageHandler != null) {
            this.messageHandler.handleMessage(message);
        }
    }

    /**
     * register message handler
     *
     * @param message
     */
    public void setMessageHandler(MessageHandler msgHandler) {
        this.messageHandler = msgHandler;
    }

    /**
     * Send a message.
     *
     * @param user
     * @param message
     */
    public void sendMessage(String message) {
        this.userSession.getAsyncRemote().sendText(message);
    }
    
    public void closeConnection() {
    	try {
			this.userSession.close(new CloseReason(CloseReason.CloseCodes.NORMAL_CLOSURE, "bye !"));
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

}
