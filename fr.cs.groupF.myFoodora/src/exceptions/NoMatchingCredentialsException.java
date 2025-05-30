package exceptions;


@SuppressWarnings("serial")
public class NoMatchingCredentialsException extends Exception{
	public NoMatchingCredentialsException() {
		super();
	}
	
	public NoMatchingCredentialsException(String message) {
		super(message);
		
	}
	public NoMatchingCredentialsException(String message , Throwable cause) {
		super(message,cause);
		
	}
	public NoMatchingCredentialsException(Throwable cause) {
		super(cause);
	}
}
