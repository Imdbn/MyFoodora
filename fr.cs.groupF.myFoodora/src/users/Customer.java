package users;
import java.util.*;
/** 
 * This is the Customer Class inheriting from its parent class User
 */
public class Customer extends User {
	private String surname ;
	private String email ;
	private String phone ;
	private Coordinate address;
	private List<Order> orderHistory = new ArrayList<>(); 
	private boolean consensus;
	
	
	/**
	 * Constructs a Customer with a given surname , email , phone number and address , along side the parent class attributes (name , username ,password)  
	 */
	public Customer(String name , String username , String password, String surname , String email, String phone , Coordinate address) {
		super(name, username,password);
		this.surname = surname;
		this.email = email;
		this.phone = phone;
		this.address = address;
	}
	public Customer(String name , String username , String password, String surname) {
		super(name, username,password);
		this.surname = surname;
		this.address = new Coordinate();
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
	public boolean isConsensus() {
		return consensus;
	}

	public void setConsensus(boolean consensus) {
		this.consensus = consensus;
	}
	
	/**
	 * Place Order method that allows the Customer to place an order
	 */
	public void placeOrder() {
		
		
	}
	
	/** 
	 * registerFidelityCard method that allows the Customer to register his fidelity card
	 */
	public void registerFidelityCard() {
		
	}
	/**
	 * unregisterFidelityCard method that allows the Customer to unregister his fidelity card
	 */
	public void unregisterFidelityCard() {
		
	}
	/**
	 * accessOrderHistory method allows the customer to access his order history
	 */
	public void accessOrderHistory() {
		
	}
	/**
	 * accessFidelityPoints method allows the customer to see his Fidelity Points 
	 */
	public void accessFidelityPoints() {
		
	}

	
	
	
}
