package test;

import coreSystem.*;
import exceptions.NoMatchingCredentialsException;
import exceptions.PermissionDeniedException;
import users.UserType;
import users.*;

public class Testing {
    public static void main(String[] args){
        // Get singleton instance
        CoreSystem core = CoreSystem.getInstance();
        CoreSystem.addDefaultManagers();
        // Create a test customer
        Customer imad = new Customer("imad" ,"imad", "1234", "imdb");
        try {
            // Attempt login
            String result = core.login("Admin", "1234");
            System.out.println(result); // Should print: Successfully logged in as CUSTOMER
        } catch (NoMatchingCredentialsException e) {
            System.out.println("Login failed: " + e.getMessage());
        }
        // Add Imad to the customer map
        try {
        	core.addCustomer(imad);
        	System.out.println("succesfuly added "+ imad.getName() +" to user" );
        }  catch(PermissionDeniedException e) {
        	System.out.println("Permission Error: "+ e.getMessage());
        }
        System.out.println(CoreSystem.getCustomers());
        System.out.println(CoreSystem.getManagers());
        

        
    }
}