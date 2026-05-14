package Imuniku.src.mapper;

import Imuniku.src.model.PetugasKesehatan;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * PetugasKesehatanMapper - ORM Data Mapper untuk PetugasKesehatan
 * Mengkonversi data dari tabel petugas_kesehatan ke objek PetugasKesehatan Java
 */
public class PetugasKesehatanMapper extends BaseMapper<PetugasKesehatan> {

    /**
     * Mengkonversi satu baris ResultSet
     * dari tabel petugas_kesehatan menjadi objek PetugasKesehatan.
     * ORM Data Mapper: database row → Java object
     */
    @Override
    public PetugasKesehatan mapRow(ResultSet rs) throws SQLException {
        PetugasKesehatan petugas = new PetugasKesehatan();

        petugas.setId(rs.getInt("id"));
        petugas.setNama(rs.getString("nama"));
        petugas.setNip(rs.getString("nip"));
        petugas.setJabatan(rs.getString("jabatan"));
        petugas.setNamaFaskes(rs.getString("nama_faskes"));
        petugas.setNoHp(rs.getString("no_hp"));
        
        // Konversi java.sql.Date → java.time.LocalDate
        java.sql.Date tglLahir = rs.getDate("tanggal_lahir");
        if (tglLahir != null) {
            petugas.setTanggalLahir(tglLahir.toLocalDate());
        }
        
        java.sql.Date tglDaftar = rs.getDate("tanggal_daftar");
        if (tglDaftar != null) {
            petugas.setTanggalDaftar(tglDaftar.toLocalDate());
        }

        return petugas;
    }

    /**
     * Mengkonversi semua baris ResultSet
     * menjadi List<PetugasKesehatan>.
     * JCF: menggunakan ArrayList
     */
    @Override
    public List<PetugasKesehatan> mapRows(ResultSet rs) throws SQLException {
        List<PetugasKesehatan> daftarPetugas = new ArrayList<>();

        while (rs.next()) {
            daftarPetugas.add(mapRow(rs));
        }

        return daftarPetugas;
    }
}
