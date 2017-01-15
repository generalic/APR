package hr.fer.zemris.apr.lab4.crossover;

import hr.fer.zemris.apr.lab4.function.IFunction;
import hr.fer.zemris.apr.lab4.solution.BitVectorSolution;

/**
 * Razred koji predstavlja križanje u dvije točke.
 * @author Boris Generalic
 */
public class TwoPointCrossover extends AbstractPointCrossover {

    public TwoPointCrossover(IFunction function) {
        super(function);
    }

    @Override
    protected void exchangeGeneticMaterial(BitVectorSolution firstChild,
            BitVectorSolution firstParent, BitVectorSolution secondChild,
            BitVectorSolution secondParent, int n) {

        int firstCrossPoint = rand.nextInt(n);
        int secondCrossPoint = rand.nextInt(n);

        while (firstCrossPoint > secondCrossPoint) {
            secondCrossPoint = rand.nextInt(n);
        }

        setGeneticMaterial(0, firstCrossPoint, firstChild, firstParent, secondChild, secondParent);
        setGeneticMaterial(firstCrossPoint, secondCrossPoint, firstChild, secondParent, secondChild, firstParent);
        setGeneticMaterial(secondCrossPoint, n, firstChild, firstParent, secondChild, secondParent);
    }

}
