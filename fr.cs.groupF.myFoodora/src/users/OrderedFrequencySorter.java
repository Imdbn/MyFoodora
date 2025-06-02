package users;

import java.util.*;
import menuMeals.*;

public class OrderedFrequencySorter {
	public ArrayList<FoodItem> sort(ArrayList<FoodItem> foodItems){
		foodItems.sort(new OrderedFrequencyComparator());
		return foodItems;
		
	}
}
