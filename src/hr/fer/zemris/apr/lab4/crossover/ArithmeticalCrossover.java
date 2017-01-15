package hr.fer.zemris.apr.lab4.crossover;

import hr.fer.zemris.apr.lab4.solution.DoubleArraySolution;

/**
 * Created by generalic on 17/12/16.
 */
public class ArithmeticalCrossover implements ICrossover<DoubleArraySolution> {

    private double lambda;

    public ArithmeticalCrossover(double lambda) {
        this.lambda = lambda;
    }

    /**
     * Operacija krizanja roditelja metodom <i>BLX-<code>&#945</code></i>.
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
        for (int i = 0; i < n; i++) {
            child.points[i] = lambda * p1[i] + (1 - lambda) * p2[i];
        }

        return child;
    }

}
