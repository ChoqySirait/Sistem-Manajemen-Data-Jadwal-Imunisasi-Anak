import model.Anak;
import mapper.AnakMapper;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        try {
            AnakMapper anakMapper = new AnakMapper();

            // Input data anak
            Anak anak = new Anak(0, "Siti", new Date());
            anakMapper.simpan(anak);

            // Ambil semua data anak
            List<Anak> daftarAnak = anakMapper.getAll();

            System.out.println("=== DATA ANAK ===");
            for (Anak a : daftarAnak) {
                System.out.println("ID: " + a.getId());
                System.out.println("Nama: " + a.getNama());
                System.out.println("-------------------");
            }

        } catch (Exception e) {
            System.out.println("Terjadi kesalahan: " + e.getMessage());
        }
    }
}