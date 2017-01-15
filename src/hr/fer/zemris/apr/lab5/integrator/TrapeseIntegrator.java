package hr.fer.zemris.apr.lab5.integrator;

import hr.fer.zemris.apr.lab1.matrix.Matrix;

/**
 * Created by generalic on 15/01/17.
 */
public class TrapeseIntegrator extends AbstractIntegrator {
    private Matrix R;

    public TrapeseIntegrator(Matrix A, Matrix x, double T, double tMax) {
        super(A, x, T, tMax);

        double[][] data = new double[][]{
                {1, 0},
                {0, 1}
        };
        Matrix U = new Matrix(data);

        Matrix mid = A.multiply(T).multiply(0.5);
        this.R = (U.subtract(mid)).inverse().multiply(U.add(mid));
    }

    @Override
    protected Matrix calculate(Matrix xK) {
        return R.multiply(xK);
    }
}
