package deliveryPolicy;

import users.Courier;
import users.Order;

import java.util.ArrayList;

import coreSystem.CoreSystem;
import exceptions.*;

public class FairOccupation implements DeliveryPolicy {
	@Override
	public void allocateCourier(Order order)throws NoCourierIsAvailableException{
		ArrayList<Courier> onDutyCouriers = CoreSystem.getOnDutyCouriers(CoreSystem.getCouriers());
		Courier currentCourier = null ;
		double currentCounter = Double.POSITIVE_INFINITY;
		for(Courier c : onDutyCouriers) {
			int counter = c.getDeliveryCounter();
			if(counter < currentCounter) {
				currentCounter = counter;
				currentCourier = c;
			}
		}
		
		if(currentCourier!=null) {
			order.setCourier(currentCourier);
			currentCourier.incrementDeliveryCounter();
		}
		
		else throw new NoCourierIsAvailableException("Sorry , but no Courier is available for the moment. please try later");
	}
}
