package itx.logging;

import java.io.IOException;
import java.util.logging.Logger;

public class Main {

	private static final Logger logger = Logger.getLogger(Main.class.getName()); 

	public static void main(String[] args) throws SecurityException, IOException {
		logger.info("#***************");
		logger.info("main started ...");
		Task task = new Task();
		task.run();
		logger.info("main finished.");
		logger.info("");
	}

}
