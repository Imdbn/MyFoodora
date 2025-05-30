package users;
import java.util.*;
/**
 * Coordinate Class a 2d representation of the position of objects using the class
 */
public class Coordinate {
	private double xpos;
	private double ypos;
	
	public Coordinate(double xpos,double ypos) {
		this.xpos = xpos;
		this.ypos = ypos;
	}
	public Coordinate() {
		Random rand = new Random();
		this.xpos = rand.nextDouble(1000);
		this.ypos = rand.nextDouble(1000);
	}
	
	
	public double getXpos() {
		return xpos;
	}

	public void setXpos(double xpos) {
		this.xpos = xpos;
	}

	public double getYpos() {
		return ypos;
	}

	public void setYpos(double ypos) {
		this.ypos = ypos;
	}
	
	/**
	 * distance method calculates the distance between the object (the initial position) and the argument position(the target position)
	 * @param coordinate the target coordinate
	 * @return the distance between the two coordinates as a double
	 */
	public double distance(Coordinate coordinate) {
		return Math.sqrt(Math.pow(coordinate.getXpos()-this.xpos,2)+Math.pow(coordinate.getYpos()-this.ypos,2));
	}
	
	
	

}
