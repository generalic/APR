package hr.fer.zemris.apr.lab3;

import hr.fer.zemris.apr.lab1.matrix.Matrix;
import hr.fer.zemris.apr.lab3.constraints.Constraint5;
import hr.fer.zemris.apr.lab3.constraints.Constraint6;
import hr.fer.zemris.apr.lab3.constraints.IConstraint;
import hr.fer.zemris.apr.lab3.functions.Function4;
import hr.fer.zemris.apr.lab3.functions.IFunction;
import hr.fer.zemris.apr.lab3.opt.Optimisation;
import hr.fer.zemris.apr.util.Pair;

import java.util.Arrays;
import java.util.List;

/**
 * Created by generalic on 06/11/16.
 */
@SuppressWarnings("Duplicates")
public class Task5 {

    public static void main(String[] args) {
        run();
    }

    private static void run() {
        Matrix initPoint4 = new Matrix(new double[]{0, 0});
        IFunction f4 = new Function4(initPoint4.getVector());

        List<IConstraint> constraints = Arrays.asList(
                new Constraint5(),
                new Constraint6()
        );

        findMinimum(initPoint4, f4, constraints);

        initPoint4 = new Matrix(new double[]{5, 5});

        Matrix innerPoint = Optimisation.innerPointSearch(initPoint4, 1.0, constraints);
        System.out.println(innerPoint);

        IFunction newF4 = new Function4(innerPoint.getVector());
        findMinimum(innerPoint, newF4, constraints);
    }

    private static void findMinimum(Matrix initPoint4, IFunction f4,
            List<IConstraint> constraints) {
        System.out.println(
                "Transforming function " + f4.getClass().getSimpleName() +
                " from point " + initPoint4 +
                " and minimizing using Hooke-Jeeves algorithm."
        );
        Pair<Matrix, Integer> results1 = Optimisation.transform(f4, constraints, 1, Task5::calc);
        System.out.println(
                "\t Found minimum " +
                results1.getLeft() +
                " in " + results1.getRight() + " iterations.\n"
        );
        f4.resetCounters();

        System.out.println("###################################################\n");
    }

    private static double calc(double t, Matrix x) {
        double[] vector = x.getVector();
        double y = vector[1];

        double a = y - 1;

        return t * a * a;
    }

}
