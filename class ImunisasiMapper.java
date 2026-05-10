package mapper;

import model.Imunisasi;
import database.DatabaseConnection;

import java.sql.*;
import java.util.*;

public class ImunisasiMapper {

    public void simpan(Imunisasi imunisasi) throws Exception {
        Connection conn = DatabaseConnection.connect();

        String query = "INSERT INTO imunisasi(nama_imunisasi, tanggal, anak_id) VALUES (?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(query);

        ps.setString(1, imunisasi.getNamaImunisasi());
        ps.setDate(2, new java.sql.Date(imunisasi.getTanggal().getTime()));
        ps.setInt(3, imunisasi.getAnakId());

        ps.executeUpdate();

        ps.close();
        conn.close();
    }

    public List<Imunisasi> getByAnak(int anakId) throws Exception {
        List<Imunisasi> list = new ArrayList<>();

        Connection conn = DatabaseConnection.connect();

        String query = "SELECT * FROM imunisasi WHERE anak_id = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, anakId);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Imunisasi imunisasi = new Imunisasi(
                rs.getInt("id"),
                rs.getString("nama_imunisasi"),
                rs.getDate("tanggal"),
                rs.getInt("anak_id")
            );
            list.add(imunisasi);
        }

        rs.close();
        ps.close();
        conn.close();

        return list;
    }
}