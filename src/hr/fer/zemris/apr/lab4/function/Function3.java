package hr.fer.zemris.apr.lab4.function;

import java.util.stream.IntStream;

/**
 * Created by generalic on 06/11/16.
 */
public class Function3 extends AbstractFunction {

    public Function3(double[] initPoint) {
        super(initPoint);
    }

    @Override
    public double evaluateFunction(double[] point) {
        double[] vector = point;

        return IntStream.range(0, vector.length)
                        .mapToDouble(i -> Math.pow(vector[i] - i, 2))
                        .sum();
    }
}
