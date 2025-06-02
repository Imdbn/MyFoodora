package users;
import exceptions.UndefinedTypeException;
import menuMeals.*;
import menuMeals.Dish.DishCategory;

public class FoodFactory {
	public FoodFactory() {
		
	}
	
	public static Dish createDish( String name, boolean isVegetarian, double price, boolean isGlutenFree, DishCategory category) {
		return new Dish( name, isVegetarian,  price,  isGlutenFree,  category);
	}
	
	public static Dish createDish( String name, boolean isVegetarian ,boolean isGlutenFree) {
		return new Dish( name, isVegetarian,isGlutenFree );
	}
	
	public static Dish createDish( String name) {
		return new Dish( name );
	}
	
	public static Meal createMeal(String mealName, MealType mealType) throws UndefinedTypeException {
		switch (mealType) {
		case FULL :
			return new FullMeal(mealName);
		case HALF :
			return new HalfMeal(mealName);
		default :
			throw new UndefinedTypeException("Undefined mealType :" + mealType);
		}
		
	}
	public static Meal createMeal(String mealName, MealType mealType, boolean isVegetarian , boolean isGlutenFree) throws UndefinedTypeException {
		switch (mealType) {
		case FULL :
			return new FullMeal(mealName,isVegetarian,isGlutenFree);
		case HALF :
			return new HalfMeal(mealName,isVegetarian,isGlutenFree);
		default :
			throw new UndefinedTypeException("Undefined mealType :" + mealType);
		}
		
	}
	
}
