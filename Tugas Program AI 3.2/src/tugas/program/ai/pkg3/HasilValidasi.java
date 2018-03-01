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
public class HasilValidasi {
    private int bagian;
    private int k;
    private double persen;

    public HasilValidasi(int bagian, int k, double persen) {
        this.bagian = bagian;
        this.k = k;
        this.persen = persen;
    }

    public int getBagian() {
        return bagian;
    }

    public void setBagian(int bagian) {
        this.bagian = bagian;
    }

    public int getK() {
        return k;
    }

    public void setK(int k) {
        this.k = k;
    }

    public double getPersen() {
        return persen;
    }

    public void setPersen(double persen) {
        this.persen = persen;
    }

    @Override
    public String toString() {
        return "HasilValidasi : " + "bagian = " + bagian + ", k = " + k + ", persen = " + persen + "\n";
    }
}
