package Imuniku.src.driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL =
            "jdbc:mysql://localhost:3306/imuniku_db";

    private static final String USER = "root";

    private static final String PASSWORD = "Ventyolaa10!";

    private static Connection connection;

    private DatabaseConnection() {
    }

    public static Connection getConnection() {

        try {

            if (connection == null || connection.isClosed()) {

                Class.forName("com.mysql.cj.jdbc.Driver");

                connection = DriverManager.getConnection(
                        URL,
                        USER,
                        PASSWORD
                );

                System.out.println(
                        "[SUCCESS] Berhasil konek ke database!"
                );
            }

        } catch (ClassNotFoundException e) {

            System.out.println(
                    "[ERROR] JDBC Driver tidak ditemukan!"
            );

        } catch (SQLException e) {

            System.out.println(
                    "[ERROR] Gagal konek ke database!"
            );

            System.out.println(e.getMessage());
        }

        return connection;
    }
}