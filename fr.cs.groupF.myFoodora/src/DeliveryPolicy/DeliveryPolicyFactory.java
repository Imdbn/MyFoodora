package DeliveryPolicy;

import exceptions.*;



public class DeliveryPolicyFactory {
	public DeliveryPolicyFactory() {
		
	}
	
	public DeliveryPolicy createDeliveryPolicy(DeliveryPolicyType deliveryPolicy) throws UndefinedPolicyException {
		switch (deliveryPolicy) {
        case FASTEST:
            return new FairOccupation();
        case FAIR:
            return new Fastest();
        default:
            throw new UndefinedPolicyException("Unknown policy type: " + deliveryPolicy);
    }
		
	}
	
	
}
