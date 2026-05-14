package Imuniku.src.mapper;

import Imuniku.src.model.Anak;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * AnakMapper - ORM Data Mapper untuk Anak
 * Mengkonversi data dari tabel anak ke objek Anak Java
 */
public class AnakMapper extends BaseMapper<Anak> {

    /**
     * Mengkonversi satu baris ResultSet
     * dari tabel anak menjadi objek Anak.
     * ORM Data Mapper: database row → Java object
     */
    @Override
    public Anak mapRow(ResultSet rs) throws SQLException {
        Anak anak = new Anak();

        anak.setId(rs.getInt("id"));
        anak.setNama(rs.getString("nama"));
        
        // Konversi java.sql.Date → java.time.LocalDate
        java.sql.Date tglLahir = rs.getDate("tanggal_lahir");
        if (tglLahir != null) {
            anak.setTanggalLahir(tglLahir.toLocalDate());
        }
        
        anak.setJenisKelamin(rs.getString("jenis_kelamin"));
        anak.setIbuId(rs.getInt("orang_tua_id"));

        return anak;
    }

    /**
     * Mengkonversi semua baris ResultSet
     * menjadi List<Anak>.
     * JCF: menggunakan ArrayList
     */
    @Override
    public List<Anak> mapRows(ResultSet rs) throws SQLException {
        List<Anak> daftarAnak = new ArrayList<>();

        while (rs.next()) {
            daftarAnak.add(mapRow(rs));
        }

        return daftarAnak;
    }
}
