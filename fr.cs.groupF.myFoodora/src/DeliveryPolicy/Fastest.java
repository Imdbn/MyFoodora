package DeliveryPolicy;

import java.util.ArrayList;

import coreSystem.CoreSystem;
import exceptions.NoCourierIsAvailableException;
import users.Courier;
import users.Order;

public class Fastest implements DeliveryPolicy {
	@Override
	public void allocateCourier(Order order) throws NoCourierIsAvailableException {
		ArrayList<Courier> onDutyCouriers = CoreSystem.getOnDutyCouriers(CoreSystem.getCouriers());
		Courier currentFastestCourier = null ;
		double currentDistance = Double.POSITIVE_INFINITY;
		for(Courier c : onDutyCouriers) {
			double distance = c.getPosition().distance(order.getRestaurant().getLocation());
			if (distance < currentDistance) {
				currentDistance = distance;
				currentFastestCourier = c ;
			}
			
		}
		if(currentFastestCourier!=null) {
		order.setCourier(currentFastestCourier);
		currentFastestCourier.incrementDeliveryCounter();
		}
		else throw new NoCourierIsAvailableException("Sorry , but no Courier is available for the moment. please try later");
	}
	
}
