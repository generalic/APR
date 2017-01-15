package hr.fer.zemris.apr.lab4.crossover;

import hr.fer.zemris.apr.lab4.function.IFunction;
import hr.fer.zemris.apr.lab4.solution.BitVectorSolution;
import hr.fer.zemris.apr.lab4.util.RandomProvider;

import java.util.Random;

/**
 * Apstraktni razred koji nudi generički algoritam kod križanja u
 * n - točaka.
 *
 * @author Boris Generalic
 *
 */
public abstract class AbstractPointCrossover implements ICrossover<BitVectorSolution> {

	private IFunction function;
	protected Random rand = RandomProvider.get();

	public AbstractPointCrossover(IFunction function) {
		super();
		this.function = function;
	}

	/**
	 * Generička metoda u kojoj se provodi križanje u n-točaka.
	 */
	@Override
	public final BitVectorSolution doCrossover(BitVectorSolution firstParent, BitVectorSolution secondParent) {
        int n = firstParent.bits.length;

        BitVectorSolution firstChild = firstParent.duplicate();
        BitVectorSolution secondChild = secondParent.duplicate();

		exchangeGeneticMaterial(firstChild, firstParent, secondChild, secondParent, n);
        firstChild.updatePoints();
        secondChild.updatePoints();

		evaluateChildrenFitness(firstChild, secondChild);

		return firstChild.fitness > secondChild.fitness ? firstChild : secondChild;
	}

	/**
	 * Razmjena genetskog materijala u n-točaka između dva roditelja.
	 * @param firstChild	prvo dijete koje nastane krizanjem
	 * @param firstParent	prvi roditelj
	 * @param secondChild	drugo dijete koje nastane krizanjem
	 * @param secondParent	drugi roditelj
	 * @param n				broj tocaka krizanja
	 */
	protected abstract void exchangeGeneticMaterial(BitVectorSolution firstChild, BitVectorSolution firstParent, BitVectorSolution secondChild,
			BitVectorSolution secondParent, int n);

	/**
	 * Evaluacija fitness-a za nastalu dijecu
	 * @param firstChild	prvo dijete
	 * @param secondChild	drugo dijete
	 */
	protected void evaluateChildrenFitness(BitVectorSolution firstChild, BitVectorSolution secondChild) {
        firstChild.setFitness(function.evaluate(firstChild.getPoints()));
        secondChild.setFitness(function.evaluate(secondChild.getPoints()));
	}

	/**
	 * Postavljanje genetskog materijala.
	 * @param start		od kojeg indexa se počinje
	 * @param end		na kojem indexu završava
	 * @param child1	prvo dijete
	 * @param parent1	prvi roditelj
	 * @param child2	drugo dijete
	 * @param parent2	drugi roditelj
	 */
	protected static void setGeneticMaterial(int start, int end, BitVectorSolution child1, BitVectorSolution parent1, BitVectorSolution child2,
			BitVectorSolution parent2) {
		for (int i = start; i < end; i++) {
            child1.bits[i] = parent1.bits[i];
            child2.bits[i] = parent2.bits[i];
		}
    }

}
