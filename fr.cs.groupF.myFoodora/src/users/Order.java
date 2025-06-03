package users;

import java.util.*;
import java.util.Map.Entry;

import coreSystem.*;
import exceptions.ItemNotFoundException;
import exceptions.NoCourierIsAvailableException;
import menuMeals.*;
import fidelityCards.*;

public class Order {
	// ========================================================================================================================================================================================
	// Fields
	//=========================================================================================================================================================================================
	private int id;
	private CoreSystem coreSystem;
	private Calendar orderDate = Calendar.getInstance();
	private HashMap<Meal, Integer> orderedMeals = new HashMap<>();
	private HashMap<Dish, Integer> orderedDishes = new HashMap<>();
	private double price = 0;
	private double finalPrice = 0;
	private double profit = 0;
	private Restaurant restaurant;
	private Customer customer;
	private Courier courier;
	
	
	// ========================================================================================================================================================================================
	// Constructors 
	//=========================================================================================================================================================================================
	public Order(Customer customer , Restaurant restaurant) {
		OrderIdGenerator idgen = OrderIdGenerator.getInstance();
		this.id = idgen.getNextOrderId();
		this.orderDate = Calendar.getInstance();
		this.customer = customer;
		this.restaurant = restaurant;
		this.coreSystem = CoreSystem.getInstance();
		this.orderedDishes = new HashMap<>();
		this.orderedMeals = new HashMap<>();
	}
		
	// ========================================================================================================================================================================================
	// Getters and Setters
	//=========================================================================================================================================================================================
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Calendar getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Calendar orderDate) {
		this.orderDate = orderDate;
	}

	

	public void setPrice(double price) {
		this.price = price;
	}
	public double getPrice() {
		return price;
	}

	public double getFinalPrice() {
		return finalPrice;
	}

	public void setFinalPrice(double totalPrice) {
		this.finalPrice = totalPrice;
	}

	public double getProfit() {
		return profit;
	}

	public void setProfit(double profit) {
		this.profit = profit;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Courier getCourier() {
		return courier;
	}

	public void setCourier(Courier courier) {
		this.courier = courier;
	}
	
	public HashMap<Meal, Integer> getOrderedMeals() {
		return orderedMeals;
	}

	public void setOrderedMeals(HashMap<Meal, Integer> orderedMeals) {
		this.orderedMeals = orderedMeals;
	}

	public HashMap<Dish, Integer> getOrderedDishes() {
		return orderedDishes;
	}

	public void setOrderedDishes(HashMap<Dish, Integer> orderedDishes) {
		this.orderedDishes = orderedDishes;
	}
	
	
	// ========================================================================================================================================================================================
	// Methods
	//=========================================================================================================================================================================================
	
	
	
	
	

	/**
	 * 
	 * @param foodItemName
	 * @param quantity
	 * @throws ItemNotFoundException 
	 */
	public void addItemToOrder(String foodItemName, Integer quantity) throws ItemNotFoundException {
		if(this.restaurant.getMenu().getItems().containsKey(foodItemName)) {
			Dish dish = this.restaurant.getMenu().getItems().get(foodItemName);
			orderedDishes.put(dish,quantity);
		}
		else if(this.restaurant.getMeals().containsKey(foodItemName)) {
			Meal meal = this.restaurant.getMeals().get(foodItemName);
			orderedMeals.put(meal, quantity);
		}
		else throw new ItemNotFoundException("The Item that you tried to add to your Order doesn't exist");
		
	}
	
	public double computePrice() {
		double price = 0;
		
		for(Entry<Meal, Integer> entry : orderedMeals.entrySet()) {
			price +=entry.getKey().getPrice()*entry.getValue();
		}
		for(Entry<Dish, Integer> entry : orderedDishes.entrySet()) {
			price +=entry.getKey().getPrice()*entry.getValue();
		}
		
		this.setPrice(price);
		return price;
	}
	
	public double computeFinalPrice() {
		FidelityCard fidelityCard = this.customer.getFidelityCard();
		
		double finalPrice = fidelityCard.computeOrderPrice(this);
		
		this.setFinalPrice(finalPrice);
		return finalPrice;
	}
	
	
	
	
	public double computeProfit() {
		double profit = this.computeFinalPrice()*CoreSystem.getMarkUpPercentage() + CoreSystem.getServiceFee() - CoreSystem.getDeliveryCost();
		this.setProfit(profit);
		return profit;
	}

	

	public void submitOrder() throws NoCourierIsAvailableException {
		CoreSystem.getRestaurants().get(restaurant.getName()).incrementOrderCounter();
		CoreSystem.getDeliveryPolicy().allocateCourier(this);
    	coreSystem.addOrder(this);
    	this.customer.getOrderHistory().add(this);
    	this.computePrice();
    	this.computeProfit();
    	this.computeFinalPrice();
    	
    	
    	/*
    	 * Update ordered frequency of chosen items of this submitted order!
    	 */
    	
    	for (Entry<Meal, Integer> entry : orderedMeals.entrySet())  {
    		entry.getKey().incrementOrderedFrequency();
    	}
    	
    	for (Entry<Dish, Integer> entry : orderedDishes.entrySet()) {
    		entry.getKey().incrementOrderedFrequency();
    	}
    	
    	
    	// Display the order, along side the price 
    	
    	System.out.println("Order : ");
    	System.out.println(this);
    	System.out.println("The price of the order, including service fee, markup percentage and deliveryCost is : "+ this.finalPrice);
			
	}
	
	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("Order ID: ").append(id).append("\n");
	    sb.append("Order Date: ").append(this.orderDate.getTime()).append("\n");
	    sb.append("Restaurant: ").append(restaurant.getName()).append("\n");
	    
	    
	    int itemIndex = 1;  // Proper use of an integer for indexing

	    if (!orderedMeals.isEmpty()) {
	        sb.append("\nMeals Ordered:\n");
	        for (Entry<Meal, Integer> entry : orderedMeals.entrySet()) {
	            sb.append("  ").append(itemIndex++).append(". ").append(entry.getKey().getName())
	              .append(" - Quantity: ").append(entry.getValue())
	              .append(", Price per unit: ").append(entry.getKey().getPrice())
	              .append("\n");
	        }
	    }
	    
	    if (!orderedDishes.isEmpty()) {
	        sb.append("\nFood Items Ordered:\n");
	        for (Entry<Dish, Integer> entry : orderedDishes.entrySet()) {
	            sb.append("  ").append(itemIndex++).append(". ").append(entry.getKey().getName())
	              .append(" - Quantity: ").append(entry.getValue())
	              .append(", Price per, unit: ").append(entry.getKey().getPrice())
	              .append("\n");
	        }
	    }
	    return sb.toString();
	}

}