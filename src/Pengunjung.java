import java.util.*; // Import untuk ArrayList, Scanner, Date, dll.
import java.text.SimpleDateFormat; // Untuk format tanggal

// Kelas yang mewakili Pengunjung
class Pengunjung {
    protected String nama; // Nama pengunjung
    protected Date waktuKunjungan; // Waktu kunjungan

    // Konstruktor untuk menginisialisasi nama dan waktu kunjungan
    public Pengunjung(String nama) {
        this.nama = nama;
        this.waktuKunjungan = new Date(); // Waktu kunjungan di-set otomatis saat objek dibuat
    }

    // Metode untuk mendapatkan nama pengunjung
    public String getNama() {
        return nama;
    }

    // Metode untuk mengatur nama pengunjung
    public void setNama(String nama) {
        this.nama = nama;
    }

    // Menampilkan informasi pengunjung (nama dan waktu kunjungan)
    public void tampilkanInfo() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        System.out.println("Nama: " + nama);
        System.out.println("Waktu Kunjungan: " + sdf.format(waktuKunjungan));
    }
}
