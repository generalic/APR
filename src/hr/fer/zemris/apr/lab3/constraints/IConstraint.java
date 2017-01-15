package hr.fer.zemris.apr.lab3.constraints;

import hr.fer.zemris.apr.lab1.matrix.Matrix;

/**
 * Created by generalic on 08/12/16.
 */
public interface IConstraint {

    double evaluate(Matrix point);

}
