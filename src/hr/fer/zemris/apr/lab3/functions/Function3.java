package hr.fer.zemris.apr.lab3.functions;

import hr.fer.zemris.apr.lab1.matrix.Matrix;

/**
 * Created by generalic on 06/11/16.
 */
public class Function3 extends AbstractFunction {


    public Function3(double[] initPoint) {
        super(initPoint);
    }

    @Override
    public Matrix evaluateFunction(Matrix point) {
        double[] vector = point.getVector();

        double x = vector[0];
        double y = vector[1];

        double a = x - 2;
        double b = y + 3;

        double result = a * a + b * b;

        return new Matrix(new double[]{result});
    }

    @Override
    protected Matrix gradientFunction(Matrix point) {
        double[] vector = point.getVector();

        double x = vector[0];
        double y = vector[1];

        double d1 = 2 * (x - 2);
        double d2 = 2 * (y + 3);
        return new Matrix(new double[]{d1, d2});
    }

    @Override
    protected Matrix hessianFunction(Matrix point) {
        double[] vector = point.getVector();

        double x = vector[0];
        double y = vector[1];

        double d11 = 2;
        double d12 = 0;
        double d21 = 0;
        double d22 = 2;

        double[][] data = new double[][]{
                {d11, d12},
                {d21, d22}
        };
        Matrix hessian = new Matrix(data);
        return hessian.inverse();
    }
}
