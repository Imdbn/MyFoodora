package menuComponent;

import java.util.*;

/**
 * This class represents a HalfMeal, composed of multiple dishes.
 */

public abstract class HalfMeal extends Meal{
	public HalfMeal(int id, String name, boolean isVegetarian, double price, boolean isGlutenFree, boolean mealOfTheWeek, ArrayList<Dish> mealComposition) {
		super(id, name, isVegetarian, price, isGlutenFree, mealOfTheWeek,mealComposition);

	}
	
	
}