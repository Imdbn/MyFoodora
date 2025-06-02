package exceptions;


@SuppressWarnings("serial")
public class RestaurantNotFoundException extends Exception{
	public RestaurantNotFoundException() {
		super();
	}
	
	public RestaurantNotFoundException(String message) {
		super(message);
		
	}
	public RestaurantNotFoundException(String message , Throwable cause) {
		super(message,cause);
		
	}
	public RestaurantNotFoundException(Throwable cause) {
		super(cause);
	}
}
