package test;

import static org.junit.jupiter.api.Assertions.*;

import coreSystem.CoreSystem;
import exceptions.BadMealCompositionCreationException;
import exceptions.ItemAlreadyExistsException;
import exceptions.ItemNotFoundException;
import exceptions.NoCourierIsAvailableException;
import exceptions.RestaurantNotFoundException;
import exceptions.UndefinedFidelityCardException;
import exceptions.UndefinedTypeException;
import fidelityCards.FidelityCardType;
import fidelityCards.PointFidelityCard;
import menuMeals.*;
import menuMeals.Dish.DishCategory;
import users.*;
import users.Order;
import notificationService.*;

import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class CustomerTest {

    private Customer customer;
    private Restaurant restaurant;
    private Courier courier;
    private Meal fullMeal;
    private Meal halfMeal;
    // Streams to capture System.out output for testing print outputs
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    
    // Singleton instance of NotificationService used in tests
    private NotificationService notificationService = NotificationService.getInstance();

    @BeforeEach
    public void setUp() throws ItemAlreadyExistsException, UndefinedFidelityCardException, UndefinedTypeException, ItemNotFoundException, BadMealCompositionCreationException {
        // Reset the system state before each test
        CoreSystem.getRestaurants().clear();

        // Create test Customer, Courier, and Restaurant objects with sample data
        customer = new Customer("Hmad", "hmad_lvista", "1234", "vista");
        courier = new Courier("Aziz","aziz","1234", "azoz", "0232134");
        restaurant = new Restaurant("BurgerPlace", "BurgerPlace", "1234");
        
        // Add dishes to the restaurant
        restaurant.addDish(new Dish("Burger", false, 10.0, false, DishCategory.MAINDISH));
        restaurant.addDish(new Dish("Fries", false, 5.0, false, DishCategory.STARTER));
        restaurant.addDish(new Dish("Coke", false, 5.0, false, DishCategory.DESSERT));
        
        // Create meals and add them to the restaurant
        halfMeal = restaurant.createMeal("BurgerAndFries", MealType.HALF);
        fullMeal = restaurant.createMeal("BurgerFriesAndCoke", MealType.FULL);
        restaurant.addMeal(fullMeal);
        restaurant.addMeal(halfMeal);
        
        // Add dishes to the respective meals
        restaurant.addDishToMeal("BurgerAndFries", "Burger");
        restaurant.addDishToMeal("BurgerFriesAndCoke", "Burger");
        restaurant.addDishToMeal("BurgerAndFries", "Fries");
        restaurant.addDishToMeal("BurgerFriesAndCoke", "Fries");
        restaurant.addDishToMeal("BurgerFriesAndCoke", "Coke");
        
        // Register restaurant, customer and courier in the core system
        CoreSystem.getRestaurants().put(restaurant.getUsername(), restaurant);
        CoreSystem.getCustomers().put(customer.getUsername(), customer);
        CoreSystem.getCouriers().put(courier.getUsername(), courier);
        
        // Reset notification observers before tests
        NotificationService.clearObservers();
        
        // Redirect System.out to capture output during tests
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        // Restore original System.out after each test to avoid side effects
        System.setOut(originalOut);
    }

    @Test
    @DisplayName("Customer places order at known restaurant")
    public void testPlaceOrder_success() throws Exception {
        // Test placing an order at a valid restaurant
        Order order = customer.placeOrder("BurgerPlace");
        
        // Assert the order was created and the restaurant is correct
        assertNotNull(order);
        assertEquals("BurgerPlace", order.getRestaurant().getName());
    }

    @Test
    @DisplayName("Customer places order at non-existent restaurant")
    public void testPlaceOrder_throwsWhenRestaurantNotFound() {
        // Test that placing an order at unknown restaurant throws an exception
        Exception exception = assertThrows(Exception.class, () -> {
            customer.placeOrder("UnknownPlace");
        });

        // Assert the exception message indicates the restaurant does not exist
        assertTrue(exception.getMessage().toLowerCase().contains("does not exist"));
    }

    @Test
    @DisplayName("Customer pays with point fidelity card — points should be added")
    public void testPayOrder_withFidelityCard() {
        // Assign a PointFidelityCard to the customer
        PointFidelityCard card = new PointFidelityCard();
        customer.setFidelityCard(card);
        
        double initialPoints = card.getPoints();

        // Customer pays an order and should earn fidelity points
        customer.payOrder(100.0);

        // Assert points have increased after payment
        assertTrue(card.getPoints() > initialPoints, "Points should increase after payment");
    }

    @Test
    @DisplayName("Customer completes valid order and it's added to history")
    public void testEndOrder_validFlow() throws Exception {
        // Customer places an order and adds items to it
        Order order = customer.placeOrder("BurgerPlace");
        order.addItemToOrder("Burger", 1); // 10.0 €
        order.addItemToOrder("Fries", 1);  // 5.0 €
        order.addItemToOrder("BurgerFriesAndCoke", 1); // 19.0 €
        
        // End the order (process payment and add to history)
        customer.endOrder(order);

        // Assert order history updated and final price correct
        assertEquals(1, customer.getOrderHistory().size(), "Order history should have one order");
        assertEquals(34.0, customer.getOrderHistory().get(0).getFinalPrice(), 0.01);

        // Check output message contains payment confirmation
        assertTrue(outContent.toString().contains("You have paid"));
    }

    @Test
    @DisplayName("Register and unregister fidelity card changes type accordingly")
    public void testRegisterAndUnregisterFidelityCard() throws UndefinedFidelityCardException {
        // Initially, customer should have a basic fidelity card (non-null)
        assertNotNull(customer.getFidelityCard());
        assertEquals("BASIC", customer.getFidelityCard().getType().toString());
        
        // Register a POINT fidelity card and check type updated
        customer.registerFidelityCard(FidelityCardType.POINT);
        assertNotNull(customer.getFidelityCard());
        assertEquals("POINT", customer.getFidelityCard().getType().toString());
        
        // Register a LOTTERY fidelity card and check type updated
        customer.registerFidelityCard(FidelityCardType.LOTTERY);
        assertNotNull(customer.getFidelityCard());
        assertEquals("LOTTERY", customer.getFidelityCard().getType().toString());

        // Unregister fidelity card: should revert to BASIC card (not null)
        customer.unregisterFidelityCard();
        assertNotNull(customer.getFidelityCard()); // Should NOT be null
        assertEquals("BASIC", customer.getFidelityCard().getType().toString()); // Expect BASIC card
    }

    @Test
    @DisplayName("Display orders should print order information")
    public void testDisplayOrders() throws Exception {
        // Place an order with 2 Burgers, then end the order
        Order order = customer.placeOrder("BurgerPlace");
        order.addItemToOrder("Burger", 2); // 2 x 10 = 20 €
        customer.endOrder(order);

        // Call displayOrders which prints order details to System.out
        customer.displayOrders();

        // Assert output contains the item name "Burger"
        assertTrue(outContent.toString().contains("Burger"), "Order display should include item names");
    }
    
    @Test
    @DisplayName("Verify Payment Processing Outputs Correct Information")
    void testOrderPaymentOutput() throws ItemNotFoundException, NoCourierIsAvailableException, RestaurantNotFoundException {
        // Place an order and add one Burger item
        Order order = customer.placeOrder("BurgerPlace");
        order.addItemToOrder("Burger", 1);

        // End order (process payment)
        customer.endOrder(order);

        // Expected payment message containing the final price, formatted as a string
        String expectedMessage = "You have paid "+ order.getFinalPrice() +"€ for your order.";

        // Assert printed output includes the payment confirmation message
        assertTrue(outContent.toString().contains(expectedMessage), 
            "Output should confirm payment was processed");
    }
    
    @Test
    @DisplayName("Check Observer Status Changes with Consensus")
    void testConsensusChangesObserverStatus() {
        // Verify customer is NOT registered as observer initially
        assertFalse(NotificationService.getObservers().contains(customer), 
            "Customer should NOT be initially registered as observer");

        // Set consensus to true, customer should register as observer
        customer.setConsensus(true);
        assertTrue(NotificationService.getObservers().contains(customer), 
            "Customer SHOULD be registered as observer after consensus is true");

        // Set consensus to false, customer should unregister as observer
        customer.setConsensus(false);
        assertFalse(NotificationService.getObservers().contains(customer), 
            "Customer should be unregistered as observer after setting consensus false");
    }

    @Test
    @DisplayName("Verify Notification Reception Outputs Correctly")
    void testNotificationReception() {
        // Define an offer of the week
        Offer offer = Offer.MEALOFTHEWEEK;

        // Enable consensus and set customer's preferred contact method and address
        customer.setConsensus(true);
        customer.setContactOffer(ContactOffers.EMAIL, "Hmad@example.com");

        // Notify observers with an offer
        notificationService.setOffer(fullMeal, restaurant, offer);

        // Expected beginning substring of the notification output
        String expectedNotification = "New email received at ";

        // Assert that the output contains the notification message
        assertTrue(outContent.toString().contains(expectedNotification), 
            "Output should contain notification details");
    }

}
