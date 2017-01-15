package hr.fer.zemris.apr.lab2.functions;

import hr.fer.zemris.apr.lab1.matrix.Matrix;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by generalic on 06/11/16.
 */
public class Function1 extends AbstractFunction {

    public Function1(double[] initPoint) {
        super(initPoint);
    }

    @Override
    protected double evaluateFunction(Matrix point) {
        double[] vector = point.getVector();

        double x = vector[0];
        double y = vector[1];

        return 100 * (y - x * x) * (y - x * x) + (1 - x) * (1 - x);
    }

    public static void main(String[] args) {
        List<Integer> list = IntStream.range(0, 5).boxed().collect(Collectors.toList());
        list.set(1, 55);

        list.forEach(System.out::println);
    }

}
