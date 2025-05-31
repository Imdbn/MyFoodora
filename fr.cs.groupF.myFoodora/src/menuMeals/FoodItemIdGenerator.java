package menuMeals;

import menuMeals.FoodItemIdGenerator;

/**
 * This is the FoodItemIdGenerator Class using the Singleton Design pattern to generate unique ids
 */
public class FoodItemIdGenerator {
	private static FoodItemIdGenerator instance = null;
	private int num ;
	/**
	 * private constructor : returns the unique FoodItemIdGenerator object
	 */
	private FoodItemIdGenerator() {}
	/**
	 * the public getInstance Method 
	 * @return
	 * the unique instance of FoodItemIdGenerator
	 */
	public static FoodItemIdGenerator getInstance() {
		if (instance == null) {
			instance = new FoodItemIdGenerator();
			
		}
		return instance;
	}
	public int getNextFoodItemId() {
		return num++; 
	}
	
}