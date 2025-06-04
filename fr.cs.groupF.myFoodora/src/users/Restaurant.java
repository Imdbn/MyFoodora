package users;
import java.util.*;

import exceptions.*;
import menuMeals.*;
import menuMeals.Dish.DishCategory;
import notificationService.NotificationService;
import notificationService.Offer;

public class Restaurant extends User{
	private Coordinate location ;
	private Menu menu;
    private HashMap<String,Meal> meals;
    private MealPriceCalculation mealPriceStrategy = new MealPriceCalculationImpl();
	private double genericDiscount = 0.05;
	private double specialDiscount = 0.1;
	private int orderCounter = 0 ;
	
	public Restaurant(String name, String username, String password, Coordinate location, Menu menu, HashMap<String,Meal> meals, double genericDiscount, double specialDiscount) {
		super(name, username, password);
		this.location = location;
		this.menu = menu;
		this.meals = meals;
		this.genericDiscount = genericDiscount;
		this.specialDiscount = specialDiscount;
	}
	
	public Restaurant(String name, String username, String password , Coordinate location, double genericDiscount,double specialDiscount) {
		super(name, username,password);
		this.specialDiscount = specialDiscount;
		this.genericDiscount = genericDiscount;
		this.menu = new Menu();
		this.meals = new HashMap<>();
		this.location = location;
		
	}
	public Restaurant(String name, String username, String password ) {
		super(name, username,password);
		this.menu = new Menu();
		this.meals = new HashMap<>();
		this.location = new Coordinate();
	}

	

	public double getGenericDiscount() {
		return genericDiscount;
	}
	

	public double getSpecialDiscount() {
		
		return specialDiscount;
	}


	public Coordinate getLocation() {
		return location;
	}

	public void setLocation(Coordinate location) {
		this.location = location;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}
	
	public HashMap<String, Meal> getMeals() {
		return meals;
	}

	public void setMeals(HashMap<String, Meal> meals) {
		this.meals = meals;
	}
	
	public int getOrderCounter() {
		return orderCounter;
	}

	public void setOrderCounter(int orderCounter) {
		this.orderCounter = orderCounter;
	}

	public MealPriceCalculation getMealPriceStrategy() {
		return mealPriceStrategy;
	}

	public void setMealPriceStrategy(MealPriceCalculation mealPriceStrategy) {
		this.mealPriceStrategy = mealPriceStrategy;
	}

	// Incrementing Order Counter
	
	
	public void incrementOrderCounter() {
		this.orderCounter++;
	}
	
	// Creating meals
	
	public Meal createMeal(String mealName, MealType mealType) throws UndefinedTypeException {
		Meal meal = FoodFactory.createMeal(mealName, mealType);
		return meal;
	}
	
	public Meal createMeal(String mealName, MealType mealType, boolean isVegetarian,boolean isGlutenFree) throws UndefinedTypeException {
		Meal meal = FoodFactory.createMeal(mealName, mealType,  isVegetarian,  isGlutenFree);
		return meal;
	}	
	
	
	
	
	//Adding meals and removing them 		
	// To be Revisited in case of MealOfTheWeek Notification
	public void addMeal(Meal meal) throws ItemAlreadyExistsException {

		if(meal.isMealOfTheWeek()) {
			NotificationService.getInstance().setOffer(meal, this, Offer.MEALOFTHEWEEK);
			meal.setPrice(this.mealPriceStrategy.calculatePrice(meal, this.specialDiscount));
		}
		else {
			meal.setPrice(this.mealPriceStrategy.calculatePrice(meal, this.genericDiscount));
		}
			if(!meals.containsValue(meal)) {
			meals.put(meal.getName(),meal);
			
		}
		else throw new ItemAlreadyExistsException("Sorry but the meal you are trying to add already exists");
	}
	
	
	public void addMeal(String mealName, MealType mealType) throws UndefinedTypeException, ItemAlreadyExistsException {
		if(!meals.containsKey(mealName)) {
		Meal meal = FoodFactory.createMeal(mealName, mealType);
		this.meals.put(mealName,meal); 
		}
		else throw new ItemAlreadyExistsException("Sorry but the meal you are trying to add already exists");
	}
	
	
	
	public void addMeal(String mealName, MealType mealType, boolean isVegetarian, boolean isGlutenFree) throws ItemAlreadyExistsException, UndefinedTypeException {
		if(!meals.containsKey(mealName)) {
		Meal meal = FoodFactory.createMeal(mealName, mealType, isVegetarian, isGlutenFree);
		this.meals.put(mealName,meal);
		}
		else throw new ItemAlreadyExistsException("Sorry but the meal you are trying to add already exists");
	}
	
	
	public void removeMeal(Meal meal) throws ItemNotFoundException {
		if(meals.containsValue(meal)) {
			this.meals.remove(meal.getName());
		}
		else throw new ItemNotFoundException("Sorry but the meal you are trying to remove doesn't exist");
	}
	public void removeMeal(String mealName) throws ItemNotFoundException {
		if(meals.containsKey(mealName)) {
			this.meals.remove(mealName);
		}
		else throw new ItemNotFoundException("Sorry but the meal you are trying to remove doesn't exist");
	}
	
	public void setGenericDiscount(double genericDiscount) {
		this.genericDiscount = genericDiscount;
		NotificationService.getInstance().setOffer(null,this,Offer.GENERICDISCOUNT);
		for (Meal meal : meals.values()) {
	        setMealPrice(meal);
	    }
	}

	public void setSpecialDiscount(double specialDiscount) {
		this.specialDiscount = specialDiscount;
		NotificationService.getInstance().setOffer(null,this,Offer.SPECIALDISCOUNT);
		for (Meal meal : meals.values()) {
	        setMealPrice(meal);
	    }
	}
	

	//Show meal
	
	
	
	public void showMeal(String mealName) throws ItemNotFoundException{
		Meal meal = this.meals.get(mealName);
		System.out.println(meal);
	}
	
	
	
	// Adding and Removing dishes from the menu
	
	
	
	public void addDish(Dish item) throws ItemAlreadyExistsException {
		this.menu.addDish(item);
	}
	public void addDish(String itemName) throws ItemAlreadyExistsException {
		this.menu.addDish(itemName);
	}
	public void addDish(String itemName,boolean isVegetarian,boolean isGlutenFree, double price ,DishCategory category ) throws ItemAlreadyExistsException {
		Dish dish = FoodFactory.createDish(itemName, isVegetarian, price, isGlutenFree , category);
		this.menu.addDish(dish);
	}
	
	public void removeDish(Dish item) throws ItemNotFoundException {
		this.menu.removeDish(item);
	}
	
	public void removeDish(String itemName) throws ItemNotFoundException{
		this.menu.removeDish(itemName);
	}
	
	// Setting SpecialOffer meals TO DO and removing them
	
	// Setting the Price of a Meal
	public void setMealPrice(Meal meal) {
		if(meal.isMealOfTheWeek()) {
			meal.setPrice(this.mealPriceStrategy.calculatePrice(meal, this.getSpecialDiscount()));
		}
		else
			meal.setPrice(this.mealPriceStrategy.calculatePrice(meal, this.getGenericDiscount()));
	}
	
	// adding a dish to a certain meal
	
	public void addDishToMeal(String mealName,String dishName) throws ItemNotFoundException, BadMealCompositionCreationException {
		Dish dish = menu.getDish(dishName);
		Meal meal = meals.get(mealName);
		meal.addDishToMeal(dish);
		setMealPrice(meal);
	}
	
	
	
	
	
	public void setSpecialOffer(String mealName) throws ItemNotFoundException {
		if(meals.containsKey(mealName)) {
			Meal meal = meals.get(mealName);
			if (!meal.isMealOfTheWeek()) {
				meal.setMealOfTheWeek(true);
				this.setMealPrice(meal);
				NotificationService.getInstance().setOffer(meal,this,Offer.MEALOFTHEWEEK);
			    
			}
		}
		else throw new ItemNotFoundException("Sorry but the meal you are trying to set as Meal of the Week doesn't exist");
	}
	
	public void setSpecialOffer(Meal meal) throws ItemNotFoundException {
		if(meals.containsValue(meal)) {
			if (!meal.isMealOfTheWeek()) {
				meal.setMealOfTheWeek(true);
				this.setMealPrice(meal);
				NotificationService.getInstance().setOffer(meal,this,Offer.MEALOFTHEWEEK);
			}
		}
		else throw new ItemNotFoundException("Sorry but the meal you are trying to set as Meal of the Week doesn't exist");
	}
	
	
	public void removeFromSpecialOffer(String mealName) throws ItemNotFoundException {
		if(meals.containsKey(mealName)) {
			Meal meal = meals.get(mealName);
			if (meal.isMealOfTheWeek()) {
				meal.setMealOfTheWeek(false);
				this.setMealPrice(meal);
			}
		}
		else throw new ItemNotFoundException("Sorry but the meal you are trying to remove from Meal of the Week doesn't exist");
	}
	
	public void removeFromSpecialOffer(Meal meal) throws ItemNotFoundException {
		if(meals.containsValue(meal)) {
			if (meal.isMealOfTheWeek()) {
				meal.setMealOfTheWeek(false);
				this.setMealPrice(meal);
			}
		}
		else throw new ItemNotFoundException("Sorry but the meal you are trying to remove from Meal of the Week doesn't exist");
	}
	// Show Sorted FoodItems
	
	public void showSortedHalfMeals() {
	    ArrayList<FoodItem> halfMeals = new ArrayList<>();
	    for (Meal meal : this.meals.values()) {
	        if (meal instanceof HalfMeal) {
	            halfMeals.add(meal);
	        }
	    }

	    OrderedFrequencySorter sorter = new OrderedFrequencySorter();
	    ArrayList<FoodItem> sortedHalfMeals = sorter.sort(halfMeals);

	    System.out.println("=== Sorted Half Meals ===");
	    if (sortedHalfMeals.isEmpty()) {
	        System.out.println("No half meals available.");
	    } else {
	        for (FoodItem item : sortedHalfMeals) {
	            System.out.printf("%-20s | Ordered: %3d %s%n",
	                    item.getName(),
	                    item.getOrderedFrequency(),
	                    (item.getOrderedFrequency() == 1 ? "time" : "times"));
	        }
	    }
	    System.out.println();
	}

	public void showSortedFullMeals() {
	    ArrayList<FoodItem> fullMeals = new ArrayList<>();
	    for (Meal meal : this.meals.values()) {
	        if (meal instanceof FullMeal) {
	            fullMeals.add(meal);
	        }
	    }

	    OrderedFrequencySorter sorter = new OrderedFrequencySorter();
	    ArrayList<FoodItem> sortedFullMeals = sorter.sort(fullMeals);

	    System.out.println("=== Sorted Full Meals ===");
	    if (sortedFullMeals.isEmpty()) {
	        System.out.println("No full meals available.");
	    } else {
	        for (FoodItem item : sortedFullMeals) {
	            System.out.printf("%-20s | Ordered: %3d %s%n",
	                    item.getName(),
	                    item.getOrderedFrequency(),
	                    (item.getOrderedFrequency() == 1 ? "time" : "times"));
	        }
	    }
	    System.out.println();
	}

	public void showSortedDishes() {
	    ArrayList<FoodItem> dishes = new ArrayList<>(this.getMenu().getItems().values());
	    OrderedFrequencySorter sorter = new OrderedFrequencySorter();
	    ArrayList<FoodItem> sortedDishes = sorter.sort(dishes);

	    System.out.println("=== Sorted Dishes ===");
	    if (sortedDishes.isEmpty()) {
	        System.out.println("No dishes available.");
	    } else {
	        for (FoodItem item : sortedDishes) {
	            System.out.printf("%-20s | Ordered: %3d %s%n",
	                    item.getName(),
	                    item.getOrderedFrequency(),
	                    (item.getOrderedFrequency() == 1 ? "time" : "times"));
	        }
	    }
	    System.out.println();
	}

	
	
	public void displayRestaurant() {
	    StringBuilder output = new StringBuilder();

	    output.append("=========================================\n");
	    output.append("         Welcome to ").append(this.name).append("\n");
	    output.append("=========================================\n\n");

	    // Menu Section
	    output.append("🧾 MENU:\n");
	    if (this.menu.getItems().isEmpty()) {
	        output.append("  ❌ No items in the menu.\n");
	    } else {
	        output.append(this.menu.toString()).append("\n");
	    }

	    // Meals Section
	    output.append("🍽️  MEALS:\n");
	    if (!this.meals.isEmpty()) {
	        int index = 1;
	        for (Meal meal : this.meals.values()) {
	            output.append(String.format("  %d. %s (%.2f€)\n", index++, meal.getName(), meal.getPrice()));
	        }
	    } else {
	        output.append("  ❌ No meals currently offered.\n");
	    }

	    output.append("\n=========================================\n");

	    System.out.println(output.toString());
	}


	
	
	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();

	    sb.append("\n📍 Restaurant Details\n");
	    sb.append("────────────────────────────────────\n");
	    sb.append("🏷️  ID: ").append(getId()).append("\n");
	    sb.append("🏠 Name: ").append(getName()).append("\n");
	    sb.append("👤 Username: ").append(getUsername()).append("\n");
	    sb.append("📌 Location: ").append(location != null ? location.toString() : "N/A").append("\n");
	    sb.append("💸 Generic Discount: ").append(genericDiscount * 100).append(" %\n");
	    sb.append("🎯 Special Discount: ").append(specialDiscount * 100).append(" %\n");
	    sb.append("📋 Menu Items: ").append(menu != null ? menu.getItems().size() : 0).append(" item(s)\n");
	    sb.append("🛒 Total Orders Processed: ").append(orderCounter).append("\n");
	    sb.append("────────────────────────────────────");

	    return sb.toString();
	}



	
	
}
