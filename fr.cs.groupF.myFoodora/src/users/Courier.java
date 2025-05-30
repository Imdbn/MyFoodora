package users;

public class Courier extends User{
	private String surname;
	private Coordinate position;
	private String phone;
	private int deliveryCounter;
	private boolean onDuty;
	
	public Courier(String name,String username, String password,String surname , Coordinate position,String phone, boolean onDuty) {
		super(name,username,password);
		this.position = position;
		this.deliveryCounter = 0;
		this.phone = phone;
		this.onDuty = onDuty;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public Coordinate getPosition() {
		return position;
	}
	/**
	 * allows the Courier to change his position
	 * @param position : coordinate 
	 */
	public void setPosition(Coordinate position) {
		this.position = position;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getDeliveryCounter() {
		return deliveryCounter;
	}

	public void setDeliveryCounter(int deliveryCounter) {
		this.deliveryCounter = deliveryCounter;
	}

	public boolean isOnDuty() {
		return onDuty;
	}
	/**
	 * allows the Courier to set his On-duty status to True(on-duty) or to False (off-duty)
	 * @param onDuty :  boolean [ True(on-Duty) or False(off-duty) ]
	 */
	public void setOnDuty(boolean onDuty) {
		this.onDuty = onDuty;
	}
	/**
	 * acceptDelivery method that allows the Courier to accept a Delivery
	 */
	public void acceptDelivery() {
	
	}
	/**
	 * refuseDelivery method that allows the Courier to refuse a Delivery
	 */
	public void refuseDelivery() {
		
	}
	
	
	
	

}
