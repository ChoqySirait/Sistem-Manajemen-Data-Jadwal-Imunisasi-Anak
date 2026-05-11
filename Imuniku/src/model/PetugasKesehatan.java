package model;

import java.time.LocalDate;

/**
 * Konsep PBO:
 * - Inheritance: extends Person
 * - Polymorphism: Override method getInfo() dengan format berbeda
 */
public class PetugasKesehatan extends Person {
    private String nip;
    private String jabatan;
    private String namaFaskes;

    public PetugasKesehatan() {
        super();
    }

    public PetugasKesehatan(String nama, String nip, String jabatan, String faskes, String noHp, LocalDate tglLahir) {
        // Mengirim data ke constructor parent
        super(nama, noHp, tglLahir);
        this.nip = nip;
        this.jabatan = jabatan;
        this.namaFaskes = faskes;
    }

    // ── Atribut Spesifik Petugas ──────────────────
    public String getNip() { return nip; }
    public void setNip(String nip) { this.nip = nip; }

    public String getJabatan() { return jabatan; }
    public void setJabatan(String jabatan) { this.jabatan = jabatan; }

    public String getNamaFaskes() { return namaFaskes; }
    public void setNamaFaskes(String faskes) { this.namaFaskes = faskes; }

    /**
     * Polymorphism: Implementasi spesifik untuk Petugas Kesehatan
     */
    @Override
    public String getInfo() {
        return String.format(
            "=== Data Petugas Kesehatan ===\n" +
            "Nama    : %s\n" +
            "NIP     : %s\n" +
            "Jabatan : %s\n" +
            "Faskes  : %s\n" +
            "No HP   : %s",
            nama, nip, jabatan, namaFaskes, noHp
        );
    }
}