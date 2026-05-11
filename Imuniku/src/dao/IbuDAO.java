package Imuniku.src.dao;

import driver.DatabaseConnection;
import mapper.IbuMapper;
import model.Ibu;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * IbuDAO menangani semua operasi database
 * untuk entity Ibu.
 *
 * Konsep PBO: Inheritance (extends BaseDAO)
 * Konsep JDBC: PreparedStatement, ResultSet
 * Konsep JCF: List, Map
 */
public class IbuDAO extends BaseDAO<Ibu> {

    private Connection conn;
    private IbuMapper mapper;

    public IbuDAO() {
        this.conn = DatabaseConnection.getConnection();
        this.mapper = new IbuMapper();
    }

    /**
     * Menyimpan data Ibu baru ke database.
     * JDBC: menggunakan PreparedStatement
     * untuk keamanan dari SQL Injection.
     */
    @Override
    public void save(Ibu ibu) {
        String sql =
            "INSERT INTO orang_tua "
            + "(nama, nik, no_hp, alamat, "
            + " tanggal_lahir, tanggal_daftar) "
            + "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps =
                 conn.prepareStatement(
                     sql,
                     Statement.RETURN_GENERATED_KEYS
                 )) {

            ps.setString(1, ibu.getNama());
            ps.setString(2, ibu.getNik());
            ps.setString(3, ibu.getNoHp());
            ps.setString(4, ibu.getAlamat());

            if (ibu.getTanggalLahir() != null) {
                ps.setDate(5, Date.valueOf(
                    ibu.getTanggalLahir()
                ));
            } else {
                ps.setNull(5, Types.DATE);
            }

            ps.setDate(6, Date.valueOf(LocalDate.now()));
            ps.executeUpdate();

            // Ambil ID yang di-generate database
            ResultSet generatedKeys =
                ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                ibu.setId(generatedKeys.getInt(1));
                System.out.println(
                    "[OK] Ibu " + ibu.getNama()
                    + " berhasil didaftarkan "
                    + "(ID: " + ibu.getId() + ")"
                );
            }

        } catch (SQLIntegrityConstraintViolationException e) {
            System.err.println(
                "[ERROR] NIK " + ibu.getNik()
                + " sudah terdaftar di sistem."
            );
        } catch (SQLException e) {
            System.err.println(
                "[ERROR] Gagal menyimpan data ibu: "
                + e.getMessage()
            );
        }
    }

    /**
     * Mengambil semua data Ibu dari database.
     * JCF: mengembalikan List<Ibu>
     * ORM: menggunakan IbuMapper.mapRows()
     */
    @Override
    public List<Ibu> findAll() {
        // JCF: ArrayList sebagai container hasil query
        List<Ibu> daftarIbu = new ArrayList<>();
        String sql =
            "SELECT * FROM orang_tua ORDER BY nama";

        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            // ORM Data Mapper: konversi ResultSet→List
            daftarIbu = mapper.mapRows(rs);

        } catch (SQLException e) {
            System.err.println(
                "[ERROR] Gagal mengambil data ibu: "
                + e.getMessage()
            );
        }
        return daftarIbu;
    }

    /**
     * Mencari Ibu berdasarkan NIK.
     * JDBC: PreparedStatement dengan parameter
     */
    public Ibu findByNik(String nik) {
        String sql =
            "SELECT * FROM orang_tua WHERE nik = ?";

        try (PreparedStatement ps =
                 conn.prepareStatement(sql)) {

            ps.setString(1, nik);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapper.mapRow(rs); // ORM Mapper
            }

        } catch (SQLException e) {
            System.err.println(
                "[ERROR] Gagal mencari ibu: "
                + e.getMessage()
            );
        }
        return null;
    }

    /**
     * Mencari Ibu berdasarkan nama (LIKE search).
     * JCF: mengembalikan List<Ibu>
     */
    public List<Ibu> findByNama(String nama) {
        List<Ibu> hasil = new ArrayList<>();
        String sql =
            "SELECT * FROM orang_tua "
            + "WHERE nama LIKE ? ORDER BY nama";

        try (PreparedStatement ps =
                 conn.prepareStatement(sql)) {

            ps.setString(1, "%" + nama + "%");
            ResultSet rs = ps.executeQuery();
            hasil = mapper.mapRows(rs);

        } catch (SQLException e) {
            System.err.println(
                "[ERROR] Gagal mencari ibu: "
                + e.getMessage()
            );
        }
        return hasil;
    }

    /**
     * Mengambil data untuk laporan menggunakan HashMap.
     * JCF: HashMap<String, Integer> untuk statistik
     * Contoh: {"total": 25, "baru_bulan_ini": 5}
     */
    public Map<String, Integer> getStatistik() {
        // JCF: HashMap untuk menyimpan data statistik
        Map<String, Integer> statistik = new HashMap<>();

        String sqlTotal =
            "SELECT COUNT(*) as total FROM orang_tua";
        String sqlBaru =
            "SELECT COUNT(*) as baru "
            + "FROM orang_tua "
            + "WHERE MONTH(tanggal_daftar) = MONTH(NOW()) "
            + "AND YEAR(tanggal_daftar) = YEAR(NOW())";

        try (Statement st = conn.createStatement()) {

            ResultSet rs1 = st.executeQuery(sqlTotal);
            if (rs1.next()) {
                statistik.put("total", rs1.getInt("total"));
            }

            ResultSet rs2 = st.executeQuery(sqlBaru);
            if (rs2.next()) {
                statistik.put(
                    "baru_bulan_ini",
                    rs2.getInt("baru")
                );
            }

        } catch (SQLException e) {
            System.err.println(
                "[ERROR] Gagal mengambil statistik: "
                + e.getMessage()
            );
        }
        return statistik;
    }

    @Override
    public Ibu findById(int id) {
        String sql =
            "SELECT * FROM orang_tua WHERE id = ?";
        try (PreparedStatement ps =
                 conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapper.mapRow(rs);
            }
        } catch (SQLException e) {
            System.err.println(
                "[ERROR] findById ibu: "
                + e.getMessage()
            );
        }
        return null;
    }

    @Override
    public void update(Ibu ibu) {
        String sql =
            "UPDATE orang_tua SET nama=?, no_hp=?, "
            + "alamat=? WHERE id=?";
        try (PreparedStatement ps =
                 conn.prepareStatement(sql)) {
            ps.setString(1, ibu.getNama());
            ps.setString(2, ibu.getNoHp());
            ps.setString(3, ibu.getAlamat());
            ps.setInt(4, ibu.getId());
            ps.executeUpdate();
            System.out.println(
                "[OK] Data ibu berhasil diperbarui."
            );
        } catch (SQLException e) {
            System.err.println(
                "[ERROR] Gagal update ibu: "
                + e.getMessage()
            );
        }
    }

    @Override
    public void delete(int id) {
        String sql =
            "DELETE FROM orang_tua WHERE id=?";
        try (PreparedStatement ps =
                 conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            int affected = ps.executeUpdate();
            if (affected > 0) {
                System.out.println(
                    "[OK] Data ibu berhasil dihapus."
                );
            }
        } catch (SQLException e) {
            System.err.println(
                "[ERROR] Gagal hapus ibu: "
                + e.getMessage()
            );
        }
    }
}