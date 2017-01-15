package hr.fer.zemris.apr.lab3.functions;

import hr.fer.zemris.apr.lab1.matrix.Matrix;

/**
 * Created by generalic on 06/11/16.
 */
public interface IFunction {

    Matrix evaluate(Matrix point);

    Matrix gradient(Matrix point);

    Matrix hessian(Matrix point);

    Matrix getInitPoint();

    int getEvaluateCounter();

    int getGradientCounter();

    int getHessianCounter();

    void resetCounters();

}
