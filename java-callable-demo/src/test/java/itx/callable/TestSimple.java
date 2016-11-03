package itx.callable;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.testng.Assert;
import org.testng.annotations.Test;

public class TestSimple {
	
	@Test
	public void testCallableSyncSuccess() {
		try {
			Service service = new Service();
			Result result = service.getResult(100);
			Assert.assertNotNull(result);
			Assert.assertTrue(result.getCounter() == 100);
		} catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void testCallableAsyncSuccess() {
		try {
			Service service = new Service();
			Future<Result> upcommingResult = service.getFutureResult(100);
			Assert.assertNotNull(upcommingResult);
			Result result = upcommingResult.get();
			Assert.assertNotNull(result);
			Assert.assertTrue(result.getCounter() == 100);
		} catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void testCallableSyncFail() {
		try {
			Service service = new Service();
			Result result = service.getResult(101);
			Assert.fail();
		} catch (ServiceException e ) {
			//expected exception
		} catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void testCallableAsyncFail() {
		try {
			Service service = new Service();
			Future<Result> upcommingResult = service.getFutureResult(101);
			Assert.assertNotNull(upcommingResult);
			Result result = upcommingResult.get();
			Assert.fail();
		} catch (ExecutionException e ) {
			//expected exception
		} catch (Exception e) {
			Assert.fail();
		}
	}

}
