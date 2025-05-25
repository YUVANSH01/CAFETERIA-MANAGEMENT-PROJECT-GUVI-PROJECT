import java.sql.*;
import java.util.Scanner;



import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;



// Abstract User class
abstract class User {
    protected Scanner sc = new Scanner(System.in);
    protected Database db = Database.getInstance();
    public abstract void performAction();
}

// Admin class
class Admin extends User {

    public boolean login() {
        try {
            System.out.print("Admin username: ");
            String username = sc.nextLine();
            System.out.print("Admin password: ");
            String password = sc.nextLine();

            return db.verifyAdmin(username, password);
        } catch (Exception e) {
            System.out.println("Login error: " + e.getMessage());
            return false;
        }
    }

    @Override
    public void performAction() {
        try {
            while (true) {
                System.out.println("\n--- Admin Panel ---");
                System.out.println("1. Add Menu Item");
                System.out.println("2. Update Menu Item");
                System.out.println("3. View Orders");
                System.out.println("4. Logout");
                System.out.print("Enter choice: ");
                int choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {
                    case 1 -> db.addMenuItem();
                    case 2 -> db.updateMenuItem();
                    case 3 -> db.viewOrders();
                    case 4 -> {
                        System.out.println("Logging out...");
                        return;
                    }
                    default -> System.out.println("Invalid choice");
                }
            }
        } catch (Exception e) {
            System.out.println("Error in admin panel: " + e.getMessage());
        }
    }
}

// Customer class
class Customer extends User {
    @Override
    public void performAction() {
        try {
            while (true) {
                System.out.println("\n--- Customer Panel ---");
                System.out.println("1. View Stores & Place Order");
                System.out.println("2. Exit");
                System.out.print("Enter choice: ");
                int choice = sc.nextInt();
                sc.nextLine();

                if (choice == 1) {
                    db.placeOrder();
                } else if (choice == 2) {
                    System.out.println("Thank you! Visit again.");
                    return;
                } else {
                    System.out.println("Invalid choice");
                }
            }
        } catch (Exception e) {
            System.out.println("Error in customer panel: " + e.getMessage());
        }
    }
}

// Singleton Database handler class
class Database {
    private static Database instance = null;
    private Connection conn;
    private Scanner sc = new Scanner(System.in);

    private static final String URL = "jdbc:mysql://localhost:3306/cafeteria_db";
    private static final String USER = "root";
    private static final String PASS = "admin@123";  // Change this

    private Database() {
        try {
            conn = DriverManager.getConnection(URL, USER, PASS);
        } catch (SQLException e) {
            System.out.println("DB Connection failed: " + e.getMessage());
            System.exit(1);
        }
    }

    public static Database getInstance() {
        if (instance == null) instance = new Database();
        return instance;
    }

    public boolean verifyAdmin(String username, String password) throws SQLException {
        String sql = "SELECT * FROM admin WHERE username = ? AND password = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, username);
        ps.setString(2, password);
        ResultSet rs = ps.executeQuery();
        return rs.next();
    }

    public void showStores() throws SQLException {
        String sql = "SELECT * FROM store";
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);
        System.out.println("\nAvailable Stores:");
        while (rs.next()) {
            System.out.printf("ID: %d | Name: %s\n", rs.getInt("id"), rs.getString("name"));
        }
    }

    public void showMenuByStore(int storeId) throws SQLException {
        String sql = "SELECT * FROM menu WHERE store_id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, storeId);
        ResultSet rs = ps.executeQuery();
        System.out.println("\nMenu Items:");
        while (rs.next()) {
            System.out.printf("ID: %d | %s | Rs%.2f\n", rs.getInt("id"), rs.getString("name"), rs.getDouble("price"));
        }
    }

    public void addMenuItem() throws SQLException {
        showStores();
        System.out.print("Enter Store ID to add item: ");
        int storeId = Integer.parseInt(sc.nextLine());

        System.out.print("Enter item name: ");
        String name = sc.nextLine();
        System.out.print("Enter price: ");
        double price = Double.parseDouble(sc.nextLine());

        String sql = "INSERT INTO menu (store_id, name, price) VALUES (?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, storeId);
        ps.setString(2, name);
        ps.setDouble(3, price);
        int rows = ps.executeUpdate();

        if (rows > 0) System.out.println("Menu item added.");
        else System.out.println("Failed to add menu item.");
    }

    public void updateMenuItem() throws SQLException {
        showStores();
        System.out.print("Enter Store ID to update item: ");
        int storeId = Integer.parseInt(sc.nextLine());

        showMenuByStore(storeId);

        System.out.print("Enter Menu Item ID to update: ");
        int itemId = Integer.parseInt(sc.nextLine());

        System.out.print("Enter new name: ");
        String newName = sc.nextLine();
        System.out.print("Enter new price: ");
        double newPrice = Double.parseDouble(sc.nextLine());

        String sql = "UPDATE menu SET name = ?, price = ? WHERE id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, newName);
        ps.setDouble(2, newPrice);
        ps.setInt(3, itemId);

        int rows = ps.executeUpdate();
        if (rows > 0) System.out.println("Menu item updated.");
        else System.out.println("Failed to update item. Check IDs.");
    }

    public void viewOrders() throws SQLException {
        String sql = "SELECT o.id, s.name AS store_name, m.name AS item_name, o.quantity, o.order_time " +
                     "FROM orders o JOIN menu m ON o.item_id = m.id JOIN store s ON m.store_id = s.id " +
                     "ORDER BY o.order_time DESC";
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);
        System.out.println("\nOrders:");
        while (rs.next()) {
            System.out.printf("Order ID: %d | Store: %s | Item: %s | Qty: %d | Time: %s\n",
                    rs.getInt("id"),
                    rs.getString("store_name"),
                    rs.getString("item_name"),
                    rs.getInt("quantity"),
                    rs.getTimestamp("order_time"));
        }
    }

    public void placeOrder() throws SQLException {
        showStores();
        System.out.print("Select Store ID: ");
        int storeId = Integer.parseInt(sc.nextLine());

        showMenuByStore(storeId);

        System.out.print("Enter Menu Item ID to order: ");
        int itemId = Integer.parseInt(sc.nextLine());

        System.out.print("Enter quantity: ");
        int qty = Integer.parseInt(sc.nextLine());

        String sql = "INSERT INTO orders (item_id, quantity) VALUES (?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, itemId);
        ps.setInt(2, qty);

        int rows = ps.executeUpdate();
        if (rows > 0) System.out.println("Order placed successfully!");
        else System.out.println("Failed to place order.");
    }
}

// Main Application Class
public class CafeteriaManagerApp{
	public static void main(String[] args) {
    	String url = "jdbc:mysql://localhost:3306/cafeteria_db";
    	String userName = "root";
    	String password = "admin@123";
    
    	try {
        	Connection con = DriverManager.getConnection(url, userName, password);
        	if (con != null)
            		System.out.println("Connection Successful");
    		} catch (SQLException e) {
        		System.out.println("Error: " + e.getMessage());
    }

    Scanner sc = new Scanner(System.in);
    Database db = Database.getInstance();

    while (true) {
        System.out.println("\n=== Cafeteria Management System ===");
        System.out.println("1. Admin Login");
        System.out.println("2. Continue as Customer");
        System.out.println("3. Exit");
        System.out.print("Enter choice: ");
        int choice = Integer.parseInt(sc.nextLine());

        switch (choice) {
            case 1 -> {
                Admin admin = new Admin();
                if (admin.login()) {
                    System.out.println("Login successful!");
                    admin.performAction();
                } else {
                    System.out.println("Invalid admin credentials.");
                }
            }
            case 2 -> {
                Customer customer = new Customer();
                customer.performAction();
            }
            case 3 -> {
                System.out.println("Exiting... Goodbye!");
                System.exit(0);
            }
            default -> System.out.println("Invalid choice.");
        }
    }
}

}
