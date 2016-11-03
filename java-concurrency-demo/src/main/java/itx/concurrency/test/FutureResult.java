package itx.concurrency.test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class FutureResult implements Future<Result> {
	
	private Result result;
	private CountDownLatch latch;
	
	public FutureResult() {
		result = null;
		latch = new CountDownLatch(1);
	}
	
	public void setResult(Result result) {
		this.result = result;
		latch.countDown();
	}

	@Override
	public boolean cancel(boolean arg0) {
		return false;
	}

	@Override
	public Result get() throws InterruptedException, ExecutionException {
		latch.await();
		return result;
	}

	@Override
	public Result get(long timeout, TimeUnit timeUnit) throws InterruptedException, ExecutionException, TimeoutException {
		latch.await(timeout, timeUnit);
		return result;
	}

	@Override
	public boolean isCancelled() {
		return false;
	}

	@Override
	public boolean isDone() {
		return (result != null);
	}

}
