package itx.callable;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Service {
	
	private ExecutorService pool;
	
	public Service() {
		pool = Executors.newFixedThreadPool(4);
	}
	
	public Result getResult(int max) throws ServiceException {
		Task task = new Task(max);
		return task.call();
	}

	public Future<Result> getFutureResult(int max) {
		Task task = new Task(max);
		return pool.submit(task);
	}

}
