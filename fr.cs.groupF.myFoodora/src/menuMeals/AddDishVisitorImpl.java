package menuMeals;
import exceptions.*;
public class AddDishVisitorImpl implements AddDishVisitor {

    @Override
    public void visit(FullMeal fullMeal, Dish dish) throws BadMealCompositionCreationException {
        fullMeal.addDish(dish);  // validation inside FullMeal
    }

    @Override
    public void visit(HalfMeal halfMeal, Dish dish) throws BadMealCompositionCreationException {
        halfMeal.addDish(dish);  // validation inside HalfMeal
    }
}

