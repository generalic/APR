package hr.fer.zemris.apr.lab2;

import hr.fer.zemris.apr.lab2.functions.FunctionGoldenSection;
import hr.fer.zemris.apr.lab2.functions.IFunction;
import hr.fer.zemris.apr.util.Pair;

import java.util.stream.IntStream;

import static hr.fer.zemris.apr.lab2.opt.Optimisation.goldenSectionSearch;
import static hr.fer.zemris.apr.lab2.opt.Optimisation.hookeJeeves;
import static hr.fer.zemris.apr.lab2.opt.Optimisation.simplex;
import static hr.fer.zemris.apr.lab2.opt.Optimisation.unimodalInterval;

/**
 * Created by generalic on 06/11/16.
 */
public class Task1 {

    public static void main(String[] args) {
        IntStream.range(-20, 20)
                 .filter(i -> i % 5 == 0)
                 .forEach(Task1::run);
    }

    private static void run(int i) {
        IFunction f = new FunctionGoldenSection(new double[]{i});

        System.out.println("Golden section starting from point " + i + ".");
        Pair<Double, Double> interval = unimodalInterval(f, f.getInitPoint(), 1);
        System.out.println(
                "\t Found minimum in " +
                goldenSectionSearch(f, interval.getLeft(), interval.getRight()) +
                " in " + f.getCallCounter() + " iterations.\n"
        );

        f.resetCallCounter();

        System.out.println("Nelder-Mead simplex starting from point " + i + ".");
        System.out.println(
                "\t Found minimum in " +
                        simplex(f, f.getInitPoint()) +
                        " in " + f.getCallCounter() + " iterations.\n"
        );

        f.resetCallCounter();

        System.out.println("Hooke-Jeeves starting from point " + i + ".");
        System.out.println(
                "\t Found minimum in " +
                        hookeJeeves(f, f.getInitPoint()) +
                        " in " + f.getCallCounter() + " iterations.\n"
        );

        System.out.println("###################################################\n");
    }

}
