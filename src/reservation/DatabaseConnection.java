package reservation;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            return DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/reservationdb",
                    "root",
                    "asha*Ka2005"
            );
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
