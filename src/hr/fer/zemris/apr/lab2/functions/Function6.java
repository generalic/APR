package hr.fer.zemris.apr.lab2.functions;

import hr.fer.zemris.apr.lab1.matrix.Matrix;

import javax.transaction.InvalidTransactionException;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Created by generalic on 06/11/16.
 */
public class Function6 extends AbstractFunction {

    public Function6(double[] inital) {
        super(inital);
    }

    @Override
    public double evaluateFunction(Matrix point) {
        double[] vector = point.getVector();

        double sum = IntStream.range(0, vector.length)
                 .mapToDouble(i -> Math.pow(vector[i], 2))
                 .sum();

        double a = Math.pow(Math.sin(Math.sqrt(sum)), 2) - 0.5;
        double b = Math.pow(1 + 0.001 * sum, 2);

        return 0.5 + a / b;
    }

}
