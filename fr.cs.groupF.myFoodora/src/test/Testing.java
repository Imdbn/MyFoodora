package test;

import coreSystem.*;
import exceptions.NoMatchingCredentialsException;
import exceptions.PermissionDeniedException;
import exceptions.UserAlreadyExistsException;
import users.*;
import java.util.Calendar;

public class Testing {
    public static void main(String[] args) throws IllegalArgumentException, UserAlreadyExistsException, PermissionDeniedException {
        // Init system
        CoreSystem core = CoreSystem.getInstance();
        CoreSystem.addDefaultManagers();
        try {
            String result = core.login("Admin", "1234");
            System.out.println(result);
        } catch (NoMatchingCredentialsException e) {
            System.out.println("Login failed: " + e.getMessage());
        }
        // Add customer
        Customer imad = new Customer("imad", "imad", "1234", "imdb");
        try {
            core.addUser(imad);
            System.out.println("Successfully added " + imad.getName());
        } catch (PermissionDeniedException e) {
            System.out.println("Permission Error: " + e.getMessage());
        }

        // Create restaurant and add to system
        Restaurant rest = new Restaurant("Le Test" , "test","1234");
        core.addUser(rest);
        
        Courier cour = new Courier("hey","heyl","1234","da","213123412");

        // Create orders with different dates
        Calendar now = Calendar.getInstance();

        Order order1 = new Order(imad, rest);
        order1.setProfit(10.0);
        order1.setFinalPrice(50.0);
        order1.setOrderDate((Calendar) now.clone()); // today
        core.addOrder(order1);

        Calendar yesterday = (Calendar) now.clone();
        yesterday.add(Calendar.DAY_OF_MONTH, -1);

        Order order2 = new Order(imad, rest);
        order2.setProfit(5.0);
        order2.setFinalPrice(30.0);
        order2.setOrderDate(yesterday);
        core.addOrder(order2);

        Calendar twoDaysAgo = (Calendar) now.clone();
        twoDaysAgo.add(Calendar.DAY_OF_MONTH, -2);

        Order order3 = new Order(imad, rest);
        order3.setProfit(8.0);
        order3.setFinalPrice(40.0);
        order3.setOrderDate(twoDaysAgo);
        core.addOrder(order3);

        // Log in as manager
        try {
            core.login("Admin", "1234");
        } catch (NoMatchingCredentialsException e) {
            System.out.println("Login failed: " + e.getMessage());
        }

        // Test compute methods
        try {
            System.out.println("Total Income: " + core.computeTotalIncome());
            System.out.println("Total Profit: " + core.computeTotalProfit());

            // Date range: yesterday to now
            System.out.println("Income [Yesterday-Today]: " + core.computeTotalIncome(twoDaysAgo, now));
            System.out.println("Profit [Yesterday-Today]: " + core.computeTotalProfit(yesterday, now));

            System.out.println("Avg Income per Customer: " + core.computeAverageIncomePerCostumer());
            System.out.println("Avg Income per Customer [Yesterday-Today]: " +
                    core.computeAverageIncomePerCostumer(yesterday, now));
            System.out.println(CoreSystem.getManagers().get("Admin"));
        } catch (PermissionDeniedException e) {
            System.out.println("Manager-only method error: " + e.getMessage());
        }
    }
}
