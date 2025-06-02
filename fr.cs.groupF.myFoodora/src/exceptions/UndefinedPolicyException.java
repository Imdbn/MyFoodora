package exceptions;


@SuppressWarnings("serial")
public class UndefinedPolicyException extends Exception{
	public UndefinedPolicyException() {
		super();
	}
	
	public UndefinedPolicyException(String message) {
		super(message);
		
	}
	public UndefinedPolicyException(String message , Throwable cause) {
		super(message,cause);
		
	}
	public UndefinedPolicyException(Throwable cause) {
		super(cause);
	}
}
