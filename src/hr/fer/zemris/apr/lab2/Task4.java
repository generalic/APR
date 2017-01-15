package hr.fer.zemris.apr.lab2;

import hr.fer.zemris.apr.lab2.functions.Function4;
import hr.fer.zemris.apr.lab2.functions.IFunction;

import java.util.stream.IntStream;

import static hr.fer.zemris.apr.lab2.opt.Optimisation.simplex;

/**
 * Created by generalic on 06/11/16.
 */
public class Task4 {

    public static void main(String[] args) {
        run();
    }

    private static void run() {
        IFunction f = new Function4(new double[]{0.5, 0.5});

        IntStream.range(0, 20).forEach(i -> {
            System.out.println(
                    "Nelder-Mead simplex starting from point " + f.getInitPoint() +
                            " for Function" + 4 +  " with moving step " + (i + 1) + "."
            );
            System.out.println(
                    "\t==> Found minimum in " +
                            simplex(f, f.getInitPoint(), i + 1) +
                            " in " + f.getCallCounter() + " iterations.\n"
            );
            f.resetCallCounter();
        });
        System.out.println("###################################################\n");

        IFunction f1 = new Function4(new double[]{20, 20});
        IntStream.range(0, 20).forEach(i -> {
            System.out.println(
                    "Nelder-Mead simplex starting from point " + f1.getInitPoint() +
                            " for Function" + 4 +  " with moving step " + (i + 1) + "."
            );
            System.out.println(
                    "\t==> Found minimum in " +
                            simplex(f1, f1.getInitPoint(), i + 1) +
                            " in " + f1.getCallCounter() + " iterations.\n"
            );
            f1.resetCallCounter();
        });
        System.out.println("###################################################\n");
    }

}
