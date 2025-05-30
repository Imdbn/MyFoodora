package users;

public class Manager extends User {

	private String surname;
	private double serviceFee;
	private double markupPercentage;
	private double deliveryCost;
	//private DeliveryPolicy deliveryPolicy;

	public Manager(String name , String username , String password, String surname, double serviceFee , double markupPercentage , double deliveryCost){//DeliveryPolicy deliveryPolicy) {
		super(name,username,password);
		this.deliveryCost = deliveryCost;
		//this.deliveryPolicy = deliveryPolicy;
		this.markupPercentage = markupPercentage;
		this.serviceFee = serviceFee;
	}
	public String getSurname() {
		return surname;
	}


	public void setSurname(String surname) {
		this.surname = surname;
	}


	public double getServiceFee() {
		return serviceFee;
	}


	public void setServiceFee(double serviceFee) {
		this.serviceFee = serviceFee;
	}


	public double getMarkupPercentage() {
		return markupPercentage;
	}


	public void setMarkupPercentage(double markupPercentage) {
		this.markupPercentage = markupPercentage;
	}


	public double getDeliveryCost() {
		return deliveryCost;
	}


	public void setDeliveryCost(double deliveryCost) {
		this.deliveryCost = deliveryCost;
	}


//	public DeliveryPolicy getDeliveryPolicy() {
//		return deliveryPolicy;
//	}
//
//
//	public void setDeliveryPolicy(DeliveryPolicy deliveryPolicy) {
//		this.deliveryPolicy = deliveryPolicy;
//	}


	public void addUser(User user) {
		// TODO - implement Manager.addUser
		throw new UnsupportedOperationException();
	}

	
	public void removeUser(User user) {
		// TODO - implement Manager.removeUser
		throw new UnsupportedOperationException();
	}

	
	public void activateUser(User user) {
		// TODO - implement Manager.activateUser
		throw new UnsupportedOperationException();
	}


	public void disactivateUser(User use) {
		// TODO - implement Manager.disactivateUser
		throw new UnsupportedOperationException();
	}


	public void computeTotalIncome() {
		// TODO - implement Manager.computeTotalIncome
		throw new UnsupportedOperationException();
	}

	public void computeTotalProfit() {
		// TODO - implement Manager.computeTotalProfit
		throw new UnsupportedOperationException();
	}

	public void computeAverageIncomePerCostumer() {
		// TODO - implement Manager.computeAverageIncomePerCostumer
		throw new UnsupportedOperationException();
	}

	public void showSortedRestaurants() {
		// TODO - implement Manager.showSortedRestaurants
		throw new UnsupportedOperationException();
	}

	public void showSortedCourier() {
		// TODO - implement Manager.showSortedCourier
		throw new UnsupportedOperationException();
	}

	public void setDeliveryPolicy() {
		// TODO - implement Manager.setDeliveryPolicy
		throw new UnsupportedOperationException();
	}

	public void determinParamToMeetTargetProfit() {
		// TODO - implement Manager.determinParamToMeetTargetProfit
		throw new UnsupportedOperationException();
	}

}