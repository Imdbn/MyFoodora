package test;

import static org.junit.jupiter.api.Assertions.*;

import coreSystem.CoreSystem;
import exceptions.*;
import fidelityCards.*;
import menuMeals.*;
import menuMeals.Dish.DishCategory;
import targetProfitPolicy.TargetProfitPolicy;
import targetProfitPolicy.TargetProfitPolicyFactory;
import targetProfitPolicy.TargetProfitPolicyType;
import users.*;
import users.Order;
import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Calendar;

/**
 * Unit test class for testing Order-related functionality.
 */
public class ManagerTest {

    private Customer customer;
    private Customer customer2;
    private Restaurant restaurant;
    private Courier courier;
    private Meal fullMeal;
    private Meal halfMeal;
    private PointFidelityCard card;
    private Manager manager;
    private Order o1;
    private Order o2;
    private Order o3;
    

    // Streams to capture System.out output for testing print outputs
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    /**
     * Setup method executed before each test.
     * Initializes a clean system state and adds sample data.
     */
    @BeforeEach
    public void setUp() throws Exception {
        // Clear core data
        CoreSystem.getRestaurants().clear();
        CoreSystem.getCouriers().clear();
        CoreSystem.getCustomers().clear();
        CoreSystem.getOrders().clear();
        CoreSystem.getManagers().clear();

        // Fidelity card
        card = new PointFidelityCard();

        // Create entities
        customer = new Customer("Hmad", "hmad_lvista", "1234", "vista");
        customer2 = new Customer("Sara", "sara_queen", "abcd", "city");
        courier = new Courier("Aziz", "aziz", "1234", "azoz", "0232134");
        restaurant = new Restaurant("BurgerPlace", "BurgerPlace", "1234");
        manager = new Manager("Rachid", "rachid_manager", "1234", "mgmt");

        // Add to core system
        CoreSystem.getCustomers().put(customer.getUsername(), customer);
        CoreSystem.getCustomers().put(customer2.getUsername(), customer2);
        CoreSystem.getCouriers().put(courier.getUsername(), courier);
        CoreSystem.getRestaurants().put(restaurant.getUsername(), restaurant);
        CoreSystem.getManagers().put(manager.getUsername(), manager);

        // Add dishes
        restaurant.addDish(new Dish("Burger", false, 10.0, false, DishCategory.MAINDISH));
        restaurant.addDish(new Dish("Fries", false, 5.0, false, DishCategory.STARTER));
        restaurant.addDish(new Dish("Coke", false, 5.0, false, DishCategory.DESSERT));

        // Create and add meals
        halfMeal = restaurant.createMeal("BurgerAndFries", MealType.HALF);
        fullMeal = restaurant.createMeal("BurgerFriesAndCoke", MealType.FULL);
        restaurant.addMeal(halfMeal);
        restaurant.addMeal(fullMeal);

        restaurant.addDishToMeal("BurgerAndFries", "Burger");
        restaurant.addDishToMeal("BurgerAndFries", "Fries");
        restaurant.addDishToMeal("BurgerFriesAndCoke", "Burger");
        restaurant.addDishToMeal("BurgerFriesAndCoke", "Fries");
        restaurant.addDishToMeal("BurgerFriesAndCoke", "Coke");

        // Create 3 orders
        // ===== Create Order 1 (customer1) 10 days ago=====
        o1 = new Order(customer, restaurant);
        o1.addItemToOrder("Burger", 3);
        o1.addItemToOrder("Fries", 1);
        o1.addItemToOrder("BurgerFriesAndCoke", 1);
        Calendar cal1 = Calendar.getInstance();
        cal1.add(Calendar.DAY_OF_MONTH, -10);
        o1.setOrderDate(cal1);
        CoreSystem.getOrders().add(o1);

        // ===== Create Order 2 (customer1) 5 days ago =====
        o2 = new Order(customer, restaurant);
        o2.addItemToOrder("BurgerFriesAndCoke", 2);
        Calendar cal2 = Calendar.getInstance();
        cal2.add(Calendar.DAY_OF_MONTH, -5);
        o2.setOrderDate(cal2);
        CoreSystem.getOrders().add(o2);

        // ===== Create Order 3 (customer2) 2 months ago=====
        o3 = new Order(customer2, restaurant);
        o3.addItemToOrder("Burger", 1);
        o3.addItemToOrder("Coke", 1);
        Calendar cal3 = Calendar.getInstance();
        cal3.add(Calendar.MONTH, -2);
        o3.setOrderDate(cal3);
        CoreSystem.getOrders().add(o3);
        
        // Capture System.out
        System.setOut(new PrintStream(outContent));
    }
    
    @AfterEach
    public void restoreStreams() {
        // Restore original System.out after each test to avoid side effects
        System.setOut(originalOut);
    }
    
    @Test
    @DisplayName("Test Compute TotalIncome ")
    void testComputeTotalIncome() {
    	double expectedTotalIncome = o1.computeFinalPrice() + o2.computeFinalPrice()+o3.computeFinalPrice(); // since no customer has a FidelityCard then Final Price = Price 
    	// o1 Price = 54 , o2 Price = 38 , o3 Price = 15 //107
    	assertEquals(107,manager.computeTotalIncome());
    	assertEquals(expectedTotalIncome,manager.computeTotalIncome());
    	
    	Calendar cal4 = Calendar.getInstance();
    	Calendar cal5 = Calendar.getInstance();
        cal4.add(Calendar.MONTH, -1);
        
        double expectedIncomeLastMonth = o1.computeFinalPrice() + o2.computeFinalPrice(); //92
        assertEquals(92,manager.computeTotalIncome(cal4,cal5));
        assertEquals(expectedIncomeLastMonth,manager.computeTotalIncome(cal4,cal5));
    	
    }
    
    @Test
    @DisplayName("Test Compute TotalProfit ")
    void testComputeTotalProfit() {
    	double expectedTotalProfit = o1.computeProfit() + o2.computeProfit()+o3.computeProfit(); // since no customer has a FidelityCard then Final Price = Price 
    	// o1 Profit = 21.2 , o2 Price = 16.4 , o3 Price = 9.5 // 47.1
    	assertEquals(expectedTotalProfit ,manager.computeTotalProfit());
    	
    	Calendar cal4 = Calendar.getInstance();
    	Calendar cal5 = Calendar.getInstance();
        cal4.add(Calendar.MONTH, -1);
        
        double expectedProfitLastMonth = o1.computeProfit() + o2.computeProfit(); // 
        assertEquals(expectedProfitLastMonth,manager.computeTotalProfit(cal4,cal5));
    	
    }
    
    @Test
    @DisplayName("Test TargetProfit Policy : ServiceFee")
    
    void testServiceFeeTargetProfitPolicy() throws UndefinedPolicyException, UnreachableTargetProfitException{
    	double targetProfit = 10;
        manager.setTargetProfitPolicy(TargetProfitPolicyType.SERVICEFEE, targetProfit);
        
        Calendar cal4 = Calendar.getInstance();
    	Calendar cal5 = Calendar.getInstance();
        cal4.add(Calendar.MONTH, -1);
        
        double expectedServiceFee = -(manager.computeTotalIncome(cal4,cal5)*CoreSystem.getMarkUpPercentage() - targetProfit - manager.computeNumberOfOrders(cal4, cal5)*CoreSystem.getDeliveryCost())/manager.computeNumberOfOrders(cal4, cal5);
        assertEquals(CoreSystem.getMarkUpPercentage(), CoreSystem.getServiceFee());
    }
}