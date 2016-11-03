package itx.callable;

import java.util.concurrent.Callable;

public class Task implements Callable<Result> {
	
	private int max;
	
	public Task(int max) {
		this.max = max;
	}

	@Override
	public Result call() throws ServiceException {
		if (max%2 != 0) {
			throw new ServiceException("Task execution exception.");
		}
		try {
			int counter = 0;
			for (int i=0; i<max; i++) {
				counter++;
				Thread.sleep(10);
			}
			return new Result(counter);
		} catch (InterruptedException e) {
			throw new ServiceException(e);
		}
	}

}
