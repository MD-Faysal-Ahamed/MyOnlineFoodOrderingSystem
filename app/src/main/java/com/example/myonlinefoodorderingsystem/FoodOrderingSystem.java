package com.example.myonlinefoodorderingsystem;

import java.util.*;

// User class to store user information
class User {
    String username, password, role;


    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
}

// FoodItem class to hold (ID, name, and price)
class FoodItem {
    int id;


    String name;
    double price;
    public FoodItem(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }
}

// Order class to store order details
class Order {

     int orderId;
    String customer;
    List<FoodItem> foodItems = new ArrayList<>();
    double totalAmount;
    public Order(int orderId, String customer) {
        this.orderId = orderId;
        this.customer = customer;
    }
    // Insert food item to order
    public void addItem(FoodItem item) {
        foodItems.add(item);
        totalAmount += item.price;
    }
    // Display order summary
         public void displayOrder() {
        System.out.println("\nOrder ID: " + orderId + " | Customer: " + customer);
        for (FoodItem item : foodItems) {
            System.out.printf(" - %s (RM%.2f)\n", item.name, item.price);
        }
        System.out.printf("Total Amount: RM%.2f\n", totalAmount);
    }
}

public class FoodOrderingSystem {
    static Scanner scanner = new Scanner(System.in);
    static List<User> users = new ArrayList<>(); // Stores users
    static List<FoodItem> menu = new ArrayList<>(); // Stores menu
    static List<Order> orders = new ArrayList<>(); // Stores orders
    static int orderIdCounter = 1;

    public static void main(String[] args) {


        // Adding default users


        users.add(new User("Admin", "Admin000", "admin"));
        users.add(new User("User", "User000", "customer"));

        // Adding sample food items
                 menu.add(new FoodItem(1, "Fried Rice", 5.00));
        menu.add(new FoodItem(2, "Steam Fish", 12.85));
        menu.add(new FoodItem(3, "Garlic Naan", 6.50));

        while (true) {
            System.out.println("\n==================================");
            System.out.println(" Welcome to the Food Ordering System!");
            System.out.println(" Please log in to continue.");
            System.out.println("====================================");

            User loggedInUser = login();
            if (loggedInUser.role.equals("admin")) {
                adminMenu();
            } else {
                customerMenu(loggedInUser.username);
            }
        }
    }

    // User login function
    public static User login() {


        while (true) {
            System.out.print("\nEnter Username (or type 'exit' to quit): ");
            String username = scanner.next();
            if (username.equalsIgnoreCase("exit")) {
                System.out.println("Exiting program...");
                System.exit(0);
            }
             System.out.print("Enter Password: ");
            String password = scanner.next();
            for (User user : users) {
                if (user.username.equals(username) && user.password.equals(password)) {
                    System.out.println("Login successful! Welcome, " + username);
                    return user;
                }
            }
            System.out.println("Invalid credentials, try again.");
        }
    }

    // Admin menu
    public static void adminMenu() {
        while (true) {

            System.out.println("\n--- Admin Menu ---");
             System.out.println("1. Display Available Menu");
                 System.out.println("2. Add Food Item");
                 System.out.println("3. View Orders");
            System.out.println("0. Logout");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1 -> viewMenu();
                case 2 -> addFoodItem();
                case 3 -> viewOrders();
                case 0 -> { return; }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    // Customer menu
    public static void customerMenu(String username) {
        Order order = new Order(orderIdCounter++, username);
        while (true) {
            // Printing this line in the UI
            System.out.println("\n--- Customer Menu ---");
            System.out.println("1. View Menu & Order");
            System.out.println("2. View My Order Summary");
            System.out.println("3. Checkout & Logout");
            System.out.println("0. Return to Main Menu");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1 -> placeOrder(order);
                case 2 -> order.displayOrder();
                case 3 -> {
                    orders.add(order);
                    System.out.println("Order placed successfully! Logging out...");
                    return;
                }
                case 0 -> { return; }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    // Function works by displaying all of the Menu
    public static void viewMenu() {
        System.out.println("\n--- Menu ---");
        for (FoodItem item : menu) {
            System.out.printf("%d. %s - RM%.2f\n", item.id, item.name, item.price);
        }
        System.out.println("0. Go Back");
        System.out.print("Enter 0 to go back: ");
        scanner.nextInt();
    }

    // Add food item for Admin
    public static void addFoodItem() {
        System.out.print("\nEnter food name (or type '0' to cancel): ");
        scanner.nextLine(); // Consume newline
        String name = scanner.nextLine();

        if (name.equals("0")) return;
        System.out.print("Enter price: ");
        double price = scanner.nextDouble();
        int newId = menu.size() + 1;
        menu.add(new FoodItem(newId, name, price));
        System.out.println("Food item added successfully!");
    }



    // Place an order for Customer
    public static void placeOrder(Order order) {
        while (true) {
                viewMenu();
            System.out.print("\nEnter food ID to add (0 to go back): ");
            int foodId = scanner.nextInt();
            if (foodId == 0) return;
            menu.stream().filter(item -> item.id == foodId).findFirst().ifPresentOrElse(
                    item -> {
                        order.addItem(item);
                        System.out.println(item.name + " added to order!");
                    },
                    () -> System.out.println("Invalid food ID. Try again.")
            );
        }
    }

    // Funtion to display all orders
    public static void viewOrders() {
                 System.out.println("\n--- All Orders ---");
        if (orders.isEmpty()) System.out.println("No orders yet.");
        else orders.forEach(Order::displayOrder);
    }
}