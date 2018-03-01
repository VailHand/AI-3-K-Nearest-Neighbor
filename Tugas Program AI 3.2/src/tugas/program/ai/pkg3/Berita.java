/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tugas.program.ai.pkg3;

import java.util.ArrayList;

/**
 *
 * @author Kukuh Rahingga P
 */
public class Berita{
    private String noBerita;
    private double like, provokasi, komentar, emosi;
    private int hoax;
    private ArrayList<HasilHoax> listCekHoax = new ArrayList();

    public Berita(String noBerita, double like, double provokasi, double komentar, double emosi, int hoax) {
        this.noBerita = noBerita;
        this.like = like;
        this.provokasi = provokasi;
        this.komentar = komentar;
        this.emosi = emosi;
        this.hoax = hoax;
    }
    
    public Berita(String noBerita, double like, double provokasi, double komentar, double emosi) {
        this.noBerita = noBerita;
        this.like = like;
        this.provokasi = provokasi;
        this.komentar = komentar;
        this.emosi = emosi;
    }

    public Berita(Berita b) {
        this.noBerita = b.getNoBerita();
        this.like = b.getLike();
        this.provokasi = b.getProvokasi();
        this.komentar = b.getKomentar();
        this.emosi = b.getEmosi();
    }
    
    public Berita(String noBerita, int hoax) {
        this.noBerita = noBerita;
        this.hoax = hoax;
    }
    
    public void addCekHoax(int k, int hoax) {
        listCekHoax.add(new HasilHoax(k, hoax));
    }
    
    public int getCekHoax(int k) {
        for (HasilHoax h : listCekHoax) {
            if (h.getK() == k) {
                return h.getCekHoax();
            }
        }
        return 0;
    }

    public String getNoBerita() {
        return noBerita;
    }

    public void setNoBerita(String noBerita) {
        this.noBerita = noBerita;
    }

    public double getLike() {
        return like;
    }

    public void setLike(double like) {
        this.like = like;
    }

    public double getProvokasi() {
        return provokasi;
    }

    public void setProvokasi(double provokasi) {
        this.provokasi = provokasi;
    }

    public double getKomentar() {
        return komentar;
    }

    public void setKomentar(double komentar) {
        this.komentar = komentar;
    }

    public double getEmosi() {
        return emosi;
    }

    public void setEmosi(double emosi) {
        this.emosi = emosi;
    }

    public int getHoax() {
        return hoax;
    }

    public void setHoax(int hoax) {
        this.hoax = hoax;
    }
    
    @Override
    public String toString() {
        return "noBerita : " + noBerita + ", like : " + like + ", provokasi : " + provokasi + ", komentar : " + komentar + ", emosi : " + emosi + ", hoax : " + hoax + "\n";
    }
    
    public String toStringValidasi() {
        return "noBerita : " + noBerita + ", hoax : " + listCekHoax + "\n";
    }
    
}
