package exceptions;


@SuppressWarnings("serial")
public class UndefinedTypeException extends Exception{
	public UndefinedTypeException() {
		super();
	}
	
	public UndefinedTypeException(String message) {
		super(message);
		
	}
	public UndefinedTypeException(String message , Throwable cause) {
		super(message,cause);
		
	}
	public UndefinedTypeException(Throwable cause) {
		super(cause);
	}
}
