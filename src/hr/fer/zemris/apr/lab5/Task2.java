package hr.fer.zemris.apr.lab5;

import hr.fer.zemris.apr.lab1.matrix.Matrix;
import hr.fer.zemris.apr.lab5.integrator.IIntegrator;
import hr.fer.zemris.apr.lab5.integrator.TrapeseIntegrator;

/**
 * Created by generalic on 15/01/17.
 */
public class Task2 {
    public static void main(String[] args) {
        double[][] data = new double[][]{
                {0, 1},
                {-200, -102}
        };
        Matrix A = new Matrix(data);

        double[][] data2 = new double[][]{
                {1},
                {-2}
        };
        Matrix x = new Matrix(data2);

        IIntegrator integrator = new TrapeseIntegrator(A, x, 0.1, 10);
//        IIntegrator integrator = new RungeKuttaIntegrator(A, x, 0.1, 10);

        integrator.integrate();
    }
}
