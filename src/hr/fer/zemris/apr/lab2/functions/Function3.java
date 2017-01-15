package hr.fer.zemris.apr.lab2.functions;

import hr.fer.zemris.apr.lab1.matrix.Matrix;

import java.util.List;
import java.util.stream.IntStream;

/**
 * Created by generalic on 06/11/16.
 */
public class Function3 extends AbstractFunction {


    public Function3(double[] initPoint) {
        super(initPoint);
    }

    @Override
    public double evaluateFunction(Matrix point) {
        double[] vector = point.getVector();

        return IntStream.range(0, vector.length)
                 .mapToDouble(i -> Math.pow(vector[i] - i, 2))
                 .sum();
    }

}
