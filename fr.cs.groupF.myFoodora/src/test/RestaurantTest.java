package test;

import exceptions.*;
import menuMeals.*;
import menuMeals.Dish.DishCategory;
import users.*;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class RestaurantTest {

    private Restaurant restaurant;
    private Meal halfMeal;
    private Meal fullMeal;

    // Streams to capture System.out output for testing print outputs
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() throws ItemNotFoundException, BadMealCompositionCreationException, ItemAlreadyExistsException, UndefinedTypeException {
        // Initialize a new Restaurant instance
        restaurant = new Restaurant("Chez Amélie", "bistro", "pass123");

        // Add some dishes to the restaurant
        restaurant.addDish(new Dish("Burger", false, 10.0, false, DishCategory.MAINDISH));
        restaurant.addDish(new Dish("Fries", false, 5.0, false, DishCategory.STARTER));
        restaurant.addDish(new Dish("Coke", false, 5.0, false, DishCategory.DESSERT));

        // Create meals
        halfMeal = restaurant.createMeal("BurgerAndFries", MealType.HALF);
        fullMeal = restaurant.createMeal("BurgerFriesAndCoke", MealType.FULL);
        restaurant.addMeal(halfMeal);
        restaurant.addMeal(fullMeal);

        // Add dishes to the meals
        restaurant.addDishToMeal("BurgerAndFries", "Burger");
        restaurant.addDishToMeal("BurgerAndFries", "Fries");

        restaurant.addDishToMeal("BurgerFriesAndCoke", "Burger");
        restaurant.addDishToMeal("BurgerFriesAndCoke", "Fries");
        restaurant.addDishToMeal("BurgerFriesAndCoke", "Coke");

        // Redirect System.out for capturing printed output in tests
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        // Restore System.out to its original stream after tests
        System.setOut(originalOut);
    }

    @Test
    @DisplayName("Test Adding a meal to the restaurant's meals")
    void testAddMeal() throws UndefinedTypeException, ItemAlreadyExistsException, ItemNotFoundException, BadMealCompositionCreationException {
        int initialSize = restaurant.getMeals().size();

        Meal newMeal = restaurant.createMeal("Mealtest", MealType.HALF);
        restaurant.addMeal(newMeal);
        restaurant.addDishToMeal("Mealtest", "Coke");

        assertEquals(initialSize + 1, restaurant.getMeals().size(), "Meal should be added to the list");
        assertEquals(newMeal, restaurant.getMeals().get("Mealtest"), "The added meal should match the one retrieved from the map");
    }

    @Test
    @DisplayName("Test removing a meal from the restaurant's meals")
    void testRemoveMeal() throws ItemNotFoundException {
        int initialSize = restaurant.getMeals().size();

        restaurant.removeMeal("BurgerAndFries");

        assertEquals(initialSize - 1, restaurant.getMeals().size(), "Meal should be removed from the list");
        assertFalse(restaurant.getMeals().containsKey("BurgerAndFries"), "Removed meal should not exist in the meal map anymore");
    }

    @Test
    @DisplayName("Test Meal Price Changes after dish added and discounts applied")
    void testMealPrice() throws UndefinedTypeException, ItemAlreadyExistsException, ItemNotFoundException, BadMealCompositionCreationException {
        // Create and add a new full meal
        Meal newMeal = restaurant.createMeal("Mealtest", MealType.FULL);
        restaurant.addMeal(newMeal);

        // Add dishes
        restaurant.addDishToMeal("Mealtest", "Burger"); // 10
        restaurant.addDishToMeal("Mealtest", "Fries");  // 5
        restaurant.addDishToMeal("Mealtest", "Coke");   // 5
        double baseMealPrice = 10 + 5 + 5;

        // Test initial price with default discount
        double expectedPriceGeneric = baseMealPrice * (1 - restaurant.getGenericDiscount());
        assertEquals(expectedPriceGeneric, newMeal.getPrice(), 0.1, "Price should be based on generic discount");

        // Change generic discount and verify update
        restaurant.setGenericDiscount(0.2); // 20% discount
        double expectedPriceUpdatedGeneric = baseMealPrice * (1 - 0.2);
        assertEquals(expectedPriceUpdatedGeneric, newMeal.getPrice(), 0.1, "Price should reflect updated generic discount");

        // Set special offer and verify it overrides generic discount
        restaurant.setSpecialOffer("Mealtest"); // Special discount should now apply
        double expectedPriceSpecial = baseMealPrice * (1 - restaurant.getSpecialDiscount());
        assertEquals(expectedPriceSpecial, newMeal.getPrice(), 0.1, "Price should reflect special discount after setting special offer");
    }
}
