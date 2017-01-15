package hr.fer.zemris.apr.lab4.mutation;

import hr.fer.zemris.apr.lab4.solution.BitVectorSolution;
import hr.fer.zemris.apr.lab4.util.RandomProvider;

import java.util.Random;

public class FlipMutation implements IMutation<BitVectorSolution> {

    private double mutationProbability;
    private Random rand = RandomProvider.get();

    public FlipMutation(double mutationProbability) {
        this.mutationProbability = mutationProbability;
    }

    @Override
    public void mutate(BitVectorSolution child) {
        int[] bits = child.bits;
        for (int i = 0; i < bits.length; i++) {
            if (rand.nextDouble() <= mutationProbability) {
                bits[i] ^= 1;
            }
        }
        child.updatePoints();
    }

}
