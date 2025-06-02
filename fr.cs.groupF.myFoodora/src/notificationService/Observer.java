package notificationService;

import users.Restaurant;
import menuMeals.Meal;



public interface Observer {
	/**
     * Notification method when a restaurant sets a new special offer.
     *
     * @param restaurant the restaurant that set a new special offer
     * @param offer the offer containing discount info
     * @param meal the meal-of-the-week
     */
	
    public void update(Restaurant restaurant, Offer offer,Meal meal);
}