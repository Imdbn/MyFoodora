package coreSystem;
import java.util.*;
import users.*; 
import exceptions.*;

public class CoreSystem {
	//==========================================================================================================================================================================================
	//Fields
	// Creating a Singleton instance of the app
	private static CoreSystem instance = null;
	
	private static List<Customer> customers = new ArrayList<>();
	private static List<Manager> managers = new ArrayList<>();
	private static List<Courier> couriers = new ArrayList<>() ;
	private static List<Restaurant> restaurants = new ArrayList<>();
	private static List<Order> orders = new ArrayList<>();
	private static Optional<User> currentUser ;
	private static Optional<UserType> currentUserType ;
	
	private double serviceFee ;
	private double markUpPrecentage;
	

	private double deliveryCost;

	
	//===========================================================================================================================================================================================
	// Constructors
	private CoreSystem() {
		currentUser = Optional.empty();
		currentUserType = Optional.empty();
	}
	
	
	
	//==========================================================================================================================================================================================
	// Instance provider
	/**
	 * get instance method to have fulfill the singleton design pattern allowing us to have one single instance of CoreSystem
	 * 
	 */
	public static CoreSystem getInstance() {
		if(instance == null) {
			instance = new CoreSystem();
		}
		return instance;
	}
	
	
	//==========================================================================================================================================================================================
	// Getters and Setters
	public static List<Customer> getCustomers() {
		return customers;
	}

	public static void setCustomers(List<Customer> customers) {
		CoreSystem.customers = customers;
	}

	public static List<Manager> getManagers() {
		return managers;
	}

	public static void setManagers(List<Manager> managers) {
		CoreSystem.managers = managers;
	}

	public static List<Courier> getCouriers() {
		return couriers;
	}

	public static void setCouriers(List<Courier> couriers) {
		CoreSystem.couriers = couriers;
	}

	public static List<Restaurant> getRestaurants() {
		return restaurants;
	}

	public static void setRestaurants(List<Restaurant> restaurants) {
		CoreSystem.restaurants = restaurants;
	}

	public static List<Order> getOrders() {
		return orders;
	}

	public static void setOrders(List<Order> orders) {
		CoreSystem.orders = orders;
	}

	public double getServiceFee() {
		return serviceFee;
	}

	public void setServiceFee(double serviceFee) {
		this.serviceFee = serviceFee;
	}

	public double getMarkUpPrecentage() {
		return markUpPrecentage;
	}

	public void setMarkUpPrecentage(double markUpPrecentage) {
		this.markUpPrecentage = markUpPrecentage;
	}

	public double getDeliveryCost() {
		return deliveryCost;
	}

	public void setDeliveryCost(double deliveryCost) {
		this.deliveryCost = deliveryCost;
	}
	
//==============================================================================================================================================================================================
//Methods
	
	
	
// Login
	public String login(String username, String password) throws NoMatchingCredentialsException {
	    if (tryLogin(managers, username, password, UserType.MANAGER)) {
	        return "Successfully logged in as MANAGER";
	    } else if (tryLogin(customers, username, password, UserType.CUSTOMER)) {
	        return "Successfully logged in as CUSTOMER";
	    } else if (tryLogin(couriers, username, password, UserType.COURIER)) {
	        return "Successfully logged in as COURIER";
	    } else if (tryLogin(restaurants, username, password, UserType.RESTAURANT)) {
	        return "Successfully logged in as RESTAURANT";
	    } else {
	        throw new NoMatchingCredentialsException("There is no user with the given credentials. Please retry.");
	    }
	}

		
	
	private <T extends User> boolean tryLogin(List<T> users,String username , String password,UserType typeofuser) {
		for (T user : users) {
			if(user.getUsername().equals(username) && user.getPassword().equals(password)) {
				currentUser = Optional.of(user);
				currentUserType = Optional.of(typeofuser);
				return true;
			}
		}
		return false;
	}
	
	//Logout
	
	public void logout() {
		currentUser = Optional.empty();
		currentUserType = Optional.empty();
	}
	
	
}
