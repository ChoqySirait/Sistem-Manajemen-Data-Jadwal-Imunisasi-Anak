package Imuniku.src.dao;

import Imuniku.src.driver.DatabaseConnection;
import Imuniku.src.mapper.VaksinMapper;
import Imuniku.src.model.Vaksin;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * VaksinDAO - Data Access Object untuk Vaksin
 * Menangani semua operasi database untuk entity Vaksin.
 *
 * Konsep PBO: Inheritance (extends BaseDAO)
 * Konsep JDBC: PreparedStatement, ResultSet
 * Konsep JCF: List
 */
public class VaksinDAO extends BaseDAO<Vaksin> {

    private Connection conn;
    private VaksinMapper mapper;

    public VaksinDAO() {
        this.conn = DatabaseConnection.getConnection();
        this.mapper = new VaksinMapper();
    }

    /**
     * Menyimpan data Vaksin baru ke database.
     * JDBC: menggunakan PreparedStatement
     * untuk keamanan dari SQL Injection.
     */
    @Override
    public void save(Vaksin vaksin) {
        String sql =
            "INSERT INTO vaksin "
            + "(kode_vaksin, nama_vaksin, nama_umum, "
            + " penyakit_dicegah, usia_bulan_pemberian, "
            + " dosis_diperlukan, cara_pemberian) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps =
                 conn.prepareStatement(
                     sql,
                     Statement.RETURN_GENERATED_KEYS
                 )) {

            ps.setString(1, vaksin.getKodeVaksin());
            ps.setString(2, vaksin.getNamaVaksin());
            ps.setString(3, vaksin.getNamaUmum());
            ps.setString(4, vaksin.getPenyakitDicegah());
            ps.setInt(5, vaksin.getUsiaBulanPemberian());
            ps.setInt(6, vaksin.getDosisDiperlukan());
            ps.setString(7, vaksin.getCaraPemberian());
            
            ps.executeUpdate();

            // Ambil ID yang di-generate database
            ResultSet generatedKeys =
                ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                vaksin.setId(generatedKeys.getInt(1));
                System.out.println(
                    "[OK] Vaksin " + vaksin.getNamaVaksin()
                    + " berhasil didaftarkan "
                    + "(ID: " + vaksin.getId() + ")"
                );
            }

        } catch (SQLIntegrityConstraintViolationException e) {
            System.err.println(
                "[ERROR] Kode vaksin "
                + vaksin.getKodeVaksin()
                + " sudah terdaftar di sistem."
            );
        } catch (SQLException e) {
            System.err.println(
                "[ERROR] Gagal menyimpan data vaksin: "
                + e.getMessage()
            );
        }
    }

    /**
     * Mengambil semua data Vaksin dari database.
     * JCF: mengembalikan List<Vaksin>
     * ORM: menggunakan VaksinMapper.mapRows()
     */
    @Override
    public List<Vaksin> findAll() {
        List<Vaksin> daftarVaksin = new ArrayList<>();
        String sql =
            "SELECT * FROM vaksin ORDER BY usia_bulan_pemberian";

        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            daftarVaksin = mapper.mapRows(rs);

        } catch (SQLException e) {
            System.err.println(
                "[ERROR] Gagal mengambil data vaksin: "
                + e.getMessage()
            );
        }
        return daftarVaksin;
    }

    /**
     * Mencari Vaksin berdasarkan ID.
     * JDBC: PreparedStatement dengan parameter
     */
    @Override
    public Vaksin findById(int id) {
        String sql =
            "SELECT * FROM vaksin WHERE id = ?";

        try (PreparedStatement ps =
                 conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapper.mapRow(rs);
            }

        } catch (SQLException e) {
            System.err.println(
                "[ERROR] Gagal mencari vaksin: "
                + e.getMessage()
            );
        }
        return null;
    }

    /**
     * Memperbarui data Vaksin yang sudah ada.
     */
    @Override
    public void update(Vaksin vaksin) {
        String sql =
            "UPDATE vaksin SET nama_vaksin=?, nama_umum=?, "
            + "penyakit_dicegah=?, usia_bulan_pemberian=?, "
            + "dosis_diperlukan=?, cara_pemberian=? "
            + "WHERE id=?";

        try (PreparedStatement ps =
                 conn.prepareStatement(sql)) {

            ps.setString(1, vaksin.getNamaVaksin());
            ps.setString(2, vaksin.getNamaUmum());
            ps.setString(3, vaksin.getPenyakitDicegah());
            ps.setInt(4, vaksin.getUsiaBulanPemberian());
            ps.setInt(5, vaksin.getDosisDiperlukan());
            ps.setString(6, vaksin.getCaraPemberian());
            ps.setInt(7, vaksin.getId());
            
            ps.executeUpdate();
            System.out.println(
                "[OK] Data vaksin berhasil diperbarui."
            );

        } catch (SQLException e) {
            System.err.println(
                "[ERROR] Gagal update vaksin: "
                + e.getMessage()
            );
        }
    }

    /**
     * Menghapus data Vaksin berdasarkan ID.
     */
    @Override
    public void delete(int id) {
        String sql =
            "DELETE FROM vaksin WHERE id=?";

        try (PreparedStatement ps =
                 conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            int affected = ps.executeUpdate();
            
            if (affected > 0) {
                System.out.println(
                    "[OK] Data vaksin berhasil dihapus."
                );
            }

        } catch (SQLException e) {
            System.err.println(
                "[ERROR] Gagal hapus vaksin: "
                + e.getMessage()
            );
        }
    }
}
