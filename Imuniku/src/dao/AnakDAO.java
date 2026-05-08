package Imuniku.src.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import Imuniku.src.driver.DatabaseConnection;
import Imuniku.src.model.Anak;

public class AnakDAO {

    public void tambahAnak(Anak anak) {

        String query = "INSERT INTO anak "
                + "(nama_anak, tanggal_lahir, jenis_kelamin, nama_orang_tua, alamat) "
                + "VALUES (?, ?, ?, ?, ?)";

        try {

            Connection conn = DatabaseConnection.getConnection();

            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, anak.getNamaAnak());
            stmt.setString(2, anak.getTanggalLahir());
            stmt.setString(3, anak.getJenisKelamin());
            stmt.setString(4, anak.getNamaOrangTua());
            stmt.setString(5, anak.getAlamat());

            stmt.executeUpdate();

            System.out.println("[SUCCESS] Data anak berhasil ditambahkan!");

        } catch (SQLException e) {

            System.out.println("[ERROR] Gagal tambah data!");
            System.out.println(e.getMessage());

            

        }
    }

        public void updateAnak(Anak anak) {

    String query = "UPDATE anak SET "
            + "nama_anak = ?, "
            + "tanggal_lahir = ?, "
            + "jenis_kelamin = ?, "
            + "nama_orang_tua = ?, "
            + "alamat = ? "
            + "WHERE id_anak = ?";

    try {

        Connection conn = DatabaseConnection.getConnection();

        PreparedStatement stmt = conn.prepareStatement(query);

        stmt.setString(1, anak.getNamaAnak());
        stmt.setString(2, anak.getTanggalLahir());
        stmt.setString(3, anak.getJenisKelamin());
        stmt.setString(4, anak.getNamaOrangTua());
        stmt.setString(5, anak.getAlamat());

        stmt.setInt(6, anak.getIdAnak());

        stmt.executeUpdate();

        System.out.println("[SUCCESS] Data anak berhasil diupdate!");

    } catch (SQLException e) {

        System.out.println("[ERROR] Gagal update data!");
        System.out.println(e.getMessage());

    }
}
}

