package users;

import java.util.*;

public class Order {
	// ========================================================================================================================================================================================
	// Fields
	private int id;
	private Calendar orderDate = Calendar.getInstance();
	//private HashMap<Meal, Integer> orderedMeals = new HashMap<>();
	//private HashMap<Dish, Integer> orderedDishes = new HashMap<>();
	private double price;
	private double totalPrice;
	private double profit;
	private Restaurant restaurant;
	private Customer customer;
	private Courier courier;
	
	
	// ========================================================================================================================================================================================
	// Constructors 
	public Order(Customer customer , Restaurant restaurant) {
		UserIdGenerator idgen = UserIdGenerator.getInstance();
		this.id = idgen.getNextUserId();
		this.orderDate = Calendar.getInstance();
		this.customer = customer;
		this.restaurant = restaurant;
	}
		
	// ========================================================================================================================================================================================
	// Getters and Setters
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

//	public HashMap<Meal, Integer> getOrderedMeals() {
//		return orderedMeals;
//	}
//
//	public void setOrderedMeals(HashMap<Meal, Integer> orderedMeals) {
//		this.orderedMeals = orderedMeals;
//	}
//
//	public HashMap<Dish, Integer> getOrderedDishes() {
//		return orderedDishes;
//	}
//
//	public void setOrderedDishes(HashMap<Dish, Integer> orderedDishes) {
//		this.orderedDishes = orderedDishes;
//	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
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
	
	
	
	
	// ========================================================================================================================================================================================
	// Methods
	
	
	
	
	/**
	 * 
	 * @param foodItemName
	 * @param quantity
	 */
	public void addItemToOrder(String foodItemName, Integer quantity) {
		// TODO - implement Order.addItemToOrder
		throw new UnsupportedOperationException();
	}

//	public double priceBeforeDiscounts() {
//		double price = 0;
//		for (Map.Entry<Meal,Integer> entry : orderedMeals.entrySet() ) {
//			price += entry.getKey().getPrice()*entry.getValue();
//		}
//		
//		for (Map.Entry<Dish, Integer> entry : orderedDishes.entrySet() ) {
//			price += entry.getKey().getPrice()*entry.getValue();
//		}
//		
//	}

//	public double priceAfterDiscounts() {
//		
//	}

	/**
	 * 
	 * @param totalPrice
	 */
	public void submitOrder(double totalPrice) {
		// TODO - implement Order.submitOrder
		throw new UnsupportedOperationException();
	}

}