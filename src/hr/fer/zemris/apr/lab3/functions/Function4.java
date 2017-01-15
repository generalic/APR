package hr.fer.zemris.apr.lab3.functions;

import hr.fer.zemris.apr.lab1.matrix.Matrix;

/**
 * Created by generalic on 06/11/16.
 */
public class Function4 extends AbstractFunction {

    public Function4(double[] initPoint) {
        super(initPoint);
    }

    @Override
    public Matrix evaluateFunction(Matrix point) {
        double[] vector = point.getVector();

        double x = vector[0];
        double y = vector[1];

        double a = x - 3;
        double b = y;

        double result = a * a + b * b;

        return new Matrix(new double[]{result});
    }

    @Override
    protected Matrix gradientFunction(Matrix point) {
        double[] vector = point.getVector();

        double x = vector[0];
        double y = vector[1];

        double d1 = 2 * (x - 3);
        double d2 = 2 * y;
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
