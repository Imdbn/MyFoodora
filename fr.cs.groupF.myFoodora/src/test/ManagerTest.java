package test;

import static org.junit.jupiter.api.Assertions.*;

import coreSystem.CoreSystem;
import exceptions.*;
import fidelityCards.*;
import menuMeals.*;
import menuMeals.Dish.DishCategory;
import targetProfitPolicy.TargetProfitPolicyType;
import users.*;
import users.Order;
import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Calendar;

/**
 * Unit test class for testing Manager-related computations on Orders,
 * such as income, profit, and service fee adjustment through TargetProfitPolicy.
 */
public class ManagerTest {

    // Test entities
    private Customer customer;
    private Customer customer2;
    private Restaurant restaurant;
    private Courier courier;
    private Courier courier2;
    private Meal fullMeal;
    private Meal halfMeal;
    private Manager manager;
    private Order o1;
    private Order o2;
    private Order o3;
    private Calendar now;
    private Calendar lastMonth;

    // Streams to capture System.out outputs for testing console prints
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    /**
     * Setup executed before each test:
     * - Initializes and clears core system state
     * - Adds test data: customers, couriers, restaurant, meals, dishes, orders
     */
    @BeforeEach
    public void setUp() throws Exception {
        // Ensure CoreSystem is initialized and cleared
        CoreSystem.getInstance();
        CoreSystem.getRestaurants().clear();
        CoreSystem.getCouriers().clear();
        CoreSystem.getCustomers().clear();
        CoreSystem.getOrders().clear();
        CoreSystem.getManagers().clear();

        new PointFidelityCard();

        // Create entities
        customer = new Customer("Hmad", "hmad_lvista", "1234", "vista");
        customer2 = new Customer("Sara", "sara_queen", "abcd", "city");
        courier = new Courier("Aziz", "aziz", "1234", "azoz", "0232134");
        courier2 = new Courier("habil", "Hobo", "1234", "hoho", "0232134");
        restaurant = new Restaurant("BurgerPlace", "BurgerPlace", "1234");
        manager = new Manager("Rachid", "rachid_manager", "1234", "mgmt");

        // Register entities in the system
        CoreSystem.getCustomers().put(customer.getUsername(), customer);
        CoreSystem.getCustomers().put(customer2.getUsername(), customer2);
        CoreSystem.getCouriers().put(courier.getUsername(), courier);
        CoreSystem.getCouriers().put(courier2.getUsername(), courier2);
        CoreSystem.getRestaurants().put(restaurant.getUsername(), restaurant);
        CoreSystem.getManagers().put(manager.getUsername(), manager);

        // Create and register dishes
        restaurant.addDish(new Dish("Burger", false, 10.0, false, DishCategory.MAINDISH));
        restaurant.addDish(new Dish("Fries", false, 5.0, false, DishCategory.STARTER));
        restaurant.addDish(new Dish("Coke", false, 5.0, false, DishCategory.DESSERT));

        // Create meals (Half and Full)
        halfMeal = restaurant.createMeal("BurgerAndFries", MealType.HALF);
        fullMeal = restaurant.createMeal("BurgerFriesAndCoke", MealType.FULL);
        restaurant.addMeal(halfMeal);
        restaurant.addMeal(fullMeal);

        // Add dishes to the created meals
        restaurant.addDishToMeal("BurgerAndFries", "Burger");
        restaurant.addDishToMeal("BurgerAndFries", "Fries");
        restaurant.addDishToMeal("BurgerFriesAndCoke", "Burger");
        restaurant.addDishToMeal("BurgerFriesAndCoke", "Fries");
        restaurant.addDishToMeal("BurgerFriesAndCoke", "Coke");

        // Create 3 orders at different times
        // ---- Order 1: 10 days ago ----
        o1 = new Order(customer, restaurant);
        o1.addItemToOrder("Burger", 3);
        o1.addItemToOrder("Fries", 1);
        o1.addItemToOrder("BurgerFriesAndCoke", 1);
        Calendar cal1 = Calendar.getInstance();
        cal1.add(Calendar.DAY_OF_MONTH, -10);
        o1.setOrderDate(cal1);
        CoreSystem.getOrders().add(o1);

        // ---- Order 2: 5 days ago ----
        o2 = new Order(customer, restaurant);
        o2.addItemToOrder("BurgerFriesAndCoke", 2);
        Calendar cal2 = Calendar.getInstance();
        cal2.add(Calendar.DAY_OF_MONTH, -5);
        o2.setOrderDate(cal2);
        CoreSystem.getOrders().add(o2);

        // ---- Order 3: 2 months ago ----
        o3 = new Order(customer2, restaurant);
        o3.addItemToOrder("Burger", 1);
        o3.addItemToOrder("Coke", 1);
        Calendar cal3 = Calendar.getInstance();
        cal3.add(Calendar.MONTH, -2);
        o3.setOrderDate(cal3);
        CoreSystem.getOrders().add(o3);

        // Setup date range for "last month to now"
        lastMonth = Calendar.getInstance();
        now = Calendar.getInstance();
        lastMonth.add(Calendar.MONTH, -1);

        // Pre-compute prices and profits for assertions
        o1.computeFinalPrice();
        o2.computeFinalPrice();
        o3.computeFinalPrice();
        o1.computeProfit();
        o2.computeProfit();
        o3.computeProfit();

        // Redirect System.out to capture outputs for print-based tests (if needed)
        System.setOut(new PrintStream(outContent));
    }

    /**
     * Restore original System.out after each test.
     */
    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    /**
     * Test total income calculation:
     * - Verifies total income over all orders
     * - Verifies total income within a date range
     */
    @Test
    @DisplayName("Test Compute TotalIncome ")
    void testComputeTotalIncome() {
        // Final prices (no discounts): o1 = 54, o2 = 38, o3 = 15
        double expectedTotalIncome = o1.computeFinalPrice() + o2.computeFinalPrice() + o3.computeFinalPrice(); // 107
        assertEquals(107, manager.computeTotalIncome());
        assertEquals(expectedTotalIncome, manager.computeTotalIncome());

        // Orders within last month: o1 and o2 only (o3 is older)
        double expectedIncomeLastMonth = o1.computeFinalPrice() + o2.computeFinalPrice(); // 92
        assertEquals(92, manager.computeTotalIncome(lastMonth, now));
        assertEquals(expectedIncomeLastMonth, manager.computeTotalIncome(lastMonth, now));
    }

    /**
     * Test total profit calculation:
     * - Verifies total profit over all orders
     * - Verifies total profit within a date range
     */
    @Test
    @DisplayName("Test Compute TotalProfit ")
    void testComputeTotalProfit() {
        // Profits: o1 = 21.2, o2 = 16.4, o3 = 9.5
        double expectedTotalProfit = o1.computeProfit() + o2.computeProfit() + o3.computeProfit(); // 47.1
        assertEquals(expectedTotalProfit, manager.computeTotalProfit());

        // Profit from orders in last month: o1 and o2
        double expectedProfitLastMonth = o1.computeProfit() + o2.computeProfit();
        assertEquals(expectedProfitLastMonth, manager.computeTotalProfit(lastMonth, now));
    }

    /**
     * Test the service fee adjustment when using the ServiceFee TargetProfitPolicy:
     * - Sets a target profit and checks if the computed service fee is correct
     */
    @Test
    @DisplayName("Test TargetProfit Policy : ServiceFee")
    void testServiceFeeTargetProfitPolicy() throws UndefinedPolicyException, UnreachableTargetProfitException {
        double targetProfit = 25;

        // Set the policy to SERVICEFEE with desired profit
        manager.setTargetProfitPolicy(TargetProfitPolicyType.SERVICEFEE, targetProfit);

        // Expected fee calculation based on equation from the policy:
        // fee = (targetProfit - revenue*markup + deliveryCost*numOrders) / numOrders
        double expectedServiceFee = (targetProfit
                - manager.computeTotalIncome(lastMonth, now) * CoreSystem.getMarkUpPercentage()
                + manager.computeNumberOfOrders(lastMonth, now) * CoreSystem.getDeliveryCost())
                / manager.computeNumberOfOrders(lastMonth, now);

        // Validate service fee was properly updated
        assertEquals(expectedServiceFee, CoreSystem.getServiceFee());
    }
}
