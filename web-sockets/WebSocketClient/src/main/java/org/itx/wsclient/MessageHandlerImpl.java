package org.itx.wsclient;

import java.util.concurrent.Future;
import java.util.logging.Logger;

public class MessageHandlerImpl implements MessageHandler {
	
	private final static Logger logger = Logger.getLogger(MessageHandlerImpl.class.getName());
	
	private int msgReceivedOK;
	private int msgReceivedCounter;
	private int msgMax;
	private String msgOut;
	private ResultHolder resultHolder;
	private boolean measurementStarted;

	public MessageHandlerImpl(int msgMax, String msgOut) {
		this.msgReceivedOK = 0;
		this.msgReceivedCounter = 0;
		this.msgMax = msgMax;
		this.msgOut = msgOut;
		this.resultHolder = new ResultHolder();
		this.measurementStarted = false;
	}
	
	public void startMeasurement() {
		measurementStarted = true;
	}

	@Override
	public void handleMessage(String message) {
		//logger.info("got message: " + message);
		if (!measurementStarted) {
			return;
		}
		if (message != null && message.equals(msgOut)) {
			msgReceivedOK++;
		}
		msgReceivedCounter++;
		if (msgReceivedCounter >= msgMax) {
			resultHolder.setResut(msgReceivedOK);
		}
	}
	
	public Future<Integer> getResult() {
		return resultHolder;
	}

}
