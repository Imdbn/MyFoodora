package menuMeals;

import java.util.HashMap;
import exceptions.*;

public class Menu {
	
	//==========================================================================================================================================================================================================
	//Fields
	//==========================================================================================================================================================================================================

	private HashMap<String,Dish> items;
	
	//==========================================================================================================================================================================================================
	//Constructors
	//==========================================================================================================================================================================================================

	public Menu() {
		this.items = new HashMap<String,Dish>();
	}
	public Menu(HashMap<String,Dish> items) {
		this.items = items;
	}
	
	
	//==========================================================================================================================================================================================================
	//Getters and setters
	//==========================================================================================================================================================================================================

	
	public HashMap<String,Dish> getItems() {
		return items;
	}
	

	public void setItems(HashMap<String,Dish> items) {
		this.items = items;
	}
	
	
	//==========================================================================================================================================================================================================
	//Methods
	//==========================================================================================================================================================================================================

	public void addDish(Dish item) throws ItemAlreadyExistsException {
		if(!items.containsValue(item)) {
			items.put(item.getName(), item);
		}
		else throw new ItemAlreadyExistsException("Sorry, but the item that you are trying to add to the menu, already exists.");
	}
	public void addDish(String itemName) throws ItemAlreadyExistsException {
		if(!items.containsKey(itemName)) {
			items.put(itemName,new Dish(itemName));
		}
		else throw new ItemAlreadyExistsException("Sorry, but the item that you are trying to add to the menu, already exists.");
	}
	
	public void removeDish(FoodItem item) throws ItemNotFoundException{
		if(items.containsValue(item)) {
			items.remove(item.getName());
		}
		else throw new ItemNotFoundException("Sorry, but the item that you are trying to remove from the menu, doesn't exist.");
	}
	
	public void removeDish(String itemName) throws ItemNotFoundException{
		if(items.containsKey(itemName)) {
			items.remove(itemName);
		}
		else throw new ItemNotFoundException("Sorry, but the item that you are trying to remove from the menu, doesn't exist.");
	}
	
	
	public Dish getDish(String itemName) throws ItemNotFoundException {
		if(items.containsKey(itemName)) {
			return  items.get(itemName);
			
		}
		else throw new ItemNotFoundException("Sorry, but the item that you are trying to get from the menu, doesn't exist.");
	}
	
	
	
	
	
	//==========================================================================================================================================================================================================
	//ToString
	//==========================================================================================================================================================================================================

	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("Menu Items:\n");
	    if (items.isEmpty()) {
	        sb.append("No items in the menu.");
	    } else {
	        for (FoodItem item : items.values()) {
	            sb.append("- ").append(item.toString()).append("\n");
	        }
	    }
	    return sb.toString();
	}
}
