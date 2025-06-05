package deliveryPolicy;

import exceptions.*;



public class DeliveryPolicyFactory {
	public DeliveryPolicyFactory() {
		
	}
	
	public static DeliveryPolicy createDeliveryPolicy(DeliveryPolicyType deliveryPolicy) throws UndefinedPolicyException {
		switch (deliveryPolicy) {
        case FAIR:
            return new FairOccupation();
        case FASTEST:
            return new Fastest();
        default:
            throw new UndefinedPolicyException("Unknown policy type: " + deliveryPolicy);
    }
		
	}
	
	
}
