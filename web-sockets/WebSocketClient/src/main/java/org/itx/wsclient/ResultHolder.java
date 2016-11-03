package org.itx.wsclient;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ResultHolder implements Future<Integer>{

	private Integer result;
	
	public ResultHolder() {
		this.result = null;
	}

	@Override
	public boolean cancel(boolean mayInterruptIfRunning) {
		return false;
	}

	@Override
	public boolean isCancelled() {
		return false;
	}

	@Override
	public boolean isDone() {
		return (result != null);
	}

	@Override
	public Integer get() throws InterruptedException, ExecutionException {
		synchronized (this) {
			if (result != null) {
				return new Integer(result);
			}
			this.wait();
			return new Integer(result);
		}
	}

	@Override
	public Integer get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
		synchronized (this) {
			if (result != null) {
				return new Integer(result);
			}
			this.wait(unit.toMillis(timeout));
			return new Integer(result);
		}
	}
	
	public void setResut(int result) {
		synchronized (this) {
			this.result = new Integer(result);
			this.notifyAll();
		}
	}

}
