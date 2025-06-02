package coreSystem;
import java.util.*;


import users.*; 
import exceptions.*;
import targetProfitPolicy.*;
import DeliveryPolicy.*;

public class CoreSystem {
	//==========================================================================================================================================================================================
	//Fields
	// Creating a Singleton instance of the app
	private static CoreSystem instance = null;
	
	private static Map<String,Customer> customers = new HashMap<>();
	private static Map<String,Manager> managers = new HashMap<>();
	private static Map<String,Courier> couriers = new HashMap<>() ;
	private static Map<String,Restaurant> restaurants = new HashMap<>();
	private static List<Order> orders = new ArrayList<>();
	private static Optional<User> currentUser ;
	private static Optional<UserType> currentUserType ;
	private static TargetProfitPolicy targetProfitPolicy ;
	private static DeliveryPolicy deliveryPolicy;
	
	private static double serviceFee ;
	private static double markUpPercentage;
	private static double deliveryCost;

	
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
	


	public static Map<String, Customer> getCustomers() {
		return customers;
	}



	public static void setCustomers(Map<String, Customer> customers) {
		CoreSystem.customers = customers;
	}



	public static Map<String, Manager> getManagers() {
		return managers;
	}



	public static void setManagers(Map<String, Manager> managers) {
		CoreSystem.managers = managers;
	}



	public static Map<String, Courier> getCouriers() {
		return couriers;
	}



	public static void setCouriers(Map<String, Courier> couriers) {
		CoreSystem.couriers = couriers;
	}



	public static Map<String, Restaurant> getRestaurants() {
		return restaurants;
	}



	public static void setRestaurants(Map<String, Restaurant> restaurants) {
		CoreSystem.restaurants = restaurants;
	}


	public static List<Order> getOrders() {
		return orders;
	}



	public static void setOrders(List<Order> orders) {
		CoreSystem.orders = orders;
	}

	public static double getMarkUpPercentage() {
		return markUpPercentage;
	}



	public static void setMarkUpPercentage(double markUpPercentage) {
		CoreSystem.markUpPercentage = markUpPercentage;
	}



	public static double getDeliveryCost() {
		return deliveryCost;
	}



	public static void setDeliveryCost(double deliveryCost) {
		CoreSystem.deliveryCost = deliveryCost;
	}



	public static void setServiceFee(double serviceFee) {
		CoreSystem.serviceFee = serviceFee;
	}
	
	public static double getServiceFee() {
		return serviceFee;
	}
	
	public static TargetProfitPolicy getTargetProfitPolicy() {
		return targetProfitPolicy;
	}



	public static void setTargetProfitPolicy(TargetProfitPolicy targetProfitPolicy) {
		CoreSystem.targetProfitPolicy = targetProfitPolicy;
	}
	
	public static DeliveryPolicy getDeliveryPolicy() {
		return deliveryPolicy;
	}



	public static void setDeliveryPolicy(DeliveryPolicy deliveryPolicy) {
		CoreSystem.deliveryPolicy = deliveryPolicy;
	}
	
	public static ArrayList<Courier> getOnDutyCouriers(Map<String, Courier> couriersMap) {
        ArrayList<Courier> onDutyList = new ArrayList<>();
        for (Courier courier : couriersMap.values()) {
            if (courier.isOnDuty()) {
                onDutyList.add(courier);
            }
        }
        return onDutyList;
	}
	
	
//==============================================================================================================================================================================================
//Methods
	
	



	public static void addDefaultManagers() {
		Manager CEO = new Manager("CEO", "Admin", "1234", "Imad");
        Manager deputy = new Manager("Deputy", "admin", "1234", "Farah");
    	managers.put(CEO.getUsername(),CEO);
    	managers.put(deputy.getUsername(),deputy);
    	System.out.println("Successfully added Default Managers to the system");
	}
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

		
	
	private <T extends User> boolean tryLogin(Map<String,T> users,String username , String password,UserType typeOfUser) {
		
		T user = users.get(username);
		if (user != null && user.getHashedPassword().equals(PasswordHasher.hashPassword(password))) {
			currentUser = Optional.of(user);
			currentUserType = Optional.of(typeOfUser);
			return true;
		}
		return false;
	}
	
	//Logout
	
	public void logout() {
		currentUser = Optional.empty();
		currentUserType = Optional.empty();
	}
	
	
	//\\ Manager Methods
	public void addUser(User user) throws PermissionDeniedException , IllegalArgumentException, UserAlreadyExistsException  {
		if (currentUserType.orElse(UserType.GUEST) == UserType.MANAGER) {
			Manager manager = (Manager) currentUser.get();
			if (user instanceof Customer) {
		        manager.addCustomer((Customer) user);
		    } else if (user instanceof Restaurant) {
		        manager.addRestaurant((Restaurant) user);
		    } else if (user instanceof Courier) {
		        manager.addCourier((Courier) user);
		    } else {
		        throw new IllegalArgumentException("Unsupported user type: " + user.getClass().getSimpleName());
		    }
		}
		else 
			 throw new PermissionDeniedException("Sorry, But you don't have the permission for said method, only Users of type Manager have Permission to use it");
	}
	
	public void removeUser(User user) throws PermissionDeniedException ,IllegalArgumentException, NoUserExistsException   {
		if (currentUserType.orElse(UserType.GUEST) == UserType.MANAGER) {
			Manager manager = (Manager) currentUser.get();
			if (user instanceof Customer) {
		        manager.removeCustomer((Customer) user);
		    } else if (user instanceof Restaurant) {
		        manager.removeRestaurant((Restaurant) user);
		    } else if (user instanceof Courier) {
		        manager.removeCourier((Courier) user);
		    } else {
		        throw new IllegalArgumentException("Unsupported user type: " + user.getClass().getSimpleName());
		    }
		}
		else 
			 throw new PermissionDeniedException("Sorry, But you don't have the permission for said method, only Users of type Manager have Permission to use it");
	
	}
	
	public void addOrder(Order order) {
		orders.add(order);	
	}
	
	
	
	
	//Computing total Income
	public double computeTotalIncome() throws PermissionDeniedException{
		if (currentUserType.orElse(UserType.GUEST) == UserType.MANAGER) {
			return ((Manager)currentUser.get()).computeTotalIncome();		
		}
		else 
			 throw new PermissionDeniedException("Sorry, But you don't have the permission for said method, only Users of type Manager have Permission to use it");
	}
	
	public double computeTotalIncome(Calendar cal1, Calendar cal2) throws PermissionDeniedException{
		if (currentUserType.orElse(UserType.GUEST) == UserType.MANAGER) {
			return ((Manager)currentUser.get()).computeTotalIncome(cal1, cal2);		
		}
		else 
			 throw new PermissionDeniedException("Sorry, But you don't have the permission for said method, only Users of type Manager have Permission to use it");
	}
	
	public double computeTotalIncomeLastMonth() throws PermissionDeniedException{
		if (currentUserType.orElse(UserType.GUEST) == UserType.MANAGER) {
			Calendar now = Calendar.getInstance();
			Calendar lastMonth = (Calendar) now.clone();
			lastMonth.add(Calendar.MONTH, -1); 
			return ((Manager)currentUser.get()).computeTotalIncome(lastMonth, now);		
		}
		else 
			 throw new PermissionDeniedException("Sorry, But you don't have the permission for said method, only Users of type Manager have Permission to use it");
	}
	
	//Computing total Profit
	public double computeTotalProfit() throws PermissionDeniedException{
		if (currentUserType.orElse(UserType.GUEST) == UserType.MANAGER) {
			return ((Manager)currentUser.get()).computeTotalProfit();		
		}
		else 
			 throw new PermissionDeniedException("Sorry, But you don't have the permission for said method, only Users of type Manager have Permission to use it");
	}
	public double computeTotalProfit(Calendar cal1 , Calendar cal2) throws PermissionDeniedException{
		if (currentUserType.orElse(UserType.GUEST) == UserType.MANAGER) {
			return ((Manager)currentUser.get()).computeTotalProfit(cal1 , cal2);		
		}
		else 
			 throw new PermissionDeniedException("Sorry, But you don't have the permission for said method, only Users of type Manager have Permission to use it");
	}
	// Compute Average Income Per Customer
	
	public double computeAverageIncomePerCostumer() throws PermissionDeniedException{
		if (currentUserType.orElse(UserType.GUEST) == UserType.MANAGER) {
			return ((Manager)currentUser.get()).computeAverageIncomePerCostumer();		
		}
		else 
			 throw new PermissionDeniedException("Sorry, But you don't have the permission for said method, only Users of type Manager have Permission to use it");
	}
	
	public double computeAverageIncomePerCostumer(Calendar cal1 , Calendar cal2) throws PermissionDeniedException{
		if (currentUserType.orElse(UserType.GUEST) == UserType.MANAGER) {
			return ((Manager)currentUser.get()).computeAverageIncomePerCostumer(cal1 , cal2);		
		}
		else 
			 throw new PermissionDeniedException("Sorry, But you don't have the permission for said method, only Users of type Manager have Permission to use it");
	}
	// Compute Number of Orders Last month
	public int computeNumberOfOrdersLastMonth() throws PermissionDeniedException{
		if (currentUserType.orElse(UserType.GUEST) == UserType.MANAGER) {
			Calendar now = Calendar.getInstance();
			Calendar lastMonth = (Calendar) now.clone();
			lastMonth.add(Calendar.MONTH, -1); 
			return ((Manager)currentUser.get()).computeNumberOfOrders(lastMonth , now);		
		}
		else 
			 throw new PermissionDeniedException("Sorry, But you don't have the permission for said method, only Users of type Manager have Permission to use it");
	}
	
	
	//Shows the Sorted Couriers with regards to delivery counter
	
	
	public void showSortedCouriers() throws PermissionDeniedException{
		if (currentUserType.orElse(UserType.GUEST) == UserType.MANAGER) {
			((Manager)currentUser.get()).showSortedCouriers();
		}
		else 
			throw new PermissionDeniedException("Sorry, But you don't have the permission for said method, only Users of type Manager have Permission to use it");
	}
	
	//Shows the Sorted Restaurants with regards to order counter

	
	public void showSortedRestaurants() throws PermissionDeniedException{
		if (currentUserType.orElse(UserType.GUEST) == UserType.MANAGER) {
			((Manager)currentUser.get()).showSortedRestaurants();
		}
		else 
			throw new PermissionDeniedException("Sorry, But you don't have the permission for said method, only Users of type Manager have Permission to use it");
	}







	

}
