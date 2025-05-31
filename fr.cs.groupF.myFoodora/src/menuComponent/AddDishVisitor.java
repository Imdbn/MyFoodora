package menuComponent;

public interface AddDishVisitor {
    void visit(Meal meal, Dish dish) throws BadMealCompositionCreationException;
}
