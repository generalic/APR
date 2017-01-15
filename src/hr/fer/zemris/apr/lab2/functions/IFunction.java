package hr.fer.zemris.apr.lab2.functions;

import hr.fer.zemris.apr.lab1.matrix.Matrix;

/**
 * Created by generalic on 06/11/16.
 */
public interface IFunction {

    double evaluate(Matrix point);

    Matrix getInitPoint();

    int getCallCounter();

    void resetCallCounter();

}
