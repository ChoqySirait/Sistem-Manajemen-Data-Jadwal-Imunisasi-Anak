package Imuniku.src.mapper;

import Imuniku.src.model.Vaksin;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * VaksinMapper - ORM Data Mapper untuk Vaksin
 * Mengkonversi data dari tabel vaksin ke objek Vaksin Java
 */
public class VaksinMapper extends BaseMapper<Vaksin> {

    /**
     * Mengkonversi satu baris ResultSet
     * dari tabel vaksin menjadi objek Vaksin.
     * ORM Data Mapper: database row → Java object
     */
    @Override
    public Vaksin mapRow(ResultSet rs) throws SQLException {
        Vaksin vaksin = new Vaksin();

        vaksin.setId(rs.getInt("id"));
        vaksin.setKodeVaksin(rs.getString("kode_vaksin"));
        vaksin.setNamaVaksin(rs.getString("nama_vaksin"));
        vaksin.setNamaUmum(rs.getString("nama_umum"));
        vaksin.setPenyakitDicegah(rs.getString("penyakit_dicegah"));
        vaksin.setUsiaBulanPemberian(rs.getInt("usia_bulan_pemberian"));
        vaksin.setDosisDiperlukan(rs.getInt("dosis_diperlukan"));
        vaksin.setCaraPemberian(rs.getString("cara_pemberian"));

        return vaksin;
    }

    /**
     * Mengkonversi semua baris ResultSet
     * menjadi List<Vaksin>.
     * JCF: menggunakan ArrayList
     */
    @Override
    public List<Vaksin> mapRows(ResultSet rs) throws SQLException {
        List<Vaksin> daftarVaksin = new ArrayList<>();

        while (rs.next()) {
            daftarVaksin.add(mapRow(rs));
        }

        return daftarVaksin;
    }
}
