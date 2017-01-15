package hr.fer.zemris.apr.lab4.tasks;

import hr.fer.zemris.apr.lab4.algorithm.GenerativeGeneticAlgorithm;
import hr.fer.zemris.apr.lab4.crossover.BlxAlphaCrossover;
import hr.fer.zemris.apr.lab4.crossover.ICrossover;
import hr.fer.zemris.apr.lab4.function.Function6;
import hr.fer.zemris.apr.lab4.function.Function7;
import hr.fer.zemris.apr.lab4.function.IFunction;
import hr.fer.zemris.apr.lab4.mutation.IMutation;
import hr.fer.zemris.apr.lab4.mutation.NormalDistributionMutation;
import hr.fer.zemris.apr.lab4.population.DoubleArrayPopulationCreator;
import hr.fer.zemris.apr.lab4.population.IPopulationCreator;
import hr.fer.zemris.apr.lab4.selection.ISelection;
import hr.fer.zemris.apr.lab4.selection.TournamentSelection;
import hr.fer.zemris.apr.lab4.solution.Decoder;
import hr.fer.zemris.apr.lab4.solution.DoubleArraySolution;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * Created by generalic on 18/12/16.
 */
public class Task2 {

    public static void main(String[] args) {
        for (int n : Arrays.asList(1, 3, 6, 10)) {
            function6(n);
        }

//        function7(6);


    }

    private static void function6(int numberOfVariables) {
        int populationSize = 40;
        double mutationProbability = 0.5;
        int generationLimit = 10000000;
        int elitism = 3;
        int precision = 5;
        int tournamentSize = 3;
        double alpha = 0.2;
        double sigma = 0.5;
        int evalationLimit = 10_000_000;
        boolean verbose = true;

        Decoder decoder = new Decoder(
                IntStream.range(0, numberOfVariables).mapToDouble(i -> -50).toArray(),
                IntStream.range(0, numberOfVariables).mapToDouble(i -> 150).toArray()
        );
        IFunction f = new Function6(new double[numberOfVariables]);

        IPopulationCreator<DoubleArraySolution> populationCreator =
                new DoubleArrayPopulationCreator(populationSize, decoder);

        ISelection<DoubleArraySolution> selection = new TournamentSelection<>(tournamentSize);
        ICrossover<DoubleArraySolution> crossover = new BlxAlphaCrossover(alpha);
        IMutation<DoubleArraySolution> mutation =
                new NormalDistributionMutation(mutationProbability, sigma);

        GenerativeGeneticAlgorithm<DoubleArraySolution> ga = new GenerativeGeneticAlgorithm<>(
                f,
                populationCreator,
                selection,
                crossover,
                mutation,
                populationSize,
                generationLimit,
                elitism,
                evalationLimit,
                verbose
        );

        DoubleArraySolution solution = ga.run();

        System.out.println("\nGenerative solution for f6: " + solution + ", " + solution.getFitness());
        //        System.out.println("Eliminative solution for f1: " + sol12 + ", " + sol12.getFitness());
        System.out.println(
                "---------------------------------------------------------------------------------------");
    }

    private static void function7(int numberOfVariables) {
        int populationSize = 40;
        double mutationProbability = 0.3;
        int generationLimit = 10000000;
        int elitism = 3;
        int precision = 5;
        int tournamentSize = 3;
        double alpha = 0.2;
        double sigma = 5;
        int evalationLimit = 10_000_000;
        boolean verbose = true;

        Decoder decoder = new Decoder(
                IntStream.range(0, numberOfVariables).mapToDouble(i -> -50).toArray(),
                IntStream.range(0, numberOfVariables).mapToDouble(i -> 150).toArray()
        );
        IFunction f = new Function7(new double[numberOfVariables]);

        IPopulationCreator<DoubleArraySolution> populationCreator =
                new DoubleArrayPopulationCreator(populationSize, decoder);

        ISelection<DoubleArraySolution> selection = new TournamentSelection<>(tournamentSize);
        ICrossover<DoubleArraySolution> crossover = new BlxAlphaCrossover(alpha);
        IMutation<DoubleArraySolution> mutation =
                new NormalDistributionMutation(mutationProbability, sigma);

        GenerativeGeneticAlgorithm<DoubleArraySolution> ga = new GenerativeGeneticAlgorithm<>(
                f,
                populationCreator,
                selection,
                crossover,
                mutation,
                populationSize,
                generationLimit,
                elitism,
                evalationLimit,
                verbose
        );

        DoubleArraySolution solution = ga.run();

        System.out.println("\nGenerative solution for f7: " + solution + ", " + solution.getFitness());
        //        System.out.println("Eliminative solution for f1: " + sol12 + ", " + sol12.getFitness());
        System.out.println(
                "---------------------------------------------------------------------------------------");
    }

}
