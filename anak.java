package model;

import java.util.Date;

public class Anak extends Orang {
    private int id;
    private Date tanggalLahir;

    public Anak(int id, String nama, Date tanggalLahir) {
        super(nama);
        this.id = id;
        this.tanggalLahir = tanggalLahir;
    }

    public int getId() {
        return id;
    }

    public Date getTanggalLahir() {
        return tanggalLahir;
    }
}