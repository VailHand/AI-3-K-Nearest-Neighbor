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
public class HasilHoax {
    private int k;
    private int cekHoax;

    public HasilHoax(int k, int cekHoax) {
        this.k = k;
        this.cekHoax = cekHoax;
    }

    public int getK() {
        return k;
    }

    public void setK(int k) {
        this.k = k;
    }

    public int getCekHoax() {
        return cekHoax;
    }

    public void setCekHoax(int cekHoax) {
        this.cekHoax = cekHoax;
    }

    @Override
    public String toString() {
        return "HasilHoax{" + "k=" + k + ", cekHoax=" + cekHoax + '}';
    }
}
