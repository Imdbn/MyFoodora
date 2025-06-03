package users;
import java.util.*;
import java.util.Calendar;
import java.util.Map;

import coreSystem.*;
import deliveryPolicy.*;
import exceptions.*;
import menuMeals.*;
import targetProfitPolicy.*;
import fidelityCards.*;

public class Manager extends User {

	private String surname;
	private CoreSystem coreSystem;
	


	public Manager(String name , String username , String password, String surname){
		super(name,username,password);
		this.surname = surname;
		this.coreSystem = CoreSystem.getInstance();

	}
	
	
	
	
	public String getSurname() {
		return surname;
	}


	public void setSurname(String surname) {
		this.surname = surname;
	}

	
	public void setTargetProfitPolicy(TargetProfitPolicyType targetProfitPolicy,double targetProfit) throws UndefinedPolicyException, UnreachableTargetProfitException{
		TargetProfitPolicy TPP = TargetProfitPolicyFactory.createTargetProfitPolicy(targetProfitPolicy);
		TPP.setParam(coreSystem, targetProfit);
		CoreSystem.setTargetProfitPolicy(TPP);
	}
	
	public void setDeliveryPolicy(DeliveryPolicyType deliveryPolicy) throws NoCourierIsAvailableException, UndefinedPolicyException{
		DeliveryPolicy DP = DeliveryPolicyFactory.createDeliveryPolicy(deliveryPolicy);
		CoreSystem.setDeliveryPolicy(DP);
	}


	public void associateCard(Customer customer, FidelityCard fidelityCard) {
		customer.setFidelityCard(fidelityCard);
	}
// Adding Users
	public void addCustomer(Customer customer) throws UserAlreadyExistsException {
		if(CoreSystem.getCustomers().get(customer.getUsername()) == null) {
		CoreSystem.getCustomers().put(customer.getUsername(),customer);
		System.out.println("Successfully added " + customer.getName() +" as a customer to the system.");	
		}
		else throw new UserAlreadyExistsException("Sorry but a customer already exists with username :" + customer.getUsername()+ ".");
	}
	public void addRestaurant(Restaurant restaurant) throws UserAlreadyExistsException {
		if(CoreSystem.getRestaurants().get(restaurant.getUsername()) == null) {
		CoreSystem.getRestaurants().put(restaurant.getUsername(),restaurant);
		System.out.println("Successfully added " + restaurant.getName() +" as a restaurant to the system.");	
		}
		else throw new UserAlreadyExistsException("Sorry but a restaurant already exists with username :" + restaurant.getUsername()+ ".");
	}
	public void addCourier(Courier courier) throws UserAlreadyExistsException {
		if(CoreSystem.getCouriers().get(courier.getUsername()) == null) {
		CoreSystem.getCouriers().put(courier.getUsername(),courier);
		System.out.println("Successfully added " + courier.getName() +" as a Courier to the system.");	
		}
		else throw new UserAlreadyExistsException("Sorry but a courier already exists with username :" + courier.getUsername()+ ".");
	}
	//Removing Users
	public void removeCustomer(Customer customer) throws NoUserExistsException {
		if(CoreSystem.getCustomers().get(customer.getUsername()) != null) {
		CoreSystem.getCustomers().remove(customer.getUsername());
		System.out.println("Successfully removed " + customer.getName() +" from the list of customers in the system");	
		}
		else throw new NoUserExistsException("Sorry but no customer exists with username :" + customer.getUsername()+ ".");
	}
	public void removeRestaurant(Restaurant restaurant) throws NoUserExistsException {
		if(CoreSystem.getRestaurants().get(restaurant.getUsername()) != null) {
		CoreSystem.getRestaurants().remove(restaurant.getUsername());
		System.out.println("Successfully removed " + restaurant.getName() +" from the list of restaurants in the system");	
		}
		else throw new NoUserExistsException("Sorry but no restaurant exists with username :" + restaurant.getUsername()+ ".");
	}
	
	public void removeCourier(Courier courier) throws NoUserExistsException {
		if(CoreSystem.getCouriers().get(courier.getUsername()) != null) {
		CoreSystem.getCouriers().remove(courier.getUsername());
		System.out.println("Successfully removed " + courier.getName() +" from the list of couriers in the system");	
		}
		else throw new NoUserExistsException("Sorry but no courier exists with username :" + courier.getUsername()+ ".");
	}
	
	
	

	public double computeTotalIncome() {
		double totalIncome = 0;
			for(Order order : CoreSystem.getOrders() ) {
				totalIncome += order.getFinalPrice();
			}
			return totalIncome;
	}
	
	public double computeTotalIncome(Calendar cal1, Calendar cal2) {
		double totalIncome = 0 ;
		for(Order order : CoreSystem.getOrders()){
			Calendar dateOfOrder = order.getOrderDate();
			if ((dateOfOrder.compareTo(cal1)>=0)&&(dateOfOrder.compareTo(cal2)<=0)){
				totalIncome += order.getFinalPrice() ;
			}
		}
		return totalIncome;
	}

	public double computeTotalProfit() {
		double totalProfit = 0;
			for(Order order : CoreSystem.getOrders() ) {
				totalProfit += order.getProfit();
			}
			return totalProfit;
	}
	
	public double computeTotalProfit(Calendar cal1, Calendar cal2) {
		double totalProfit = 0 ;
		for(Order order : CoreSystem.getOrders()){
			Calendar dateOfOrder = order.getOrderDate();
			if ((dateOfOrder.compareTo(cal1)>=0)&&(dateOfOrder.compareTo(cal2)<=0)){
				totalProfit += order.getProfit() ;
			}
		}
		return totalProfit;
	}
	public double computeAverageIncomePerCostumer() {
		double averageIncomePerCostumer = 0;
		if(CoreSystem.getCustomers().values().size() != 0 ) {
			averageIncomePerCostumer = computeTotalProfit()/CoreSystem.getCustomers().values().size();
		}
		return averageIncomePerCostumer;		
	}
	
	public double computeAverageIncomePerCostumer(Calendar cal1, Calendar cal2) {
		double averageIncomePerCostumer = 0 ;
		int numberOfOrders = 0 ;
		for(Order order : CoreSystem.getOrders()){
			Calendar dateOfOrder = order.getOrderDate();
			if ((dateOfOrder.compareTo(cal1)>=0)&&(dateOfOrder.compareTo(cal2)<=0)){
				numberOfOrders+= 1 ;
			}
		}
		if(numberOfOrders != 0) {
			averageIncomePerCostumer = computeTotalProfit(cal1,cal2)/numberOfOrders;
		}
		return averageIncomePerCostumer;
	}
	
	public int computeNumberOfOrders(Calendar cal1 , Calendar cal2) {
		int numberOfOrders = 0 ;
		for(Order order : CoreSystem.getOrders()){
			Calendar dateOfOrder = order.getOrderDate();
			if ((dateOfOrder.compareTo(cal1)>=0)&&(dateOfOrder.compareTo(cal2)<=0)){
				numberOfOrders+= 1 ;
			}
		}
		return numberOfOrders;
	}
	
	public void showSortedRestaurants() {
		RestaurantSorter restSorter = new RestaurantSorter();
		ArrayList<Restaurant> sortedRestaurants =restSorter.sort(CoreSystem.getRestaurants());
		System.out.println("Restaurants : ");
		for(Restaurant restaurant : sortedRestaurants ) {
			System.out.println(restaurant);
		}
	}

	public void showSortedCouriers() {
		CourierSorter courSorter = new CourierSorter();
		ArrayList<Courier> sortedCouriers =courSorter.sort(CoreSystem.getCouriers());
		System.out.println("Couriers : ");
		for(Courier courier : sortedCouriers ) {
			System.out.println(courier);
		}
	}
	
	public void showCustomers() {
		
		Map<String, Customer> customers = CoreSystem.getCustomers();
	
		for (Customer customer : customers.values()) {
			System.out.println(customer);
		}

	}

	public void showMenuItems (String restaurantName) throws RestaurantNotFoundException {
		if(CoreSystem.getRestaurants().containsKey(restaurantName)) {
			Menu menu = CoreSystem.getRestaurants().get(restaurantName).getMenu();
			for(Dish dish : menu.getItems().values()) {
				System.out.println(dish);
			}
		}
		else throw new RestaurantNotFoundException("The restaurant "+restaurantName+" does not exist!");
	}
	

	
	@Override
	public String toString() {
	    return "Manager{" +
	            "id=" + getId() +
	            ", name='" + getName() + '\'' +
	            ", username='" + getUsername() + '\'' +
	            ", surname='" + surname + '\'' +
	            '}';
	}







}