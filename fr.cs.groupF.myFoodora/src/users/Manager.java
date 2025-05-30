package users;
import coreSystem.*;

public class Manager extends User {

	private String surname;
	private CoreSystem coreSystem;
	//private DeliveryPolicy deliveryPolicy;

	public Manager(String name , String username , String password, String surname){//DeliveryPolicy deliveryPolicy) {
		super(name,username,password);
		this.surname = surname;
		this.coreSystem = CoreSystem.getInstance();
	}
	public String getSurname() {
		return surname;
	}


	public void setSurname(String surname) {
		this.surname = surname;
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


	public double computeTotalIncome() {
		double totalIncome = 0;
			for(Order order : CoreSystem.getOrders() ) {
				totalIncome += order.getFinalPrice();
			}
			return totalIncome;
	}

	public double computeTotalProfit() {
		double totalProfit = 0;
			for(Order order : CoreSystem.getOrders() ) {
				totalProfit += order.getProfit();
			}
			return totalProfit;
	}

	public void computeAverageIncomePerCostumer() {
		double averageIncomePerCostumer = 0;
			
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