package hr.fer.zemris.apr.lab4.algorithm;

import hr.fer.zemris.apr.lab4.crossover.ICrossover;
import hr.fer.zemris.apr.lab4.function.IFunction;
import hr.fer.zemris.apr.lab4.mutation.IMutation;
import hr.fer.zemris.apr.lab4.population.IPopulationCreator;
import hr.fer.zemris.apr.lab4.selection.ISelection;
import hr.fer.zemris.apr.lab4.solution.SingleObjectiveSolution;
import hr.fer.zemris.apr.lab4.util.RandomProvider;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

/**
 * Created by generalic on 17/12/16.
 */
public class GenerativeGeneticAlgorithm<T extends SingleObjectiveSolution>
        implements IOptAlgorithm<T> {

    private static final double EPSILON = 1e-6;

    public IFunction f;

    public IPopulationCreator<T> populationCreator;

    public ISelection<T> selection;
    public ICrossover<T> crossover;
    public IMutation<T> mutation;

    public int populationSize;
    public int generationLimit;
    public int elitism;
    public int evaluationLimit;
    public boolean verbose;

    public Random rand = RandomProvider.get();

    public GenerativeGeneticAlgorithm(
            IFunction f, IPopulationCreator<T> populationCreator, ISelection<T> selection,
            ICrossover<T> crossover, IMutation<T> mutation,
            int populationSize, int generationLimit, int elitism,
            int evaluationLimit, boolean verbose) {
        super();
        this.f = f;
        this.populationCreator = populationCreator;
        this.selection = selection;
        this.crossover = crossover;
        this.mutation = mutation;
        this.populationSize = populationSize;
        this.generationLimit = generationLimit;
        this.elitism = elitism;
        this.evaluationLimit = evaluationLimit;
        this.verbose = verbose;
    }

    @Override
    public T run() {
        List<T> population = populationCreator.create();

        population.forEach(c -> c.setFitness(f.evaluate(c.getPoints())));
        population.sort(Comparator.naturalOrder());

        T prevBest = population.get(0);

        for (int generation = 1; generation <= generationLimit; generation++) {
            if (f.getCallCounter() >= evaluationLimit) {
                if (verbose) {
                    System.out.println("Evaluation limit reached!");
                }
                break;
            }

            List<T> newPopulation = new ArrayList<>(populationSize);

            population.sort(Comparator.naturalOrder());
            T best = population.get(0);
            if (generation % 10000 == 0 && verbose) {
                System.out.println(
                        "Iteration: " + generation + ".) | " +
                                "Best: " + best.toString() + " | Fitness: " + best.getFitness()

                );
            }
            if (best.getFitness() <= EPSILON) {
                if (verbose) {
                    System.out.println("Found minimum before iteration exhaustion!");
                }
                break;
            }
            // Elitizam.
            int count = 0;
            while (count < elitism) {
                newPopulation.add(population.get(count));
                count++;
            }
            //            if (elitism == 1) {
            //                newPopulation.set(0, best);
            ////                nextPopulation[0] = best;
            //            }
            // Generiraj preostale ili sve, ovisno o elitizmu.
            for (int i = elitism; i < populationSize; i++) {
                // Selektiraj.
                T parent1 = selection.select(population);
                T parent2 = selection.select(population);

                T child = crossover.doCrossover(parent1, parent2);
                // Mutiraj.
                mutation.mutate(child);
                child.setFitness(f.evaluate(child.getPoints()));
                newPopulation.add(child);
            }
            population = newPopulation;

            if (prevBest.getFitness() > best.getFitness() && verbose) {
                System.out.println(
                        "Iteration: " + generation + ".) | " +
                                "Best: " + best.toString() + " | Fitness: " + best.getFitness()

                );
            }
            prevBest = best;
        }
        if (verbose) {
            System.out.println("Number of generative evaluations: " + f.getCallCounter());
        }
        f.resetCallCounter();

        population.sort(Comparator.naturalOrder());
        return population.get(0);
    }
}
