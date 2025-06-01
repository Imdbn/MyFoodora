package menuMeals;

//import menuMeals.*;
import exceptions.BadMealCompositionCreationException;

public interface AddDishVisitor {
    void visit(Dish dish, FullMeal meal) throws BadMealCompositionCreationException;
    void visit(Dish dish, HalfMeal meal) throws BadMealCompositionCreationException;
}


