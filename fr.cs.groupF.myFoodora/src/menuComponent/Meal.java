package menuComponent;

import java.util.List;

/**
 * This class represents a Meal, composed of multiple dishes.
 */

public class Meal extends MenuComponent{
	
	protected boolean mealOfTheWeek;
	protected List<Dish> mealComposition;
	
	public Meal(int id, String name, boolean isVegetarian, double price, boolean isGlutenFree,boolean mealOfTheWeek, List<Dish> mealComposition) {
		super(id, name, isVegetarian, price, isGlutenFree);
		this.mealOfTheWeek = mealOfTheWeek;
		this.mealComposition = mealComposition;
	}
	
	
}