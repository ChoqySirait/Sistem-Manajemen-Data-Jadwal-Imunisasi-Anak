package Imuniku.src.mapper;

import Imuniku.src.model.RiwayatImunisasi;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * RiwayatMapper - ORM Data Mapper untuk RiwayatImunisasi
 * Mengkonversi data dari tabel riwayat_imunisasi ke objek RiwayatImunisasi Java
 */
public class RiwayatMapper extends BaseMapper<RiwayatImunisasi> {

    /**
     * Mengkonversi satu baris ResultSet
     * dari tabel riwayat_imunisasi menjadi objek RiwayatImunisasi.
     * ORM Data Mapper: database row → Java object
     */
    @Override
    public RiwayatImunisasi mapRow(ResultSet rs) throws SQLException {
        RiwayatImunisasi riwayat = new RiwayatImunisasi();

        riwayat.setId(rs.getInt("id"));
        riwayat.setAnakId(rs.getInt("anak_id"));
        riwayat.setVaksinId(rs.getInt("vaksin_id"));
        
        // Konversi java.sql.Date → java.time.LocalDate
        java.sql.Date tanggalPelaksanaan = rs.getDate("tanggal_pelaksanaan");
        if (tanggalPelaksanaan != null) {
            riwayat.setTanggalPelaksanaan(tanggalPelaksanaan.toLocalDate());
        }
        
        riwayat.setNamaFaskes(rs.getString("nama_faskes"));
        riwayat.setNamaPetugas(rs.getString("nama_petugas"));
        riwayat.setCatatan(rs.getString("catatan"));
        
        // Coba ambil petugas_id dan nama_vaksin jika ada di query result
        try {
            int petugasId = rs.getInt("petugas_id");
            if (!rs.wasNull()) {
                riwayat.setPetugasId(petugasId);
            }
        } catch (SQLException e) {
            // Column tidak ada di query, skip
        }
        
        try {
            riwayat.setNamaVaksin(rs.getString("nama_vaksin"));
        } catch (SQLException e) {
            // Column tidak ada di query, skip
            riwayat.setNamaVaksin("");
        }

        return riwayat;
    }

    /**
     * Mengkonversi semua baris ResultSet
     * menjadi List<RiwayatImunisasi>.
     * JCF: menggunakan ArrayList
     */
    @Override
    public List<RiwayatImunisasi> mapRows(ResultSet rs) throws SQLException {
        List<RiwayatImunisasi> daftarRiwayat = new ArrayList<>();

        while (rs.next()) {
            daftarRiwayat.add(mapRow(rs));
        }

        return daftarRiwayat;
    }
}
