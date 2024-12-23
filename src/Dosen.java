import java.sql.*; // Untuk JDBC
import java.text.SimpleDateFormat; // Untuk format tanggal
import java.util.Date; // tanggal dan waktu

// kelas Dosen yang juga merupakan turunan dari Pengunjung dan warisan identitas
public class Dosen extends Pengunjung implements Identitas {
    private String nip; // Nomor Induk Pegawai
    private Date waktuKunjungan; // Menyimpan waktu kunjungan dosen ke labor

   // Konstruktor untuk inisialisasi nama dan NIP, sekaligus mencatat waktu kunjungan
    public Dosen(String nama, String nip) {
        super(nama); // Memanggil konstruktor kelas induk (Pengunjung) untuk mengatur nama
        this.nip = nip; // Inisialisasi NIP
        this.waktuKunjungan = new Date(); // Waktu kunjungan otomatis saat objek dibuat
    }

    // Getter untuk NIP
    public String getNip() {
        return nip; // Mengembalikan nilai NIP dosen
    }

    // Getter untuk waktu kunjungan
    public Date getWaktuKunjungan() {
        return waktuKunjungan; // Mengembalikan waktu kunjungan
    }

    // Metode untuk menyimpan data dosen ke dalam database
    public void simpanData() {
         // Validasi sebelum menyimpan
         if (getNama() == null || getNama().trim().isEmpty()) {
            System.out.println("Nama tidak boleh kosong! Data tidak dapat disimpan.");
            return;
        }
        if (nip == null || nip.trim().isEmpty()) {
            System.out.println("NIP tidak boleh kosong! Data tidak dapat disimpan.");
            return;
        }
        // Query SQL untuk menyisipkan data dosen ke tabel dosen
        String query = "INSERT INTO dosen (nama, nip, waktu_kunjungan) VALUES (?, ?, ?)";
        
         // Exception handling untuk operasi database
        try (Connection conn = Database.getConnection(); // Membuka koneksi ke database menggunakan metode dari kelas Database
             PreparedStatement stmt = conn.prepareStatement(query)) { // Menggunakan PreparedStatement untuk keamanan dari SQL Injectio
            
            // Mengatur parameter query berdasarkan data dosen
            stmt.setString(1, this.getNama()); // Ambil nama dari superclass Pengunjung
            stmt.setString(2, this.nip); // NIP dosen

            // Konversi waktu kunjungan ke format SQL TIMESTAMP
            stmt.setTimestamp(3, new Timestamp(waktuKunjungan.getTime()));

            // Eksekusi query dan mengembalikan jumlah baris yang terpengaruh
            int rowsAffected = stmt.executeUpdate();
            
            // Mengecek hasil eksekusi
            if (rowsAffected > 0) {
                System.out.println("Data Dosen berhasil disimpan!");
            } else {
                System.out.println("Gagal menyimpan data Dosen.");
            }
        } catch (SQLException e) {
            // Menangkap kesalahan SQL dan menampilkan pesan error
            System.out.println("Terjadi kesalahan saat menyimpan data: " + e.getMessage());
        }
    }

    // Menampilkan informasi dosen
    @Override
    public void tampilkanInfo() {
        super.tampilkanInfo(); // Menampilkan informasi dari superclass (nama)
        System.out.println("NIP: " + nip); // Menampilkan NIP dosen
        
        // Format dan tampilkan date time kunjungan dalam format dd-MM-yyyy HH:mm:ss
        System.out.println("Waktu Kunjungan: " + new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(waktuKunjungan));
    }

    // Implementasi metode isiIdentitas
    @Override
    public void isiIdentitas() {
        // Kosong karena identitas sudah diatur melalui konstruktor
    }

    public void hapusData() {
        // Query untuk menghapus data berdasarkan NIP
        String query = "DELETE FROM dosen WHERE nip = ?";
        
        try (Connection conn = Database.getConnection();  // Membuka koneksi database
             PreparedStatement stmt = conn.prepareStatement(query)) {  // Membuat PreparedStatement
            
            // Set parameter NIP yang ingin dihapus
            stmt.setString(1, this.nip);
            
            // Eksekusi query
            int rowsAffected = stmt.executeUpdate();
            
            // Mengecek hasil penghapusan
            if (rowsAffected > 0) {
                System.out.println("Data dosen dengan NIP " + nip + " berhasil dihapus!");
            } else {
                System.out.println("Data dosen dengan NIP " + nip + " tidak ditemukan.");
            }
        } catch (SQLException e) {
            // Menangani kesalahan SQL
            System.out.println("Terjadi kesalahan saat menghapus data: " + e.getMessage());
        }
    }
    
}
