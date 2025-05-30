package users;



/**
 * This is the UserIdGenerator Class using the Singleton Design pattern to generate unique ids
 */
public class UserIdGenerator {
	private static UserIdGenerator instance = null;
	private int num ;
	/**
	 * private constructor : returns the unique UserIdGenerator object
	 */
	private UserIdGenerator() {}
	/**
	 * the public getInstance Method 
	 * @return
	 * the unique instance of UserIdGenerator
	 */
	public static UserIdGenerator getInstance() {
		if (instance == null) {
			instance = new UserIdGenerator();
			
		}
		return instance;
	}
	public int getNextUserId() {
		return num++; 
	}
	
}
