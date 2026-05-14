package Imuniku.src.mapper;

import Imuniku.src.model.JadwalImunisasi;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * JadwalMapper - ORM Data Mapper untuk JadwalImunisasi
 * Mengkonversi data dari tabel jadwal_imunisasi ke objek JadwalImunisasi Java
 */
public class JadwalMapper extends BaseMapper<JadwalImunisasi> {

    /**
     * Mengkonversi satu baris ResultSet
     * dari tabel jadwal_imunisasi menjadi objek JadwalImunisasi.
     * ORM Data Mapper: database row → Java object
     */
    @Override
    public JadwalImunisasi mapRow(ResultSet rs) throws SQLException {
        JadwalImunisasi jadwal = new JadwalImunisasi();

        jadwal.setId(rs.getInt("id"));
        jadwal.setAnakId(rs.getInt("anak_id"));
        jadwal.setVaksinId(rs.getInt("vaksin_id"));
        
        // Konversi java.sql.Date → java.time.LocalDate
        java.sql.Date tanggalTarget = rs.getDate("tanggal_target");
        if (tanggalTarget != null) {
            jadwal.setTanggalTarget(tanggalTarget.toLocalDate());
        }
        
        jadwal.setStatus(rs.getString("status"));
        
        // Coba ambil nama_vaksin jika ada di query result
        try {
            jadwal.setNamaVaksin(rs.getString("nama_vaksin"));
        } catch (SQLException e) {
            // Column tidak ada di query, skip
            jadwal.setNamaVaksin("");
        }

        return jadwal;
    }

    /**
     * Mengkonversi semua baris ResultSet
     * menjadi List<JadwalImunisasi>.
     * JCF: menggunakan ArrayList
     */
    @Override
    public List<JadwalImunisasi> mapRows(ResultSet rs) throws SQLException {
        List<JadwalImunisasi> daftarJadwal = new ArrayList<>();

        while (rs.next()) {
            daftarJadwal.add(mapRow(rs));
        }

        return daftarJadwal;
    }
}
