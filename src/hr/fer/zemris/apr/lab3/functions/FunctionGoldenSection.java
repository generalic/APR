package hr.fer.zemris.apr.lab3.functions;

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
    protected Matrix evaluateFunction(Matrix point) {
        double result = Arrays.stream(point.getVector())
                     .map(x -> Math.pow(x - 3, 2))
                     .sum();

        return new Matrix(new double[]{result});
    }

    @Override
    protected Matrix gradientFunction(Matrix point) {
        return null;
    }

    @Override
    protected Matrix hessianFunction(Matrix point) {
        return null;
    }
}
