package Imuniku.src.model;

public class Anak {

    private int idAnak;
    private String namaAnak;
    private String tanggalLahir;
    private String jenisKelamin;
    private String namaOrangTua;
    private String alamat;
    public Anak() {
    }
    public Anak(int idAnak, String namaAnak, String tanggalLahir, String jenisKelamin, String namaOrangTua,
            String alamat) {
        this.idAnak = idAnak;
        this.namaAnak = namaAnak;
        this.tanggalLahir = tanggalLahir;
        this.jenisKelamin = jenisKelamin;
        this.namaOrangTua = namaOrangTua;
        this.alamat = alamat;
    }
    public int getIdAnak() {
        return idAnak;
    }
    public void setIdAnak(int idAnak) {
        this.idAnak = idAnak;
    }
    public String getNamaAnak() {
        return namaAnak;
    }
    public void setNamaAnak(String namaAnak) {
        this.namaAnak = namaAnak;
    }
    public String getTanggalLahir() {
        return tanggalLahir;
    }
    public void setTanggalLahir(String tanggalLahir) {
        this.tanggalLahir = tanggalLahir;
    }
    public String getJenisKelamin() {
        return jenisKelamin;
    }
    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }
    public String getNamaOrangTua() {
        return namaOrangTua;
    }
    public void setNamaOrangTua(String namaOrangTua) {
        this.namaOrangTua = namaOrangTua;
    }
    public String getAlamat() {
        return alamat;
    }
    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

}