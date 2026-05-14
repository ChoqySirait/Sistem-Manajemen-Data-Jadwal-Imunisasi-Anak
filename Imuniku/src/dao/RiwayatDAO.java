package Imuniku.src.dao;

import Imuniku.src.driver.DatabaseConnection;
import Imuniku.src.mapper.RiwayatMapper;
import Imuniku.src.model.RiwayatImunisasi;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * RiwayatDAO - Data Access Object untuk RiwayatImunisasi
 * Menangani semua operasi database untuk entity RiwayatImunisasi.
 *
 * Konsep PBO: Inheritance (extends BaseDAO)
 * Konsep JDBC: PreparedStatement, ResultSet
 * Konsep JCF: List
 */
public class RiwayatDAO extends BaseDAO<RiwayatImunisasi> {

    private Connection conn;
    private RiwayatMapper mapper;

    public RiwayatDAO() {
        this.conn = DatabaseConnection.getConnection();
        this.mapper = new RiwayatMapper();
    }

    /**
     * Menyimpan data RiwayatImunisasi baru ke database.
     * JDBC: menggunakan PreparedStatement
     * untuk keamanan dari SQL Injection.
     */
    @Override
    public void save(RiwayatImunisasi riwayat) {
        String sql =
            "INSERT INTO riwayat_imunisasi "
            + "(anak_id, vaksin_id, petugas_id, "
            + " tanggal_pelaksanaan, nama_faskes, "
            + " nama_petugas, catatan) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps =
                 conn.prepareStatement(
                     sql,
                     Statement.RETURN_GENERATED_KEYS
                 )) {

            ps.setInt(1, riwayat.getAnakId());
            ps.setInt(2, riwayat.getVaksinId());
            
            if (riwayat.getPetugasId() > 0) {
                ps.setInt(3, riwayat.getPetugasId());
            } else {
                ps.setNull(3, Types.INTEGER);
            }
            
            if (riwayat.getTanggalPelaksanaan() != null) {
                ps.setDate(4, Date.valueOf(
                    riwayat.getTanggalPelaksanaan()
                ));
            } else {
                ps.setDate(4, Date.valueOf(LocalDate.now()));
            }
            
            ps.setString(5, riwayat.getNamaFaskes());
            ps.setString(6, riwayat.getNamaPetugas());
            ps.setString(7, riwayat.getCatatan());
            
            ps.executeUpdate();

            // Ambil ID yang di-generate database
            ResultSet generatedKeys =
                ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                riwayat.setId(generatedKeys.getInt(1));
                System.out.println(
                    "[OK] Riwayat imunisasi berhasil disimpan "
                    + "(ID: " + riwayat.getId() + ")"
                );
            }

        } catch (SQLException e) {
            System.err.println(
                "[ERROR] Gagal menyimpan riwayat imunisasi: "
                + e.getMessage()
            );
        }
    }

    /**
     * Mengambil semua data RiwayatImunisasi dari database.
     * JCF: mengembalikan List<RiwayatImunisasi>
     * ORM: menggunakan RiwayatMapper.mapRows()
     */
    @Override
    public List<RiwayatImunisasi> findAll() {
        List<RiwayatImunisasi> daftarRiwayat = new ArrayList<>();
        String sql =
            "SELECT * FROM riwayat_imunisasi ORDER BY tanggal_pelaksanaan DESC";

        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            daftarRiwayat = mapper.mapRows(rs);

        } catch (SQLException e) {
            System.err.println(
                "[ERROR] Gagal mengambil data riwayat: "
                + e.getMessage()
            );
        }
        return daftarRiwayat;
    }

    /**
     * Mencari RiwayatImunisasi berdasarkan ID.
     * JDBC: PreparedStatement dengan parameter
     */
    @Override
    public RiwayatImunisasi findById(int id) {
        String sql =
            "SELECT * FROM riwayat_imunisasi WHERE id = ?";

        try (PreparedStatement ps =
                 conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapper.mapRow(rs);
            }

        } catch (SQLException e) {
            System.err.println(
                "[ERROR] Gagal mencari riwayat: "
                + e.getMessage()
            );
        }
        return null;
    }

    /**
     * Memperbarui data RiwayatImunisasi yang sudah ada.
     */
    @Override
    public void update(RiwayatImunisasi riwayat) {
        String sql =
            "UPDATE riwayat_imunisasi SET vaksin_id=?, "
            + "tanggal_pelaksanaan=?, nama_faskes=?, "
            + "nama_petugas=?, catatan=? WHERE id=?";

        try (PreparedStatement ps =
                 conn.prepareStatement(sql)) {

            ps.setInt(1, riwayat.getVaksinId());
            
            if (riwayat.getTanggalPelaksanaan() != null) {
                ps.setDate(2, Date.valueOf(
                    riwayat.getTanggalPelaksanaan()
                ));
            } else {
                ps.setNull(2, Types.DATE);
            }
            
            ps.setString(3, riwayat.getNamaFaskes());
            ps.setString(4, riwayat.getNamaPetugas());
            ps.setString(5, riwayat.getCatatan());
            ps.setInt(6, riwayat.getId());
            
            ps.executeUpdate();
            System.out.println(
                "[OK] Data riwayat berhasil diperbarui."
            );

        } catch (SQLException e) {
            System.err.println(
                "[ERROR] Gagal update riwayat: "
                + e.getMessage()
            );
        }
    }

    /**
     * Menghapus data RiwayatImunisasi berdasarkan ID.
     */
    @Override
    public void delete(int id) {
        String sql =
            "DELETE FROM riwayat_imunisasi WHERE id=?";

        try (PreparedStatement ps =
                 conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            int affected = ps.executeUpdate();
            
            if (affected > 0) {
                System.out.println(
                    "[OK] Data riwayat berhasil dihapus."
                );
            }

        } catch (SQLException e) {
            System.err.println(
                "[ERROR] Gagal hapus riwayat: "
                + e.getMessage()
            );
        }
    }

    /**
     * Mengambil riwayat berdasarkan ID Anak.
     * JCF: mengembalikan List<RiwayatImunisasi>
     */
    public List<RiwayatImunisasi> findByAnakId(int anakId) {
        List<RiwayatImunisasi> hasil = new ArrayList<>();
        String sql =
            "SELECT * FROM riwayat_imunisasi "
            + "WHERE anak_id = ? ORDER BY tanggal_pelaksanaan DESC";

        try (PreparedStatement ps =
                 conn.prepareStatement(sql)) {

            ps.setInt(1, anakId);
            ResultSet rs = ps.executeQuery();
            hasil = mapper.mapRows(rs);

        } catch (SQLException e) {
            System.err.println(
                "[ERROR] Gagal mencari riwayat by anak: "
                + e.getMessage()
            );
        }
        return hasil;
    }
}