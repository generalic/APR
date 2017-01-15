package hr.fer.zemris.apr.lab4.function;

import java.util.Arrays;

/**
 * Created by generalic on 21/11/16.
 */
public class Function7 extends AbstractFunction {

    public Function7(double[] initPoint) {
        super(initPoint);
    }

    @Override
    protected double evaluateFunction(double[] point) {
        double[] vector = point;

        double sum = Arrays.stream(vector)
                           .map(x -> x * x)
                           .sum();

        double a = Math.pow(sum, 0.25);
        double b = 1 + Math.pow(Math.sin(50 * Math.pow(sum, 0.1)), 2);

        return a * b;
    }

}
