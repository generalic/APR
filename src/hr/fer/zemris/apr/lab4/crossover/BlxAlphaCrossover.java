package hr.fer.zemris.apr.lab4.crossover;

import hr.fer.zemris.apr.lab4.solution.DoubleArraySolution;
import hr.fer.zemris.apr.lab4.util.RandomProvider;

import java.util.Random;

/**
 * Created by generalic on 17/12/16.
 */
public class BlxAlphaCrossover implements ICrossover<DoubleArraySolution> {

    private double alpha ;

    private Random rand = RandomProvider.get();

    public BlxAlphaCrossover(double alpha) {
        this.alpha = alpha;
    }

    /**
     * Operacija krizanja roditelja metodom <i>BLX-<code>&#945</code></i>.
     *
     * @param firstParent
     * @param secondParent
     * @return
     */
    @Override
    public DoubleArraySolution doCrossover(DoubleArraySolution firstParent,
            DoubleArraySolution secondParent) {
        double[] p1 = firstParent.getPoints();
        double[] p2 = secondParent.getPoints();
        int n = p1.length;

        DoubleArraySolution child = new DoubleArraySolution(n);
        for(int i = 0; i < n; i++) {
            double cMin = Math.min(p1[i], p2[i]);
            double cMax = Math.max(p1[i], p2[i]);

            double diff = (cMax - cMin) * alpha;

            double min = cMin - diff;
            double max = cMax + diff;

            child.points[i] = rand.nextDouble() * (max - min) + min;
        }

        return child;
    }

}
