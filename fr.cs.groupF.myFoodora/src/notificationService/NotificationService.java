package notificationService;

import java.util.*;
import users.*;
import menuMeals.*;

/**
 * Singleton class that manages observers (customers) and notifies them when a restaurant
 * sets a new special offer (like meal-of-the-week or generic/special discounts).
 */
public class NotificationService implements Observable {

    private static NotificationService instance = null;

    private static ArrayList<Observer> subscribers = new ArrayList<>();
    private static boolean changed = false;

    private NotificationService() {
        subscribers = new ArrayList<>();
        changed = false;
    }

    /**
     * Singleton instance getter
     */
    public static NotificationService getInstance() {
        if (instance == null) {
            instance = new NotificationService();
        }
        return instance;
    }


    public void registerObserver(Observer observer) {
        if (!subscribers.contains(observer)) {
        	subscribers.add(observer);
        }
    }


    public void removeObserver(Observer observer) {
    	
        if (subscribers.contains(observer)) {
    		subscribers.remove(observer);
    
    	}
    }


    public void notifyObservers(Meal meal, Restaurant restaurant, Offer offer) {
    	if (changed) {
    		for (Observer observer : subscribers) {
    			observer.update(restaurant, offer, meal);
    		}
        }
    	changed=false;
    }

    public void setOffer(Meal meal, Restaurant restaurant, Offer offer) {
    	changed=true;
        notifyObservers(meal, restaurant, offer);
    }
    
    public static ArrayList<Observer> getObservers() {
		return subscribers;
	}

	public static void setObservers(ArrayList<Observer> subscribers) {
		NotificationService.subscribers = subscribers;
	}
	
	public static void clearObservers() {
		subscribers = new ArrayList<Observer>();
	}
    

  
}