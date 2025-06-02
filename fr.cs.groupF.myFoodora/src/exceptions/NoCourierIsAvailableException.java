package exceptions;


@SuppressWarnings("serial")
public class NoCourierIsAvailableException extends Exception{
	public NoCourierIsAvailableException() {
		super();
	}
	
	public NoCourierIsAvailableException(String message) {
		super(message);
		
	}
	public NoCourierIsAvailableException(String message , Throwable cause) {
		super(message,cause);
		
	}
	public NoCourierIsAvailableException(Throwable cause) {
		super(cause);
	}
}
