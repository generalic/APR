package hr.fer.zemris.apr.lab2;

import hr.fer.zemris.apr.lab1.matrix.Matrix;
import hr.fer.zemris.apr.lab2.functions.Function6;
import hr.fer.zemris.apr.lab2.functions.IFunction;

import java.util.concurrent.ThreadLocalRandom;

import static hr.fer.zemris.apr.lab2.opt.Optimisation.hookeJeeves;

/**
 * Created by generalic on 06/11/16.
 */
public class Task5 {

    public static void main(String[] args) {
        run();
    }

    private static void run() {
        int foundSolutions = 0;
        for(int i = 0; i < 5000; i++) {
            int x = ThreadLocalRandom.current().nextInt(-50, 50 + 1);
            int y = ThreadLocalRandom.current().nextInt(-50, 50 + 1);

            IFunction f = new Function6(new double[]{x, y});
            Matrix solution = hookeJeeves(f, f.getInitPoint());

            if (f.evaluate(solution) <= 1e-4) {
                foundSolutions++;
            }

            f.resetCallCounter();
        }

        System.out.println(foundSolutions);
    }

}
