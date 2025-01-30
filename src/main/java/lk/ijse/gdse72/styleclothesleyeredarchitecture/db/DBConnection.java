package lk.ijse.gdse72.styleclothesleyeredarchitecture.db;

import lombok.Getter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Getter
public class DBConnection {
    private static DBConnection instance;

    private final Connection connection;

    private DBConnection() throws SQLException {
        String URL = "jdbc:mysql://localhost:3306/styleclothes";
        String USER = "root";
        String PASSWORD = "mysql";
        connection = DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static synchronized DBConnection getInstance() throws SQLException {
        if (instance == null) {
            instance = new DBConnection();
        }
        return instance;
    }
}