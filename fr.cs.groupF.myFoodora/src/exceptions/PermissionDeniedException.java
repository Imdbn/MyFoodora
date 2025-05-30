package exceptions;


@SuppressWarnings("serial")
public class PermissionDeniedException extends Exception{
	public PermissionDeniedException() {
		super();
	}
	
	public PermissionDeniedException(String message) {
		super(message);
		
	}
	public PermissionDeniedException(String message , Throwable cause) {
		super(message,cause);
		
	}
	public PermissionDeniedException(Throwable cause) {
		super(cause);
	}
}
