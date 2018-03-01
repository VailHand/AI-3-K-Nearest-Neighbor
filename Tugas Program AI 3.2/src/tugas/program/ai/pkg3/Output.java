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
public class Output{
    private Berita beritaValidation, beritaTrain;
    private double eucDistance;

    public Output(Berita beritaValidation, Berita beritaTrain, double eucDistance) {
        this.beritaValidation = beritaValidation;
        this.beritaTrain = beritaTrain;
        this.eucDistance = eucDistance;
    }

    public int getHoaxTrain() {
        return beritaTrain.getHoax();
    }

    public double getEucDistance() {
        return eucDistance;
    }

    public void setEucDistance(double eucDistance) {
        this.eucDistance = eucDistance;
    }

    @Override
    public String toString() {
        return "beritaValidation = " + beritaTrain + ", eucDistance = " + eucDistance + "\n";
    }
    
    
}
