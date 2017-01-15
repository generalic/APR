package hr.fer.zemris.apr.lab4.function;

/**
 * Created by generalic on 06/11/16.
 */
public interface IFunction {

    double evaluate(double[] point);

    double[] getInitPoint();

    int getCallCounter();

    void resetCallCounter();

}
