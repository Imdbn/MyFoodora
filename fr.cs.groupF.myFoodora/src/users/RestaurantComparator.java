package users;

import java.util.Comparator;

public class RestaurantComparator implements Comparator<Restaurant>{
	@Override
	public int compare(Restaurant restaurant1,Restaurant restaurant2) {
		return restaurant1.getOrderCounter()-restaurant2.getOrderCounter();
	}
	
}

