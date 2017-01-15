package hr.fer.zemris.apr.lab2.functions;

import hr.fer.zemris.apr.lab1.matrix.Matrix;

import java.util.Arrays;

/**
 * Created by generalic on 06/11/16.
 */
public class FunctionGoldenSection extends AbstractFunction {

    public FunctionGoldenSection(double[] initPoint) {
        super(initPoint);
    }

    @Override
    protected double evaluateFunction(Matrix point) {
        return Arrays.stream(point.getVector())
                     .map(x -> Math.pow(x - 3, 2))
                     .sum();
    }
}
