package menuMeals;

import exceptions.*;
import java.util.ArrayList;

/**
 * This class represents a HalfMeal, which must be composed of exactly
 * 2 dishes: either (Starter + MainDish) or (MainDish + Dessert).
 */
public class HalfMeal extends Meal {

    public HalfMeal(String mealName, boolean isVegetarian, boolean isGlutenFree, boolean mealOfTheWeek) {
        super(mealName, isVegetarian, isGlutenFree, mealOfTheWeek);
        this.mealComposition = new ArrayList<>();
    }

    public HalfMeal(String mealName, boolean isVegetarian, boolean isGlutenFree) {
        super(mealName, isVegetarian, isGlutenFree);
        this.mealComposition = new ArrayList<>();
    }

    public HalfMeal(String mealName) {
        super(mealName);
        this.mealComposition = new ArrayList<>();
    }

    @Override
    public void addDishToMeal(Dish dish) throws BadMealCompositionCreationException {
        this.itemVisitor.visit(dish, this);
        mealComposition.add(dish);
    }

    @Override
	public String toString() {
		String s = "The meal '" + this.name + "' composed of : ";
		for (int i=0;i<this.mealComposition.size()-1;i++) {
			s += this.mealComposition.get(i).getName() +", ";
		}
		if (!this.mealComposition.isEmpty())
			s+=this.mealComposition.get(this.mealComposition.size()-1).getName()+".";
		return s ;
		}
}


