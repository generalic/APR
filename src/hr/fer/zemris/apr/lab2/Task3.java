package hr.fer.zemris.apr.lab2;

import hr.fer.zemris.apr.lab2.functions.Function4;
import hr.fer.zemris.apr.lab2.functions.IFunction;

import static hr.fer.zemris.apr.lab2.opt.Optimisation.hookeJeeves;
import static hr.fer.zemris.apr.lab2.opt.Optimisation.simplex;

/**
 * Created by generalic on 06/11/16.
 */
public class Task3 {

    public static void main(String[] args) {
        run();
    }

    private static void run() {
        IFunction f = new Function4(new double[]{5, 5});

        System.out.println(
                "Nelder-Mead simplex starting from point " + f.getInitPoint() +
                " for Function" + 4 +  "."
        );
        System.out.println(
                "\t==> Found minimum in " +
                        simplex(f, f.getInitPoint()) +
                        " in " + f.getCallCounter() + " iterations.\n"
        );
        f.resetCallCounter();
        System.out.println("###################################################\n");

        System.out.println(
                "Hooke-Jeeves starting from point " + f.getInitPoint() +
                        " for Function" + 4 +  "."
        );
        System.out.println(
                "\t==> Found minimum in " +
                        hookeJeeves(f, f.getInitPoint()) +
                        " in " + f.getCallCounter() + " iterations.\n"
        );
        f.resetCallCounter();
        System.out.println("###################################################\n");
    }

}
