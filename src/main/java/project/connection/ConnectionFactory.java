package project.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLOutput;

public class ConnectionFactory {
    public static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/games_store";
        String username = "root";
        String password = "root";
        return DriverManager.getConnection(url, username, password);
    }
}
