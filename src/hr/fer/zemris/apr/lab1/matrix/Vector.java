package hr.fer.zemris.apr.lab1.matrix;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * Created by generalic on 06/11/16.
 */
public class Vector extends Matrix {

    public Vector(double[] vector) {
        super(vector);
    }

    public Vector(int n) {
        super(n);
    }

    public int size() {
        return super.getRows();
    }

    public double get(int index) {
        return super.get(index, 0);
    }

    public void set(int index, double value) {
        super.set(index, 0, value);
    }

    @Override
    public String toString() {
        return Arrays.toString(getVector());
    }

    @Override
    public Vector copy() {
        return new Vector(this.getVector());
    }

    @Override
    public Vector multiply(double scalar) {
        return new Vector(super.multiply(scalar).getVector());
    }

    @Override
    public Vector add(Matrix other) {
        return new Vector(super.add(other).getVector());
    }

    @Override
    public Vector subtract(Matrix other) {
        return new Vector(super.subtract(other).getVector());
    }
}
