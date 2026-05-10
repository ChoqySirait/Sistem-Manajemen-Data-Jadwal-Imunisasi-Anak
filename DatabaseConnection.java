

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static Connection connection;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                // Load file properties
                Properties props = new Properties();
                props.load(new FileInputStream("db.properties"));

                String url = props.getProperty("db.url");
                String user = props.getProperty("db.user");
                String pass = props.getProperty("db.password");

                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(url, user, pass);
                System.out.println("[SUCCESS] Berhasil konek menggunakan config lokal!");
                
            } catch (IOException e) {
                System.out.println("[ERROR] File db.properties tidak ditemukan!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return connection;
    }
}               