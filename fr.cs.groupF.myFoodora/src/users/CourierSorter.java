package users;

import java.util.*;

public class CourierSorter {
	public ArrayList<Courier> sort(Map<String,Courier> couriers){
		ArrayList<Courier> sortedCouriers = new ArrayList<>(couriers.values());
		CourierComparator comp = new CourierComparator();
		sortedCouriers.sort(comp.reversed());
		return sortedCouriers;
		
	}
}
