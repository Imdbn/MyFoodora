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
	    mealComposition.add(dish);
	}



	
	@Override
	public String toString() {
		String s = "The meal '" + this.name + "' is composed of : ";
		for (int i=0;i<this.mealComposition.size()-1;i++) {
			s += this.mealComposition.get(i).getName() +", ";
		}
		if (!this.mealComposition.isEmpty())
			s+=this.mealComposition.get(this.mealComposition.size()-1).getName()+".";
		return s ;
		}
	
}
