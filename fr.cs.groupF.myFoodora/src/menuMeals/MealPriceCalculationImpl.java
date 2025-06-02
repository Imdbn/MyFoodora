package menuMeals;


public class MealPriceCalculationImpl implements MealPriceCalculation {

    @Override
    public double calculatePrice(Meal meal, double discount) {
        double total = 0.0;

        for (Dish dish : meal.getMealComposition()) {
            total += dish.getPrice();
        }

 
        total *= (1.0 - discount);

        return total;
    }
}
