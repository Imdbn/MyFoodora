package users;



/**
 * This is the OrderIdGenerator Class using the Singleton Design pattern to generate unique ids
 */
public class OrderIdGenerator {
	private static OrderIdGenerator instance = null;
	private int num ;
	/**
	 * private constructor : returns the unique OrderIdGenerator object
	 */
	private OrderIdGenerator() {}
	/**
	 * the public getInstance Method 
	 * @return
	 * the unique instance of OrderIdGenerator
	 */
	public static OrderIdGenerator getInstance() {
		if (instance == null) {
			instance = new OrderIdGenerator();
			
		}
		return instance;
	}
	public int getNextOrderId() {
		return num++; 
	}
	
}
