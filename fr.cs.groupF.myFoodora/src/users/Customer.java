package users;
import java.text.SimpleDateFormat;
import java.util.*;

import coreSystem.CoreSystem;
import exceptions.NoCourierIsAvailableException;
import exceptions.RestaurantNotFoundException;
import exceptions.UndefinedFidelityCardException;
import notificationService.*;
import notificationService.Observer;
import fidelityCards.*;
import menuMeals.*;

/** 
 * This is the Customer Class inheriting from its parent class User
 */
public class Customer extends User implements Observer {
	private String surname ;
	private String email ;
	private String phone ;
	private Coordinate address;
	private List<Order> orderHistory = new ArrayList<>(); 
	private boolean consensus;
	private FidelityCard fidelityCard ;
	private ContactOffers contactOffer = ContactOffers.EMAIL;
	
	
	/**
	 * Constructs a Customer with a given surname , email , phone number and address , along side the parent class attributes (name , username ,password)  
	 * @throws UndefinedFidelityCardException 
	 */
	public Customer(String name , String username , String password, String surname , String email, String phone , Coordinate address) throws UndefinedFidelityCardException {
		super(name, username,password);
		this.surname = surname;
		this.email = email;
		this.phone = phone;
		this.address = address;
		this.fidelityCard = FidelityCardFactory.createFidelityCard(FidelityCardType.BASIC);
	}
	public Customer(String name , String username , String password, String surname) throws UndefinedFidelityCardException {
		super(name, username,password);
		this.surname = surname;
		this.address = new Coordinate();
		this.fidelityCard = FidelityCardFactory.createFidelityCard(FidelityCardType.BASIC);
	}
	
	public String getSurname() {
		return surname;
	}
	
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public Coordinate getAddress() {
		return address;
	}
	
	public void setAddress(Coordinate address) {
		this.address = address;
	}
	
	public List<Order> getOrderHistory() {
		return orderHistory;
	}

	public void setOrderHistory(ArrayList<Order> orderHistory) {
		this.orderHistory = orderHistory;
	}
	
	
	public FidelityCard getFidelityCard() {
		return fidelityCard;
	}
	public void setFidelityCard(FidelityCard fidelityCard) {
		this.fidelityCard = fidelityCard;
	}
	public ContactOffers getContactOffer() {
		return contactOffer;
	}
	public void setContactOffer(ContactOffers contactOffer , String mailOrPhone) {
		this.contactOffer = contactOffer;
		switch(contactOffer) {
		case EMAIL :
			this.email = mailOrPhone;
		case PHONE :
			this.phone = mailOrPhone;
		}
	}
	
	public boolean isConsensus() {
		return consensus;
	}

	public void setConsensus(boolean consensus) {
		if (!this.consensus) {
			if(consensus) {
				NotificationService.getInstance().registerObserver(this);
			}
		}
		if(this.consensus) {
			if(!consensus){
				NotificationService.getInstance().removeObserver(this);
			}
		}
		
		this.consensus = consensus;
	}
	
	
	
	@Override
	public void update(Restaurant restaurant, Offer offer,Meal meal) {
		if(consensus) {
			switch(offer){
			case MEALOFTHEWEEK:
				switch(this.contactOffer) {
				case EMAIL:
					System.out.println("New email received at " + this.email + ": Restaurant " + restaurant.getName() + " has a new special offer: "
							+ " New meal of the week: " + meal.getName());
					break;
				case PHONE:
					System.out.println("New sms received at " + this.phone + ": Restaurant " + restaurant.getName() + " has a new special offer: "
							+ " New meal of the week: " + meal.getName());
					break;
				}
				break;
				
			case GENERICDISCOUNT:
				switch(this.contactOffer) {
				case EMAIL:
					System.out.println("New email received at " + this.email + ": Restaurant " + restaurant.getName() + " has a new special offer: "
							+ " New generic discount factor: " + restaurant.getGenericDiscount());
					break;
				case PHONE:
					System.out.println("New sms received at " + this.phone + ": Restaurant " + restaurant.getName() + " has a new special offer: "
							+ " New generic discount factor: " + restaurant.getGenericDiscount());
					break;
				}
				break;
				
			case SPECIALDISCOUNT:
				switch(this.contactOffer) {
				case EMAIL:
					System.out.println("New email received at " + this.email + ": Restaurant " + restaurant.getName() + " has a new special offer: "
							+ " New special discount factor: " + restaurant.getSpecialDiscount());
					break;
				case PHONE:
					System.out.println("New sms received at " + this.phone + ": Restaurant " + restaurant.getName() + " has a new special offer: "
							+ " New special discount factor: " + restaurant.getSpecialDiscount());
					break;
				}
				break;
			}
		}
	}
	
	
	
	
	
	

	/**
	 * Place Order method that allows the Customer to place an order
	 */
	public Order placeOrder(String restaurantName) throws RestaurantNotFoundException {
		if(CoreSystem.getRestaurants().containsKey(restaurantName)) {
			return new Order(this,CoreSystem.getRestaurants().get(restaurantName));
			
		}
		else throw new RestaurantNotFoundException("The restaurant "+restaurantName+" does not exist!");
		
	}
	
	// pay Order that allows the customer to add point to his fidelity card if it happens to be a pOint fidelity card
	public void payOrder(double price) {

	    System.out.println("You have paid "+ price +"€ for your order.");
	    System.out.println("");

	    if (this.fidelityCard instanceof PointFidelityCard) {
	        PointFidelityCard pointCard = (PointFidelityCard) this.fidelityCard;
	        double numberAddedPts = pointCard.computeNumberAddedPoints(price);
	        pointCard.updatePoints(price);

	        if (numberAddedPts>=1) {
	        	String pointsMessage = "You earned " + numberAddedPts + " " + (numberAddedPts == 1 ? "fidelity point" : "fidelity points") + "!";
	        	System.out.println(pointsMessage);
	        }
	        
	    }
	}
	
	public void endOrder(Order order) throws NoCourierIsAvailableException{
	    System.out.println("Finalizing your order...");
	    order.submitOrder(); 
	    double price = order.getFinalPrice(); 
	    

	    if (price > 0) {
	        System.out.println("Payment in process...");
	       
	        this.payOrder(price); 
	    } else if (price == 0) {
	        System.out.println("No payment needed. Your order seems to be free!");
	    } else {
	        System.out.println("Error: Negative price encountered. Please contact support.");
	    }
	}
	
	
	/** 
	 * registerFidelityCard method that allows the Customer to register his fidelity card
	 * @throws UndefinedFidelityCardException 
	 */
	public void registerFidelityCard(FidelityCardType cardType) throws UndefinedFidelityCardException {
		FidelityCard card = FidelityCardFactory.createFidelityCard(cardType);
		if (card!=null)
			this.setFidelityCard(card);
	}
	/**
	 * unregisterFidelityCard method that allows the Customer to unregister his fidelity card
	 * @throws UndefinedFidelityCardException 
	 */
	public void unregisterFidelityCard() throws UndefinedFidelityCardException {
		this.setFidelityCard(FidelityCardFactory.createFidelityCard(FidelityCardType.BASIC));
	}
	
	
	public void displayFidelityCard() {
		System.out.println(this.fidelityCard);
	}
	
	public void displayOrders() {
		   
	    System.out.println("Completed Orders (" + this.orderHistory.size()+")");

	    if (this.orderHistory.isEmpty()) {
	        System.out.println("No completed orders yet.");
	        return;
	    }

	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 

	    for (Order order : this.orderHistory) {
	        
	        System.out.println("\nOrder #" + id + ":");
	        
	        Calendar orderDate = order.getOrderDate();
	        String formattedDate = dateFormat.format(orderDate.getTime());
	        System.out.println("Delivered • " + formattedDate + " • Total: €" + String.format("%.2f", order.getFinalPrice()));
	        
	        System.out.println(order.toString());
	    }
	}
	
	
	@Override
	public String toString() {
	    return "Customer{" +
	            "id=" + getId() +
	            ", name='" + getName() + '\'' +
	            ", username='" + getUsername() + '\'' +
	            ", surname='" + surname + '\'' +
	            ", email='" + email + '\'' +
	            ", phone='" + phone + '\'' +
	            ", address=" + address +
	            '}';
	}
	
	


	
	
	
}
