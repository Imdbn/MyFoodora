package menuComponent;

import java.util.*;

/**
 * This class represents a FullMeal, composed of multiple dishes.
 */

public abstract class FullMeal extends Meal{
	public FullMeal(int id, String name, boolean isVegetarian, double price, boolean isGlutenFree, boolean mealOfTheWeek, ArrayList<Dish> mealComposition) {
		super(id, name, isVegetarian, price, isGlutenFree, mealOfTheWeek,mealComposition);

	}
	
	
}
