package hr.fer.zemris.apr.lab3.functions;

import hr.fer.zemris.apr.lab1.matrix.Matrix;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

/**
 * Created by generalic on 06/11/16.
 */
public abstract class AbstractFunction implements IFunction {

    private Matrix initPoint;
    private Map<String, Matrix> evaluateValues;
    private Map<String, Matrix> gradientValues;
    private Map<String, Matrix> hessianValues;
    private int evaluateCounter;
    private int gradientCounter;
    private int hessianCounter;

    public AbstractFunction(double[] initPoint) {
        this.initPoint = new Matrix(initPoint);
        this.evaluateCounter = 0;
        this.gradientCounter = 0;
        this.hessianCounter = 0;
        this.evaluateValues = new HashMap<>();
        this.gradientValues = new HashMap<>();
        this.hessianValues = new HashMap<>();
    }

    @Override
    public final Matrix evaluate(Matrix point) {
        return strategyEvaluate(
                () -> evaluateCounter++,
                evaluateValues,
                this::evaluateFunction,
                point
        );
    }

    @Override
    public Matrix gradient(Matrix point) {
        return strategyEvaluate(
                () -> gradientCounter++,
                gradientValues,
                this::gradientFunction,
                point
        );
    }

    @Override
    public Matrix hessian(Matrix point) {
        return strategyEvaluate(
                () -> hessianCounter++,
                hessianValues,
                this::hessianFunction,
                point
        );
    }

    @Override
    public Matrix getInitPoint() {
        return initPoint;
    }

    @Override
    public int getEvaluateCounter() {
        return evaluateCounter;
    }

    @Override
    public int getGradientCounter() {
        return gradientCounter;
    }

    @Override
    public int getHessianCounter() {
        return hessianCounter;
    }

    @Override
    public void resetCounters() {
        this.evaluateCounter = 0;
        this.gradientCounter = 0;
        this.hessianCounter = 0;
    }

    protected abstract Matrix evaluateFunction(Matrix point);

    protected abstract Matrix gradientFunction(Matrix point);

    protected abstract Matrix hessianFunction(Matrix point);

    private Matrix strategyEvaluate(Runnable increment, Map<String, Matrix> values,
            Function<Matrix, Matrix> function,
            Matrix point
    ) {
        increment.run();

        Matrix value = values.get(point.toString());
        if (Objects.isNull(value)) {
            value = function.apply(point);
            values.put(point.toString(), value);
        }

        return value;
    }
}
