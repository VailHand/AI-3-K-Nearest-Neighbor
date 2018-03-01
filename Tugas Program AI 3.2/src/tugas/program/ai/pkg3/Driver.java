/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tugas.program.ai.pkg3;

/**
 *
 * @author Kukuh Rahingga P
 */
public class Driver {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int kelipatan = 4;
        int batasCekK = 500;
        Aplikasi app = new Aplikasi(10, kelipatan, batasCekK);
        
        // Hitung Validasi
        System.out.println("Proses penghitungan");
        app.prosesDataValidasi();
        System.out.println("==================================================");
        System.out.println("hasil penghitungan");
        int k = 0;
        double best = 0;
        for (int i = 3; i < batasCekK; i+=kelipatan) {
            double rata2 = app.cariRataRata(i);
            if (rata2 > best) {
                best = rata2;
                k = i;
                System.out.println("k = " + k + ", akurasi = " + best);
            } 
        }

        System.out.println("==================================================");
        System.out.println("Hasil Untuk Data Validasi");
        System.out.println("K terbaik = " + k);
        System.out.println("Akurasi terbaik = " + best + "%");
        System.out.println("");
        
        // Hitung Test
        System.out.println("==================================================");
        System.out.println("Penghitungan Data test ................");
        app.prosesDataTest(k);
        System.out.println("Hasil Untuk Data Test");
        app.writeDataTest();
        app.lihatBeritaTest();
        
    }
    
}
