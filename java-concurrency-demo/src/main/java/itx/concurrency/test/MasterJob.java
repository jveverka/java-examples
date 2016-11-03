package itx.concurrency.test;

import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MasterJob implements Runnable {
	
	private static final Logger logger = Logger.getLogger(MasterJob.class.getName());
	private static final int DELAY = 1; 
	private int iterations;
	private int counter;
	private FutureResult futureResult;
	private SlaveJob slaveJob;
	
	public MasterJob(SlaveJob slaveJob, int iterations) {
		this.slaveJob = slaveJob;
		this.iterations = iterations;
		this.futureResult = new FutureResult();
		this.counter = 0;		
	}

	@Override
	public void run() {
		logger.info("MASTER started ...");
		try {
			for (int i=0; i<iterations; i++) {
				counter++;
				logger.info("MASTER in progress [" + i + "] ...");
				Thread.sleep(DELAY);
			}
			logger.info("MASTER done [" + iterations + "/" + counter + "]");
		} catch (InterruptedException e) {
			logger.log(Level.SEVERE, "", e);
		}
		logger.info("MASTER is waiting for slave ...");
		slaveJob.waitJoinStopped();
		Result result = new Result(iterations, counter, slaveJob.getIterations(), slaveJob.getCounter());
		futureResult.setResult(result);
		logger.info("MASTER finished ...");
	}
	
	public Future<Result> getResult() {
		return futureResult;
	}

}
