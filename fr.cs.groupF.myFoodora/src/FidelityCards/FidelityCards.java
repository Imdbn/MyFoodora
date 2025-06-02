package FidelityCards;
import users.Order;

public abstract class FidelityCards {
	protected FidelityCardType type;
	
	public FidelityCards() {
		
	}
	
	public FidelityCards(FidelityCardType type) {
		this.type = type;
	}

	public FidelityCardType getType() {
		return type;
	}

	public void setType(FidelityCardType type) {
		this.type = type;
	}
	
	public abstract double computeOrderReduction (Order order);
	
	public abstract double computeOrderPrice(Order order);
	
	
	
}