package hr.fer.zemris.apr.lab4.mutation;

import hr.fer.zemris.apr.lab4.solution.DoubleArraySolution;
import hr.fer.zemris.apr.lab4.util.RandomProvider;

import java.util.Random;

public class NormalDistributionMutation implements IMutation<DoubleArraySolution> {

    private double mutationProbability;
    private double sigma;
    private Random rand = RandomProvider.get();

    public NormalDistributionMutation(double mutationProbability, double sigma) {
        this.mutationProbability = mutationProbability;
        this.sigma = sigma;
    }

    @Override
    public void mutate(DoubleArraySolution child) {
        double[] points = child.getPoints();

        for (int i = 0; i < points.length; i++) {
            if (rand.nextDouble() <= mutationProbability) {
                points[i] += rand.nextGaussian() * sigma;
            }
        }
    }

}
