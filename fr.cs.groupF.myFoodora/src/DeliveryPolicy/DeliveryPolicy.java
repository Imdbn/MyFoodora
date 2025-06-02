package DeliveryPolicy;

import users.Order;
import exceptions.*;

public interface DeliveryPolicy {
	public abstract void allocateCourier(Order order)throws NoCourierIsAvailableException;

}
