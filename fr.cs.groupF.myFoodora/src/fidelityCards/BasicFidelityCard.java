package fidelityCards;

import users.Order;

public class BasicFidelityCard extends FidelityCard {
	

	public BasicFidelityCard() {
		super(FidelityCardType.BASIC);
	}
	
	
	public double computeOrderReduction (Order order) {
		return 0;
	}
	
	public double computeOrderPrice(Order order) {
		return order.computePrice();
	}


	@Override
	public String toString() {
		return "Basic Fidelity Card";
	}
	
	
}
