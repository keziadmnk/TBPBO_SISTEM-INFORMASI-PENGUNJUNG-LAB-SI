import java.sql.Connection; // Untuk objek koneksi JDBC
import java.sql.DriverManager; // Untuk mengatur koneksi ke database
import java.sql.SQLException; // Untuk menangani kesalahan terkait SQL

// Kelas Database untuk mengatur koneksi JDBC
public class Database {
    private static final String URL = "jdbc:mysql://localhost:3306/db_lab"; // URL database
    private static final String USER = "root"; // Username database
    private static final String PASSWORD = ""; // Password database

     // Methode untuk mendapatkan koneksi ke database
    public static Connection getConnection() {
        try {
            // Memuat driver JDBC untuk MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Membuat koneksi menggunakan DriverManager
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            // Exception handling jika driver JDBC tidak ditemukan atau koneksi gagal
            System.out.println("Koneksi Gagal: " + e.getMessage());
            return null;
        }
    }
}
