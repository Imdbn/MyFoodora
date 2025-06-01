package users;
import menuMeals.*;

public class Restaurant extends User{
	private Coordinate location ;
	private Menu menu;
//	private Meal meals;
	private double genericDiscount = 0.05;
	private double specialDiscount = 0.1;
	private int orderCounter = 0 ;
	
	public Restaurant(String name, String username, String password, Coordinate location, Menu menu, double genericDiscount, double specialDiscount) {
		super(name, username, password);
		this.location = location;
		this.menu = menu;
		this.genericDiscount = genericDiscount;
		this.specialDiscount = specialDiscount;
	}
	
	public Restaurant(String name, String username, String password ) {
		super(name, username,password);
		this.menu = new Menu();
		this.location = new Coordinate();
	}

	public double getGenericDiscount() {
		return genericDiscount;
	}
	

	public double getSpecialDiscount() {
		return specialDiscount;
	}
	/**
	 * establishes the generic Discount
	 */
	public void setGenericDiscount(double genericDiscount) {
		this.genericDiscount = genericDiscount;
	}
	/**
	 * establishes the special Discount
	 */

	public void setSpecialDiscount(double specialDiscount) {
		this.specialDiscount = specialDiscount;
	}

	public Coordinate getLocation() {
		return location;
	}

	public void setLocation(Coordinate location) {
		this.location = location;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}
	
	public int getOrderCounter() {
		return orderCounter;
	}

	public void setOrderCounter(int orderCounter) {
		this.orderCounter = orderCounter;
	}

	
	public void removeDish() {
		
	}
	public void createMeal() {
		
	}
	
	@Override
	public String toString() {
	    return "Restaurant{" +
	            "id=" + getId() +
	            ", name='" + getName() + '\'' +
	            ", username='" + getUsername() + '\'' +
	            ", location=" + location +
	            ", genericDiscount=" + genericDiscount +
	            ", specialDiscount=" + specialDiscount +
	            ", menu=" + menu +
	            '}';
	}

	
}
