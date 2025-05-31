package menuMeals;

/**
 * Strategy interface for calculating the price of a meal.
 */
public interface MealPriceCalculation {
    /**
     * Calculates the price of the given meal.
     * 
     * @param meal the meal for which the price is to be calculated
     * @return the calculated price
     */
    double calculatePrice(Meal meal);
}
