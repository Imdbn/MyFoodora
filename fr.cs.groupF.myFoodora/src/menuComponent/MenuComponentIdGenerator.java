package menuComponent;

import menuComponent.MenuComponentIdGenerator;

/**
 * This is the MenuComponentIdGenerator Class using the Singleton Design pattern to generate unique ids
 */
public class MenuComponentIdGenerator {
	private static MenuComponentIdGenerator instance = null;
	private int num ;
	/**
	 * private constructor : returns the unique UserIdGenerator object
	 */
	private MenuComponentIdGenerator() {}
	/**
	 * the public getInstance Method 
	 * @return
	 * the unique instance of MenuComponentIdGenerator
	 */
	public static MenuComponentIdGenerator getInstance() {
		if (instance == null) {
			instance = new MenuComponentIdGenerator();
			
		}
		return instance;
	}
	public int getNextMenuComponentId() {
		return num++; 
	}
	
}