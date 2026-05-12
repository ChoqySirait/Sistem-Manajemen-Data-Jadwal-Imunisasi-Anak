package Imuniku.src.service;

import Imuniku.src.dao.JadwalDAO;
import Imuniku.src.dao.VaksinDAO;

import Imuniku.src.model.Anak;
import Imuniku.src.model.JadwalImunisasi;
import Imuniku.src.model.Vaksin;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Business Logic Jadwal Imunisasi
 */
public class JadwalService {

    private VaksinDAO vaksinDAO;

    private JadwalDAO jadwalDAO;

    public JadwalService() {

        vaksinDAO = new VaksinDAO();

        jadwalDAO = new JadwalDAO();
    }

    /**
     * Generate jadwal otomatis
     */
    public void generateJadwal(Anak anak) {

        System.out.println(
            "\n[INFO] Membuat jadwal imunisasi..."
        );

        List<Vaksin> semuaVaksin =
            vaksinDAO.findAll();

        int jumlah = 0;

        for (Vaksin vaksin : semuaVaksin) {

            LocalDate tanggalTarget =
                anak.getTanggalLahir()
                    .plusMonths(
                        vaksin.getUsiaBulanPemberian()
                    );

            JadwalImunisasi jadwal =
                new JadwalImunisasi();

            jadwal.setAnakId(
                anak.getId()
            );

            jadwal.setVaksinId(
                vaksin.getId()
            );

            jadwal.setNamaVaksin(
                vaksin.getNamaVaksin()
            );

            jadwal.setTanggalTarget(
                tanggalTarget
            );

            jadwal.setStatus(
                "MENDATANG"
            );

            jadwalDAO.save(jadwal);

            jumlah++;
        }

        System.out.println(
            "[SUCCESS] "
            + jumlah
            + " jadwal berhasil dibuat."
        );
    }

    /**
     * Grouping jadwal berdasarkan status
     */
    public Map<String,
            List<JadwalImunisasi>>
    getJadwalGroupedByStatus(int anakId) {

        List<JadwalImunisasi> semuaJadwal =
            jadwalDAO.findByAnakId(anakId);

        LocalDate hariIni =
            LocalDate.now();

        for (JadwalImunisasi jadwal
                : semuaJadwal) {

            if ("MENDATANG".equals(
                    jadwal.getStatus())
                    &&
                jadwal.getTanggalTarget()
                    .isBefore(hariIni)) {

                jadwal.setStatus(
                    "TERLEWAT"
                );

                jadwalDAO.updateStatus(
                    jadwal.getId(),
                    "TERLEWAT"
                );
            }
        }

        Map<String,
            List<JadwalImunisasi>>
            grouped = new LinkedHashMap<>();

        grouped.put(
            "MENDATANG",
            new ArrayList<>()
        );

        grouped.put(
            "SELESAI",
            new ArrayList<>()
        );

        grouped.put(
            "TERTUNDA",
            new ArrayList<>()
        );

        grouped.put(
            "TERLEWAT",
            new ArrayList<>()
        );

        for (JadwalImunisasi jadwal
                : semuaJadwal) {

            List<JadwalImunisasi> list =
                grouped.get(
                    jadwal.getStatus()
                );

            if (list != null) {

                list.add(jadwal);
            }
        }

        return grouped;
    }

    /**
     * Laporan kelengkapan imunisasi
     */
    public String getLaporanKelengkapan(
            int anakId) {

        List<JadwalImunisasi> semua =
            jadwalDAO.findByAnakId(anakId);

        if (semua.isEmpty()) {

            return
                "Belum ada jadwal imunisasi.";
        }

        long selesai =
            semua.stream()
                .filter(j ->
                    "SELESAI".equals(
                        j.getStatus()
                    )
                )
                .count();

        long terlewat =
            semua.stream()
                .filter(j ->
                    "TERLEWAT".equals(
                        j.getStatus()
                    )
                )
                .count();

        int total = semua.size();

        double persen =
            selesai * 100.0 / total;

        return String.format(
            "Total      : %d vaksin\n"
            + "Selesai   : %d vaksin (%.0f%%)\n"
            + "Terlewat  : %d vaksin\n"
            + "Sisa      : %d vaksin",
            total,
            selesai,
            persen,
            terlewat,
            (total - selesai)
        );
    }
}