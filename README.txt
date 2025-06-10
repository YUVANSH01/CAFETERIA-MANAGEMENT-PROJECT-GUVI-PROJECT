Cafeteria Management System
===========================

This is a Java Swing-based desktop application for managing a cafeteria. It includes separate panels for Admin and Customers, supports user authentication, and interacts with a MySQL database.

Developed By: Yuvansh Khandelwal  
Java Version: Java SE 23  
MySQL Version: 8.0.35  
Connector/J Version: mysql-connector-java-8.0.35.jar

---

📁 Project Structure:
---------------------
Place all the following `.java` files in the same directory:
- CafeteriaUI.java
- MainFrame.java
- AdminLogin.java
- AdminPanelFrame.java
- CustomerPanelFrame.java

Make sure the MySQL connector JAR file (`mysql-connector-java-8.0.35.jar`) is also present in the same directory.

---

🧱 MySQL Database Setup:
------------------------
1. Open MySQL and create a database:
   ```sql
   CREATE DATABASE cafeteria_db;
   USE cafeteria_db;
Create the required tables:

sql
Copy
Edit
CREATE TABLE admin (
    username VARCHAR(50) PRIMARY KEY,
    password VARCHAR(50) NOT NULL
);

INSERT INTO admin VALUES ('admin', 'admin123');

CREATE TABLE menu (
    item_id INT AUTO_INCREMENT PRIMARY KEY,
    item_name VARCHAR(100),
    price DECIMAL(10,2)
);

CREATE TABLE `order` (
    order_id INT AUTO_INCREMENT PRIMARY KEY,
    customer_name VARCHAR(100),
    item_id INT,
    quantity INT,
    FOREIGN KEY (item_id) REFERENCES menu(item_id)
);

CREATE TABLE store (
    store_id INT AUTO_INCREMENT PRIMARY KEY,
    item_name VARCHAR(100),
    quantity INT
);
⚙️ How to Compile:
Open Command Prompt and navigate to the project directory.

Compile all .java files:

bash
Copy
Edit
javac -cp ".;mysql-connector-java-8.0.35.jar" *.java
🚀 How to Run:
Launch the application:

bash
Copy
Edit
java -cp ".;mysql-connector-java-8.0.35.jar" CafeteriaUI
Ensure that MySQL server is running and your database credentials are correct in the Java files (likely hardcoded in a getConnection() or similar method).

✅ Features:
Admin Login

Menu Management

Order Placement

Store Inventory Tracking

GUI-based user interaction

❗ Troubleshooting:
If you see No suitable driver found, make sure the .jar file is correct and added properly in -cp.

If GUI doesn't load, check that all .java files compiled correctly.

Ensure the MySQL service is running and port 3306 is open.

