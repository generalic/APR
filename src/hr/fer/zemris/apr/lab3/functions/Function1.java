package hr.fer.zemris.apr.lab3.functions;

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

    public static void main(String[] args) {
        List<Integer> list = IntStream.range(0, 5).boxed().collect(Collectors.toList());
        list.set(1, 55);

        list.forEach(System.out::println);
    }

    @Override
    protected Matrix evaluateFunction(Matrix point) {
        double[] vector = point.getVector();

        double x = vector[0];
        double y = vector[1];

        double result = 100 * (y - x * x) * (y - x * x) + (1 - x) * (1 - x);
        return new Matrix(new double[]{result});
    }

    @Override
    protected Matrix gradientFunction(Matrix point) {
        double[] vector = point.getVector();

        double x = vector[0];
        double y = vector[1];

        double d1 = 400 * x * (x * x - y) + 2 * (x - 1);
        double d2 = 200 * (y - x * x);
        return new Matrix(new double[]{d1, d2});
    }

    @Override
    protected Matrix hessianFunction(Matrix point) {
        double[] vector = point.getVector();

        double x = vector[0];
        double y = vector[1];

        double d11 = 400 * (3 * x * x - y) + 2;
        double d12 = -400 * x;
        double d21 = -400 * x;
        double d22 = 200;

        double[][] data = new double[][]{
                {d11, d12},
                {d21, d22}
        };
        Matrix hessian = new Matrix(data);
        return hessian.inverse();
    }

}
