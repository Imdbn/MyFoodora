package exceptions;


@SuppressWarnings("serial")
public class BadMealCompositionCreationException extends Exception{
	public BadMealCompositionCreationException() {
		super();
	}
	
	public BadMealCompositionCreationException(String message) {
		super(message);
		
	}
	public BadMealCompositionCreationException(String message , Throwable cause) {
		super(message,cause);
		
	}
	public BadMealCompositionCreationException(Throwable cause) {
		super(cause);
	}
}
