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
	
	private static double serviceFee =2 ;
	private static double markUpPercentage = 0.3;
	private static double deliveryCost = 5;

	
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
	
	public static Optional<User> getCurrentUser() {
		return currentUser;
	}



	public static void setCurrentUser(Optional<User> currentUser) {
		CoreSystem.currentUser = currentUser;
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
	



	public static void my_Foodora_ini() throws ItemAlreadyExistsException, UndefinedTypeException, BadMealCompositionCreationException, ItemNotFoundException, UndefinedFidelityCardException {
		Manager CEO = new Manager("CEO", "ceo", "123456789", "Imad");
        Manager deputy = new Manager("Deputy", "admin", "1234", "Farah");
    	managers.put(CEO.getUsername(),CEO);
    	managers.put(deputy.getUsername(),deputy);
    	
    	Restaurant chickenPlace = new Restaurant("ChickenPlace", "ChickenPlace", "1234");
    	
    	chickenPlace.addDish(FoodFactory.createDish("ShrimpCocktail", false, 6.0, true, DishCategory.STARTER));
        chickenPlace.addDish(FoodFactory.createDish("GrilledChicken", false, 12.0, true, DishCategory.MAINDISH));
        chickenPlace.addDish(FoodFactory.createDish("IceCream", false, 5.0, true, DishCategory.DESSERT));
        
        Meal halfMeal = chickenPlace.createMeal("ChickenAndIceCream", MealType.HALF);
        Meal fullMeal = chickenPlace.createMeal("ChickenSpecial", MealType.FULL);
        
        chickenPlace.addMeal(fullMeal);
        chickenPlace.addMeal(halfMeal);
        
        chickenPlace.addDishToMeal("ChickenAndIceCream","GrilledChicken");
        chickenPlace.addDishToMeal("ChickenAndIceCream","IceCream");
        
        chickenPlace.addDishToMeal("ChickenSpecial", "GrilledChicken");
        chickenPlace.addDishToMeal("ChickenSpecial", "IceCream");
        chickenPlace.addDishToMeal("ChickenSpecial", "ShrimpCocktail");
        
        chickenPlace.setMealPrice(fullMeal);
        chickenPlace.setMealPrice(halfMeal);
        
        restaurants.put(chickenPlace.getUsername(),chickenPlace);
        
        Restaurant burgerPlace = new Restaurant("BurgerPlace", "BurgerPlace", "1234");

        
        burgerPlace.addDish(FoodFactory.createDish("Burger", false, 10.0, false, DishCategory.MAINDISH));
        burgerPlace.addDish(FoodFactory.createDish("Fries", false, 5.0, false, DishCategory.STARTER));
        burgerPlace.addDish(FoodFactory.createDish("Coke", false, 5.0, false, DishCategory.DESSERT));

        
        Meal halfMeal_ = burgerPlace.createMeal("BurgerAndFries", MealType.HALF);
        Meal fullMeal_ = burgerPlace.createMeal("BurgerFriesAndCoke", MealType.FULL);
        burgerPlace.addMeal(fullMeal_);
        burgerPlace.addMeal(halfMeal_);
        
        
        burgerPlace.addDishToMeal("BurgerAndFries", "Burger");
        burgerPlace.addDishToMeal("BurgerFriesAndCoke", "Burger");
        burgerPlace.addDishToMeal("BurgerAndFries", "Fries");
        burgerPlace.addDishToMeal("BurgerFriesAndCoke", "Fries");
        burgerPlace.addDishToMeal("BurgerFriesAndCoke", "Coke");
        
        burgerPlace.setMealPrice(fullMeal_);
        burgerPlace.setMealPrice(halfMeal_);
        restaurants.put(burgerPlace.getUsername(),burgerPlace);
        
        Customer customer = new Customer("Hmad", "hmad_lvista", "1234", "vista");
        Customer customer2 = new Customer("meryam", "Meryam_queen", "abcd", "city");
        Courier courier = new Courier("Aziz", "aziz", "1234", "azoz", "0232134");
        Courier courier2 = new Courier("habil", "Hobo", "1234", "hoho", "0232134");
        
        courier.setOnDuty(true);
        
        customers.put(customer.getUsername(),customer);
        customers.put(customer2.getUsername(), customer2);
        couriers.put(courier.getUsername(), courier);
        couriers.put(courier2.getUsername(), courier2);
        
        
    	
	}
	
	
	
	



	public static Optional<UserType> getCurrentUserType() {
		return currentUserType;
	}



	public static void setCurrentUserType(Optional<UserType> currentUserType) {
		CoreSystem.currentUserType = currentUserType;
	}



	public double computeTotalIncomeLastMonth() {
		Calendar now = Calendar.getInstance();
		Calendar lastMonth = (Calendar) now.clone();
		lastMonth.add(Calendar.MONTH, -1); 
		Manager manager = new Manager("CEO", "Admin", "1234", "Imad");
		return manager.computeTotalIncome(lastMonth, now);		
}
	
	// Compute Number of Orders Last month
		public int computeNumberOfOrdersLastMonth() {
			
				Calendar now = Calendar.getInstance();
				Calendar lastMonth = (Calendar) now.clone();
				lastMonth.add(Calendar.MONTH, -1); 
				Manager manager = new Manager("CEO", "Admin", "1234", "Imad");
				return manager.computeNumberOfOrders(lastMonth , now);		
			
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
    public void removeConsensus() throws PermissionDeniedException {
        if (currentUserType.orElse(UserType.GUEST) == UserType.CUSTOMER ) {
            Customer customer = (Customer) currentUser.get();
            customer.setConsensus(false);
        } else {
            throw new PermissionDeniedException(
                "Sorry, But you don't have the permission for said method, only Users of type Customer have Permission to use it."
            );
        }
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
            Customer customer = (Customer) currentUser.get();
            if (customerOrders.contains(order)) {
                customer.endOrder(order);
                customerOrders.remove(order);  // Remove order from customerOrders after ending it
            } else {
                throw new ItemNotFoundException("Sorry there is no such Order for the current logged in customer.");
            }
        } else {
            throw new PermissionDeniedException("Sorry, But you don't have the permission for said method, only Users of type Customer have Permission to use it");
        }
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
    		((Restaurant)currentUser.get()).removeFromSpecialOffer(mealName);
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
