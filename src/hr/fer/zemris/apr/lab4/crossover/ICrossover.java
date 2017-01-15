package hr.fer.zemris.apr.lab4.crossover;

import hr.fer.zemris.apr.lab4.solution.SingleObjectiveSolution;

public interface ICrossover<T extends SingleObjectiveSolution> {

	T doCrossover(T firstParent, T secondParent);

}