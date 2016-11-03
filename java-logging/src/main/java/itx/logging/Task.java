package itx.logging;

import java.util.logging.Logger;

public class Task implements Runnable {
	
	private static final Logger logger = Logger.getLogger(Task.class.getName()); 
	
	public Task() { 
	}

	@Override
	public void run() {
		logger.info("task started ...");
		logger.info("task finished.");
	}
	
	

}
