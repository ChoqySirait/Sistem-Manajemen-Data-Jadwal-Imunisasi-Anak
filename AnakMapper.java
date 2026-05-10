package mapper;

import model.Anak;
import database.DatabaseConnection;

import java.sql.*;
import java.util.*;

public class AnakMapper {

    public void simpan(Anak anak) throws Exception {
        Connection conn = DatabaseConnection.connect();

        String query = "INSERT INTO anak(nama, tanggal_lahir) VALUES (?, ?)";
        PreparedStatement ps = conn.prepareStatement(query);

        ps.setString(1, anak.getNama());
        ps.setDate(2, new java.sql.Date(anak.getTanggalLahir().getTime()));

        ps.executeUpdate();

        ps.close();
        conn.close();
    }

    public List<Anak> getAll() throws Exception {
        List<Anak> list = new ArrayList<>();

        Connection conn = DatabaseConnection.connect();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM anak");

        while (rs.next()) {
            Anak anak = new Anak(
                rs.getInt("id"),
                rs.getString("nama"),
                rs.getDate("tanggal_lahir")
            );
            list.add(anak);
        }

        rs.close();
        st.close();
        conn.close();

        return list;
    }
}