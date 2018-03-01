/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tugas.program.ai.pkg3;

import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.Workbook;
import java.io.File;
import java.io.*;
import java.util.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import jxl.Sheet;
import jxl.Workbook;

/**
 *
 * @author Kukuh Rahingga P
 */
public class Aplikasi{
    int kFold, batas, kForTest, batasCekK, kelipatan;
    double akurasi;
    ArrayList<Berita> listBeritaTest = new ArrayList();
    ArrayList<Berita> listBeritaTrain = new ArrayList();
    ArrayList<Berita> listBeritaValidasi = new ArrayList();
    ArrayList<Output> outputCek = new ArrayList();
    ArrayList<HasilValidasi> listHasilValidasi = new ArrayList();
    
    public Aplikasi(int kFold, int kelipatan, int batasCekK) {
        this.kFold = kFold;
        this.kelipatan = kelipatan;
        this.batasCekK = batasCekK;
    }
    
    // Memproses Data validasi dengan kfold
    public void prosesDataValidasi() {
        int titikBatas = 0, bagian = 1;
        
        // For untuk membagi data train dan data validasi berdasarkan jumlah fold
        for (int semuaBagian = 0; semuaBagian < kFold; semuaBagian++) {
            System.out.println("Penghitungan K-fold ke - " + semuaBagian);
            listBeritaTrain.removeAll(listBeritaTrain);
            listBeritaValidasi.removeAll(listBeritaValidasi);
            addBerita();
            
            // Memasukkan berita bagian ke list validasi
            for (int j = titikBatas; j < titikBatas + batas; j++) {
                Berita b = listBeritaTrain.get(j);
                listBeritaValidasi.add(b);
            }
            
            // Memasukkan berita train ke list train
            for (int hapusObjek = 0; hapusObjek < batas; hapusObjek++) {
                listBeritaTrain.remove(titikBatas);
            }
            
            // For untuk menghitung data per bagian
            for (Berita beritaValidasi : listBeritaValidasi) {
                outputCek.removeAll(outputCek);
                for (Berita beritaTrain : listBeritaTrain) {
                    double hasilHitung = hitungEuclidean(beritaValidasi, beritaTrain);
                    outputCek.add(new Output(beritaValidasi, beritaTrain, hasilHitung));
                }
                
                // Sorting hasil output dari penghitungan euclidean
                quickSorting(outputCek, 0, outputCek.size()-1);
                
                // For untuk mencari k dan hasil hoax dari k tersebut
                for (int cobaK = 3; cobaK < batasCekK; cobaK+=kelipatan) {
                    int ya = 0, tidak = 0;
                    for (int batasK = 0; batasK < cobaK; batasK++) {
                        Output o = outputCek.get(batasK);
                        if (o.getHoaxTrain() == 1) {
                            ya += 1;
                        } else {
                            tidak += 1;
                        }
                    }
                    if (ya > tidak) {
                        beritaValidasi.addCekHoax(cobaK, 1);
                    } else {
                        beritaValidasi.addCekHoax(cobaK, 0);
                    }
                }
            }
            
            // Mencari Persentase tiap bagian.
            for (int k = 3; k < batasCekK; k+=kelipatan) {
                double persentase = perbandinganHasilValidasi(listBeritaValidasi, k);
                listHasilValidasi.add(new HasilValidasi(bagian, k, persentase));
            }
            bagian++;
            titikBatas += batas;
        }
    }
    
    // Memproses data test dengan k yang sudah didapatkan
    public void prosesDataTest(int kTest) {
        resetSemuaList();
        addBerita();
        addBeritaTest();
        for (Berita beritaTest : listBeritaTest) {
            outputCek.removeAll(outputCek);
            for (Berita beritaTrain : listBeritaTrain) {
                double hasilHitung = hitungEuclidean(beritaTest, beritaTrain);
                outputCek.add(new Output(beritaTest, beritaTrain, hasilHitung));
            }
            quickSorting(outputCek, 0, outputCek.size()-1);
            int ya = 0, tidak = 0;
            for (int i = 0; i < kTest; i++) {
                Output o = outputCek.get(i);
                if (o.getHoaxTrain() == 1) {
                    ya += 1;
                } else {
                    tidak += 1;
                }
            }
            if (ya > tidak) {
                beritaTest.setHoax(1);
            } else 
                beritaTest.setHoax(0);
        }
    }
    
    // Memasukkan berita train ke arraylist
    public void addBerita() {
        File fileTrain = new File("Dataset Tugas 3 AI 1718 Data Train.xls");
        if (fileTrain.exists()) {
            try {
                Workbook workbook = Workbook.getWorkbook(fileTrain);
                Sheet sheet = workbook.getSheets()[0];
                
                TableModel model = new DefaultTableModel(sheet.getRows(), sheet.getColumns());
                for (int row = 1; row < sheet.getRows(); row++) {
                    int col = 0;
                    String berita = sheet.getCell(col, row).getContents();
                    col++;
                    double like = Double.parseDouble(sheet.getCell(col, row).getContents());
                    col++;
                    double provokasi = Double.parseDouble(sheet.getCell(col, row).getContents());
                    col++;
                    double komentar = Double.parseDouble(sheet.getCell(col, row).getContents());
                    col++;
                    double emosi = Double.parseDouble(sheet.getCell(col, row).getContents());
                    col++;
                    int hoax = Integer.parseInt(sheet.getCell(col, row).getContents());
                    listBeritaTrain.add(new Berita(berita, like, provokasi, komentar, emosi, hoax));
                }
                batas = sheet.getRows()/kFold;
            } catch (Exception e) {
                System.out.println("Konversi gagal");
            }
        } else {
            System.out.println("File tidak ditemukan");
        } 
    }
    
    // Memasukkan berita test ke array list
    public void addBeritaTest() {
        File fileTest = new File("Dataset Tugas 3 AI 1718 Data Test.xls");
        if (fileTest.exists()) {
            try {
                Workbook workbook = Workbook.getWorkbook(fileTest);
                Sheet sheet = workbook.getSheets()[0];
                
                TableModel model = new DefaultTableModel(sheet.getRows(), sheet.getColumns());
                for (int row = 1; row < sheet.getRows(); row++) {
                    int col = 0;
                    String berita = sheet.getCell(col, row).getContents();
                    col++;
                    double like = Double.parseDouble(sheet.getCell(col, row).getContents());
                    col++;
                    double provokasi = Double.parseDouble(sheet.getCell(col, row).getContents());
                    col++;
                    double komentar = Double.parseDouble(sheet.getCell(col, row).getContents());
                    col++;
                    double emosi = Double.parseDouble(sheet.getCell(col, row).getContents());
                    listBeritaTest.add(new Berita(berita, like, provokasi, komentar, emosi));
                }
            } catch (Exception e) {
                System.out.println("Konversi gagal");
            }
            
        } else {
            System.out.println("File tidak ditemukan");
        }
    }
    
    // Menulis hasil hitung ke file excel
    public void writeDataTest(){
        File fileTest = new File("Dataset Tugas 3 AI 1718 Data Test.xls");
        if (fileTest.exists()) {
            try {
                WritableWorkbook workbook = Workbook.createWorkbook(new File("Hasil Test.xls"));
                WritableSheet sheet = workbook.createSheet("Hasil Test", 0);
                
                sheet.addCell(new Label(0, 0, "Berita"));
                sheet.addCell(new Label(1, 0, "Like"));
                sheet.addCell(new Label(2, 0, "Provokasi"));
                sheet.addCell(new Label(3, 0, "Komentar"));
                sheet.addCell(new Label(4, 0, "Emosi"));
                sheet.addCell(new Label(5, 0, "Hoax"));
                int row = 1;
                for (Berita berita : listBeritaTest) {
                    sheet.addCell(new Label(0, row, berita.getNoBerita()));
                    String like = ""; like += berita.getLike();
                    sheet.addCell(new Label(1, row, like));
                    String Provokasi = ""; Provokasi += berita.getProvokasi();
                    sheet.addCell(new Label(2, row, Provokasi));
                    String Komentar = ""; Komentar += berita.getKomentar();
                    sheet.addCell(new Label(3, row, Komentar));
                    String Emosi = ""; Emosi += berita.getEmosi();
                    sheet.addCell(new Label(4, row, Emosi));
                    String hoax = ""; hoax += berita.getHoax();
                    sheet.addCell(new Label(5, row, hoax));
                    row++;
                }
                
                workbook.write();
                workbook.close();
            } catch (Exception e) {
                System.out.println("Konversi gagal");
            }
            
        } else {
            System.out.println("File tidak ditemukan");
        }
    }

    // Menampilkan berita test
    public void lihatBeritaTest() {
        for (Berita berita : listBeritaTest) {
            System.out.print(berita.toString());
        }
    }
    
    public double perbandinganHasilValidasi(ArrayList<Berita> hasilValidasi, int k){
        double hasil = 0;
        double ya = 0;
        double pembagi = hasilValidasi.size();
        for (Berita berita : hasilValidasi) {
            if (berita.getHoax() == berita.getCekHoax(k)) {
                ya++;
            }
        }
        hasil = (ya/pembagi)*100;
        return hasil;
    }
    
    // Mencari rata-rata dari setiap k yang dicari
    public double cariRataRata(int k) {
        double ret, res = 0, count = 0;
        for (HasilValidasi hasil : listHasilValidasi) {
            if (hasil.getK() == k){
                res += hasil.getPersen();
                count++;
            }
        }
        ret = res/count;
        return ret;
    }
    
    // Menghitung euclidean
    public double hitungEuclidean(Berita bTest, Berita bTrain) {
        double jumlah, hasil ;
        jumlah = Math.pow(bTrain.getLike()- bTest.getLike(), 2) + Math.pow(bTrain.getProvokasi()- bTest.getProvokasi(), 2) + Math.pow(bTrain.getKomentar()- bTest.getKomentar(), 2) + Math.pow(bTrain.getEmosi() - bTest.getEmosi(), 2);
        hasil = Math.sqrt(jumlah);
        return hasil;
    }
    
    // Mengurutkan array hasil penghitungan tiap fold
    public void quickSorting(ArrayList<Output> op, int low, int high) {
        int i = low;
        int j = high;
        double pivot = op.get(low).getEucDistance();
        do {            
            while (op.get(i).getEucDistance() < pivot) {   
                i++;
            }
            while (op.get(j).getEucDistance() > pivot) {                
                j--;
            }
            Output B1 = op.get(i);
            Output B2 = op.get(j);
            if (i < j) {
                op.remove(i);
                op.remove(j-1);
                op.add(i, B2);
                op.add(j, B1);
                i++;
                j--;
            } else if (i == j) {
                i++; j--;
            }
        } while (i <= j);
        if (low < j) {
            quickSorting(op, low, j);
        }
        if (i < high) {
            quickSorting(op, i, high);
        }
    }
    
    // Mereset array list untuk digunakan proses lain
    public void resetSemuaList() {
        listBeritaTest.removeAll(listBeritaTest);
        listBeritaTrain.removeAll(listBeritaTrain);
        listBeritaValidasi.removeAll(listBeritaValidasi);
        outputCek.removeAll(outputCek);
        listHasilValidasi.removeAll(listHasilValidasi);
    }
}
