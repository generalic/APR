package hr.fer.zemris.apr.lab2.functions;

import hr.fer.zemris.apr.lab1.matrix.Matrix;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by generalic on 06/11/16.
 */
public abstract class AbstractFunction implements IFunction {

    private Matrix initPoint;
    private Map<String, Double> values;
    private int counter;

    public AbstractFunction(double[] initPoint) {
        this.initPoint = new Matrix(initPoint);
        this.counter = 0;
        this.values = new HashMap<>();
    }

    @Override
    public final double evaluate(Matrix point) {
        counter++;

        Double value = values.get(point.toString());
        if (Objects.isNull(value)) {
            value = evaluateFunction(point);
            values.put(point.toString(), value);
        }

        return evaluateFunction(point);
    }

    @Override
    public Matrix getInitPoint() {
        return initPoint;
    }

    protected abstract double evaluateFunction(Matrix point);

    @Override
    public int getCallCounter() {
        return counter;
    }

    @Override
    public void resetCallCounter() {
        this.counter = 0;
    }
}
