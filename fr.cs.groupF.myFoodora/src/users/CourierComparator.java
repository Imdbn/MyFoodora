package users;

import java.util.Comparator;

public class CourierComparator implements Comparator<Courier>{
	@Override
	public int compare(Courier courier1,Courier courier2) {
		return courier1.getDeliveryCounter()-courier2.getDeliveryCounter();
	}
	
}
