package menuMeals;

/**
 * This class represents a single dish item in a restaurant's menu.
 * A dish is a basic component that has no sub-elements (unlike meals),
 * and is classified by a specific category (starter, main dish, or dessert).
 */

public class Dish extends FoodItem {
	public enum DishCategory {
		STARTER, MAINDISH, DESSERT
	}
	private DishCategory category; 
	
	// Default constructor
    public Dish(String name) {
        super(name);
    }
	
	public Dish( String name, boolean isVegetarian, double price, boolean isGlutenFree, DishCategory category) {
        super( name, isVegetarian, price, isGlutenFree);
        this.category = category;
    }
	
	public Dish(String name,boolean isVegetarian, boolean isGlutenFree) {
		super(name,isVegetarian,isGlutenFree);
	}
	
	/**
     * Gets the category of the dish.
     *
     * @return dish category (STARTER, MAINDISH, or DESSERT)
     */
	 public DishCategory getCategory() {
	        return category;
	    }
	 
	 /**
	     * Sets the category of the dish.
	     *
	     * @param category dish category to set
	     */
	 public void setCategory(DishCategory category) {
	     this.category = category;
	    }
	 @Override
	 public String toString() {
	     return String.format(
	         "\n🍽️ Dish: %s (ID: %d)\n" +
	         "────────────────────────────\n" +
	         "📦 Category     : %s\n" +
	         "🥗 Vegetarian   : %s\n" +
	         "🌾 Gluten-Free  : %s\n" +
	         "💰 Price        : %.2f €\n",
	         getName(), getId(),
	         category,
	         isVegetarian() ? "Yes" : "No",
	         isGlutenFree() ? "Yes" : "No",
	         price
	     );
	 }

}