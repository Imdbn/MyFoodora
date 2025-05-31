package menuComponent;

public interface AddDishVisitor {
    void visit(FullMeal fullMeal, Dish dish) throws BadMealCompositionCreationException;
    void visit(HalfMeal halfMeal, Dish dish) throws BadMealCompositionCreationException;
}


