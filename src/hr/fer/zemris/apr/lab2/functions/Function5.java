package hr.fer.zemris.apr.lab2.functions;

import hr.fer.zemris.apr.lab1.matrix.Matrix;

/**
 * Created by generalic on 21/11/16.
 */
public class Function5 extends AbstractFunction {

    public Function5(double[] initPoint) {
        super(initPoint);
    }

    @Override
    protected double evaluateFunction(Matrix point) {
        double[] vector = point.getVector();

        double x = vector[0];
        double y = vector[1];

        double a = x * x;
        double b = 2 * y * y;

        return a + b;

    }

}
