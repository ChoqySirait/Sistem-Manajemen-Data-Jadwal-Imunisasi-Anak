# ImuniKu — Sistem Informasi Imunisasi Ibu dan Anak

> Sistem pencatatan dan pengelolaan data imunisasi
> berbasis Java Console dengan MySQL Database

---

## Deskripsi Aplikasi

ImuniKu adalah aplikasi Java console yang dirancang untuk membantu tenaga kesehatan di Posyandu, Puskesmas, dan Klinik Desa dalam mengelola data imunisasi ibu dan anak secara digital.

Aplikasi ini menyediakan menu interaktif untuk:
- menyimpan data ibu dan anak,
- mencatat imunisasi anak,
- menampilkan jadwal imunisasi otomatis berdasarkan usia anak,
- serta menghasilkan laporan kelengkapan imunisasi per anak.

Sistem ini dikembangkan sebagai backend engine yang menerapkan konsep Pemrograman Berorientasi Objek (PBO) dengan database MySQL.

---

## Latar Belakang Proyek

Proyek ini merupakan **tahap implementasi dan pengembangan lanjutan** dari tugas UI/UX sebelumnya berjudul **"Perancangan Aplikasi Jadwal Imunisasi Anak Indonesia"**.

Pada proyek UI/UX, telah dilakukan:
- Riset lapangan mengenai permasalahan imunisasi anak dan pencatatan manual di Posyandu
- Perancangan desain antarmuka (mockup, wireframe, user flow)
- Analisis kebutuhan fungsional dan kebutuhan pengguna

Proyek **ImuniKu** sekarang melanjutkan dengan:
- Mengimplementasikan logika bisnis yang dirancang sebelumnya
- Membangun sistem penyimpanan data dengan database MySQL
- Menciptakan backend engine yang solid dengan menerapkan konsep PBO
- Menyediakan antarmuka console yang intuitif sebagai pengujian fungsional

Dengan demikian, ImuniKu adalah realisasi nyata dari konsep yang telah dirancang pada tahap UI/UX sebelumnya.

---

## Fitur Utama

- Manajemen data ibu
  - pendaftaran ibu baru
  - daftar ibu terdaftar
  - pencarian ibu berdasarkan NIK atau nama
- Manajemen data anak
  - pendaftaran anak baru terkait ibu
  - tampilan anak per ibu
- Manajemen imunisasi
  - pencatatan riwayat imunisasi anak
  - tampilan jadwal imunisasi per anak
  - status kelengkapan imunisasi (MENDATANG, SELESAI, TERTUNDA, TERLEWAT)
- Master data vaksin
  - tampilan data vaksin lengkap dengan usia pemberian
- Menu laporan
  - laporan status imunisasi per anak
  - statistik ibu terdaftar

---

## Teknologi yang Digunakan

- Java 17 / 21
- MySQL 8.x
- JDBC dengan MySQL Connector/J
- Java Collection Framework (ArrayList, HashMap)

---

## Konsep PBO yang Diterapkan

### 1. Inheritance
- `Person` sebagai kelas dasar untuk entitas manusia
- `Ibu` dan `PetugasKesehatan` sebagai kelas turunan
- `BaseDAO` dan `BaseMapper` sebagai kelas abstrak yang menjadi pola dasar untuk DAO dan Mapper konkret

### 2. JDBC
- Koneksi singleton ke MySQL di `DatabaseConnection`
- Operasi CRUD menggunakan JDBC
- Menggunakan `ResultSet` untuk membaca hasil query

### 3. JCF (Java Collection Framework)
- `ArrayList<Anak>` dan `ArrayList<JadwalImunisasi>` untuk menyimpan daftar objek
- `HashMap<String, List<JadwalImunisasi>>` untuk mengelompokkan jadwal berdasarkan status

### 4. ORM Data Mapper
- Setiap entitas memiliki mapper tersendiri
- Mapper mengubah baris `ResultSet` menjadi objek Java
- Memisahkan logika domain dan logika akses data

---

## Struktur Proyek

- `Imuniku/src/main/Main.java` - entry point dan menu konsol utama
- `Imuniku/src/driver/DatabaseConnection.java` - konfigurasi koneksi MySQL
- `Imuniku/src/dao/` - kelas DAO untuk operasi data (Ibu, Anak, Vaksin, Riwayat, Jadwal)
- `Imuniku/src/mapper/` - kelas Mapper untuk konversi `ResultSet` ke objek model
- `Imuniku/src/model/` - kelas model data (Ibu, Anak, Vaksin, JadwalImunisasi, RiwayatImunisasi, Person, PetugasKesehatan)
- `Imuniku/src/service/` - logika bisnis untuk pembuatan jadwal dan laporan
- `Imuniku/src/util/InputHelper.java` - utilitas input konsol

---

## Persyaratan Sistem

- Java JDK 8 atau lebih baru
- MySQL Server 8.x
- MySQL Connector/J untuk koneksi JDBC

---


## Cara Menjalankan

1. Jalankan MySQL Server.
2. Siapkan database `imuniku_db` dan tabel yang diperlukan.
3. Kompilasi file Java dengan menambahkan library `mysql-connector-java.jar` ke classpath.

Contoh kompilasi di Windows:

```bash
cd Imuniku
javac -cp ".;path\to\mysql-connector-java.jar" src\main\Main.java src\dao\*.java src\driver\*.java src\model\*.java src\service\*.java src\util\*.java
```

Jalankan aplikasi:

```bash
java -cp ".;path\to\mysql-connector-java.jar" Imuniku.src.main.Main
```

---

## Cara Menggunakan

- Pilih menu `Manajemen Ibu` untuk mendaftarkan, melihat, dan mencari data ibu.
- Pilih menu `Manajemen Anak` untuk mendaftarkan anak baru dan melihat daftar anak per ibu.
- Pilih menu `Imunisasi` untuk mencatat imunisasi, melihat jadwal imunisasi, dan melihat riwayat imunisasi anak.
- Pilih menu `Master Data Vaksin` untuk melihat daftar vaksin dan usia pemberiannya.
- Pilih menu `Laporan` untuk melihat status imunisasi per anak dan statistik ibu terdaftar.

---

## Catatan Penting

- Struktur package harus sesuai dengan `package Imuniku.src...` di kode sumber.
- Database harus tersedia dan terkoneksi agar aplikasi dapat berjalan.
- Beberapa fitur seperti penghapusan data tersedia di kelas DAO, tetapi menu utama fokus pada pendaftaran, tampilan, pencatatan, dan laporan.

---

## Manfaat Proyek

ImuniKu membantu mendigitalisasi pencatatan imunisasi anak dan ibu sehingga data lebih mudah diakses, lebih terstruktur, dan lebih aman daripada catatan manual. Sistem ini juga menghasilkan jadwal imunisasi otomatis sehingga tenaga kesehatan dapat memantau status imunisasi anak dengan lebih efisien.
-