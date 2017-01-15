package hr.fer.zemris.apr.lab3.constraints;

import hr.fer.zemris.apr.lab1.matrix.Matrix;

/**
 * Created by generalic on 08/12/16.
 */
public class Constraint41 implements IConstraint {

    @Override
    public double evaluate(Matrix point) {
        double[] vector = point.getVector();

        double x = vector[0];

        return 100 - x;
    }
}
