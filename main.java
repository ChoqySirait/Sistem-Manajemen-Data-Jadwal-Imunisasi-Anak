import Imuniku.database.DatabaseConnection;
import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        // Panggil koneksi
        Connection koneksi = DatabaseConnection.getConnection();

        if (koneksi != null) {
            System.out.println("Koneksi MySQL Workbench Berhasil!");
        } else {
            System.out.println("Gagal! Cek apakah Service MySQL di Windows sudah Start.");
        }
    }
}