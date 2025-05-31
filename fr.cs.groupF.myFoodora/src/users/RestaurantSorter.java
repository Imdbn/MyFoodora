package users;

import java.util.*;

public class RestaurantSorter {
	public ArrayList<Restaurant> sort(Map<String,Restaurant> restaurants){
		ArrayList<Restaurant> sortedRestaurants = new ArrayList<>(restaurants.values());
		sortedRestaurants.sort(new RestaurantComparator());
		return sortedRestaurants;
		
	}
}
