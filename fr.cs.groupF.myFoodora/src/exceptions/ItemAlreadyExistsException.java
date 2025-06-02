package exceptions;


@SuppressWarnings("serial")
public class ItemAlreadyExistsException extends Exception{
	public ItemAlreadyExistsException() {
		super();
	}
	
	public ItemAlreadyExistsException(String message) {
		super(message);
		
	}
	public ItemAlreadyExistsException(String message , Throwable cause) {
		super(message,cause);
		
	}
	public ItemAlreadyExistsException(Throwable cause) {
		super(cause);
	}
}
