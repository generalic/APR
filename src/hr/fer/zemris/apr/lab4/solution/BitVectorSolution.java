package hr.fer.zemris.apr.lab4.solution;

import hr.fer.zemris.apr.lab4.util.RandomProvider;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

/**
 * Razred koji predstavlja rješenje u binarnom zapisu.<br>
 * @author Boris Generalic
 */
public class BitVectorSolution extends SingleObjectiveSolution {

    public int numberOfVariables;
    public int[] ranges;
    /** Polje bajtova služi za prikaz rješenja u domeni {0, 1}. */
    public int[] bits;
    public int[] points;

    public int p;
    public Decoder decoder;

    private Random rand = RandomProvider.get();

    public BitVectorSolution(int size) {
        this.bits = new int[size];
    }

    public BitVectorSolution(Decoder decoder, int p) {
        this.decoder = decoder;
        this.p = p;

        this.numberOfVariables = decoder.numberOfVariables;
        this.ranges = new int[numberOfVariables];
        this.points = new int[numberOfVariables];

        for (int i = 0; i < numberOfVariables; i++) {
            double min = decoder.mins[i];
            double max = decoder.maxs[i];

            int n = (int) Math.ceil(
                    (Math.log(Math.floor(1 + (max - min) * Math.pow(10, p))) / Math.log(2))
            );
            ranges[i] = n;
        }

        for (int i = 0; i < numberOfVariables; i++) {
            this.points[i] = rand.nextInt((int) Math.pow(2, ranges[i] - 1));
        }

        updateBits();
    }

    @Override
    public double[] getPoints() {
        return IntStream.range(0, numberOfVariables)
                                   .mapToDouble(
                                           i -> decoder.convertFromBinary(points[i], i, ranges[i]))
                                   .toArray();
    }

    public static void main(String[] args) {
        int n = (int) Math.rint(54.1);
        n = (int) Math.ceil(54.5);
        n = (int) Math.round(54.3);
        System.out.println(n);

        Decoder decoder = new Decoder(new double[]{0, 0}, new double[]{5, 5});
        BitVectorSolution v = new BitVectorSolution(decoder, 2);

        System.out.println(Arrays.toString(v.bits));
        System.out.println(Arrays.toString(v.points));

        v.updatePoints();

    }

    private void updateBits() {
        this.bits = IntStream.range(0, numberOfVariables)
                             .flatMap(i -> Arrays.stream(getBinary(points[i], ranges[i])))
                             .toArray();
    }

    public void updatePoints() {
        int start = 0;
        for (int i = 0; i < ranges.length; i++) {
            int[] v = Arrays.copyOfRange(bits, start, start + ranges[i]);

            StringBuilder sb = new StringBuilder();
            Arrays.stream(v).forEach(sb::append);

            points[i] = Integer.parseInt(sb.toString(), 2);
            start += ranges[i];
        }
    }

    public int[] getBinary(int value, int n) {
        int[] sol = new int[n];
        String binary = Integer.toBinaryString(value);

        int diff = n - binary.length();
        if (diff > 0) {
            for (int i = 0; i < diff; i++) {
                binary = "0" + binary;
            }
        }

        for (int j = 0; j < binary.length(); j++) {
            int tmp = Integer.parseInt("" + binary.charAt(j));
            sol[j] = tmp;
        }
        return sol;
    }

    @Override
    public BitVectorSolution duplicate() {
        BitVectorSolution result = new BitVectorSolution(decoder, p);
        result.fitness = this.fitness;
        return result;
    }

    /**
     * Postavlja polje bitova na 0 ili 1, na temelju slučajnog odabira.
     * @param rand generator slučajnih brojeva
     */
    public void randomize(Random rand) {
        for (int i = 0; i < bits.length; i++) {
            bits[i] = (byte) (rand.nextBoolean() ? 1 : 0);
        }
    }

    @Override
    public String toString() {
        return Arrays.toString(getPoints());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }

        BitVectorSolution that = (BitVectorSolution) o;

        return Arrays.equals(bits, that.bits);

    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(bits);
    }
}
