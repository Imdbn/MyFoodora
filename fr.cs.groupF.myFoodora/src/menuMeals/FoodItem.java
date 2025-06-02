package menuMeals;


/** This is the abstract class representing FoodItem
 * 
 */


public abstract class FoodItem{
	protected int id;
	protected String name;
	protected boolean isVegetarian = false;
	protected double price;
	protected boolean isGlutenFree = false;
	protected int orderedFrequency;
	
	
	

	/** 
	 * Constructors to initialize common properties
	 * @param id Unique identifier
	 * @param name Name of the component
	 * @param isVegetarian Vegetarian or not
	 * @param price
	 * @param isGlutenFree Gluten-free or not
	 */

	public FoodItem( String name, boolean isVegetarian, double price, boolean isGlutenFree) {
		FoodItemIdGenerator idgen = FoodItemIdGenerator.getInstance();
		this.id = idgen.getNextFoodItemId();
		this.name = name;
		this.price = price;
		this.isVegetarian = isVegetarian;
		this.isGlutenFree = isGlutenFree;
		
	}
	
	
	public FoodItem( String name, boolean isVegetarian, boolean isGlutenFree) {
		FoodItemIdGenerator idgen = FoodItemIdGenerator.getInstance();
		this.id = idgen.getNextFoodItemId();
		this.name = name;
		this.isVegetarian = isVegetarian;
		this.isGlutenFree = isGlutenFree;
		
	}
	
	public FoodItem(String name) {
		FoodItemIdGenerator idgen = FoodItemIdGenerator.getInstance();
		this.id = idgen.getNextFoodItemId();
		this.name = name;
	  
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public boolean isVegetarian() {
		return isVegetarian;
	}


	public void setVegetarian(boolean isVegetarian) {
		this.isVegetarian = isVegetarian;
	}


	public double getPrice() {
		return price;
	}


	public void setPrice(double price) {
		this.price = price;
	}


	public boolean isGlutenFree() {
		return isGlutenFree;
	}


	public void setGlutenFree(boolean isGlutenFree) {
		this.isGlutenFree = isGlutenFree;
	}


	public int getOrderedFrequency() {
		return orderedFrequency;
	}


	public void setOrderedFrequency(int orderedFrequency) {
		this.orderedFrequency = orderedFrequency;
	}

	public void incrementOrderedFrequency() {
		this.orderedFrequency++;
	}
	
	
}
