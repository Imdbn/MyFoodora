package test;


import coreSystem.CoreSystem;
import exceptions.BadMealCompositionCreationException;
import exceptions.ItemAlreadyExistsException;
import exceptions.ItemNotFoundException;
import exceptions.UndefinedFidelityCardException;
import exceptions.UndefinedTypeException;
import menuMeals.Dish;
import menuMeals.Meal;
import menuMeals.MealType;
import menuMeals.Dish.DishCategory;
import notificationService.NotificationService;
import users.Customer;
import users.Restaurant;

public class Testing {
	public static void main(String[] args) throws ItemNotFoundException, BadMealCompositionCreationException, ItemAlreadyExistsException, UndefinedFidelityCardException, UndefinedTypeException {
		CoreSystem.getRestaurants().clear();

        Customer customer = new Customer("John", "john_doe", "1234", "Doe");

        Restaurant restaurant = new Restaurant("BurgerPlace", "FastFood", "1234");
        restaurant.addDish(new Dish("Burger", false, 10.0, false, DishCategory.MAINDISH));
        restaurant.addDish(new Dish("Fries", false, 5.0, false, DishCategory.STARTER));
        restaurant.addDish(new Dish("Coke",false, 3.0,false,DishCategory.DESSERT));
        
        Meal halfMeal = restaurant.createMeal("BurgerAndFries", MealType.HALF);
        Meal fullMeal = restaurant.createMeal("BurgerFriesAndCoke", MealType.FULL);
        System.out.println(halfMeal);
        restaurant.addDishToMeal("BurgerAndFries", "Burger");
        restaurant.addDishToMeal("BurgerFriesAndCoke", "Burger");
        restaurant.addDishToMeal("BurgerAndFries", "Fries");
        restaurant.addDishToMeal("BurgerFriesAndCoke", "Fries");
        restaurant.addDishToMeal("BurgerFriesAndCoke", "Coke");
        System.out.println(customer);
        CoreSystem.getRestaurants().put("BurgerPlace", restaurant);
        CoreSystem.getCustomers().put("john_doe",customer);
        
        NotificationService.getInstance();
        NotificationService.clearObservers();
        System.out.println("halfMeal = " + halfMeal + " at " + System.identityHashCode(halfMeal));
        System.out.println("fullMeal = " + fullMeal + " at " + System.identityHashCode(fullMeal));

	}
	
}
