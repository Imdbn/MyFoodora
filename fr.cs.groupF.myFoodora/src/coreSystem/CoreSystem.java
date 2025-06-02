package coreSystem;
import java.util.*;


import deliveryPolicy.*;
import users.*; 
import exceptions.*;
import targetProfitPolicy.*;
import fidelityCards.*;
import menuMeals.*;
import menuMeals.Dish.DishCategory;

public class CoreSystem {
	//===========================================================================================================================================================================================================
	//Fields
	//===========================================================================================================================================================================================================
	// Creating a Singleton instance of the app
	private static CoreSystem instance = null;
	
	private static Map<String,Customer> customers = new HashMap<>();
	private static Map<String,Manager> managers = new HashMap<>();
	private static Map<String,Courier> couriers = new HashMap<>() ;
	private static Map<String,Restaurant> restaurants = new HashMap<>();
	private static List<Order> orders = new ArrayList<>();
	private static List<Order> customerOrders = new ArrayList<>();
	private static Optional<User> currentUser ;
	private static Optional<UserType> currentUserType ;
	private static TargetProfitPolicy targetProfitPolicy = new TargetProfitServiceFee();  
	private static DeliveryPolicy deliveryPolicy = new Fastest();
	
	private static double serviceFee ;
	private static double markUpPercentage;
	private static double deliveryCost;

	
	//===========================================================================================================================================================================================================
	// Constructors
	//===========================================================================================================================================================================================================
	private CoreSystem() {
		currentUser = Optional.empty();
		currentUserType = Optional.empty();
		
        
		
	}
	
	
	
	//===========================================================================================================================================================================================================
	//===========================================================================================================================================================================================================
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
	
	
	//===========================================================================================================================================================================================================
	// Getters and Setters
	//===========================================================================================================================================================================================================


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
	
	public static List<Order> getCustomerOrders() {
		return customerOrders;
	}



	public static void setCustomerOrders(List<Order> customerOrders) {
		CoreSystem.customerOrders = customerOrders;
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
	
	
	//===========================================================================================================================================================================================================
	//Methods
	//===========================================================================================================================================================================================================	
	



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
		customerOrders = new ArrayList<>();
	}
	
	//===========================================================================================================================================================================================================
	//\\ Manager Methods
	//===========================================================================================================================================================================================================
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
	
	public void setDeliveryPolicyType(DeliveryPolicyType deliveryPolicyType) throws PermissionDeniedException, NoCourierIsAvailableException, UndefinedPolicyException {
    	if (currentUserType.orElse(UserType.GUEST) == UserType.MANAGER) {
    		Manager manager = (Manager) currentUser.get();
    		manager.setDeliveryPolicy(deliveryPolicyType);
    	} else {
    		throw new PermissionDeniedException("Only managers can perform this action.");
    	}
    }
	
	public void setTargetProfitPolicyType(TargetProfitPolicyType targetProfitPolicyType , double target) throws PermissionDeniedException, UndefinedPolicyException, UnreachableTargetProfitException {
    	if (currentUserType.orElse(UserType.GUEST) == UserType.MANAGER) {
    		Manager manager = (Manager) currentUser.get();
    		manager.setTargetProfitPolicy(targetProfitPolicyType, target);
    	} else {
    		throw new PermissionDeniedException("Only managers can perform this action.");
    	}
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

	
	public void showCustomers() throws PermissionDeniedException{
		if (currentUserType.orElse(UserType.GUEST) == UserType.MANAGER) {
			((Manager)currentUser.get()).showCustomers();
		}
		else 
			throw new PermissionDeniedException("Sorry, But you don't have the permission for said method, only Users of type Manager have Permission to use it");
	}
	
	
	public void showMenuItem(String restaurantName) throws PermissionDeniedException, RestaurantNotFoundException{
		if (currentUserType.orElse(UserType.GUEST) == UserType.MANAGER) {
			((Manager)currentUser.get()).showMenuItems(restaurantName);
		}
		else 
			throw new PermissionDeniedException("Sorry, But you don't have the permission for said method, only Users of type Manager have Permission to use it");
	}


    public void associateCard(String userName, FidelityCardType cardType) throws PermissionDeniedException, UndefinedFidelityCardException {
    	if (currentUserType.orElse(UserType.GUEST) == UserType.MANAGER) {
    		FidelityCard card = FidelityCardFactory.createFidelityCard(cardType);
    		Customer customer = customers.get(userName);
    		((Manager)currentUser.get()).associateCard(customer, card);
    	}
    	else
    		throw new PermissionDeniedException("Sorry, But you don't have the permission for said method, only Users of type Manager have Permission to use it");
    }
    
    public void showSortedHalfMeals(String restaurantName) throws  PermissionDeniedException{
    	if (currentUserType.orElse(UserType.GUEST) == UserType.MANAGER)  {
    		restaurants.get(restaurantName).showSortedHalfMeals();
    	}
    	else
    		throw new PermissionDeniedException("Sorry, But you don't have the permission for said method, only Users of type Manager have Permission to use it");
    }
    
    public void showSortedHalfMeals(Restaurant restaurant) throws  PermissionDeniedException{
    	if (currentUserType.orElse(UserType.GUEST) == UserType.MANAGER)  {
    		restaurant.showSortedHalfMeals();
    	}
    	else
    		throw new PermissionDeniedException("Sorry, But you don't have the permission for said method, only Users of type Manager have Permission to use it");
    }
    public void showSortedFullMeals(String restaurantName) throws  PermissionDeniedException{
    	if (currentUserType.orElse(UserType.GUEST) == UserType.MANAGER)  {
    		restaurants.get(restaurantName).showSortedFullMeals();
    	}
    	else
    		throw new PermissionDeniedException("Sorry, But you don't have the permission for said method, only Users of type Manager have Permission to use it");
    }
    public void showSortedFullMeals(Restaurant restaurant) throws  PermissionDeniedException{
    	if (currentUserType.orElse(UserType.GUEST) == UserType.MANAGER)  {
    		restaurant.showSortedFullMeals();
    	}
    	else
    		throw new PermissionDeniedException("Sorry, But you don't have the permission for said method, only Users of type Manager have Permission to use it");
    }
    
    public void showSortedDishes(String restaurantName) throws  PermissionDeniedException{
    	if (currentUserType.orElse(UserType.GUEST) == UserType.MANAGER)  {
    		restaurants.get(restaurantName).showSortedDishes();
    	}
    	else
    		throw new PermissionDeniedException("Sorry, But you don't have the permission for said method, only Users of type Maneger have Permission to use it");
    }
    public void showSortedDishes(Restaurant restaurant) throws  PermissionDeniedException{
    	if (currentUserType.orElse(UserType.GUEST) == UserType.MANAGER)  {
    		restaurant.showSortedDishes();
    	}
    	else
    		throw new PermissionDeniedException("Sorry, But you don't have the permission for said method, only Users of type Manager have Permission to use it");
    }

	//===========================================================================================================================================================================================================
    //\\Customer Methods
    //===========================================================================================================================================================================================================

    
    public void setConsensusMail(String mail) throws PermissionDeniedException{
    	if (currentUserType.orElse(UserType.GUEST) == UserType.CUSTOMER) {
    		((Customer)currentUser.get()).setConsensus(true);
    		((Customer)currentUser.get()).setContactOffer(ContactOffers.EMAIL,mail);
    	}
    	else throw new PermissionDeniedException("Sorry, But you don't have the permission for said method, only Users of type Customer have Permission to use it");
    }


    public void setConsensusPhone(String phone) throws PermissionDeniedException{
    	if (currentUserType.orElse(UserType.GUEST) == UserType.CUSTOMER) {
    		((Customer) currentUser.get()).setConsensus(true);
    		((Customer)currentUser.get()).setContactOffer(ContactOffers.PHONE,phone);
    	}
    	else throw new PermissionDeniedException("Sorry, But you don't have the permission for said method, only Users of type Customer have Permission to use it");
    }

    public Order placeOrder(String restaurantName) throws PermissionDeniedException, RestaurantNotFoundException{
    	if (currentUserType.orElse(UserType.GUEST) == UserType.CUSTOMER) {
    		Order order = ((Customer)currentUser.get()).placeOrder(restaurantName);
    		customerOrders.add(order);
    		return order;
    	}
    	else throw new PermissionDeniedException("Sorry, But you don't have the permission for said method, only Users of type Customer have Permission to use it");
    }
    
    public void addItemToOrder(Order order,String foodItemName, Integer quantity) throws ItemNotFoundException, PermissionDeniedException {
    	if (currentUserType.orElse(UserType.GUEST) == UserType.CUSTOMER) {
    		if(customerOrders.contains(order)) {
    			order.addItemToOrder(foodItemName, quantity);
    		}
    		else throw new ItemNotFoundException("Sorry there is no such Order for the current logged in customer.");
    		
    	}
    	else throw new PermissionDeniedException("Sorry, But you don't have the permission for said method, only Users of type Customer have Permission to use it");
    }
    
    public void endOrder(Order order) throws NoCourierIsAvailableException, ItemNotFoundException, PermissionDeniedException {
    	if (currentUserType.orElse(UserType.GUEST) == UserType.CUSTOMER) {
    		if(customerOrders.contains(order)) {
    			((Customer)currentUser.get()).endOrder(order);;
    		}
    		else throw new ItemNotFoundException("Sorry there is no such Order for the current logged in customer.");
    		
    	}
    	else throw new PermissionDeniedException("Sorry, But you don't have the permission for said method, only Users of type Customer have Permission to use it");
    }

    public void displayHistoryOrders() throws PermissionDeniedException {
 		if (currentUserType.orElse(UserType.GUEST) == UserType.CUSTOMER) {
 			((Customer)currentUser.get()).displayOrders();
 		}
 		else
 		    throw new PermissionDeniedException("Sorry, But you don't have the permission for said method, only Users of type Customer have Permission to use it");
     }

	
    
    public void registerFidelityCard(FidelityCardType cardType) throws PermissionDeniedException, UndefinedFidelityCardException {
		if (currentUserType.orElse(UserType.GUEST) == UserType.CUSTOMER) {
			((Customer)currentUser.get()).registerFidelityCard(cardType);
		}
		else
		    throw new PermissionDeniedException("Sorry, But you don't have the permission for said method, only Users of type Customer have Permission to use it");
    }
    
    public void unregisterFidelityCard() throws PermissionDeniedException, UndefinedFidelityCardException {
		if (currentUserType.orElse(UserType.GUEST) == UserType.CUSTOMER) {
			((Customer)currentUser.get()).unregisterFidelityCard();
		}
		else
		    throw new PermissionDeniedException("Sorry, But you don't have the permission for said method, only Users of type Customer have Permission to use it");
    }
    
    public void displayFidelityCardInfo() throws PermissionDeniedException {
		if (currentUserType.orElse(UserType.GUEST) == UserType.CUSTOMER) {
			((Customer)currentUser.get()).displayFidelityCard();;
		}
		else
		    throw new PermissionDeniedException("Sorry, But you don't have the permission for said method, only Users of type Customer have Permission to use it");
    }
    
    
    
    
	//===========================================================================================================================================================================================================
	//\\ Restaurant Methods
	//===========================================================================================================================================================================================================
    
    
    public void showMenuAndMeals() throws PermissionDeniedException {
    	if (currentUserType.orElse(UserType.GUEST) == UserType.RESTAURANT) {
	    	((Restaurant)currentUser.get()).displayRestaurant();
    	}
    	else
    		throw new PermissionDeniedException("Sorry, But you don't have the permission for said method, only Users of type Restaurant have Permission to use it");
    	
    }
    
    
    public void setSpecialDiscount(String discount) throws PermissionDeniedException, IllegalArgumentException{
    	if (currentUserType.orElse(UserType.GUEST) == UserType.RESTAURANT){
            double discountFactor;
            try {
                discountFactor = Double.parseDouble(discount);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid discount format. Please enter a double value.");
            }
            

            if (discountFactor <= 0 || discountFactor >= 1) {
                throw new IllegalArgumentException("The discount factor must be greater than 0 and less than 1.");
            }	
	    	((Restaurant)currentUser.get()).setSpecialDiscount(discountFactor);
    	}
    	else
    		throw new PermissionDeniedException("Sorry, But you don't have the permission for said method, only Users of type Restaurant have Permission to use it");
    	
    }
    
    public void setGenericDiscount(String discount) throws PermissionDeniedException, IllegalArgumentException{
    	if (currentUserType.orElse(UserType.GUEST) == UserType.RESTAURANT) {
    		
            double discountFactor;
            try {
                discountFactor = Double.parseDouble(discount);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid discount format. Please enter a double value.");
            }
            

            if (discountFactor <= 0 || discountFactor >= 1) {
                throw new IllegalArgumentException("The discount factor must be greater than 0 and less than 1.");
            }	
	    	((Restaurant)currentUser.get()).setGenericDiscount(discountFactor);
    	}
    	else
    		throw new PermissionDeniedException("Sorry, But you don't have the permission for said method, only Users of type Restaurant have Permission to use it");
    	
    }
    
    public void addDishRestaurantMenu(String dishName,DishCategory category,boolean isVegetarian,boolean isGlutenFree, double price) throws PermissionDeniedException, ItemAlreadyExistsException {
    	if (currentUserType.orElse(UserType.GUEST) == UserType.RESTAURANT)  {
	    	((Restaurant)currentUser.get()).addDish(dishName, isVegetarian, isGlutenFree, price, category);
    	}
    	else
    		throw new PermissionDeniedException("Sorry, But you don't have the permission for said method, only Users of type Restaurant have Permission to use it");
    	
    	
    }
    public void addDishRestaurantMenu(String dishName) throws PermissionDeniedException, ItemAlreadyExistsException {
    	if (currentUserType.orElse(UserType.GUEST) == UserType.RESTAURANT)  {
	    	((Restaurant)currentUser.get()).addDish(dishName);
    	}
    	else
    		throw new PermissionDeniedException("Sorry, But you don't have the permission for said method, only Users of type Restaurant have Permission to use it");
    	
    	
    }
    public void addDishRestaurantMenu(Dish dish) throws PermissionDeniedException, ItemAlreadyExistsException {
    	if (currentUserType.orElse(UserType.GUEST) == UserType.RESTAURANT)  {
	    	((Restaurant)currentUser.get()).addDish(dish);
    	}
    	else
    		throw new PermissionDeniedException("Sorry, But you don't have the permission for said method, only Users of type Restaurant have Permission to use it");
    	
    	
    }
    


    
    public void createMeal (String mealName, MealType mealType) throws PermissionDeniedException, UndefinedTypeException, ItemAlreadyExistsException {
    	if (currentUserType.orElse(UserType.GUEST) == UserType.RESTAURANT)  {
    		 ((Restaurant)currentUser.get()).addMeal(mealName, mealType);
    	}
    	else {
    		throw new PermissionDeniedException("Sorry, But you don't have the permission for said method, only Users of type Restaurant have Permission to use it");
    	}
    }
    
    public void createMeal (String mealName, MealType mealType,boolean isVegetarian, boolean isGlutenFree) throws PermissionDeniedException, ItemAlreadyExistsException, UndefinedTypeException {
    	if (currentUserType.orElse(UserType.GUEST) == UserType.RESTAURANT)  {
    		((Restaurant)currentUser.get()).addMeal(mealName, mealType, isVegetarian, isGlutenFree);
    	}
    	else {
    		throw new PermissionDeniedException("Sorry, But you don't have the permission for said method, only Users of type Restaurant have Permission to use it");
    	}
    }
    
    
    public void addDish2Meal( String mealName , String dishName) throws  PermissionDeniedException, BadMealCompositionCreationException, ItemNotFoundException {
    	if (currentUserType.orElse(UserType.GUEST) == UserType.RESTAURANT)  {
				((Restaurant)currentUser.get()).addDishToMeal(mealName, dishName);	
    		}
    	else
    		throw new PermissionDeniedException("Sorry, But you don't have the permission for said method, only Users of type Restaurant have Permission to use it");
    }
    
    public void showMeal(String mealName) throws  PermissionDeniedException, ItemNotFoundException{
    	if (currentUserType.orElse(UserType.GUEST) == UserType.RESTAURANT)  {
    		((Restaurant)currentUser.get()).showMeal(mealName);
    	}
    	else
    		throw new PermissionDeniedException("Sorry, But you don't have the permission for said method, only Users of type Restaurant have Permission to use it");
    }
    
    

    	
    public void setSpecialOffer(String mealName) throws  PermissionDeniedException, ItemNotFoundException{
    	if (currentUserType.orElse(UserType.GUEST) == UserType.RESTAURANT)  {
    		((Restaurant)currentUser.get()).setSpecialOffer(mealName);
    	}
    	else
    		throw new PermissionDeniedException("Sorry, But you don't have the permission for said method, only Users of type Restaurant have Permission to use it");
    }
    
    
    public void removeFromSpecialOffer(String mealName) throws  PermissionDeniedException, ItemNotFoundException{
    	if (currentUserType.orElse(UserType.GUEST) == UserType.RESTAURANT)  {
    		((Restaurant)currentUser.get()).setSpecialOffer(mealName);
    	}
    	else
    		throw new PermissionDeniedException("Sorry, But you don't have the permission for said method, only Users of type Restaurant have Permission to use it");
    }
    
    public void showSortedHalfMeals() throws  PermissionDeniedException{
    	if (currentUserType.orElse(UserType.GUEST) == UserType.RESTAURANT)  {
    		((Restaurant)currentUser.get()).showSortedHalfMeals();
    	}
    	else
    		throw new PermissionDeniedException("Sorry, But you don't have the permission for said method, only Users of type Restaurant have Permission to use it");
    }
    public void showSortedFullMeals() throws  PermissionDeniedException{
    	if (currentUserType.orElse(UserType.GUEST) == UserType.RESTAURANT)  {
    		((Restaurant)currentUser.get()).showSortedFullMeals();
    	}
    	else
    		throw new PermissionDeniedException("Sorry, But you don't have the permission for said method, only Users of type Restaurant have Permission to use it");
    }
    
    public void showSortedDishes() throws  PermissionDeniedException{
    	if (currentUserType.orElse(UserType.GUEST) == UserType.RESTAURANT)  {
    		((Restaurant)currentUser.get()).showSortedDishes();
    	}
    	else
    		throw new PermissionDeniedException("Sorry, But you don't have the permission for said method, only Users of type Restaurant have Permission to use it");
    }
    
    
    
    //===========================================================================================================================================================================================================
  	//\\ Courier Methods
  	//===========================================================================================================================================================================================================
    
    public void setOnDuty(String username, boolean isOnDuty) throws PermissionDeniedException {
    	if (currentUserType.orElse(UserType.GUEST) == UserType.COURIER) {
    		Courier courier = (Courier) currentUser.get();
    		courier.setOnDuty(isOnDuty);
    	}
    	else {
    		throw new PermissionDeniedException("Sorry, But you don't have the permission for said method, only Users of type Courier have Permission to use it");
    	}   	
    }
    
 	}
