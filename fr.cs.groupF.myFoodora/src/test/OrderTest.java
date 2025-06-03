package test;

import static org.junit.jupiter.api.Assertions.*;

import coreSystem.CoreSystem;
import exceptions.*;
import fidelityCards.PointFidelityCard;
import menuMeals.*;
import menuMeals.Dish.DishCategory;
import users.*;
import users.Order;
import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * Unit test class for testing Order-related functionality.
 */
public class OrderTest {

    private Customer customer;
    private Restaurant restaurant;
    private Courier courier;
    private Meal fullMeal;
    private Meal halfMeal;
    private Order order;

    // Streams to capture System.out output for testing print outputs
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    /**
     * Setup method executed before each test.
     * Initializes a clean system state and adds sample data.
     */
    @BeforeEach
    public void setUp() throws ItemAlreadyExistsException, UndefinedFidelityCardException, UndefinedTypeException, ItemNotFoundException, BadMealCompositionCreationException {
        // Clear previous restaurant data
        CoreSystem.getRestaurants().clear();

        // Create test entities
        customer = new Customer("Hmad", "hmad_lvista", "1234", "vista");
        courier = new Courier("Aziz","aziz","1234", "azoz", "0232134");
        restaurant = new Restaurant("BurgerPlace", "BurgerPlace", "1234");

        // Add sample dishes
        restaurant.addDish(new Dish("Burger", false, 10.0, false, DishCategory.MAINDISH));
        restaurant.addDish(new Dish("Fries", false, 5.0, false, DishCategory.STARTER));
        restaurant.addDish(new Dish("Coke", false, 5.0, false, DishCategory.DESSERT));

        // Create and register meals
        halfMeal = restaurant.createMeal("BurgerAndFries", MealType.HALF);
        fullMeal = restaurant.createMeal("BurgerFriesAndCoke", MealType.FULL);
        restaurant.addMeal(fullMeal);
        restaurant.addMeal(halfMeal);

        // Compose meals with dishes
        restaurant.addDishToMeal("BurgerAndFries", "Burger");
        restaurant.addDishToMeal("BurgerFriesAndCoke", "Burger");
        restaurant.addDishToMeal("BurgerAndFries", "Fries");
        restaurant.addDishToMeal("BurgerFriesAndCoke", "Fries");
        restaurant.addDishToMeal("BurgerFriesAndCoke", "Coke");

        // Register test users and restaurant into the core system
        CoreSystem.getRestaurants().put(restaurant.getUsername(), restaurant);
        CoreSystem.getCustomers().put(customer.getUsername(), customer);
        CoreSystem.getCouriers().put(courier.getUsername(), courier);

        // Create a new order for testing
        order = new Order(customer, restaurant);

        // Redirect System.out to a custom output stream to capture print statements
        System.setOut(new PrintStream(outContent));
    }

    /**
     * Restore System.out after each test to avoid side effects on other tests.
     */
    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    /**
     * Test that items can be added to an order and that the total price is calculated correctly.
     */
    @Test
    @DisplayName("Adding Items to Order and checking if price changes")
    void testAddItemToOrder() throws ItemNotFoundException {
        // Add individual items and meals
        order.addItemToOrder("Burger", 3);                 // 10 * 3 = 30
        order.addItemToOrder("Fries", 1);                  // 5 * 1 = 5
        order.addItemToOrder("BurgerFriesAndCoke", 1);     // fullMeal assumed to be 19

        double expectedPrice = 30 + 5 + 19;
        assertEquals(expectedPrice, order.computePrice(), "Computed order price should match the expected total.");
    }

    /**
     * Test that the final price after applying a fidelity card discount is correct.
     */
    @Test
    @DisplayName("Adding Items to Order and checking if finalprice changes, after the fidelity card applies")
    void testAddItemToOrderFidelity() throws ItemNotFoundException {
        // Set up fidelity card and apply points (assuming 10% discount rule)
        customer.setFidelityCard(new PointFidelityCard());
        ((PointFidelityCard) customer.getFidelityCard()).addPoints(100); // Enough points for a discount

        // Add individual items and meals
        order.addItemToOrder("Burger", 3);                 // 30
        order.addItemToOrder("Fries", 1);                  // 5
        order.addItemToOrder("BurgerFriesAndCoke", 1);     // 19

        double expectedFinalPrice = (30 + 5 + 19) * 0.9;    // 10% discount applied
        assertEquals(expectedFinalPrice, order.computeFinalPrice(), "Final price should reflect 10% discount from fidelity card.");
    }
    /**
     * Test that submitting an order when a courier is available does not throw an exception.
     */
    @Test
    @DisplayName("Submitting order with available courier should not throw")
    void testSubmitOrderWithCourierAvailable() {
        assertDoesNotThrow(() -> {
            order.submitOrder();
        }, "Order submission should not throw when courier is available.");
    }
    /**
     * Test that submitting an order when no courier is available throws NoCourierAvailableException.
     */
   
    @Test
    @DisplayName("Submitting order with NO courier should throw NoCourierAvailableException")
    void testSubmitOrderNoCourierAvailable() {
        // Remove the only courier
        CoreSystem.getCouriers().clear();

        assertThrows(NoCourierIsAvailableException.class, () -> {
            order.submitOrder();
        }, "Order submission should throw NoCourierAvailableException when no courier is available.");
    }
}
