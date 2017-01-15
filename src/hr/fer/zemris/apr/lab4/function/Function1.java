package hr.fer.zemris.apr.lab4.function;

/**
 * Created by generalic on 06/11/16.
 */
public class Function1 extends AbstractFunction {

    public Function1(double[] initPoint) {
        super(initPoint);
    }

    @Override
    protected double evaluateFunction(double[] point) {
        double[] vector = point;

        double x = vector[0];
        double y = vector[1];

        return 100 * Math.pow((y - Math.pow(x, 2)), 2) + Math.pow(1 - x, 2);
    }

}
