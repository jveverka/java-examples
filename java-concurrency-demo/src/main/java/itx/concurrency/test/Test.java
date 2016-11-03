package itx.concurrency.test;

import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Test {
	
	private static final Logger logger = Logger.getLogger(Test.class.getName());
	
	public static void main(String[] args) {
		logger.info("main started ...");
		int errorCounter = 0;
		int i;
		boolean cumulativeResult = true;
		for (i=0; i<10000; i++) {
			boolean result = test();
			cumulativeResult = cumulativeResult & result;
			logger.info("testResult: " + result + "/" + cumulativeResult);
			if (!result) {
				errorCounter++;
			}
		}
		logger.info("cumulativeResult: " + cumulativeResult);
		logger.info("main[" + i + "/" + errorCounter + "] done.");
	}

	public static boolean test() {
		logger.info("#*********************************************");
		logger.info("test started ...");
		Random random = new Random();
		int masterCycles = 1 +  random.nextInt(10);
		int slaveCycles = 1 +  random.nextInt(10);
		logger.info("Master cycles: " + masterCycles);
		logger.info("Slave  cycles: " + slaveCycles);
		SlaveJob slaveJob = new SlaveJob(slaveCycles);
		MasterJob masterJob = new MasterJob(slaveJob, masterCycles);
		Thread masterThread = new Thread(masterJob);
		Thread slaveThread = new Thread(slaveJob);
		slaveThread.start();
		masterThread.start();
		logger.info("getting result future ...");
		Future<Result> upcommingResult = masterJob.getResult();
		try {
			logger.info("waiting for result ...");
			Result result = upcommingResult.get();
			if (result.isCorrect()) {
				logger.info("OKOKO: true");
			} else {	
				logger.info("ERROR: " + result.toString());
			}
			logger.info("done.");
			return result.isCorrect();
		} catch (InterruptedException e) {
			logger.log(Level.SEVERE, "", e);
		} catch (ExecutionException e) {
			logger.log(Level.SEVERE, "", e);
		}
		logger.info("#!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		return false;
	}

}
