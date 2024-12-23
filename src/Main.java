import java.util.*; //untuk ArrayList dan Scanner

// Kelas Main untuk menjalankan program
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // Scanner untuk membaca input pengguna
        ArrayList<AsistenLab> daftarAsisten = new ArrayList<>(); // Menyimpan daftar Asisten Lab
        ArrayList<Pengunjung> daftarPengunjung = new ArrayList<>();  // Menyimpan daftar semua pengunjung
        boolean sistemTerbuka = false;  //untuk mengecek login admin

        // Proses login admin
        while (!sistemTerbuka) {
            System.out.println("\n=== WELCOME TO LABORATORIUM OF INFORMATION SYSTEM! ===");
            System.out.print("Masukkan Username: ");
            String username = scanner.nextLine();  // Membaca username
            System.out.print("Masukkan Password: ");
            String password = scanner.nextLine(); // Membaca password

            // Validasi username dan password
            if (username.equals("labor") && password.equals("akucintaSI")) {
                sistemTerbuka = true;
                System.out.println("Login berhasil!");
            } else {
                System.out.println("Username atau password kamu salah. Silakan coba lagi.");
            }
        }

        // Menu utama
        while (true) {
            try {
                 // Tampilan menu utama
                System.out.println("\nHalo, Selamat berkunjung di Laboratorium Sistem Informasi! Silakan isi data pengunjung dahulu ya!");
                System.out.println("1. Mahasiswa non Asisten");
                System.out.println("2. Dosen");
                System.out.println("3. Asisten Lab");
                System.out.println("4. Lihat Daftar Pengunjung");
                System.out.println("5. Lihat Total Pengunjung");
                System.out.println("6. Keluar");
                System.out.println("7. Hapus Data Pengunjung");
                System.out.print("Pilihan Anda: ");
                int pilihan = scanner.nextInt();  // Membaca pilihan pengguna
                scanner.nextLine();  

                if (pilihan == 6) {
                     // Keluar dari sistem
                    System.out.println("Terima kasih sudah menggunakan sistem. Sampai jumpa!");
                    break;
                }

                String nama; // Variabel untuk menyimpan nama pengunjung
                switch (pilihan) {
                    case 1:
                        // Mahasiswa non Asisten
                        Mahasiswa mahasiswa = new Mahasiswa("");
                        mahasiswa.isiIdentitas(); // Input identitas mahasiswa
                        mahasiswa.simpanData(); // Simpan data ke database
                        daftarPengunjung.add(mahasiswa); // Tambahkan ke daftar pengunjung
                        break;
                       
                        case 2:
                        System.out.print("Masukkan Nama Dosen: ");
                        nama = scanner.nextLine();
                        System.out.print("Masukkan NIP Dosen: ");
                        String nip = scanner.nextLine(); 
                        Dosen dosen = new Dosen(nama, nip); 
                        dosen.isiIdentitas(); // Input identitas dosen
                        dosen.simpanData(); // Simpan data ke database
                        daftarPengunjung.add(dosen); // Tambahkan ke daftar pengunjung
                        break;
                    
                        case 3:
                         // Asisten Lab
                        System.out.print("Masukkan Nama Asisten Lab: ");
                        nama = scanner.nextLine();
                        System.out.print("Masukkan NIM: ");
                        String nim = scanner.nextLine();

                         // Cari asisten berdasarkan NIM
                        AsistenLab asisten = daftarAsisten.stream()
                                .filter(a -> a.getNim().equals(nim))
                                .findFirst()
                                .orElse(null);

                        if (asisten == null) {
                            // Jika asisten belum terdaftar
                            asisten = new AsistenLab(nama, nim);
                            daftarAsisten.add(asisten); // Tambahkan ke daftar asisten
                            System.out.println("Data Asisten Lab akan dimasukkan");
                            } else {
                            System.out.println("Data Asisten Lab sudah ada dalam daftar.");
                        }

                         // Menu untuk absen bagi aslab
                        System.out.println("\nMenu Absen:");
                        System.out.println("1. Absen Masuk");
                        System.out.println("2. Absen Keluar");
                        System.out.print("Pilihan Anda: ");
                        int pilihanAbsen = scanner.nextInt();
                        scanner.nextLine(); 

                        if (pilihanAbsen == 1) {
                            asisten.absenMasuk(); // Proses absen masuk
                        } else if (pilihanAbsen == 2) {
                            asisten.absenKeluar(); // Proses absen kelua
                        } else {
                            System.out.println("Pilihan tidak valid.");
                        }

                        // Tambahkan Asisten Lab ke daftarPengunjung jika dia belum ada
                        if (!daftarPengunjung.contains(asisten)) {
                            daftarPengunjung.add(asisten);
                        }
                        break;
                        
                        case 4:
                        // Lihat daftar pengunjung
                        if (daftarPengunjung.isEmpty()) {
                            System.out.println("Belum ada pengunjung yang tercatat.");
                        } else {
                            System.out.println("\nDaftar Pengunjung:");
                            for (Pengunjung pengunjung : daftarPengunjung) {
                                pengunjung.tampilkanInfo(); // Tampilkan informasi pengunjung
                                System.out.println("---------------------------");
                            }
                        }
                        break;

                        case 5:
                        // Lihat total pengunjung
                        int totalMahasiswa = 0;
                        int totalDosen = 0;
                        int totalAsisten = 0;
                    
                        for (Pengunjung pengunjung : daftarPengunjung) {
                            if (pengunjung instanceof Mahasiswa && !(pengunjung instanceof AsistenLab)) {
                                totalMahasiswa++; // Hitung mahasiswa non asisten
                            } else if (pengunjung instanceof Dosen) {
                                totalDosen++; // Hitung dosen
                            } else if (pengunjung instanceof AsistenLab) {
                                totalAsisten++; // Hitung asisten lab
                            }
                        }
                    
                        System.out.println("=== Total Pengunjung ===");
                        System.out.println("Mahasiswa non Asisten: " + totalMahasiswa);
                        System.out.println("Dosen: " + totalDosen);
                        System.out.println("Asisten Lab: " + totalAsisten);
                        System.out.println("Total Keseluruhan: " + daftarPengunjung.size());
                        break;

                        case 7:
                            // Hapus data pengunjung
                            System.out.println("\n=== Hapus Data Pengunjung ===");
                            System.out.println("1. Hapus Mahasiswa non Asisten");
                            System.out.println("2. Hapus Dosen");
                            System.out.println("3. Hapus Asisten Lab");
                            System.out.print("Pilihan Anda: ");
                            int pilihanHapus = scanner.nextInt();
                            scanner.nextLine();

                            switch (pilihanHapus) {
                                case 1:
                                    System.out.print("Masukkan nama mahasiswa yang akan dihapus: ");
                                    String namaMahasiswa = scanner.nextLine();
                                    Pengunjung mahasiswaHapus = daftarPengunjung.stream()
                                            .filter(p -> p instanceof Mahasiswa && p.getNama().equalsIgnoreCase(namaMahasiswa))
                                            .findFirst()
                                            .orElse(null);

                                    if (mahasiswaHapus != null) {
                                        ((Mahasiswa) mahasiswaHapus).hapusData(); // Panggil metode hapusData()
                                        daftarPengunjung.remove(mahasiswaHapus);
                                        System.out.println("Mahasiswa berhasil dihapus dari daftar pengunjung.");
                                    } else {
                                        System.out.println("Mahasiswa dengan nama tersebut tidak ditemukan.");
                                    }
                                    break;

                                case 2:
                                    System.out.print("Masukkan NIP dosen yang akan dihapus: ");
                                    String nipDosen = scanner.nextLine();
                                    Dosen dosenHapus = daftarPengunjung.stream()
                                            .filter(p -> p instanceof Dosen && ((Dosen) p).getNip().equalsIgnoreCase(nipDosen))
                                            .map(p -> (Dosen) p)
                                            .findFirst()
                                            .orElse(null);

                                    if (dosenHapus != null) {
                                        dosenHapus.hapusData(); // Hapus data dari database
                                        daftarPengunjung.remove(dosenHapus); // Hapus dari daftar lokal
                                        System.out.println("Dosen berhasil dihapus.");
                                    } else {
                                        System.out.println("Dosen dengan NIP tersebut tidak ditemukan.");
                                    }
                                    break;

                                case 3:
                                    System.out.print("Masukkan NIM asisten yang akan dihapus: ");
                                    String nimAsisten = scanner.nextLine();
                                    AsistenLab asistenHapus = daftarAsisten.stream()
                                            .filter(a -> a.getNim().equalsIgnoreCase(nimAsisten))
                                            .findFirst()
                                            .orElse(null);

                                    if (asistenHapus != null) {
                                        asistenHapus.hapusData(); // Panggil metode hapusData()
                                        daftarAsisten.remove(asistenHapus); // Hapus dari daftar asisten
                                        daftarPengunjung.remove(asistenHapus); // Hapus dari daftar pengunjung
                                        System.out.println("Asisten Lab berhasil dihapus dari daftar.");
                                    } else {
                                        System.out.println("Asisten Lab dengan NIM tersebut tidak ditemukan.");
                                    }
                                    break;

                                default:
                                    System.out.println("Pilihan tidak valid.");
                            }
                            break;

                    default:
                        // Pilihan tidak valid
                        System.out.println("Pilihan tidak valid. Silakan coba lagi.");
                }
            } catch (InputMismatchException e) {
                // exception handling, tangani kesalahan input angka
                System.out.println("Input tidak valid. Harap masukkan angka.");
                scanner.nextLine(); 
            }
        }

        scanner.close(); // Tutup scanner
    }
}
