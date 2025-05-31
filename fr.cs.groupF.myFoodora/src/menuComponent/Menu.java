package menuComponent;

import java.util.*;

/**
 * This class represents a Menu that contains both Meals and Dishes.
 * It allows adding and removing Meals and Dishes separately.
 */
public class Menu {

    private ArrayList<Meal> meals;
    private ArrayList<Dish> dishes;

    // Constructor initializes empty lists
    public Menu() {
        this.meals = new ArrayList<>();
        this.dishes = new ArrayList<>();
    }

    // Getters
    public ArrayList<Meal> getMeals() {
        return meals;
    }

    public ArrayList<Dish> getDishes() {
        return dishes;
    }

    // Add a Meal if not already present
    public void addMeal(Meal meal) {
        if (!meals.contains(meal)) {
            meals.add(meal);
        }
    }

    // Remove a Meal if present
    public void removeMeal(Meal meal) {
        meals.remove(meal);
    }

    // Add a Dish if not already present
    public void addDish(Dish dish) {
        if (!dishes.contains(dish)) {
            dishes.add(dish);
        }
    }

    // Remove a Dish if present
    public void removeDish(Dish dish) {
        dishes.remove(dish);
    }

    // String representation of the Menu showing meals and dishes
    @Override
    public String toString() {
        StringBuilder menu = new StringBuilder();

        if (!meals.isEmpty()) {
        	menu.append("Meals in the menu:\n");
            for (Meal meal : meals) {
            	menu.append(meal.getName()).append("\n");
            }
        } else {
        	menu.append("No meals in the menu.\n");
        }

        if (!dishes.isEmpty()) {
        	menu.append("Dishes in the menu:\n");
            for (Dish dish : dishes) {
            	menu.append(dish.getName()).append("\n");
            }
        } else {
           menu.append("No dishes in the menu.\n");
        }

        return menu.toString();
    }
}
