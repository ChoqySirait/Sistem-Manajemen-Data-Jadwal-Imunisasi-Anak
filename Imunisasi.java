package model;

import java.util.Date;

public class Imunisasi {
    private int id;
    private String namaImunisasi;
    private Date tanggal;
    private int anakId;

    public Imunisasi(int id, String namaImunisasi, Date tanggal, int anakId) {
        this.id = id;
        this.namaImunisasi = namaImunisasi;
        this.tanggal = tanggal;
        this.anakId = anakId;
    }

    public String getNamaImunisasi() {
        return namaImunisasi;
    }

    public Date getTanggal() {
        return tanggal;
    }

    public int getAnakId() {
        return anakId;
    }
}