package hr.fer.zemris.apr.lab5.integrator;

import hr.fer.zemris.apr.lab1.matrix.Matrix;

/**
 * Created by generalic on 15/01/17.
 */
public class RungeKuttaIntegrator extends AbstractIntegrator {
    public RungeKuttaIntegrator(Matrix A, Matrix x, double T, double tMax) {
        super(A, x, T, tMax);
    }

    @Override
    protected Matrix calculate(Matrix xK) {
        Matrix m1 = A.multiply(xK);
        Matrix m2 = A.multiply(xK.add(m1.multiply(T).multiply(0.5)));
        Matrix m3 = A.multiply(xK.add(m2.multiply(T).multiply(0.5)));
        Matrix m4 = A.multiply(xK.add(m3.multiply(T)));

        return xK.add(m1.add(m2.multiply(2)).add(m3.multiply(2)).add(m4).multiply(T / 6));
    }
}
