package hr.fer.zemris.apr.lab3;

import hr.fer.zemris.apr.lab1.matrix.Matrix;
import hr.fer.zemris.apr.lab3.constraints.Constraint1;
import hr.fer.zemris.apr.lab3.constraints.Constraint2;
import hr.fer.zemris.apr.lab3.constraints.Constraint31;
import hr.fer.zemris.apr.lab3.constraints.Constraint32;
import hr.fer.zemris.apr.lab3.constraints.Constraint41;
import hr.fer.zemris.apr.lab3.constraints.Constraint42;
import hr.fer.zemris.apr.lab3.constraints.IConstraint;
import hr.fer.zemris.apr.lab3.functions.Function1;
import hr.fer.zemris.apr.lab3.functions.Function2;
import hr.fer.zemris.apr.lab3.functions.IFunction;
import hr.fer.zemris.apr.lab3.opt.Optimisation;

import java.util.Arrays;
import java.util.List;

/**
 * Created by generalic on 06/11/16.
 */
@SuppressWarnings("Duplicates")
public class Task3 {

    public static void main(String[] args) {
        run();
    }

    private static void run() {
        Matrix initPoint1 = new Matrix(new double[]{-1.9, 2});
        IFunction f1 = new Function1(initPoint1.getVector());

        Matrix initPoint2 = new Matrix(new double[]{0.1, 0.3});
        IFunction f2 = new Function2(initPoint2.getVector());

        List<IConstraint> constraints = Arrays.asList(
                new Constraint1(),
                new Constraint2(),
                new Constraint31(),
                new Constraint32(),
                new Constraint41(),
                new Constraint42()
        );

        System.out.println(
                "Box algorithm starting from point " + initPoint1 +
                " for " + f1.getClass().getSimpleName() + "."
        );
        System.out.println(
                "\t Found minimum " +
                Optimisation.box(f1, constraints) +
                " in " + f1.getEvaluateCounter() + " iterations.\n"
        );
        f1.resetCounters();

        System.out.println("###################################################\n");

        System.out.println(
                "Box algorithm starting from point " + initPoint2 +
                " for " + f2.getClass().getSimpleName() + "."
        );
        System.out.println(
                "\t Found minimum " +
                Optimisation.box(f2, constraints) +
                " in " + f2.getEvaluateCounter() + " iterations.\n"
        );
        f2.resetCounters();

    }

}
