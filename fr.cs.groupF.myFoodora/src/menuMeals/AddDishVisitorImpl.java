package menuMeals;

import exceptions.*;

public class AddDishVisitorImpl implements AddDishVisitor {

    @Override
    public void visit(Dish dish, HalfMeal halfMeal) throws BadMealCompositionCreationException {
        if (halfMeal.getMealComposition().size() == 2) {
            throw new BadMealCompositionCreationException("Attempt to add third item to a half-meal.");
        }

        if (halfMeal.getMealComposition().size() == 1) {
            Dish existingDish = halfMeal.getMealComposition().get(0);


            if (dish.getCategory().equals(existingDish.getCategory())) {
                throw new BadMealCompositionCreationException("Attempt to create half-meal with duplicate categories.");
            }

            if (!dish.getCategory().equals(Dish.DishCategory.MAINDISH) &&
                !existingDish.getCategory().equals(Dish.DishCategory.MAINDISH)) {
                throw new BadMealCompositionCreationException("Attempt to create a half-meal with no main dish.");
            }
        }

        if (halfMeal.isVegetarian() && !dish.isVegetarian()) {
            throw new BadMealCompositionCreationException("Attempt to add non-vegetarian item to a vegetarian meal.");
        }

        if (halfMeal.isGlutenFree() && !dish.isGlutenFree()) {
            throw new BadMealCompositionCreationException("Attempt to add gluten-containing item to a gluten-free meal.");
        }

        
        halfMeal.getMealComposition().add(dish);
    }

    @Override
    public void visit(Dish dish, FullMeal fullMeal) throws BadMealCompositionCreationException {
        if (fullMeal.getMealComposition().size() == 3) {
            throw new BadMealCompositionCreationException("Attempt to add fourth item to a full-meal.");
        }

        for (Dish existingDish : fullMeal.getMealComposition()) {
            if (dish.getCategory().equals(existingDish.getCategory())) {
                throw new BadMealCompositionCreationException("Attempt to create full-meal with duplicate categories.");
            }
        }

        if (fullMeal.isVegetarian() && !dish.isVegetarian()) {
            throw new BadMealCompositionCreationException("Attempt to add non-vegetarian item to a vegetarian meal.");
        }

        if (fullMeal.isGlutenFree() && !dish.isGlutenFree()) {
            throw new BadMealCompositionCreationException("Attempt to add gluten-containing item to a gluten-free meal.");
        }

        fullMeal.getMealComposition().add(dish);
    }
}

