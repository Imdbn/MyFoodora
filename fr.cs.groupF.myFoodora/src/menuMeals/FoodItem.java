package menuMeals;


/** This is the abstract class representing FoodItem
 * 
 */


public abstract class FoodItem{
	protected int id;
	protected String name;
	protected boolean isVegetarian;
	protected double price;
	protected boolean isGlutenFree;
	
	
	

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
	
	public FoodItem() {
		FoodItemIdGenerator idgen = FoodItemIdGenerator.getInstance();
		this.id = idgen.getNextFoodItemId();
	  
	}

	/**
	 * Gets the FoodItem's id
	 */
	
	public int getId() {
		return id;
	}
	
	/** 
	 * Gets the FoodItem's name
	 */
	
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the FoodItem's name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Gets the FoodItem's price
	 */
	public double getprice() {
		return price;
	}
	
	/**
	 * Sets the FoodItem's price
	 */
	public void setprice(double price) {
		this.price = price;
	}
	
	/** 
	 * Checks whether the component is vegetarian.
	 */
	
	public boolean isVegetarian(){
		return isVegetarian;
	}
	/** 
	 * 	Sets the vegetarian status of the component.
	 */
	
	public void setisVegetarian(boolean isVegetarian) {
		this.isVegetarian = isVegetarian;
	}
	
	/** 
	 * Checks whether the component is gluten-free.
	 */
	
	public boolean isGlutenFree(){
		return isGlutenFree;
	
	}

	/** 
	 * 	Sets the gluten-free status of the component.
	 */
	
	public void setisGlutenFree(boolean isGlutenFree) {
		this.isGlutenFree = isGlutenFree;
	}
	
	
	
}
