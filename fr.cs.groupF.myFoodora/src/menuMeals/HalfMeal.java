package menuMeals;
import exceptions.*;

import java.util.ArrayList;

public class HalfMeal extends Meal {

    public HalfMeal( String name, boolean isVegetarian, double price, boolean isGlutenFree, boolean mealOfTheWeek, ArrayList<Dish> mealComposition) {
        super( name, isVegetarian, price, isGlutenFree, mealOfTheWeek, mealComposition);
        mealComposition = new ArrayList<>();
    }

    @Override
    public void addDish(Dish dish) throws BadMealCompositionCreationException {
        if (mealComposition.size() >= 2) {
            throw new BadMealCompositionCreationException("HalfMeal can only have two dishes.");
        }

        // Check if adding the dish respects the half meal rules:
        boolean hasStarter = false;
        boolean hasMainDish = false;
        boolean hasDessert = false;

        for (Dish d : mealComposition) {
            if (d.category == Dish.DishCategory.STARTER) hasStarter = true;
            else if (d.category == Dish.DishCategory.MAINDISH) hasMainDish = true;
            else if (d.category == Dish.DishCategory.DESSERT) hasDessert = true;
        }

        switch (dish.category) {
            case STARTER:
                if (hasStarter)
                    throw new BadMealCompositionCreationException("HalfMeal can only have one starter.");
                if (hasDessert)
                    throw new BadMealCompositionCreationException("HalfMeal cannot have starter and dessert together.");
                break;
            case MAINDISH:
                if (hasMainDish)
                    throw new BadMealCompositionCreationException("HalfMeal can only have one main dish.");
                break;
            case DESSERT:
                if (hasDessert)
                    throw new BadMealCompositionCreationException("HalfMeal can only have one dessert.");
                if (hasStarter)
                    throw new BadMealCompositionCreationException("HalfMeal cannot have starter and dessert together.");
                break;
        }

        // Check if dish matches meal type (vegetarian/glutenFree)
        if (this.isVegetarian && !dish.isVegetarian)
            throw new BadMealCompositionCreationException("Dish is not vegetarian as required by HalfMeal.");
        if (this.isGlutenFree && !dish.isGlutenFree)
            throw new BadMealCompositionCreationException("Dish is not gluten free as required by HalfMeal.");

        mealComposition.add(dish);
    }

    /**
     * Check if the half meal is complete (2 dishes and valid combination)
     */
    public boolean isComplete() {
        if (mealComposition.size() != 2) return false;

        boolean hasStarter = false;
        boolean hasMainDish = false;
        boolean hasDessert = false;

        for (Dish d : mealComposition) {
            if (d.category == Dish.DishCategory.STARTER) hasStarter = true;
            else if (d.category == Dish.DishCategory.MAINDISH) hasMainDish = true;
            else if (d.category == Dish.DishCategory.DESSERT) hasDessert = true;
        }

        // Valid combinations for half meal:
        return (hasStarter && hasMainDish) || (hasMainDish && hasDessert);
    }
}
