package menuMeals;


public class MealPriceCalculationImpl implements MealPriceCalculation {

    @Override
    public double calculatePrice(Meal meal, double discount) {
        double total = 0.0;

        for (Dish dish : meal.getmealComposition()) {
            total += dish.getprice();
        }

 
        total *= (1.0 - discount);

        return total;
    }
}
