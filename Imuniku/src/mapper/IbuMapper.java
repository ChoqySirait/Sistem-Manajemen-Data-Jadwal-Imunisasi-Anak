package Imuniku.src.mapper;

import model.Ibu;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * IbuMapper bertanggung jawab untuk mengkonversi
 * data dari tabel orang_tua ke objek Ibu Java
 * dan sebaliknya.
 *
 * Konsep PBO: Inheritance (extends BaseMapper)
 * Konsep ORM: mapRow() adalah inti Data Mapper
 */
public class IbuMapper extends BaseMapper<Ibu> {

    /**
     * Mengkonversi satu baris ResultSet
     * dari tabel orang_tua menjadi objek Ibu.
     *
     * Ini adalah implementasi ORM Data Mapper:
     * database row → Java object
     */
    @Override
    public Ibu mapRow(ResultSet rs)
        throws SQLException {

        Ibu ibu = new Ibu();

        ibu.setId(rs.getInt("id"));
        ibu.setNama(rs.getString("nama"));
        ibu.setNik(rs.getString("nik"));
        ibu.setNoHp(rs.getString("no_hp"));
        ibu.setAlamat(rs.getString("alamat"));

        // Konversi java.sql.Date → java.time.LocalDate
        java.sql.Date tglLahir =
            rs.getDate("tanggal_lahir");
        if (tglLahir != null) {
            ibu.setTanggalLahir(tglLahir.toLocalDate());
        }

        java.sql.Date tglDaftar =
            rs.getDate("tanggal_daftar");
        if (tglDaftar != null) {
            ibu.setTanggalDaftar(tglDaftar.toLocalDate());
        }

        return ibu;
    }

    /**
     * Mengkonversi semua baris ResultSet
     * menjadi List<Ibu>.
     * JCF: menggunakan ArrayList
     */
    @Override
    public List<Ibu> mapRows(ResultSet rs)
        throws SQLException {

        // JCF: ArrayList untuk menyimpan koleksi Ibu
        List<Ibu> daftarIbu = new ArrayList<>();

        while (rs.next()) {
            daftarIbu.add(mapRow(rs));
        }

        return daftarIbu;
    }
}