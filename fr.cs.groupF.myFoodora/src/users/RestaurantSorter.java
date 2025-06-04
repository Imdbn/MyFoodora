package users;

import java.util.*;

public class RestaurantSorter {
	public ArrayList<Restaurant> sort(Map<String,Restaurant> restaurants){
		ArrayList<Restaurant> sortedRestaurants = new ArrayList<>(restaurants.values());
		RestaurantComparator comp = new RestaurantComparator();
		sortedRestaurants.sort(comp.reversed());
		return sortedRestaurants;
		
	}
}
