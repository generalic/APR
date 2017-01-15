package hr.fer.zemris.apr.lab2.functions;

import hr.fer.zemris.apr.lab1.matrix.Matrix;

/**
 * Created by generalic on 06/11/16.
 */
public class Function4 extends AbstractFunction {

    public Function4(double[] initPoint) {
        super(initPoint);
    }

    @Override
    public double evaluateFunction(Matrix point) {
        double[] vector = point.getVector();

        double x = vector[0];
        double y = vector[1];

        double a = x * x - y * y;
        double b = x * x + y * y;

        return Math.abs(a) + Math.sqrt(b);
    }

}
