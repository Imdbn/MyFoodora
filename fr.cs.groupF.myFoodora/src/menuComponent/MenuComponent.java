package menuComponent;


/** This is the abstract class representing MenuComponents
 * 
 */


public abstract class MenuComponent{
	protected int id;
	protected String name;
	protected boolean isVegetarian;
	protected double price;
	protected boolean isGlutenFree;
	

	/** 
	 * Constructor to initialize common properties
	 * @param id Unique identifier
	 * @param name Name of the component
	 * @param isVegetarian Vegetarian or not
	 * @param price
	 * @param isGlutenFree Gluten-free or not
	 */
	public MenuComponent(int id, String name, boolean isVegetarian, double price, boolean isGlutenFree) {
		MenuComponentIdGenerator idgen = MenuComponentIdGenerator.getInstance();
		this.id = idgen.getNextMenuComponentId();
		this.name = name;
		this.price = price;
		this.isVegetarian = isVegetarian;
		this.isGlutenFree = isGlutenFree;
		
	}
	/**
	 * Gets the MenuComponent's id
	 */
	
	public int getId() {
		return id;
	}
	
	/** 
	 * Gets the MenuComponent's name
	 */
	
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the MenuComponent's name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Gets the MenuComponent's price
	 */
	public double getprice() {
		return price;
	}
	
	/**
	 * Sets the MenuComponent's price
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
