package hr.fer.zemris.apr.lab4.algorithm;

import hr.fer.zemris.apr.lab4.solution.SingleObjectiveSolution;

/**
 * Sučelje implementiraju algoritmi.<br>
 * Algoritam nudi metodu kojom vraća rezultat provođenja algoritma.
 *
 * @author Boris Generalic
 *
 * @param <T>	tip rješenja
 */
public interface IOptAlgorithm<T extends SingleObjectiveSolution> {

	/**
     * Izvodi algoritam i vraća rezultat.
	 *
	 * @return	rezultat
	 */
	T run();

}