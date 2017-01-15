package hr.fer.zemris.apr.lab2.functions;

import hr.fer.zemris.apr.lab1.matrix.Matrix;

import java.util.List;

/**
 * Created by generalic on 06/11/16.
 */
public class Function2 extends AbstractFunction {


    public Function2(double[] initPoint) {
        super(initPoint);
    }

    @Override
    public double evaluateFunction(Matrix point) {
        double[] vector = point.getVector();

        double x = vector[0];
        double y = vector[1];

        double a = (x - 4) * (x - 4);
        double b = (y - 2) * (y - 2);

        return a + 4 * b;
    }

}
