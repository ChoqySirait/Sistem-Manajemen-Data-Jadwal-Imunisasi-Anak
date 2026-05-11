package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Konsep PBO: 
 * - Abstraction: class abstract dengan method abstract getInfo()
 * - Encapsulation: atribut protected agar bisa diakses oleh subclass (Ibu & Petugas)
 */
public abstract class Person {
    protected int id;
    protected String nama;
    protected String noHp;
    protected LocalDate tanggalLahir;
    protected LocalDate tanggalDaftar;

    // Formatter untuk tampilan tanggal yang lebih rapi (dd-MM-yyyy)
    protected static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public Person() {
        this.tanggalDaftar = LocalDate.now(); // Default tanggal daftar adalah hari ini
    }

    public Person(String nama, String noHp, LocalDate tanggalLahir) {
        this.nama = nama;
        this.noHp = noHp;
        this.tanggalLahir = tanggalLahir;
        this.tanggalDaftar = LocalDate.now();
    }

    // ── Getter & Setter ──────────────────────────
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }

    public String getNoHp() { return noHp; }
    public void setNoHp(String noHp) { this.noHp = noHp; }

    public LocalDate getTanggalLahir() { return tanggalLahir; }
    public void setTanggalLahir(LocalDate tgl) { this.tanggalLahir = tgl; }

    public LocalDate getTanggalDaftar() { return tanggalDaftar; }
    public void setTanggalDaftar(LocalDate tgl) { this.tanggalDaftar = tgl; }

    /**
     * Method Abstract: Setiap subclass (Ibu/Petugas) WAJIB
     * mengimplementasikan cara menampilkan info mereka sendiri.
     */
    public abstract String getInfo();

    @Override
    public String toString() {
        return String.format("ID: %d | Nama: %s | HP: %s", id, nama, noHp);
    }
}