package hr.fer.zemris.apr.lab3;

import hr.fer.zemris.apr.lab1.matrix.Matrix;
import hr.fer.zemris.apr.lab3.functions.Function1;
import hr.fer.zemris.apr.lab3.functions.Function2;
import hr.fer.zemris.apr.lab3.functions.IFunction;
import hr.fer.zemris.apr.lab3.opt.Optimisation;

/**
 * Created by generalic on 06/11/16.
 */
@SuppressWarnings("Duplicates")
public class Task2 {

    public static void main(String[] args) {
        run();
    }

    private static void run() {
        Matrix initPoint1 = new Matrix(new double[]{-1.9, 2});
        IFunction f1 = new Function1(initPoint1.getVector());

        Matrix initPoint2 = new Matrix(new double[]{0.1, 0.3});
        IFunction f2 = new Function2(initPoint2.getVector());

        System.out.println(
                "Gradient descent starting from point " + initPoint1 +
                " for " + f1.getClass().getSimpleName() + " with shift optimization."
        );
        System.out.println(
                "\t Found minimum in " +
                        Optimisation.gradientDescent(f1, true) +
                        " in " + f1.getEvaluateCounter() + " iterations and " +
                        f1.getGradientCounter() + " gradient calculations.\n"
        );
        f1.resetCounters();

        System.out.println("###################################################\n");

        System.out.println(
                "Newton-Raphson starting from point " + initPoint1 +
                        " for " + f1.getClass().getSimpleName() + " with shift optimization."
        );
        System.out.println(
                "\t Found minimum in " +
                        Optimisation.newtonRaphson(f1, true) +
                        " in " + f1.getEvaluateCounter() + " iterations and " +
                        f1.getGradientCounter() + " gradient calculations and " +
                        f1.getHessianCounter() + " hessian calculations.\n"
        );
        f1.resetCounters();

        System.out.println("###################################################\n");

        System.out.println(
                "Gradient descent starting from point " + initPoint2 +
                        " for " + f2.getClass().getSimpleName() + " with shift optimization."
        );
        System.out.println(
                "\t Found minimum in " +
                        Optimisation.gradientDescent(f2, true) +
                        " in " + f2.getEvaluateCounter() + " iterations and " +
                        f2.getGradientCounter() + " gradient calculations.\n"
        );
        f2.resetCounters();

        System.out.println("###################################################\n");

        System.out.println(
                "Newton-Raphson starting from point " + initPoint2 +
                        " for " + f2.getClass().getSimpleName() + " with shift optimization."
        );
        System.out.println(
                "\t Found minimum in " +
                        Optimisation.newtonRaphson(f2, true) +
                        " in " + f2.getEvaluateCounter() + " iterations and " +
                        f2.getGradientCounter() + " gradient calculations and " +
                        f2.getHessianCounter() + " hessian calculations.\n"
        );
        f2.resetCounters();
    }

}
