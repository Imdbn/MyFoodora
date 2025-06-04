package menuMeals;
import exceptions.*;

import java.util.ArrayList;

public class FullMeal extends Meal{
	
	
	
	public FullMeal(String mealName, boolean isVegetarian, boolean isGlutenFree) {
		super(mealName, isVegetarian, isGlutenFree);
		this.mealComposition = new ArrayList<Dish>();
	}
	
	public FullMeal(String mealName) {
		super(mealName);
		this.mealComposition = new ArrayList<Dish>();
	}
	
	public void addDishToMeal(Dish dish) throws BadMealCompositionCreationException{
		this.itemVisitor.visit(dish, this);
	}



	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("\n🍱 Meal: ").append(this.name).append("\n");
	    sb.append("────────────────────────────\n");

	    if (mealComposition.isEmpty()) {
	        sb.append("⚠️  This meal contains no dishes.\n");
	    } else {
	        sb.append("📋 Composition:\n");
	        for (int i = 0; i < mealComposition.size(); i++) {
	            sb.append("  ").append(i + 1).append(". ").append(mealComposition.get(i).getName()).append("\n");
	        }
	    }

	    return sb.toString();
	}
	
}
