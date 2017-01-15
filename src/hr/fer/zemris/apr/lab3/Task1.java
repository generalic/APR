package hr.fer.zemris.apr.lab3;

import hr.fer.zemris.apr.lab1.matrix.Matrix;
import hr.fer.zemris.apr.lab3.functions.Function3;
import hr.fer.zemris.apr.lab3.functions.IFunction;
import hr.fer.zemris.apr.lab3.opt.Optimisation;

/**
 * Created by generalic on 06/11/16.
 */
public class Task1 {

    public static void main(String[] args) {
        run();
    }

    private static void run() {
        Matrix initPoint = new Matrix(new double[]{0, 0});
        IFunction f = new Function3(initPoint.getVector());

        System.out.println(
                "Gradient descent starting from point " + initPoint +
                " for " + f.getClass().getSimpleName() + " without shift optimization."
        );
        System.out.println(
                "\t Found minimum in " +
                        Optimisation.gradientDescent(f, false) +
                        " in " + f.getEvaluateCounter() + " iterations and " +
                        f.getGradientCounter() + " gradient calculations.\n"
        );
        f.resetCounters();

        System.out.println(
                "Gradient descent starting from point " + initPoint +
                " for " + f.getClass().getSimpleName() + " with shift optimization."
        );
        System.out.println(
                "\t Found minimum in " +
                        Optimisation.gradientDescent(f, true) +
                        " in " + f.getEvaluateCounter() + " iterations and " +
                        f.getGradientCounter() + " gradient calculations.\n"
        );
        f.resetCounters();
    }

}
