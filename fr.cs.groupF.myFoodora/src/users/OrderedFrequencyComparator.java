package users;

import java.util.Comparator;

import menuMeals.*;

public class OrderedFrequencyComparator implements Comparator<FoodItem>{
	@Override
	public int compare(FoodItem item1,FoodItem item2) {
		return item1.getOrderedFrequency()-item2.getOrderedFrequency();
	}
	
}

