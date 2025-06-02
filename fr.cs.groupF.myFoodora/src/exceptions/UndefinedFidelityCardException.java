package exceptions;


@SuppressWarnings("serial")
public class UndefinedFidelityCardException extends Exception{
	public UndefinedFidelityCardException() {
		super();
	}
	
	public UndefinedFidelityCardException(String message) {
		super(message);
		
	}
	public UndefinedFidelityCardException(String message , Throwable cause) {
		super(message,cause);
		
	}
	public UndefinedFidelityCardException(Throwable cause) {
		super(cause);
	}
}
