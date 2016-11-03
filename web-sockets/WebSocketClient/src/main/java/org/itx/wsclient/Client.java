package org.itx.wsclient;

import java.net.URI;
import java.util.concurrent.Future;
import java.util.logging.Logger;

public class Client {

	private final static Logger logger = Logger.getLogger(Client.class.getName());
	private final static String defautWsURL = "ws://localhost:8080/ws";
	private final static int WARMUP_MAX = 100000;
	private final static int MSG_MAX = 1000000;
	private final static String MSG_IN = "Ping";
	private final static String MSG_OUT = "Pong";

    public static void main(String [] args) {
		try {
			logger.info("Web-Socket Client started ... ");
			String ulr = defautWsURL;
			if (args != null && args.length > 0) {
				ulr = args[0];
			}
			
			logger.info("URL" + ulr);
			URI wsUri = new URI(ulr);

			logger.info("creating client endpoint ...");
			WebsocketClientEndpoint wsEndpoint = new WebsocketClientEndpoint(wsUri); 
			MessageHandlerImpl mHandler = new MessageHandlerImpl(MSG_MAX, MSG_OUT);
			wsEndpoint.setMessageHandler(mHandler);
			logger.info("warming up: " + WARMUP_MAX);
			for (int i=0; i<WARMUP_MAX; i++) {
				wsEndpoint.sendMessage(MSG_IN);
			}
			logger.info("starting measurement ...");
			Thread.sleep(3000);
			mHandler.startMeasurement();
			logger.info("measuring ...");
			
			/**/
			long startTime = System.nanoTime();
			for (int i=0; i<MSG_MAX; i++) {
				wsEndpoint.sendMessage(MSG_IN);
			}
			Future<Integer> upcommingResult = mHandler.getResult();
			Integer result = upcommingResult.get();
			long durationNano = System.nanoTime() - startTime;
			/**/
			
			logger.info("expected : " + MSG_MAX);
			logger.info("received : " + result);
			wsEndpoint.closeConnection();
			float durationMillis = durationNano / 1000000F;
			float durationSec = durationNano / 1000000000F;
			float requestDuration = durationNano / (MSG_MAX * 1000000F);
			logger.info("duration : " + durationMillis + " ms");
			logger.info("duration : " + durationSec + " s");
			logger.info("req duration : " + requestDuration + " ms");
			logger.info("req/second   : " + (MSG_MAX / durationSec));
			logger.info("done. ");
		} catch (Exception e) {
			logger.info("expected parameter is WS ULR: ws://localhost:8080/ws");
			e.printStackTrace();
		}

    }
	
}
