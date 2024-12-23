import java.sql.*; // Untuk JDBC
import java.util.Scanner; // Impor Scanner dari java.util


// Kelas Mahasiswa yang merupakan turunan dari Pengunjung
public class Mahasiswa extends Pengunjung implements Identitas {
    private String nim; // NIM mahasiswa
    private String tujuan; // Tujuan kunjungan

    // Konstruktor Mahasiswa yang memanggil konstruktor superclass
    public Mahasiswa(String nama) {
        super(nama);
    }

     // Implementasi method isiIdentitas dari interface Identitas
    @Override
    public void isiIdentitas() {
        Scanner scanner = new Scanner(System.in);  // Membuka Scanner untuk membaca input pengguna
        
        // Loop untuk memastikan input nama valid
        while (true) {
            System.out.print("Masukkan Nama: ");
            String nama = scanner.nextLine().trim();
            if (nama.isEmpty()) {
                System.out.println("Nama tidak boleh kosong! Silakan ulangi.");
            } else {
                this.setNama(nama);
                break;
            }
        }

        // Loop untuk memastikan input NIM valid
        while (true) {
            try {
                System.out.print("Masukkan NIM: ");
                nim = scanner.nextLine().trim(); // Membaca input NIM
                if (nim.isEmpty() || !nim.matches("\\d+")) { // Validasi bahwa NIM harus diisi dan berupa angka
                    throw new NumberFormatException("NIM harus berupa angka dan tidak boleh kosong!");
                }
                break; // Keluar dari loop jika NIM valid
            } catch (NumberFormatException e) {
                System.out.println(e.getMessage()); // Menampilkan pesan error
            }
        }

        // Loop untuk memastikan input tujuan valid
        while (true) {
            System.out.print("Masukkan Tujuan: ");
            tujuan = scanner.nextLine().trim();
            if (tujuan.isEmpty()) {
                System.out.println("Tujuan tidak boleh kosong! Silakan ulangi.");
            } else {
                break;
            }
        }
    }

    // Override metode tampilkanInfo untuk menampilkan info mahasiswa
    @Override
    public void tampilkanInfo() {
        super.tampilkanInfo(); // Memanggil method dari superclass untuk menampilkan nama
        System.out.println("NIM: " + nim); // Menampilkan NIM mahasiswa
        System.out.println("Tujuan: " + tujuan); // Menampilkan tujuan kunjungan
    }

    // method untuk menyimpan data mahasiswa ke dalam database
    public void simpanData() {
        //query SQL untuk menyisipkan data mahasiswa ke tabel mahasiswa
        String query = "INSERT INTO mahasiswa (nama, nim, tujuan) VALUES (?, ?, ?)";
        try (Connection conn = Database.getConnection(); // Membuka koneksi ke database menggunakan kelas Database
             PreparedStatement stmt = conn.prepareStatement(query)) {  // Membuat PreparedStatement untuk query yang aman dari SQL Injection
            
                // Set nilai parameter query
            stmt.setString(1, this.getNama()); // Ambil nama dari superclass
            stmt.setString(2, this.nim);  // NIM mahasiswa
            stmt.setString(3, this.tujuan); // Tujuan kunjungan
 
            // Eksekusi query
            int rowsAffected = stmt.executeUpdate();
            
            // Mengecek apakah data berhasil disimpan
            if (rowsAffected > 0) {
                System.out.println("Data Mahasiswa berhasil disimpan!");
            } else {
                System.out.println("Gagal menyimpan data Mahasiswa.");
            }
        } catch (SQLException e) {
              // Exception handling untuk menangani kesalahan SQL
            System.out.println("Terjadi kesalahan saat menyimpan data: " + e.getMessage());
        }
    }

    public void hapusData() { //delete
        // Query untuk menghapus data berdasarkan NIM
        String query = "DELETE FROM mahasiswa WHERE nim = ?";
        
        try (Connection conn = Database.getConnection();  // Membuka koneksi database
             PreparedStatement stmt = conn.prepareStatement(query)) {  // Membuat PreparedStatement
            
            // Set parameter NIM yang ingin dihapus
            stmt.setString(1, this.nim);
            
            // Eksekusi query
            int rowsAffected = stmt.executeUpdate();
            
            // Mengecek hasil penghapusan
            if (rowsAffected > 0) {
                System.out.println("Data mahasiswa dengan NIM " + nim + " berhasil dihapus!");
            } else {
                System.out.println("Data mahasiswa dengan NIM " + nim + " tidak ditemukan.");
            }
        } catch (SQLException e) {
            // Menangani kesalahan SQL
            System.out.println("Terjadi kesalahan saat menghapus data: " + e.getMessage());
        }
    }
    
}
