package hr.fer.zemris.apr.lab4.solution;

/**
 * Created by generalic on 17/12/16.
 */
public class Decoder {

    public double[] mins;
    public double[] maxs;

    public int numberOfVariables;

    public Decoder(double[] mins, double[] maxs) {
        this.mins = mins;
        this.maxs = maxs;
        this.numberOfVariables = mins.length;
    }

    public int convertToBinary(double x, int index, int n) {
        double min = mins[index];
        double max = maxs[index];
        return (int) Math.round(((x - min) / (max - min) * (Math.pow(2, n - 1))));
    }

    public double convertFromBinary(int b, int index, int n) {
        double min = mins[index];
        double max = maxs[index];
        return min + b * (max - min) / (Math.pow(2, n - 1));
    }

}
