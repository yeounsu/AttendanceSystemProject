package attendancesystema;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class connectDB{
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/statics?serverTimezone=UTC", "root", "1234");
    }
}