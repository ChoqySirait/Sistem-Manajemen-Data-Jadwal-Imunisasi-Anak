package Imuniku.src.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import Imuniku.src.driver.DatabaseConnection;

public class AnakService {

    public void tampilkanDataAnak() {

        String query = "SELECT * FROM anak";

        try {

            Connection conn = DatabaseConnection.getConnection();

            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(query);

            System.out.println("=== DATA ANAK ===");

            while (rs.next()) {

                System.out.println("ID              : " + rs.getInt("id_anak"));
                System.out.println("Nama Anak       : " + rs.getString("nama_anak"));
                System.out.println("Tanggal Lahir   : " + rs.getString("tanggal_lahir"));
                System.out.println("Jenis Kelamin   : " + rs.getString("jenis_kelamin"));
                System.out.println("Nama Orang Tua  : " + rs.getString("nama_orang_tua"));
                System.out.println("Alamat          : " + rs.getString("alamat"));

                System.out.println("==============================");
            }

        } catch (Exception e) {

            System.out.println("[ERROR] Gagal menampilkan data!");
            System.out.println(e.getMessage());

        }
    }
}