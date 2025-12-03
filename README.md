### Online Reservation System

A simple and beginner-friendly Online Train Reservation System built using Java Swing, MySQL, and JDBC.
This project includes:

✔ User Login
✔ Train Ticket Reservation
✔ Auto Train Name Filling
✔ Ticket Cancellation using PNR
✔ MySQL Database Storage
✔ User-friendly GUI (Java Swing)

## Features

# 1️⃣ Login Module

User must enter valid username & password.

Credentials are verified from the database.

# 2️⃣ Reservation Module

Enter Passenger Name, Train No., Class, Date, From, To.

Train Name auto-filled based on Train Number.

Input validation for empty fields.

Successful reservation automatically generates a PNR in MySQL.

# 3️⃣ Cancellation Module

Enter PNR to search booking.

Displays full ticket details.

Allows user to cancel reservation permanently from the database.

Database Structure (MySQL)

Database
reservationdb

Table:users
CREATE TABLE users (
id INT AUTO_INCREMENT PRIMARY KEY,
username VARCHAR(50),
password VARCHAR(50)
);
INSERT INTO users (username, password) VALUES ('admin', 'admin123');

Table: reservations
CREATE TABLE reservations (
pnr INT AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(100),
train_no VARCHAR(20),
train_name VARCHAR(100),
class_type VARCHAR(20),
journey_date DATE,
from_place VARCHAR(100),
to_place VARCHAR(100)
);

Technologies Used
Component	               Technology
Programming Language	   Java (JDK 8+)
GUI Framework	           Java Swing
Database	               MySQL
Connectivity	           JDBC
IDE Used	               IntelliJ IDEA

## How to Run the Project

✔ Step 1 — Clone the Repository
git clone https://github.com/AshaKommana/online-reservation-system.git

✔ Step 2 — Open in IntelliJ IDEA

Open the project folder

Ensure JDK 1.8+ is selected

✔ Step 3 — Add MySQL JDBC Driver

In IntelliJ:

File → Project Structure → Libraries → + → From Maven
Search:

mysql:mysql-connector-java:8.0.33

✔ Step 4 — Set Up MySQL Database

Run all SQL commands (given above) in MySQL Workbench.

✔ Step 5 — Run the Project

Run the main class:

Main.java

# Sample Credentials
Username	Password
admin	admin123
