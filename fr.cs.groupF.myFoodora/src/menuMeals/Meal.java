package menuMeals;
import exceptions.*;
import java.util.*;

/**
 * This class represents a Meal, composed of multiple dishes.
 */

public abstract class Meal extends FoodItem{
	
	protected boolean mealOfTheWeek;
	protected ArrayList<Dish> mealComposition;
	
	
	public Meal( String name, boolean isVegetarian, double price, boolean isGlutenFree, boolean mealOfTheWeek, ArrayList<Dish> mealComposition) {
		super( name, isVegetarian, price, isGlutenFree);
		this.mealOfTheWeek = mealOfTheWeek;
		this.mealComposition = mealComposition;
	}
	
	public abstract void addDish(Dish dish) throws BadMealCompositionCreationException;


	/** 
	 * Checks whether meal is the meal of the week.
	 */
	
	public boolean ismealOfTheWeek() {
		return mealOfTheWeek;
	}
	/** 
	 * 	Sets the meal-of-the-week status of the meal.
	 */
	
	public void setmealOfTheWeek(boolean mealOfTheWeek) {
		this.mealOfTheWeek = mealOfTheWeek; 
	}
	
	/**
	 * Gets the Meal's composition
	 */
	
	public ArrayList<Dish> getmealComposition() {
		return mealComposition;
	}
	
	/**
	 * Sets the Meal's composition
	 */
	
	public void setmealComposition(ArrayList<Dish> mealComposition){
		this.mealComposition = mealComposition;
	}

	
}