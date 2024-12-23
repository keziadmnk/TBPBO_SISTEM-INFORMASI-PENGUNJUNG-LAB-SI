import java.sql.*; // Untuk JDBC menghubungkan Java dengan database
import java.text.SimpleDateFormat; // Untuk format tanggal
import java.util.Date; // Untuk manipulasi waktu


// Kelas AsistenLab adalah subkelas dari Pengunjung dan mengimplementasikan interface Identitas
public class AsistenLab extends Pengunjung implements Identitas {
    private String nim; // Menyimpan NIM asisten
    private Date waktuMasuk; // Waktu saat asisten absen masuk
    private Date waktuKeluar; // Waktu saat asisten absen keluar
    private long durasiPiket; // Durasi piket dalam menit

    // Konstruktor AsistenLab menerima nama dan NIM
    public AsistenLab(String nama, String nim) {
        super(nama); // Memanggil konstruktor superclass (Pengunjung)
        this.nim = nim;
    }

     // method untuk mencatat waktu masuk (Date)
    public void absenMasuk() {
         // cek apakah sudah absen masuk sebelumnya
        if (waktuMasuk != null) {
            System.out.println("Anda sudah absen masuk sebelumnya.");
            return; // menghentikan eksekusi jika sudah absen masuk
        }
        this.waktuMasuk = new Date(); // Menyimpan waktu saat ini (Date)
        System.out.println("Absen masuk berhasil pada " + new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(waktuMasuk));
    }

    // Method untuk mencatat waktu keluar
    public void absenKeluar() {
         // Validasi jika belum absen masuk
        if (waktuMasuk == null) {
            System.out.println("Anda belum absen masuk. Harap absen masuk terlebih dahulu.");
            return;
        }
        
        // Validasi jika sudah absen keluar sebelumnya
        if (waktuKeluar != null) {
            System.out.println("Anda sudah absen keluar sebelumnya.");
            return;
        }
        this.waktuKeluar = new Date(); // Menyimpan waktu saat ini (Date n time)
        durasiPiket = (waktuKeluar.getTime() - waktuMasuk.getTime()) / (1000 * 60); // Durasi dalam menit
        System.out.println("Absen keluar berhasil pada " + new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(waktuKeluar));
        System.out.println("Durasi piket: " + durasiPiket + " menit.");
    
        // Simpan data ke database setelah absen keluar
        simpanData();
    }

    // Metode untuk validasi apakah durasi piket memenuhi syarat minimal 120 menit
    public boolean validasiPiket() {
        return durasiPiket >= 120;
    }

    @Override
    public void isiIdentitas() {
        // Tidak perlu input ulang NIM karena sudah diterima di konstruktor
    }

    @Override
    public void tampilkanInfo() {
        // Menampilkan informasi dari superclass
        super.tampilkanInfo();
        System.out.println("NIM: " + nim);
        // Menampilkan informasi tambahan jika waktu masuk dan keluar sudah tercatat
        if (waktuMasuk != null && waktuKeluar != null) {
            System.out.println("Durasi Piket: " + durasiPiket + " menit");
            System.out.println("Validasi Piket: " + (validasiPiket() ? "Valid" : "Tidak Valid"));
        }
    }

    // Getter untuk NIM
    public String getNim() {
        return nim;
    }

    // Methd untuk menyimpan data asisten lab ke dalam database
    public void simpanData() {
        // Validasi apakah waktuMasuk dan waktuKeluar sudah diisi
        if (waktuMasuk == null || waktuKeluar == null) {
            System.out.println("Data tidak lengkap. Harap pastikan sudah absen masuk dan keluar sebelum menyimpan data.");
            return;
        }
    
        String query = "INSERT INTO asisten_lab (nama, nim, waktu_masuk, waktu_keluar, durasi_piket) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = Database.getConnection(); // Membuka koneksi ke database
             PreparedStatement stmt = conn.prepareStatement(query)) { // Membuat query yang telah disiapkan
    
            stmt.setString(1, this.getNama());  // Mengatur parameter nama
            stmt.setString(2, this.nim); // Mengatur parameter NIM
            stmt.setTimestamp(3, new Timestamp(waktuMasuk.getTime())); // Konversi waktuMasuk ke SQL Timestamp
            stmt.setTimestamp(4, new Timestamp(waktuKeluar.getTime())); // Konversi waktuKeluar ke SQL Timestamp
            stmt.setLong(5, durasiPiket); // Mengatur parameter durasi piket
    
            // Eksekusi query dan mendapatkan jumlah baris yang terpengaru
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Data Asisten Lab berhasil disimpan!");
            } else {
                System.out.println("Gagal menyimpan data Asisten Lab.");
            }
        } catch (SQLException e) {
            // Exception handling jika terjadi kesalahan saat menyimpan data
            System.out.println("Terjadi kesalahan saat menyimpan data: " + e.getMessage());
        }
    }

    // Metode untuk menghapus data asisten lab dari database
public void hapusData() {
    String query = "DELETE FROM asisten_lab WHERE nim = ?"; // Query untuk menghapus data berdasarkan NIM

    try (Connection conn = Database.getConnection(); // Membuka koneksi ke database
         PreparedStatement stmt = conn.prepareStatement(query)) { // Membuat query yang telah disiapkan

        stmt.setString(1, this.nim); // Mengatur parameter NIM

        int rowsAffected = stmt.executeUpdate(); // Eksekusi query dan cek jumlah baris yang terpengaruh
        if (rowsAffected > 0) {
            System.out.println("Data Asisten Lab dengan NIM " + nim + " berhasil dihapus dari database!");
        } else {
            System.out.println("Data Asisten Lab dengan NIM " + nim + " tidak ditemukan di database.");
        }
    } catch (SQLException e) {
        // Exception handling jika terjadi kesalahan saat menghapus data
        System.out.println("Terjadi kesalahan saat menghapus data Asisten Lab: " + e.getMessage());
    }
}

    
    }