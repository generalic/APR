package hr.fer.zemris.apr.lab4.selection;

import hr.fer.zemris.apr.lab4.solution.SingleObjectiveSolution;

import java.util.List;

/**
 * Sučelje implementiraju razredi koji predstavljaju strategiju
 * selekcije roditelja iz populacija kod GA.
 *
 * @author Boris Generalic
 *
 */
public interface ISelection<T extends SingleObjectiveSolution> {

	/**
	 * Odabire i vraća roditelj iz dane populacije.
	 *
	 * @param population
	 * @return
	 */
	T select(List<T> population);

}
