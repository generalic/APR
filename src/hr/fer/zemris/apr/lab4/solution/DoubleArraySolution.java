package hr.fer.zemris.apr.lab4.solution;

import hr.fer.zemris.apr.lab4.util.RandomProvider;

import java.util.Arrays;
import java.util.Random;
import java.util.function.Function;

/**
 * Razred koji predstavlja rješenje u realnom zapisu.<br>
 * @author Boris Generalic
 */
public class DoubleArraySolution extends SingleObjectiveSolution {

    /** Vektor točaka. */
    public double[] points;

    private Random rand = RandomProvider.get();

    public DoubleArraySolution(int size) {
        this.points = new double[size];
    }

    public DoubleArraySolution(Decoder decoder) {
        this.points = new double[decoder.numberOfVariables];
        randomize(decoder.mins, decoder.maxs);
    }

    @Override
    public double[] getPoints() {
        return points;
    }

    @Override
    public DoubleArraySolution duplicate() {
        DoubleArraySolution result = new DoubleArraySolution(points.length);
        result.points = Arrays.copyOf(this.points, this.points.length);
        result.fitness = this.fitness;
        return result;
    }

    public void randomize(double[] mins, double[] maxs) {
        randomizeValues(i -> rand.nextDouble() * (maxs[i] - mins[i]) + mins[i]);
    }

    public void randomize(double min, double max) {
        randomizeValues(i -> rand.nextDouble() * (max - min) + min);
    }

    /**
     * Metoda postavlja vektor rješenja na slučajno odabrane točke iz intervala
     * {@code <min, max>}
     * @param function vrijednost {@code i}-te točke
     */
    private void randomizeValues(Function<Integer, Double> function) {
        for (int i = 0; i < points.length; i++) {
            points[i] = function.apply(i);
        }
    }

    @Override
    public String toString() {
        return Arrays.toString(points);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }

        DoubleArraySolution that = (DoubleArraySolution) o;

        return Arrays.equals(points, that.points);

    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(points);
    }
}
