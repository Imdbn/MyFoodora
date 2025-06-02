package exceptions;


@SuppressWarnings("serial")
public class UnreachableTargetProfitException extends Exception{
	public UnreachableTargetProfitException() {
		super();
	}
	
	public UnreachableTargetProfitException(String message) {
		super(message);
		
	}
	public UnreachableTargetProfitException(String message , Throwable cause) {
		super(message,cause);
		
	}
	public UnreachableTargetProfitException(Throwable cause) {
		super(cause);
	}
}
