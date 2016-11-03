package itx.concurrency.test;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SlaveJob implements Runnable {
	
	private static final Logger logger = Logger.getLogger(SlaveJob.class.getName());
	private static final int DELAY = 1; 
	private int iterations;
	private int counter;
	private AtomicBoolean hasFinished;
	
	public SlaveJob(int iterations) {
		this.iterations = iterations;
		this.counter = 0;
		this.hasFinished = new AtomicBoolean(false);
	}

	public synchronized void waitJoinStopped() {
		if (!hasFinished.get()) {
			try {
				this.wait();
			} catch (InterruptedException e) {
			}
		}
	}

	@Override
	public void run() {
		logger.info("SLAVE started ...");
		try {
			for (int i=0; i<iterations; i++) {
				counter++;
				logger.info("SLAVE in progress [" + i + "] ...");
				Thread.sleep(DELAY);
			}
			logger.info("SLAVE done [" + iterations + "/" + counter + "]");
		} catch (InterruptedException e) {
			logger.log(Level.SEVERE, "", e);
		}
		logger.info("SLAVE finished ...");
		synchronized(this) {
			hasFinished.set(true);
			this.notify();
		}
	}
	
	public int getIterations() {
		return iterations;
	}

	public int getCounter() {
		return counter;
	}

}
