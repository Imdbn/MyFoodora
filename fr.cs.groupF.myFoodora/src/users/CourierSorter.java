package users;

import java.util.*;

public class CourierSorter {
	public ArrayList<Courier> sort(Map<String,Courier> couriers){
		ArrayList<Courier> sortedCouriers = new ArrayList<>(couriers.values());
		sortedCouriers.sort(new CourierComparator());
		return sortedCouriers;
		
	}
}
