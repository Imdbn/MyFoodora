package exceptions;


@SuppressWarnings("serial")
public class NoUserExistsException extends Exception{
	public NoUserExistsException() {
		super();
	}
	
	public NoUserExistsException(String message) {
		super(message);
		
	}
	public NoUserExistsException(String message , Throwable cause) {
		super(message,cause);
		
	}
	public NoUserExistsException(Throwable cause) {
		super(cause);
	}
}
