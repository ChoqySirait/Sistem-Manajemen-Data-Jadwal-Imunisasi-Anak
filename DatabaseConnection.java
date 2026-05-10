package database;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/imunisasi_db";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection connect() throws Exception {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}