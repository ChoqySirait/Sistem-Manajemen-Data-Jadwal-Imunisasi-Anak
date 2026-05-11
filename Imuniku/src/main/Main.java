package Imuniku.src.main;

import dao.*;
import driver.DatabaseConnection;
import model.*;
import service.*;
import util.InputHelper;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Main adalah entry point program ImuniKu.
 * Menampilkan menu konsol interaktif dan
 * menghubungkan semua komponen sistem.
 */
public class Main {

    // DAO dan Service yang digunakan
    private static IbuDAO ibuDAO = new IbuDAO();
    private static AnakDAO anakDAO = new AnakDAO();
    private static VaksinDAO vaksinDAO = new VaksinDAO();
    private static RiwayatDAO riwayatDAO =
        new RiwayatDAO();
    private static JadwalService jadwalService =
        new JadwalService();
    private static InputHelper input = new InputHelper();

    public static void main(String[] args) {

        // Inisialisasi koneksi database
        if (DatabaseConnection.getConnection() == null) {
            System.err.println(
                "Program tidak dapat berjalan "
                + "tanpa koneksi database."
            );
            return;
        }

        System.out.println(
            "\n======================================="
        );
        System.out.println(
            "  IMUNIKU — Sistem Informasi Imunisasi"
        );
        System.out.println(
            "  Ibu dan Anak | IT Del 2026"
        );
        System.out.println(
            "======================================="
        );

        boolean running = true;
        while (running) {
            tampilMenuUtama();
            int pilihan = input.readInt("Pilihan: ");

            switch (pilihan) {
                case 1 -> menuManajemenIbu();
                case 2 -> menuManajemenAnak();
                case 3 -> menuImunisasi();
                case 4 -> menuMasterVaksin();
                case 5 -> menuLaporan();
                case 0 -> running = false;
                default ->
                    System.out.println(
                        "[!] Pilihan tidak valid."
                    );
            }
        }

        System.out.println(
            "\nTerima kasih menggunakan ImuniKu."
        );
        DatabaseConnection.closeConnection();
        input.close();
    }

    // ─────────────────────────────────────────────
    // MENU UTAMA
    // ─────────────────────────────────────────────

    private static void tampilMenuUtama() {
        System.out.println(
            "\n─────────────────────────────"
        );
        System.out.println("  MENU UTAMA");
        System.out.println(
            "─────────────────────────────"
        );
        System.out.println("1. Manajemen Ibu");
        System.out.println("2. Manajemen Anak");
        System.out.println("3. Imunisasi");
        System.out.println("4. Master Data Vaksin");
        System.out.println("5. Laporan");
        System.out.println("0. Keluar");
        System.out.println(
            "─────────────────────────────"
        );
    }

    // ─────────────────────────────────────────────
    // MENU MANAJEMEN IBU
    // ─────────────────────────────────────────────

    private static void menuManajemenIbu() {
        boolean kembali = false;
        while (!kembali) {
            System.out.println(
                "\n── Manajemen Ibu ──"
            );
            System.out.println("1. Daftarkan Ibu Baru");
            System.out.println("2. Lihat Semua Ibu");
            System.out.println("3. Cari Ibu");
            System.out.println("0. Kembali");

            int p = input.readInt("Pilihan: ");
            switch (p) {
                case 1 -> daftarIbuBaru();
                case 2 -> lihatSemuaIbu();
                case 3 -> cariIbu();
                case 0 -> kembali = true;
                default ->
                    System.out.println(
                        "[!] Pilihan tidak valid."
                    );
            }
        }
    }

    private static void daftarIbuBaru() {
        System.out.println("\n─── Daftar Ibu Baru ───");

        String nama = input.readString("Nama lengkap  : ");
        String nik  = input.readString("NIK (16 digit): ");
        String alamat = input.readString("Alamat        : ");
        String noHp = input.readString("No HP         : ");
        String tglStr = input.readString(
            "Tgl lahir (yyyy-MM-dd): "
        );

        LocalDate tglLahir = null;
        try {
            tglLahir = LocalDate.parse(tglStr);
        } catch (Exception e) {
            System.out.println(
                "[!] Format tanggal tidak valid. "
                + "Menggunakan null."
            );
        }

        Ibu ibu = new Ibu(nama, nik, alamat,
                          noHp, tglLahir);
        ibuDAO.save(ibu);
    }

    private static void lihatSemuaIbu() {
        System.out.println("\n─── Daftar Semua Ibu ───");
        List<Ibu> daftarIbu = ibuDAO.findAll();

        if (daftarIbu.isEmpty()) {
            System.out.println(
                "Belum ada data ibu terdaftar."
            );
            return;
        }

        System.out.printf(
            "%-5s %-25s %-18s %-15s%n",
            "ID", "Nama", "NIK", "No HP"
        );
        System.out.println("-".repeat(65));

        // JCF: iterasi List<Ibu>
        for (Ibu ibu : daftarIbu) {
            System.out.printf(
                "%-5d %-25s %-18s %-15s%n",
                ibu.getId(),
                ibu.getNama(),
                ibu.getNik(),
                ibu.getNoHp()
            );
        }
        System.out.println(
            "Total: " + daftarIbu.size() + " ibu"
        );
    }

    private static void cariIbu() {
        System.out.println("\n─── Cari Ibu ───");
        System.out.println(
            "Cari berdasarkan: 1.NIK  2.Nama"
        );
        int p = input.readInt("Pilihan: ");

        if (p == 1) {
            String nik = input.readString("Masukkan NIK: ");
            Ibu ibu = ibuDAO.findByNik(nik);
            if (ibu != null) {
                System.out.println(ibu.getInfo());
            } else {
                System.out.println(
                    "[!] Ibu dengan NIK " + nik
                    + " tidak ditemukan."
                );
            }
        } else if (p == 2) {
            String nama =
                input.readString("Masukkan nama: ");
            List<Ibu> hasil =
                ibuDAO.findByNama(nama);
            if (hasil.isEmpty()) {
                System.out.println(
                    "[!] Tidak ditemukan."
                );
            } else {
                hasil.forEach(System.out::println);
            }
        }
    }

    // ─────────────────────────────────────────────
    // MENU MANAJEMEN ANAK
    // ─────────────────────────────────────────────

    private static void menuManajemenAnak() {
        boolean kembali = false;
        while (!kembali) {
            System.out.println(
                "\n── Manajemen Anak ──"
            );
            System.out.println("1. Daftarkan Anak Baru");
            System.out.println("2. Lihat Anak per Ibu");
            System.out.println("0. Kembali");

            int p = input.readInt("Pilihan: ");
            switch (p) {
                case 1 -> daftarAnakBaru();
                case 2 -> lihatAnakPerIbu();
                case 0 -> kembali = true;
            }
        }
    }

    private static void daftarAnakBaru() {
        System.out.println(
            "\n─── Daftar Anak Baru ───"
        );

        int ibuId = input.readInt("ID Ibu         : ");
        Ibu ibu = ibuDAO.findById(ibuId);
        if (ibu == null) {
            System.out.println(
                "[!] Ibu dengan ID " + ibuId
                + " tidak ditemukan."
            );
            return;
        }
        System.out.println(
            "Ibu: " + ibu.getNama()
        );

        String nama =
            input.readString("Nama anak      : ");
        String tglStr = input.readString(
            "Tgl lahir (yyyy-MM-dd): "
        );
        System.out.println(
            "Jenis kelamin: 1.Laki-laki  2.Perempuan"
        );
        int jk = input.readInt("Pilihan: ");
        String jenisKelamin =
            (jk == 1) ? "Laki-laki" : "Perempuan";

        LocalDate tglLahir;
        try {
            tglLahir = LocalDate.parse(tglStr);
        } catch (Exception e) {
            System.out.println(
                "[!] Format tanggal tidak valid."
            );
            return;
        }

        Anak anak = new Anak(
            nama, tglLahir, jenisKelamin, ibuId
        );
        anakDAO.save(anak);

        if (anak.getId() > 0) {
            // Generate jadwal otomatis setelah
            // anak berhasil didaftarkan
            jadwalService.generateJadwal(anak);
        }
    }

    private static void lihatAnakPerIbu() {
        int ibuId = input.readInt("Masukkan ID Ibu: ");
        List<Anak> daftarAnak =
            anakDAO.findByOrangTuaId(ibuId);

        if (daftarAnak.isEmpty()) {
            System.out.println(
                "Belum ada anak terdaftar untuk ibu ini."
            );
            return;
        }

        System.out.println(
            "\n── Daftar Anak ──"
        );
        for (Anak anak : daftarAnak) {
            System.out.println(anak);
        }
    }

    // ─────────────────────────────────────────────
    // MENU IMUNISASI
    // ─────────────────────────────────────────────

    private static void menuImunisasi() {
        boolean kembali = false;
        while (!kembali) {
            System.out.println(
                "\n── Manajemen Imunisasi ──"
            );
            System.out.println("1. Catat Imunisasi");
            System.out.println("2. Lihat Jadwal Anak");
            System.out.println("3. Lihat Riwayat Anak");
            System.out.println("0. Kembali");

            int p = input.readInt("Pilihan: ");
            switch (p) {
                case 1 -> catatImunisasi();
                case 2 -> lihatJadwal();
                case 3 -> lihatRiwayat();
                case 0 -> kembali = true;
            }
        }
    }

    private static void lihatJadwal() {
        int anakId = input.readInt(
            "Masukkan ID Anak: "
        );
        Anak anak = anakDAO.findById(anakId);
        if (anak == null) {
            System.out.println(
                "[!] Anak tidak ditemukan."
            );
            return;
        }

        System.out.println(
            "\n── Jadwal Imunisasi: "
            + anak.getNama() + " ──"
        );

        // JCF: Map<String, List<>> dari JadwalService
        Map<String, List<JadwalImunisasi>> grouped =
            jadwalService.getJadwalGroupedByStatus(
                anakId
            );

        for (Map.Entry<String,
                 List<JadwalImunisasi>> entry
             : grouped.entrySet()) {

            String status = entry.getKey();
            List<JadwalImunisasi> list = entry.getValue();

            if (!list.isEmpty()) {
                System.out.println(
                    "\n[" + status + "]"
                );
                for (JadwalImunisasi j : list) {
                    System.out.printf(
                        "  %-30s → %s%n",
                        j.getNamaVaksin(),
                        j.getTanggalTarget()
                    );
                }
            }
        }

        System.out.println(
            "\n── Status Kelengkapan ──"
        );
        System.out.println(
            jadwalService.getLaporanKelengkapan(anakId)
        );
    }

    private static void catatImunisasi() {
        System.out.println(
            "\n─── Catat Imunisasi ───"
        );

        int anakId = input.readInt("ID Anak  : ");
        int vaksinId = input.readInt("ID Vaksin: ");
        String faskes =
            input.readString("Nama Faskes: ");
        String petugas =
            input.readString("Nama Petugas: ");
        String catatan =
            input.readString("Catatan (Enter=kosong): ");

        RiwayatImunisasi riwayat =
            new RiwayatImunisasi();
        riwayat.setAnakId(anakId);
        riwayat.setVaksinId(vaksinId);
        riwayat.setTanggalPelaksanaan(LocalDate.now());
        riwayat.setNamaFaskes(faskes);
        riwayat.setNamaPetugas(petugas);
        riwayat.setCatatan(catatan);

        riwayatDAO.save(riwayat);
    }

    private static void lihatRiwayat() {
        int anakId = input.readInt("ID Anak: ");
        List<RiwayatImunisasi> riwayat =
            riwayatDAO.findByAnakId(anakId);

        if (riwayat.isEmpty()) {
            System.out.println(
                "Belum ada riwayat imunisasi."
            );
            return;
        }

        System.out.println(
            "\n── Riwayat Imunisasi ──"
        );
        for (RiwayatImunisasi r : riwayat) {
            System.out.println(r);
        }
    }

    // ─────────────────────────────────────────────
    // MENU MASTER VAKSIN
    // ─────────────────────────────────────────────

    private static void menuMasterVaksin() {
        System.out.println(
            "\n─── Daftar Master Vaksin ───"
        );
        List<Vaksin> vaksinList = vaksinDAO.findAll();

        System.out.printf(
            "%-5s %-10s %-30s %-8s%n",
            "ID", "Kode", "Nama Vaksin", "Usia(bln)"
        );
        System.out.println("-".repeat(55));

        for (Vaksin v : vaksinList) {
            System.out.printf(
                "%-5d %-10s %-30s %-8d%n",
                v.getId(),
                v.getKodeVaksin(),
                v.getNamaVaksin(),
                v.getUsiaBulanPemberian()
            );
        }
    }

    // ─────────────────────────────────────────────
    // MENU LAPORAN
    // ─────────────────────────────────────────────

    private static void menuLaporan() {
        System.out.println(
            "\n─── Laporan ───"
        );
        System.out.println(
            "1. Status Imunisasi per Anak"
        );
        System.out.println(
            "2. Statistik Ibu Terdaftar"
        );
        System.out.println("0. Kembali");

        int p = input.readInt("Pilihan: ");

        if (p == 1) {
            int anakId =
                input.readInt("ID Anak: ");
            System.out.println(
                jadwalService.getLaporanKelengkapan(
                    anakId
                )
            );
        } else if (p == 2) {
            Map<String, Integer> stat =
                ibuDAO.getStatistik();
            System.out.println(
                "\n── Statistik Ibu ──"
            );
            System.out.println(
                "Total terdaftar  : "
                + stat.get("total")
            );
            System.out.println(
                "Baru bulan ini   : "
                + stat.get("baru_bulan_ini")
            );
        }
    }
}