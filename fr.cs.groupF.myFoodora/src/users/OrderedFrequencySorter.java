package users;

import java.util.*;
import menuMeals.*;

public class OrderedFrequencySorter {
	public ArrayList<FoodItem> sort(ArrayList<FoodItem> foodItems){
		OrderedFrequencyComparator comp = new OrderedFrequencyComparator();
		foodItems.sort(comp.reversed());
		return foodItems;
		
	}
}
