package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Konsep PBO:
 * - Inheritance: extends Person
 * - Polymorphism: Override method getInfo()
 * - JCF: Menggunakan List<Anak> untuk relasi 1-ke-banyak
 */
public class Ibu extends Person {
    private String nik;
    private String alamat;
    private List<Anak> daftarAnak; // Implementasi JCF ArrayList

    public Ibu() {
        super(); // Memanggil constructor Person()
        this.daftarAnak = new ArrayList<>();
    }

    public Ibu(String nama, String nik, String alamat, String noHp, LocalDate tanggalLahir) {
        // Memanggil constructor parent (Person)
        super(nama, noHp, tanggalLahir);
        this.nik = nik;
        this.alamat = alamat;
        this.daftarAnak = new ArrayList<>();
    }

    // ── Atribut Spesifik Ibu ──────────────────────
    public String getNik() { return nik; }
    public void setNik(String nik) { this.nik = nik; }

    public String getAlamat() { return alamat; }
    public void setAlamat(String alamat) { this.alamat = alamat; }

    public List<Anak> getDaftarAnak() { return daftarAnak; }
    public void setDaftarAnak(List<Anak> list) { this.daftarAnak = list; }
    
    public void tambahAnak(Anak anak) {
        this.daftarAnak.add(anak);
    }

    /**
     * Polymorphism: Implementasi spesifik untuk Ibu
     */
    @Override
    public String getInfo() {
        return String.format(
            "=== Data Ibu ===\n" +
            "ID      : %d\n" +
            "Nama    : %s\n" +
            "NIK     : %s\n" +
            "Alamat  : %s\n" +
            "No HP   : %s\n" +
            "Jml Anak: %d orang",
            id, nama, nik, alamat, noHp, daftarAnak.size()
        );
    }
}