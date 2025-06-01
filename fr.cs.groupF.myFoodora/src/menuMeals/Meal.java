package menuMeals;
import exceptions.*;
import java.util.*;

/**
 * This class represents a Meal, composed of multiple dishes.
 */

public abstract class Meal extends FoodItem{
	
	
	/*
	 * Whether it is meal of the week or Not
	 */
	
	protected boolean mealOfTheWeek;
	
	/*
	 * List of dishes which compose the Meal
	 */
	
	protected ArrayList<Dish> mealComposition;
	
	
	public Meal (String name) {
		super();
		this.name=name;
	}
	
	public Meal (String name, boolean isVegetarian, boolean isGlutenFree, boolean mealOfTheWeek){
		super(name,isVegetarian,isGlutenFree);
		this.mealOfTheWeek=mealOfTheWeek;		
		}
	
	public Meal (String name, boolean isVegetarian, boolean isGlutenFree){
		super(name,isVegetarian,isGlutenFree);
		}
	
	
	/*
	 * Declaring and instantiating the addDishVisitor
	 */
	
	protected AddDishVisitor itemVisitor = new AddDishVisitorImpl();
	
	
	public abstract void addDishToMeal(Dish dish) throws BadMealCompositionCreationException;


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