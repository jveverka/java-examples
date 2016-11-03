package org.itx.wsserver;

import io.undertow.Undertow;
import io.undertow.server.handlers.resource.ClassPathResourceManager;

import static io.undertow.Handlers.path;
import static io.undertow.Handlers.resource;
import static io.undertow.Handlers.websocket;

public class Server {
	
	//private static final String DefaultHost = "localhost";
	//private static final int DefaultPort = 8080;
	
	public static void main(String[] args) {
		
        WSReceiveListener receiveListener = new WSReceiveListener();
        WebSocketHadler wsHandler = new WebSocketHadler(receiveListener);
        
        Undertow server = Undertow.builder()
                .addHttpListener(8080, "0.0.0.0")
                .setHandler(path()
                    .addPrefixPath("/ws", websocket(wsHandler))
                    .addPrefixPath("/", resource(new ClassPathResourceManager(Server.class.getClassLoader(), Server.class.getPackage()))
                    .addWelcomeFiles("index.html")))
                .build();

        server.start();		
	}

}
