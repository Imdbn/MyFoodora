package test;

import static org.junit.jupiter.api.Assertions.*;

import coreSystem.CoreSystem;
import exceptions.*;
import fidelityCards.*;
import menuMeals.*;
import menuMeals.Dish.DishCategory;
import users.*;
import users.Order;
import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * Unit test class for testing PointFidelityCard-related functionality.
 */
public class FidelityCardTest {

    private Customer customer;
    private Restaurant restaurant;
    private Courier courier;
    private Meal fullMeal;
    private Meal halfMeal;
    private Order order;
    private PointFidelityCard cardP;

    // Stream used to capture System.out for testing any printed output
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    /**
     * Setup method executed before each test.
     * Initializes core entities and resets the system state.
     */
    @BeforeEach
    public void setUp() throws ItemAlreadyExistsException, UndefinedFidelityCardException, UndefinedTypeException, ItemNotFoundException, BadMealCompositionCreationException {
        // Reset system data
        CoreSystem.getRestaurants().clear();

        // Initialize a new PointFidelityCard
        cardP = new PointFidelityCard();

        // Create sample users
        customer = new Customer("Hmad", "hmad_lvista", "1234", "vista");
        courier = new Courier("Aziz", "aziz", "1234", "azoz", "0232134");

        // Create a sample restaurant
        restaurant = new Restaurant("BurgerPlace", "BurgerPlace", "1234");

        // Add sample dishes to the restaurant's menu
        restaurant.addDish(new Dish("Burger", false, 10.0, false, DishCategory.MAINDISH));
        restaurant.addDish(new Dish("Fries", false, 5.0, false, DishCategory.STARTER));
        restaurant.addDish(new Dish("Coke", false, 5.0, false, DishCategory.DESSERT));

        // Create meals
        halfMeal = restaurant.createMeal("BurgerAndFries", MealType.HALF);
        fullMeal = restaurant.createMeal("BurgerFriesAndCoke", MealType.FULL);
        restaurant.addMeal(fullMeal);
        restaurant.addMeal(halfMeal);

        // Add dishes to meals
        restaurant.addDishToMeal("BurgerAndFries", "Burger");
        restaurant.addDishToMeal("BurgerAndFries", "Fries");
        restaurant.addDishToMeal("BurgerFriesAndCoke", "Burger");
        restaurant.addDishToMeal("BurgerFriesAndCoke", "Fries");
        restaurant.addDishToMeal("BurgerFriesAndCoke", "Coke");

        // Register all entities into the core system
        CoreSystem.getRestaurants().put(restaurant.getUsername(), restaurant);
        CoreSystem.getCustomers().put(customer.getUsername(), customer);
        CoreSystem.getCouriers().put(courier.getUsername(), courier);

        // Create an order for the customer
        order = new Order(customer, restaurant);
        order.addItemToOrder("Burger", 3);                 // 3 * 10 = 30
        order.addItemToOrder("Fries", 1);                  // 1 * 5 = 5
        order.addItemToOrder("BurgerFriesAndCoke", 1);     // full meal assumed to cost 19
        order.getPrice(); // Total expected price = 54.0

        // Redirect System.out to capture console output during tests
        System.setOut(new PrintStream(outContent));
    }

    /**
     * Restores the original System.out after each test.
     * Prevents side effects across tests.
     */
    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    // ================== PointFidelityCard Tests ==================

    /**
     * Test that points are accumulated correctly after placing an order.
     * Each 10 units of money spent should give 1 point.
     */
    @Test
    @DisplayName("Test Point accumulation after the customer ends his order ")
    void testPointaccumulation() throws NoCourierIsAvailableException {
        customer.setFidelityCard(cardP);
        customer.endOrder(order);  // Completes the order and adds points
        double expectedPoints = order.getPrice() / 10; // 54 / 10 = 5.4
        assertEquals(expectedPoints, cardP.getPoints());
    }

    /**
     * Test that no discount is applied when the customer does not have enough points.
     * In this fidelity system, 100 points are required to activate the 10% discount.
     */
    @Test
    @DisplayName("Test no discount applied when not enough points")
    void testNoDiscountApplied() throws NoCourierIsAvailableException {
        customer.setFidelityCard(cardP);
        cardP.setPoints(99); // Below threshold for discount
        customer.endOrder(order); // Should not apply discount
        double finalPrice = order.getFinalPrice();
        assertEquals(54.0, finalPrice, "No discount should be applied if points are less than target");
    }

    /**
     * Test that a 10% discount is correctly applied when the customer has 100 points or more.
     * Also verifies that points are reset and new points are earned based on the reduced order price.
     */
    @Test
    @DisplayName("Test discount applied when enough points are present")
    void testDiscountApplied() throws NoCourierIsAvailableException {
        customer.setFidelityCard(cardP);
        cardP.setPoints(100); // Enough for discount

        customer.endOrder(order); // Should apply 10% discount

        double finalPrice = order.getFinalPrice(); // Expected = 54 * 0.9 = 48.6
        assertEquals(54.0 * 0.9, finalPrice, "Discount should be applied reducing the price by 10%");

        double expectedNewPoints = finalPrice / 10; // Points added after reset = 48.6 / 10 = 4.86
        assertEquals(expectedNewPoints, cardP.getPoints(), "Points should be reset and updated after applying discount");
    }

}
