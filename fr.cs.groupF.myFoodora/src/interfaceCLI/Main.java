package interfaceCLI;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import coreSystem.CoreSystem;
import exceptions.*;
import users.*;
import targetProfitPolicy.*;
import deliveryPolicy.*;
import fidelityCards.*;
import menuMeals.*;
import menuMeals.Dish.DishCategory;
import notificationService.Offer;

public class Main {
private static CoreSystem coreSystem;
private static Map<String, Order> orderNameMap = new HashMap<>();

	public static void main(String[] args) throws ItemAlreadyExistsException, UndefinedTypeException, BadMealCompositionCreationException, ItemNotFoundException, UndefinedFidelityCardException {
		coreSystem = CoreSystem.getInstance();
		 try {
		        CoreSystem.my_Foodora_ini();
		    } catch (Exception e) {
		        System.out.println("Error during system initialization: " + e.getMessage());
		        e.printStackTrace();
		    }
		Scanner scanner = new Scanner(System.in);
	
		welcomeUser();
		
		System.out.println("Please use 'login <username> <password>'.");
		System.out.println("For any inquiries regarding the commands, type 'help'.");
	
		while (true) {
			System.out.print("> ");
			String input = scanner.nextLine();
			if (input.equalsIgnoreCase("exit")) {
				System.out.println("Exiting...");
				break;
			}
			handleCommand(input);
		}
	
		scanner.close();
	}
	public static void runTestFile(String fileName) {
	    FileReader file = null;
	    BufferedReader reader = null;
	    int i = 1;

	    try {
	        // Print current working directory
	        System.out.println("🔍 Looking for file in: " + new File(".").getAbsolutePath());

	        
	        file = new FileReader(fileName);

	        
	        reader = new BufferedReader(file);
	        String line = "";

	        
	        while ((line = reader.readLine()) != null) {
	            System.out.println(i + " - " + line);
	            handleCommand(line);
	            System.out.println();
	            i++;
	        }
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    } finally {
	        if (file != null) {
	            try {
	                file.close();
	                reader.close();
	            } catch (IOException e) {
	                System.out.println("File not found: " + fileName);
	                
	            }
	        }
	    }
	}

	
	public static void welcomeUser() {
	    System.out.println("===============================================");
	    System.out.println("         🍽️  Welcome to MyFoodora!  🍽️");
	    System.out.println("===============================================");
	    System.out.println("We're thrilled to have you here. 😊");
	    System.out.println("From delicious meals to quick deliveries,");
	    System.out.println("we’re here to make your food experience better.");
	    System.out.println("-----------------------------------------------");
	    System.out.println("....log in to continue...");
	    System.out.println();
	}
	
	private static void printHelp() {
	    System.out.println("Available commands:\n");
	    System.out.println("runTest <testScenarioFile> - Execute commands from a scenario file.\n");
	    System.out.println("runtest <testScenarioFile> - Execute commands from a scenario file.\n");
	    System.out.println("login <username> <password> - Log in with specified credentials.");
	    System.out.println("logout - Log out of the current session.");
	    System.out.println("----------------------------------------");
	    System.out.println("Manager related tasks:");
	    System.out.println("registerCustomer <firstName> <lastName> <username> <password>");
	    System.out.println("registerRestaurant <name> <username> <password>");
	    System.out.println("registerCourier <firstName> <lastName> <username> <password>");
	    System.out.println("showCourierDeliveries");
	    System.out.println("showRestaurantsTop");
	    System.out.println("showMenuItem <restaurant-name>");
	    System.out.println("setDeliveryPolicy <delPolicyName>");
	    System.out.println("setProfitPolicy <ProfitPolicyName>");
	    System.out.println("associateCard <userName> <cardType>");
	    System.out.println("showTotalProfit [<startDate> <endDate>]");
	    System.out.println("----------------------------------------");
	    System.out.println("Customer related tasks:");
	    System.out.println("createOrder <restaurantName> <orderName>");
	    System.out.println("addItem2Order <orderName> <itemName>");
	    System.out.println("endOrder <orderName>");
	    System.out.println("removeConsensus");
	    System.out.println("setConsensusPhone <phone>");
	    System.out.println("setConsensusMail <mail>");
	    System.out.println("historyOfOrders");
	    System.out.println("registerFidelityCard <cardType>");
	    System.out.println("unregisterFidelityCard");
	    System.out.println("displayFidelityCardInfo");
	    System.out.println("----------------------------------------");
	    System.out.println("Restaurant related tasks:");
	    System.out.println("showMenu");
	    System.out.println("setSpecialDiscountFactor <specialDiscountFactor>");
	    System.out.println("setGenericDiscountFactor <genericDiscountFactor>");
	    System.out.println("addDishRestaurantMenu <dishName> <dishCategory> <foodType> <glutenFree> <unitPrice>");
	    System.out.println("createMeal <mealName> <mealType> [<standardOrVegetarian> <GlutenFree>]");
	    System.out.println("addDish2Meal <dishName> <mealName>");
	    System.out.println("showMeal <mealName>");
	    System.out.println("setSpecialOffer <mealName>");
	    System.out.println("removeFromSpecialOffer <mealName>");
	    System.out.println("showSortedHalfMeals");
	    System.out.println("showSortedDishes");
	    System.out.println("----------------------------------------");
	    System.out.println("Courier related tasks:");
	    System.out.println("onDuty <username>");
	    System.out.println("offDuty <username>");
	}
	
	private static void handleCommand(String input) throws UndefinedFidelityCardException {
	    String[] parts = input.trim().split("\\s+");
	    String command = parts[0];

	    switch (command) {
	    	case "runTest":
		        if (parts.length == 2) {
		            String testFilePath = parts[1];
		            try {
		                runTestFile(testFilePath);
		            } catch (Exception e) {
		                System.out.println("Error running test file: " + e.getMessage());
		            }
		        } else {
		            System.out.println("Usage: runTest <testScenario-file>");
		        }
		        break;
	    	case "runtest":
		        if (parts.length == 2) {
		            String testFilePath = parts[1];
		            try {
		                runTestFile(testFilePath);
		            } catch (Exception e) {
		                System.out.println("Error running test file: " + e.getMessage());
		            }
		        } else {
		            System.out.println("Usage: runtest <testScenario-file>");
		        }
		        break;
	        case "help":
	            printHelp();
	            break;

	        case "login":
	            if (parts.length == 3) {
	                String username = parts[1];
	                String password = parts[2];
	                try {
	                    String result = coreSystem.login(username, password);
	                    System.out.println(result);
	                    System.out.println("Welcome back to myFoodora, " + CoreSystem.getCurrentUser().get().getName() + "!");
	                    

	                    // Check if logged-in user is a Customer
	                    Optional<UserType> userTypeOpt = CoreSystem.getCurrentUserType();
	                    if (userTypeOpt.isPresent() && userTypeOpt.get() == UserType.CUSTOMER) {
	                    	notifyCustomerOnLogin((Customer) CoreSystem.getCurrentUser().get(), coreSystem);
	                        Map<String, Restaurant> restaurants = CoreSystem.getRestaurants();
	                        if (restaurants.isEmpty()) {
	                            System.out.println("Currently, there are no restaurants available.");
	                        } else {
	                            System.out.println("Here are the restaurants available for you to order from:");
	                            for (Restaurant r : restaurants.values()) {
	                                System.out.println(" - " + r.getName());
	                            }
	                        }
	                    }
	                } catch (NoMatchingCredentialsException e) {
	                    System.out.println("Login failed: " + e.getMessage());
	                }
	            } else {
	                System.out.println("Usage: login <username> <password>");
	            }
	            break;



	        case "logout":
	            coreSystem.logout();
	            System.out.println("Logged out.");
	            break;
	        //=================================================================================================================================================================================================   
	        // manager's Methods
		    //=================================================================================================================================================================================================   

	        case "registerCustomer":
	            if (parts.length == 5) {
	                String firstName = parts[1];
	                String surName = parts[2];
	                String username = parts[3];
	                String password = parts[4];

	                Customer customer = new Customer(firstName, username, password, surName);

	                try {
	                    coreSystem.addUser(customer);
	                    
	                } catch (Exception e) {
	                    System.out.println("Registering failed! " + e.getMessage());
	                }
	            } else {
	                System.out.println("Usage: registerCustomer <firstName> <lastName> <username> <password>");
	            }
	            break;

	        case "registerRestaurant":
	            if (parts.length == 4) {
	                String name = parts[1];
	                String username = parts[2];
	                String password = parts[3];

	                Restaurant restaurant = new Restaurant(name, username, password);

	                try {
	                    coreSystem.addUser(restaurant);
	            
	                } catch (Exception e) {
	                    System.out.println("Registering failed! " + e.getMessage());
	                }
	            } else {
	                System.out.println("Usage: registerRestaurant <name> <username> <password>");
	            }
	            break;

	        case "registerCourier":
	            if (parts.length == 6) {
	                String firstName = parts[1];
	                String lastName = parts[2];
	                String username = parts[3];
	                String password = parts[4];
	                String phone = parts[5];

	                Courier courier = new Courier(firstName, username, password, lastName, phone);

	                try {
	                    coreSystem.addUser(courier);
	                    
	                } catch (Exception e) {
	                    System.out.println("Registering failed! " + e.getMessage());
	                }
	            } else {
	                System.out.println("Usage: registerCourier <firstName> <lastName> <username> <password> <phone>");
	            }
	            break;
	            
	        case "showCourierDeliveries":
	            if (parts.length == 1) {
	                try {
	                    coreSystem.showSortedCouriers();
	                } catch (Exception e) {
	                    System.out.println("Fail to display sorted Couriers! " + e.getMessage());
	                }
	            } else {
	                System.out.println("Usage: showCourierDeliveries");
	            }
	            break;

	        case "showCustomers":
	            if (parts.length == 1) {
	                try {
	                    coreSystem.showCustomers();
	                } catch (Exception e) {
	                    System.out.println("Fail to display Customers! " + e.getMessage());
	                }
	            } else {
	                System.out.println("Usage: showCustomers");
	            }
	            break;

	        case "showRestaurantTop":
	            if (parts.length == 1) {
	                try {
	                    coreSystem.showSortedRestaurants();
	                } catch (Exception e) {
	                    System.out.println("Fail to display sorted Restaurants! " + e.getMessage());
	                }
	            } else {
	                System.out.println("Usage: showRestaurantTop");
	            }
	            break;

	        case "showMenuItem":
	            if (parts.length == 2) {
	                String restaurantName = parts[1];
	                try {
	                    coreSystem.showMenuItem(restaurantName);
	                } catch (Exception e) {
	                    System.out.println("Fail to display menu item! " + e.getMessage());
	                }
	            } else {
	                System.out.println("Usage: showMenuItem <restaurant-name>");
	            }
	            break;
	            
	        case "setDeliveryPolicy":
	            if (parts.length == 2) {
	                String delPolicyStr = parts[1];
	                try {
	                    DeliveryPolicyType deliveryPolicyType = DeliveryPolicyType.valueOf(delPolicyStr.toUpperCase());
	                    coreSystem.setDeliveryPolicyType(deliveryPolicyType);
	                    System.out.println("Delivery policy changed successfully to: " + deliveryPolicyType);
	                } catch (IllegalArgumentException e) {
	                    System.out.println("Invalid delivery policy type: " + delPolicyStr);
	                } catch (Exception e) {
	                    System.out.println("Failed to set delivery policy: " + e.getMessage());
	                }
	            } else {
	                System.out.println("Usage: setDeliveryPolicy <DELIVERY_POLICY_TYPE> (FASTEST or FAIR)");
	            }
	            break;


	        case "setProfitPolicy":
	            if (parts.length == 3) {
	                String profPolicyStr = parts[1];
	                try {
	                    double target = Double.parseDouble(parts[2]);
	                    TargetProfitPolicyType profitPolicyType = TargetProfitPolicyType.valueOf(profPolicyStr.toUpperCase());
	                    coreSystem.setTargetProfitPolicyType(profitPolicyType, target);
	                    
	                    // Assuming computeTargetProfitPolicyParam is a static method in TargetProfitPolicyType
	                    double computedParam = CoreSystem.getTargetProfitPolicy().computeTargetProfitPolicyParam(coreSystem, target);
	                    
	                    System.out.println("Profit policy changed successfully to: " + profitPolicyType 
	                                       + " with parameter value: " + computedParam);
	                } catch (IllegalArgumentException e) {
	                    System.out.println("Invalid profit policy type: " + profPolicyStr); 
	                } catch (Exception e) {
	                    System.out.println("Failed to set profit policy: " + e.getMessage());
	                }
	            } else {
	                System.out.println("Usage: setProfitPolicy <TARGET_PROFIT_POLICY_TYPE>(SERVICEFEE , MARKUP or DELIVERYCOST) <target>");
	            }
	            break;

	        case "associateCard":
	            if (parts.length == 3) {
	                String userName = parts[1];
	                String cardTypeStr = parts[2];
	                try {
	                    FidelityCardType cardType = FidelityCardType.valueOf(cardTypeStr.toUpperCase());
	                    coreSystem.associateCard(userName, cardType);
	                    System.out.println("Fidelity card of type " + cardType + " has been assigned successfully to customer " + userName + "!");
	                } catch (IllegalArgumentException e) {
	                    System.out.println("Invalid card type: " + cardTypeStr);
	                } catch (Exception e) {
	                    System.out.println("User doesn't exist");
	                }
	            } else {
	                System.out.println("Usage : associateCard <userName> <cardType>");
	            }
	            break;

	        case "showTotalProfit":
	            if (parts.length == 1) {
	                try {
	                    System.out.println("Total Profit: " + coreSystem.computeTotalProfit());
	                } catch (Exception e) {
	                    System.out.println(e.getMessage());
	                }
	            } else if (parts.length == 3) {
	                String startDateStr = parts[1];
	                String endDateStr = parts[2];
	                try {
	                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	                    Calendar startCal = Calendar.getInstance();
	                    Calendar endCal = Calendar.getInstance();
	                    startCal.setTime(sdf.parse(startDateStr));
	                    endCal.setTime(sdf.parse(endDateStr));

	                    System.out.println("Total Profit: " + coreSystem.computeTotalProfit(startCal, endCal));
	                } catch (ParseException e) {
	                    System.out.println("Invalid date format. Please use yyyy-MM-dd.");
	                } catch (Exception e) {
	                    System.out.println(e.getMessage());
	                }
	            } else {
	                System.out.println("Usage : showTotalProfit <> or showTotalProfit <startDate> <endDate>");
	            }
	            break;
	            //=================================================================================================================================================================================================   
		        // Customer's Methods
			    //=================================================================================================================================================================================================     
	        case "createOrder":
	            if (parts.length == 3) {
	                String restaurantName = parts[1];
	                String orderName = parts[2];

	                try {
	                    Order order = coreSystem.placeOrder(restaurantName);
	                    orderNameMap.put(orderName, order); // Use map for name lookup

	                    System.out.println("Order from Restaurant " + restaurantName + " created successfully");

	                    for (Restaurant restaurant : CoreSystem.getRestaurants().values()) {
	                        if (restaurant.getName().equalsIgnoreCase(restaurantName)) {
	                            restaurant.displayRestaurant();
	                            break;
	                        }
	                    }
	                } catch (Exception e) {
	                    System.out.println("Fail to create order! " + e.getMessage());
	                }
	            } else {
	                System.out.println("Usage : createOrder <restaurantName> <orderName>");
	            }
	            break;


	        case "addItem2Order":
	            if (parts.length >= 3) {
	                String orderName = parts[1];
	                String itemName = parts[2];
	                int quantity = 1;

	                if (parts.length == 4) {
	                    try {
	                        quantity = Integer.parseInt(parts[3]);
	                    } catch (NumberFormatException e) {
	                        System.out.println("Invalid quantity. Defaulting to 1.");
	                    }
	                }

	                Order order = orderNameMap.get(orderName);
	                if (order == null) {
	                    System.out.println("Order '" + orderName + "' not found.");
	                    break;
	                }

	                try {
	                    coreSystem.addItemToOrder(order, itemName, quantity);
	                    System.out.println(itemName + " x" + quantity + " added to order '" + orderName + "'.");
	                } catch (Exception e) {
	                    System.out.println("Fail to add item! " + e.getMessage());
	                }
	            } else {
	                System.out.println("Usage : addItem2Order <orderName> <itemName> [quantity]");
	            }
	            break;


	        case "endOrder":
	            if (parts.length == 2) {
	                String orderName = parts[1];
	                Order order = orderNameMap.get(orderName);

	                if (order == null) {
	                    System.out.println("Order '" + orderName + "' not found.");
	                    break;
	                }

	                try {
	                    coreSystem.endOrder(order);
	                    orderNameMap.remove(orderName);  // Remove order from the map after ending
	                    System.out.println("Order '" + orderName + "' finalized successfully.");
	                } catch (Exception e) {
	                    System.out.println("Fail to finalize order! " + e.getMessage());
	                }
	            } else {
	                System.out.println("Usage : endOrder <orderName>");
	            }
	            break;

	        case "historyOfOrders":
	            if (parts.length == 1) {
	                try {
	                    coreSystem.displayHistoryOrders();
	                } catch (PermissionDeniedException e) {
	                    System.out.println("Access denied: " + e.getMessage());
	                } catch (Exception e) {
	                    System.out.println("An unexpected error occurred while displaying order history: " + e.getMessage());
	                }
	            } else {
	                System.out.println("Usage: historyOfOrders");
	            }
	            break;
	        
	        case "setConsensusMail":
	            if (parts.length == 2) {
	                String mail = parts[1];
	                try {
	                    coreSystem.setConsensusMail(mail);
	                    System.out.println("✅ You will now receive email notifications about special offers at: " + mail);
	                } catch (PermissionDeniedException e) {
	                    System.out.println("❌ Access denied: " + e.getMessage());
	                } catch (Exception e) {
	                    System.out.println("⚠️ An unexpected error occurred while setting mail consensus: " + e.getMessage());
	                }
	            } else {
	                System.out.println("Usage: setConsensusMail <email>");
	            }
	            break;

	        case "setConsensusPhone":
	            if (parts.length == 2) {
	                String phone = parts[1];
	                try {
	                    coreSystem.setConsensusPhone(phone);
	                    System.out.println("✅ You will now receive phone notifications about special offers at: " + phone);
	                } catch (PermissionDeniedException e) {
	                    System.out.println("❌ Access denied: " + e.getMessage());
	                } catch (Exception e) {
	                    System.out.println("⚠️ An unexpected error occurred while setting phone consensus: " + e.getMessage());
	                }
	            } else {
	                System.out.println("Usage: setConsensusPhone <phoneNumber>");
	            }
	            break;
	        case "removeConsensus":
	            try {
	                coreSystem.removeConsensus();
	                System.out.println("✅ Consensus removed successfully.");
	            } catch (PermissionDeniedException e) {
	                System.out.println("❌ Access denied: " + e.getMessage());
	            } catch (Exception e) {
	                System.out.println("⚠️ An unexpected error occurred while removing consensus: " + e.getMessage());
	            }
	            break;



	        case "registerFidelityCard":
	            if (parts.length == 2) {
	                try {
	                    FidelityCardType cardType = FidelityCardType.valueOf(parts[1].toUpperCase());
	                    coreSystem.registerFidelityCard(cardType);
	                    System.out.println("Registering successful! Welcome to the " + cardType + " fidelity card plan!");
	                } catch (IllegalArgumentException e) {
	                    System.out.println("Invalid fidelity card type: " + parts[1]);
	                } catch (UndefinedFidelityCardException e) {
	                    System.out.println("Fidelity card type not recognized: " + e.getMessage());
	                } catch (PermissionDeniedException e) {
	                    System.out.println("Access denied: " + e.getMessage());
	                } catch (Exception e) {
	                    System.out.println("An unexpected error occurred while registering the fidelity card: " + e.getMessage());
	                }
	            } else {
	                System.out.println("Usage: registerFidelityCard <cardType> (POINT , BASIC or LOTTERY)");
	            }
	            break;
	            
	        case "unregisterFidelityCard":
	            if (parts.length == 1) {
	                try {
	                    coreSystem.unregisterFidelityCard();
	                    System.out.println("You have successfully unregistered from the fidelity card plan.");
	                } catch (PermissionDeniedException | UndefinedFidelityCardException e) {
	                    System.out.println("Fail to unregister Fidelity Card plan: " + e.getMessage());
	                } catch (Exception e) {
	                    System.out.println("An unexpected error occurred: " + e.getMessage());
	                }
	            } else {
	                System.out.println("Usage: unregisterFidelityCard");
	            }
	            break;
	        case "displayFidelityCardInfo":
	            if (parts.length == 1) {
	                try {
	                    coreSystem.displayFidelityCardInfo();
	                } catch (PermissionDeniedException e) {
	                    System.out.println("Fail to display Fidelity Card information: " + e.getMessage());
	                } catch (Exception e) {
	                    System.out.println("An unexpected error occurred: " + e.getMessage());
	                }
	            } else {
	                System.out.println("Usage: displayFidelityCardInfo");
	            }
	            break;
	            
	            //=================================================================================================================================================================================================   
		        // restaurant's Methods
			    //=================================================================================================================================================================================================
	        case "showMenu":
	            if (parts.length == 1) {
	                try {
	                    coreSystem.showMenuAndMeals();
	                } catch (Exception e) {
	                    System.out.println("Fail to display menu! " + e.getMessage());
	                }
	            } else {
	                System.out.println("Usage : showMenu");
	            }
	            break;

	        case "setSpecialDiscountFactor":
	            if (parts.length == 2) {
	                String specialDiscountFactor = parts[1];
	                try {
	                    coreSystem.setSpecialDiscount(specialDiscountFactor);
	                    System.out.println("Special discount updated successfully to " + specialDiscountFactor);
	                } catch (Exception e) {
	                    System.out.println("Fail to set special discount factor! " + e.getMessage());
	                }
	            } else {
	                System.out.println("Usage : setSpecialDiscountFactor <specialDiscountFactor>");
	            }
	            break;

	        case "setGenericDiscountFactor":
	            if (parts.length == 2) {
	                String genericDiscountFactor = parts[1];
	                try {
	                    coreSystem.setGenericDiscount(genericDiscountFactor);
	                    System.out.println("Generic discount updated successfully to " + genericDiscountFactor);
	                } catch (Exception e) {
	                    System.out.println("Fail to set generic discount factor! " + e.getMessage());
	                }
	            } else {
	                System.out.println("Usage : setGenericDiscountFactor <genericDiscountFactor>");
	            }
	            break;

	        case "addDishRestaurantMenu":
	            if (parts.length == 6) {
	                String dishName = parts[1];
	                String dishCategoryStr = parts[2];
	                String foodType = parts[3];
	                String glutenFree = parts[4];
	                String unitPriceStr = parts[5];

	                try {
	                    DishCategory category = DishCategory.valueOf(dishCategoryStr.toUpperCase());
	                    boolean isVegetarian = foodType.equalsIgnoreCase("vegetarian");
	                    boolean isGlutenFree = glutenFree.equalsIgnoreCase("yes");
	                    double unitPrice = Double.parseDouble(unitPriceStr);

	                    coreSystem.addDishRestaurantMenu(dishName, category, isVegetarian, isGlutenFree, unitPrice);

	                    // Print dish details
	                    System.out.println("Dish added successfully to the Menu:");
	                    System.out.println(" - Name       : " + dishName);
	                    System.out.println(" - Category   : " + category);
	                    System.out.println(" - Food Type  : " + (isVegetarian ? "Vegetarian" : "Non-Vegetarian"));
	                    System.out.println(" - Gluten Free: " + (isGlutenFree ? "Yes" : "No"));
	                    System.out.println(" - Unit Price : €" + String.format("%.2f", unitPrice));
	                } catch (IllegalArgumentException e) {
	                    System.out.println("Invalid dish category or format error: " + e.getMessage());
	                } catch (Exception e) {
	                    System.out.println("Fail to add Dish to Menu! " + e.getMessage());
	                }

	            } else {
	                System.out.println("Usage : addDishRestaurantMenu <dishName> <dishCategory>(STARTER , MAINDISH or DESSERT) <foodType>(Vegetarian or Non-Vegetarian) <glutenFree (yes or no)> <unitPrice>");
	            }
	            break;


	        case "createMeal":
	            if (parts.length == 3) {
	                String mealName = parts[1];
	                String mealTypeStr = parts[2];

	                try {
	                    MealType mealType = MealType.valueOf(mealTypeStr.toUpperCase());
	                    System.out.println("Creating meal '" + mealName + "' with type '" + mealType + "'. By default, it will be standard and gluten-containing.");
	                    coreSystem.createMeal(mealName, mealType);
	                    System.out.println("Meal '" + mealName + "' created successfully!");
	                } catch (Exception e) {
	                    System.out.println("Failed to create meal '" + mealName + "'. Reason: " + e.getMessage());
	                }
	            } else if (parts.length == 5) {
	                String mealName = parts[1];
	                String mealTypeStr = parts[2];
	                String vegStr = parts[3];
	                String glutenStr = parts[4];

	                try {
	                    MealType mealType = MealType.valueOf(mealTypeStr.toUpperCase());
	                    boolean isVegetarian = vegStr.equalsIgnoreCase("vegetarian");
	                    boolean isGlutenFree = glutenStr.equalsIgnoreCase("yes");

	                    System.out.println("Creating meal '" + mealName + "' with type '" + mealType + "', vegetarian: " + isVegetarian + ", gluten free: " + isGlutenFree + ".");
	                    coreSystem.createMeal(mealName, mealType, isVegetarian, isGlutenFree);
	                    System.out.println("Meal '" + mealName + "' created successfully!");
	                } catch (Exception e) {
	                    System.out.println("Failed to create meal '" + mealName + "'. Reason: " + e.getMessage());
	                }
	            } else {
	                System.out.println("Usage : createMeal <mealName> <mealType (full or half)>");
	                System.out.println("Or    : createMeal <mealName> <mealType (full/half)> <standardOrVegetarian> <GlutenFree (Yes/No)>");
	            }
	            break;

	        case "addDish2Meal":
	            if (parts.length == 3) {
	                String dishName = parts[1];
	                String mealName = parts[2];
	                try {
	                    System.out.println("Adding dish '" + dishName + "' to meal '" + mealName + "'.");
	                    coreSystem.addDish2Meal(mealName, dishName); 
	                    System.out.println("Dish '" + dishName + "' added successfully to meal '" + mealName + "'.");
	                } catch (Exception e) {
	                    System.out.println("Failed to add dish '" + dishName + "' to meal '" + mealName + "'. Reason: " + e.getMessage());
	                }
	            } else {
	                System.out.println("Usage : addDish2Meal <dishName> <mealName>");
	            }
	            break;


	        case "showMeal":
	            if (parts.length == 2) {
	                String mealName = parts[1];
	                try {
	                    coreSystem.showMeal(mealName);
	                } catch (Exception e) {
	                    System.out.println("Fail to display meal! " + e.getMessage());
	                }
	            } else {
	                System.out.println("Usage : showMeal <mealName>");
	            }
	            break;
	        case "setSpecialOffer":
	            if (parts.length == 2) {
	                String mealName = parts[1];
	                try {
	                    coreSystem.setSpecialOffer(mealName);
	                    System.out.println("New Special Offer: " + mealName + " is the meal of the Week!");
	                } catch (Exception e) {
	                    System.out.println("Fail to set special offer on meal " + mealName + "! " + e.getMessage());
	                }
	            } else {
	                System.out.println("Usage: setSpecialOffer <mealName>");
	            }
	            break;

	        case "removeFromSpecialOffer":
	            if (parts.length == 2) {
	                String mealName = parts[1];
	                try {
	                    coreSystem.removeFromSpecialOffer(mealName);
	                    System.out.println("Removed special offer from meal: " + mealName);
	                } catch (Exception e) {
	                    System.out.println("Fail to remove special offer from meal " + mealName + "! " + e.getMessage());
	                }
	            } else {
	                System.out.println("Usage: removeFromSpecialOffer <mealName>");
	            }
	            break;

	        case "showSortedHalfMeals":
	            if (parts.length == 1) {
	                try {
	                    coreSystem.showSortedHalfMeals();
	                } catch (Exception e) {
	                    System.out.println("Fail to display sorted half meals! " + e.getMessage());
	                }
	            } else {
	                System.out.println("Usage: showSortedHalfMeals");
	            }
	            break;

	        case "showSortedFullMeals":
	            if (parts.length == 1) {
	                try {
	                    coreSystem.showSortedFullMeals();
	                } catch (Exception e) {
	                    System.out.println("Fail to display sorted full meals! " + e.getMessage());
	                }
	            } else {
	                System.out.println("Usage: showSortedFullMeals");
	            }
	            break;

	        case "showSortedDishes":
	            if (parts.length == 1) {
	                try {
	                    coreSystem.showSortedDishes();
	                } catch (Exception e) {
	                    System.out.println("Fail to display sorted dishes! " + e.getMessage());
	                }
	            } else {
	                System.out.println("Usage: showSortedDishes");
	            }
	            break;
	            
	            //=================================================================================================================================================================================================   
		        // courier's Methods
			    //=================================================================================================================================================================================================
	        case "onDuty":
	            if (parts.length == 2) {
	                String username = parts[1];
	                try {
	                    coreSystem.setOnDuty(username, true);
	                    System.out.println("Status changed successfully");
	                } catch (Exception e) {
	                    System.out.println(e.getMessage());
	                }
	            } else {
	                System.out.println("Usage: onDuty <username>");
	            }
	            break;

	        case "offDuty":
	            if (parts.length == 2) {
	                String username = parts[1];
	                try {
	                    coreSystem.setOnDuty(username, false);
	                    System.out.println("Status changed successfully");
	                } catch (Exception e) {
	                    System.out.println(e.getMessage());
	                }
	            } else {
	                System.out.println("Usage: offDuty <username>");
	            }
	            break;

	        default:
	            System.out.println("Unknown command. Type 'help' for a list of commands.");
	    }
	}
	
	public static void notifyCustomerOnLogin(Customer customer, CoreSystem coreSystem) {
	    if (!customer.isConsensus()) return;  // Only notify if consented

	    Map<String, Restaurant> restaurants = CoreSystem.getRestaurants();

	    for (Restaurant restaurant : restaurants.values()) {
	        // Loop over the meals of the restaurant
	        for (Meal meal : restaurant.getMeals().values()) {
	            if (meal.isMealOfTheWeek()) {
	                customer.update(restaurant, Offer.MEALOFTHEWEEK, meal);
	            }
	        }
	        // Notify generic discount if generic discount is higher than default value (0.05)
	        if (restaurant.getGenericDiscount() > 0.05) {
	            customer.update(restaurant, Offer.GENERICDISCOUNT, null);
	        }
	        // Notify special discount if special discount is higher than default value (0.1)
	        if (restaurant.getSpecialDiscount() > 0.1) {
	            customer.update(restaurant, Offer.SPECIALDISCOUNT, null);
	        }
	    }
	}

}
