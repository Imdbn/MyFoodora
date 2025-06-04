package users;

/**
 * The Courier class represents a delivery person in the system.
 * Each courier has a name, username, password, surname, position (coordinates),
 * phone number, delivery count, and an on-duty status.
 */
public class Courier extends User {

    // =============================================================
    // Fields
    // =============================================================

    private String surname;
    private Coordinate position;
    private String phone;
    private int deliveryCounter;
    private boolean onDuty;

    // =============================================================
    // Constructors
    // =============================================================

    /**
     * Constructs a Courier with full initialization including position.
     *
     * @param name      the name of the courier
     * @param username  the username of the courier
     * @param password  the password for login
     * @param surname   the surname of the courier
     * @param position  the current coordinate position
     * @param phone     the contact phone number
     */
    public Courier(String name, String username, String password, String surname, Coordinate position, String phone) {
        super(name, username, password);
        this.surname = surname;
        this.position = position;
        this.deliveryCounter = 0;
        this.phone = phone;
        this.onDuty = true;
    }

    /**
     * Constructs a Courier without a predefined position.
     * A default Coordinate is used instead.
     *
     * @param name      the name of the courier
     * @param username  the username of the courier
     * @param password  the password for login
     * @param surname   the surname of the courier
     * @param phone     the contact phone number
     */
    public Courier(String name, String username, String password, String surname, String phone) {
        super(name, username, password);
        this.surname = surname;
        this.deliveryCounter = 0;
        this.phone = phone;
        this.position = new Coordinate();
        this.onDuty = true;
    }

    // =============================================================
    // Getters and Setters
    // =============================================================

    /**
     * @return the surname of the courier
     */
    public String getSurname() {
        return surname;
    }

    /**
     * @param surname the surname to set
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * @return the current position of the courier
     */
    public Coordinate getPosition() {
        return position;
    }

    /**
     * Updates the courier's position.
     *
     * @param position the new coordinate
     */
    public void setPosition(Coordinate position) {
        this.position = position;
    }

    /**
     * @return the courier's phone number
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone the phone number to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return the number of deliveries completed by the courier
     */
    public int getDeliveryCounter() {
        return deliveryCounter;
    }

    /**
     * @param deliveryCounter the number of deliveries to set
     */
    public void setDeliveryCounter(int deliveryCounter) {
        this.deliveryCounter = deliveryCounter;
    }

    /**
     * @return true if the courier is currently on duty, false otherwise
     */
    public boolean isOnDuty() {
        return onDuty;
    }

    /**
     * Sets the on-duty status of the courier.
     *
     * @param onDuty true if the courier is on duty, false otherwise
     */
    public void setOnDuty(boolean onDuty) {
        this.onDuty = onDuty;
    }

    // =============================================================
    // Utility Methods
    // =============================================================

    /**
     * Increments the delivery counter by 1.
     */
    public void incrementDeliveryCounter() {
        this.deliveryCounter++;
    }

    /**
     * @return a string representation of the courier's state
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n🚴‍♂️ Courier Details\n");
        sb.append("──────────────────────────────\n");
        sb.append("🆔 ID          : ").append(getId()).append("\n");
        sb.append("👤 Name        : ").append(getName()).append(" ").append(surname).append("\n");
        sb.append("🔑 Username    : ").append(getUsername()).append("\n");
        sb.append("💼 Position    : ").append(position).append("\n");
        sb.append("📞 Phone       : ").append(phone).append("\n");
        sb.append("📦 Deliveries  : ").append(deliveryCounter).append("\n");
        sb.append("⏰ On Duty     : ").append(onDuty ? "Yes" : "No").append("\n");
        sb.append("──────────────────────────────");
        return sb.toString();
    }

}
