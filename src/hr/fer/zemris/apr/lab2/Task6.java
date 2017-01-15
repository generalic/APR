package hr.fer.zemris.apr.lab2;

import hr.fer.zemris.apr.lab2.functions.Function5;
import hr.fer.zemris.apr.lab2.functions.IFunction;

import java.util.Arrays;
import java.util.List;

import static hr.fer.zemris.apr.lab2.opt.Optimisation.coordinateDescent;
import static hr.fer.zemris.apr.lab2.opt.Optimisation.hookeJeeves;
import static hr.fer.zemris.apr.lab2.opt.Optimisation.simplex;

/**
 * Created by generalic on 06/11/16.
 */
@SuppressWarnings("Duplicates")
public class Task6 {

    public static void main(String[] args) {
        run();
    }

    private static void run() {
        List<IFunction> functions = Arrays.asList(
                new Function5(new double[]{3.0, 3.0})
        );

        for (int i = 0, n = functions.size(); i < n; i++) {
            IFunction f = functions.get(i);
            System.out.println(
                    "Nelder-Mead simplex starting from point " + f.getInitPoint() +
                    " for Function" + (i + 1) +  "."
            );
            System.out.println(
                    "\t==> Found minimum in " +
                            simplex(f, f.getInitPoint()) +
                            " in " + f.getCallCounter() + " iterations.\n"
            );
            f.resetCallCounter();
        }
        System.out.println("###################################################\n");

        for (int i = 0, n = functions.size(); i < n; i++) {
            IFunction f = functions.get(i);
            System.out.println(
                    "Hooke-Jeeves starting from point " + f.getInitPoint() +
                            " for function" + (i + 1) +  "."
            );
            System.out.println(
                    "\t==> Found minimum in " +
                            hookeJeeves(f, f.getInitPoint()) +
                            " in " + f.getCallCounter() + " iterations.\n"
            );
            f.resetCallCounter();
        }
        System.out.println("###################################################\n");

        for (int i = 0, n = functions.size(); i < n; i++) {
            IFunction f = functions.get(i);
            System.out.println(
                    "Coordinate descent starting from point " + f.getInitPoint() +
                            " for function" + (i + 1) +  "."
            );
            System.out.println(
                    "\t==> Found minimum in " +
                            coordinateDescent(f, f.getInitPoint()) +
                            " in " + f.getCallCounter() + " iterations.\n"
            );
            f.resetCallCounter();
        }
        System.out.println("###################################################\n");
    }

}
