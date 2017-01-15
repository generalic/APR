package hr.fer.zemris.apr.lab4.selection;

import hr.fer.zemris.apr.lab4.solution.SingleObjectiveSolution;
import hr.fer.zemris.apr.lab4.util.RandomProvider;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class TournamentSelection<T extends SingleObjectiveSolution> implements ISelection<T> {

	/** Veličina turnira. */
	private int k;
	private Random rand = RandomProvider.get();

	public TournamentSelection(int k) {
		super();
		this.k = k;
	}

	@Override
	public T select(List<T> population) {
		int size = population.size();
		Set<T> tournament = new HashSet<>(k);
		while(tournament.size() < k) {
			tournament.add(population.get(rand.nextInt(size)));
		}
		return tournament.stream()
				.sorted()
				.findFirst()
				.get();
	}
}
