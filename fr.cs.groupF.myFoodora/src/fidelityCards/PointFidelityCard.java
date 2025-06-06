package fidelityCards;

import users.Order;

public class PointFidelityCard extends FidelityCard {
	
	/*
	 * Depends on the customer, the number of points accumulated so far.
	 */
	private double points = 0;
	
	/*
	 * the price to reach in order to receive 1 point
	 */
	
	private static double amountReduction = 10 ;
	
	/**
	 * the number of points to reach in order to get a discount in the next order
	 */
	
	private static double targetPoints = 100 ;
	
	/**
	 * the rate of the discount applied when the targetPoints value is reached
	 */
	
	private static double discountFactor = 0.1 ;
	
	public PointFidelityCard() {
		super(FidelityCardType.POINT);
	}
	
	
	public double computeOrderReduction(Order order) {
		double reduction = 0;
		if (this.points>=targetPoints) {
			reduction = order.computePrice()*discountFactor;
		}
		return reduction;
	}
	
	public double computeOrderPrice (Order order) {
		double reduction = this.computeOrderReduction(order);
		double initialPrice = order.computePrice();
		
		if (reduction!=0) {
			this.points -= targetPoints;
		}
		
		return initialPrice - reduction;
	}
	
	public void addPoints(double numberPoints) {
		this.points+=numberPoints;
	}
	
	public double computeNumberAddedPoints(double price) {
		return price/amountReduction;
	}

	public void updatePoints(double price) {

		double numberPoints = this.computeNumberAddedPoints(price);
		this.addPoints(numberPoints);

	}

	public double getPoints() {
		return points;
	}


	public void setPoints(double points) {
		this.points = points;
	}


	public static double getAmountReduction() {
		return amountReduction;
	}


	public static void setAmountReduction(double amountReduction) {
		PointFidelityCard.amountReduction = amountReduction;
	}


	public static double getTargetPoints() {
		return targetPoints;
	}


	public static void setTargetPoints(double targetPoints) {
		PointFidelityCard.targetPoints = targetPoints;
	}


	public static double getDiscountFactor() {
		return discountFactor;
	}


	public static void setDiscountFactor(double discountFactor) {
		PointFidelityCard.discountFactor = discountFactor;
	}


	@Override
	public String toString() {
		return "Point Fidelity Card: "+"\n"+"Number of points gained so far: "+points;
	}
	
	
	
}