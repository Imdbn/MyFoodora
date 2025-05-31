package menuMeals;
import exceptions.*;

import java.util.ArrayList;

public class FullMeal extends Meal {

	public FullMeal( String name, boolean isVegetarian, double price, boolean isGlutenFree, boolean mealOfTheWeek, ArrayList<Dish> mealComposition) {
	    super( name, isVegetarian, price, isGlutenFree, mealOfTheWeek, mealComposition);
        mealComposition = new ArrayList<>();
    }

    @Override
    public void addDish(Dish dish) throws BadMealCompositionCreationException {
        // Check if dish category already exists in mealComposition
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
                    throw new BadMealCompositionCreationException("FullMeal can only have one starter.");
                break;
            case MAINDISH:
                if (hasMainDish) 
                    throw new BadMealCompositionCreationException("FullMeal can only have one main dish.");
                break;
            case DESSERT:
                if (hasDessert) 
                    throw new BadMealCompositionCreationException("FullMeal can only have one dessert.");
                break;
        }

        // Check if dish matches meal type (vegetarian/glutenFree)
        if (this.isVegetarian && !dish.isVegetarian)
            throw new BadMealCompositionCreationException("Dish is not vegetarian as required by FullMeal.");
        if (this.isGlutenFree && !dish.isGlutenFree)
            throw new BadMealCompositionCreationException("Dish is not gluten free as required by FullMeal.");

        mealComposition.add(dish);
    }

    /**
     * check if the full meal is complete
     */
    public boolean isComplete() {
        boolean hasStarter = false;
        boolean hasMainDish = false;
        boolean hasDessert = false;

        for (Dish d : mealComposition) {
            if (d.category == Dish.DishCategory.STARTER) hasStarter = true;
            else if (d.category == Dish.DishCategory.MAINDISH) hasMainDish = true;
            else if (d.category == Dish.DishCategory.DESSERT) hasDessert = true;
        }
        return hasStarter && hasMainDish && hasDessert;
    }
}

