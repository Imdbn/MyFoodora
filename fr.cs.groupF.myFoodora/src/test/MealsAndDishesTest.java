package test;

import menuMeals.*;
import menuMeals.Dish.DishCategory;
import users.FoodFactory;
import exceptions.*;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.*;

public class MealsAndDishesTest {

    private Meal fullMeal;
    private Meal halfMeal;
    private Dish starterVeg;
    private Dish starterGF;
    private Dish mainVeg;
    private Dish mainGF;
    private Dish dessertVeg;
    private Dish dessertGF;

    // Streams to capture System.out output for testing print outputs
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUp() throws Exception {
        // Create dishes for each category: vegetarian and gluten-free
        starterVeg = FoodFactory.createDish("Veg Soup", true, 4.0, false, DishCategory.STARTER);
        starterGF = FoodFactory.createDish("Shrimp Cocktail", false, 6.0, true, DishCategory.STARTER);

        mainVeg = FoodFactory.createDish("Tofu Steak", true, 10.0, false, DishCategory.MAINDISH);
        mainGF = FoodFactory.createDish("Grilled Chicken", false, 12.0, true, DishCategory.MAINDISH);

        dessertVeg = FoodFactory.createDish("Fruit Salad", true, 5.0, false, DishCategory.DESSERT);
        dessertGF = FoodFactory.createDish("Ice Cream", false, 5.0, true, DishCategory.DESSERT);

        // Create two types of meals for testing
        fullMeal = FoodFactory.createMeal("FullMeal", MealType.FULL);
        halfMeal = FoodFactory.createMeal("HalfMeal", MealType.HALF);

        // Redirect System.out to capture output during tests
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        // Restore original System.out after each test to avoid side effects
        System.setOut(originalOut);
    }

    @Test
    @DisplayName("Ensure FullMeal is initialized with correct defaults")
    void testFullMealInitializationDefaults() {
        assertEquals("FullMeal", fullMeal.getName());
        assertFalse(fullMeal.isVegetarian());
        assertFalse(fullMeal.isGlutenFree());
        assertFalse(fullMeal.isMealOfTheWeek());
        assertNotNull(fullMeal.getMealComposition());
    }

    @Test
    @DisplayName("Ensure HalfMeal is initialized with correct defaults")
    void testHalfMealInitializationDefaults() {
        assertEquals("HalfMeal", halfMeal.getName());
        assertFalse(halfMeal.isVegetarian());
        assertFalse(halfMeal.isGlutenFree());
        assertFalse(halfMeal.isMealOfTheWeek());
        assertNotNull(halfMeal.getMealComposition());
    }

    @Test
    @DisplayName("Add exactly three valid dishes to FullMeal (starter, main, dessert)")
    void testAddThreeValidDishesToFullMeal() {
        assertDoesNotThrow(() -> fullMeal.addDishToMeal(starterVeg));
        assertDoesNotThrow(() -> fullMeal.addDishToMeal(mainGF));
        assertDoesNotThrow(() -> fullMeal.addDishToMeal(dessertVeg));

        assertEquals(3, fullMeal.getMealComposition().size());
        assertTrue(fullMeal.getMealComposition().contains(mainGF));

        assertThrows(BadMealCompositionCreationException.class,
            () -> fullMeal.addDishToMeal(mainVeg),
            "Should throw when adding a 4th dish to a FullMeal");
    }

    @Test
    @DisplayName("Add two valid dishes to HalfMeal and reject invalid third dish")
    void testAddTwoValidDishesToHalfMealThenFailOnThird() {
        assertDoesNotThrow(() -> halfMeal.addDishToMeal(starterVeg));
        assertDoesNotThrow(() -> halfMeal.addDishToMeal(mainGF));

        assertEquals(2, halfMeal.getMealComposition().size());
        assertTrue(halfMeal.getMealComposition().contains(mainGF));

        // Third addition should fail regardless of category
        assertThrows(BadMealCompositionCreationException.class,
            () -> halfMeal.addDishToMeal(dessertVeg));

        assertThrows(BadMealCompositionCreationException.class,
            () -> halfMeal.addDishToMeal(mainVeg));
    }

    @Test
    @DisplayName("Prevent mixing incompatible categories in HalfMeal (starter + dessert)")
    void testInvalidCategoryPairInHalfMeal() {
        // Reset meal composition
        halfMeal.setMealComposition(new ArrayList<>());

        assertDoesNotThrow(() -> halfMeal.addDishToMeal(starterVeg));

        // Should fail: cannot mix starter and dessert in half meal
        assertThrows(BadMealCompositionCreationException.class,
            () -> halfMeal.addDishToMeal(dessertGF));
    }

    @Test
    @DisplayName("Restrict FullMeal to gluten-free dishes when set to gluten-free")
    void testFullMealGlutenFreeRestriction() {
        fullMeal.setGlutenFree(true);

        assertDoesNotThrow(() -> fullMeal.addDishToMeal(starterGF));
        assertThrows(BadMealCompositionCreationException.class,
            () -> fullMeal.addDishToMeal(mainVeg),
            "Vegetarian dish not GF should not be allowed in GF meal");
    }

    @Test
    @DisplayName("Restrict HalfMeal to vegetarian dishes when set to vegetarian")
    void testHalfMealVegetarianRestriction() {
        halfMeal.setVegetarian(true);

        assertDoesNotThrow(() -> halfMeal.addDishToMeal(starterVeg));
        assertThrows(BadMealCompositionCreationException.class,
            () -> halfMeal.addDishToMeal(mainGF),
            "Non-vegetarian dish should not be allowed in vegetarian meal");
    }
    
    @Test
    @DisplayName("Reject adding two dishes of the same category to a fullmeal")
    void testAddDuplicateCategoryDishToFullMeal() {
        // Add a starter dish to fullMeal - should succeed
        assertDoesNotThrow(() -> fullMeal.addDishToMeal(starterVeg));

        // Attempt to add another starter dish - should fail with exception
        assertThrows(BadMealCompositionCreationException.class,
            () -> fullMeal.addDishToMeal(starterGF),
            "Adding a second dish of the same category (starter) should throw an exception");
    }
    @Test
    @DisplayName("Reject adding two dishes of the same category to a halfmeal")
    void testAddDuplicateCategoryDishToHalfMeal() {
        // Add a starter dish to fullMeal - should succeed
        assertDoesNotThrow(() -> halfMeal.addDishToMeal(dessertVeg));

        // Attempt to add another starter dish - should fail with exception
        assertThrows(BadMealCompositionCreationException.class,
            () -> halfMeal.addDishToMeal(dessertGF),
            "Adding a second dish of the same category (starter) should throw an exception");
    }
}
    
    

