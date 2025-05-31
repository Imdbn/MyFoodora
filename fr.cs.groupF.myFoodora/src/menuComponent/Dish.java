package menuComponent;

/**
 * This class represents a single dish item in a restaurant's menu.
 * A dish is a basic component that has no sub-elements (unlike meals),
 * and is classified by a specific category (starter, main dish, or dessert).
 */

public class Dish extends MenuComponent {
	public enum DishCategory {
		STARTER, MAINDISH, DESSERT
	}
	public DishCategory category; 
	
	public Dish(int id, String name, boolean isVegetarian, double price, boolean isGlutenFree, DishCategory category) {
        super(id, name, isVegetarian, price, isGlutenFree);
        this.category = category;
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
	    public String toString(){
	    	return "Dish{\n" +
	    		       "id=" + id + ",\n" +
	    		       "name='" + name + '\'' + ",\n" +
	    		       "category=" + category + ",\n" +
	    		       "vegetarian=" + isVegetarian + ",\n" +
	    		       "glutenFree=" + isGlutenFree + ",\n" +
	    		       "price=" + price + ",\n" +
	    		       '}';

	              
	    }
	
}