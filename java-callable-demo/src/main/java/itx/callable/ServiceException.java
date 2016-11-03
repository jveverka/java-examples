package itx.callable;

public class ServiceException extends Exception {
	
	public ServiceException(String message) {
		super(message);
	}

	public ServiceException(Exception e) {
		super(e);
	}

}
