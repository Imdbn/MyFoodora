# MyFoodora

## Description

MyFoodora is a comprehensive food delivery management system simulating realistic interactions between restaurants, customers, and couriers. It includes user role management, dynamic menu creation with dietary preferences, order processing, delivery strategies, loyalty programs, and notifications. The project runs in any standard Java environment and features a command-line interface (CLI) for interactive use.

## Features

- Role-based user management (Manager, Customer, Courier).  
- Dynamic menu creation supporting dietary preferences and meal customization.  
- Handles concurrent order processing and multiple delivery policies.  
- Loyalty program with point-based fidelity cards and rewards.  
- Notification system via email and SMS for customers.  
- Interactive CLI with support for scripted test scenarios.

## Prerequisites

- Java Development Kit (JDK) 8 or higher.  
- Compatible Java environment for standard execution.

## Usage

Run the project by executing the `Main` class located in the `interface` package. This opens an interactive console for using the app and running test scenarios.

To execute a test scenario, enter one of the following commands in the CLI:
```java
runTest("TestScenario.txt");
```
or
```java
runTest("TestScenario1.txt");
```
depending on which scenario you want to run.

## Notes

- The project separates core logic from interface code for easy testing and maintenance.  
- A detailed project report is included in the repository for further understanding of the design and implementation.

## FreeForAll

Feel free to explore, modify, or use this project for educational purposes. 
