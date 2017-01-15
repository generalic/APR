package hr.fer.zemris.apr.lab4.crossover;

import hr.fer.zemris.apr.lab4.function.IFunction;
import hr.fer.zemris.apr.lab4.solution.BitVectorSolution;

/**
 * Razred koji predstavlja križanje u jednoj točki.
 *
 * @author Boris Generalic
 *
 */
public class OnePointCrossover extends AbstractPointCrossover {

	public OnePointCrossover(IFunction function) {
		super(function);
	}

	@Override
	protected void exchangeGeneticMaterial(BitVectorSolution firstChild, BitVectorSolution firstParent, BitVectorSolution secondChild,
			BitVectorSolution secondParent, int n) {

		int crossPoint = rand.nextInt(n);

		setGeneticMaterial(0, crossPoint, firstChild, firstParent, secondChild, secondParent);
		setGeneticMaterial(crossPoint, n, firstChild, secondParent, secondChild, firstParent);
	}

}
