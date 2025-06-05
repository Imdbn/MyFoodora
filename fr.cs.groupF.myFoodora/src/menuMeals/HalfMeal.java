package menuMeals;

import exceptions.*;
import java.util.ArrayList;

/**
 * This class represents a HalfMeal, which must be composed of exactly
 * 2 dishes: either (Starter + MainDish) or (MainDish + Dessert).
 */
public class HalfMeal extends Meal {

   

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
        
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n🥗 Half-Meal: ").append(this.name).append("\n");
        sb.append("────────────────────────────\n");

        if (mealComposition.isEmpty()) {
            sb.append("⚠️  This half-meal contains no dishes.\n");
        } else {
            sb.append("📋 Composition:\n");
            for (int i = 0; i < mealComposition.size(); i++) {
                sb.append("  ").append(i + 1).append(". ").append(mealComposition.get(i).getName()).append("\n");
            }
            sb.append("💰 Total Price: ").append(String.format("%.2f", getPrice())).append(" €\n");
        }

        return sb.toString();
    }


}


