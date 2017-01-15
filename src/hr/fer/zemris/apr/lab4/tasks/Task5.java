package hr.fer.zemris.apr.lab4.tasks;

import hr.fer.zemris.apr.lab4.algorithm.GenerativeGeneticAlgorithm;
import hr.fer.zemris.apr.lab4.crossover.BlxAlphaCrossover;
import hr.fer.zemris.apr.lab4.crossover.ICrossover;
import hr.fer.zemris.apr.lab4.crossover.OnePointCrossover;
import hr.fer.zemris.apr.lab4.function.Function6;
import hr.fer.zemris.apr.lab4.function.IFunction;
import hr.fer.zemris.apr.lab4.mutation.FlipMutation;
import hr.fer.zemris.apr.lab4.mutation.IMutation;
import hr.fer.zemris.apr.lab4.mutation.NormalDistributionMutation;
import hr.fer.zemris.apr.lab4.population.BitVectorPopulationCreator;
import hr.fer.zemris.apr.lab4.population.DoubleArrayPopulationCreator;
import hr.fer.zemris.apr.lab4.population.IPopulationCreator;
import hr.fer.zemris.apr.lab4.selection.ISelection;
import hr.fer.zemris.apr.lab4.selection.TournamentSelection;
import hr.fer.zemris.apr.lab4.solution.BitVectorSolution;
import hr.fer.zemris.apr.lab4.solution.Decoder;
import hr.fer.zemris.apr.lab4.solution.DoubleArraySolution;
import hr.fer.zemris.apr.lab4.solution.SingleObjectiveSolution;

import java.util.stream.IntStream;

/**
 * Created by generalic on 18/12/16.
 */
public class Task5 {

    public static void main(String[] args) {
        function6(3);

    }

    private static void function6(int numberOfVariables) {

        int populationSize = 100;
        double mutationProbability = 0.9;
        int generationLimit = 100_000;
        int elitism = 2;
        int precision = 4;
        int tournamentSize = 4;
        double alpha = 0.75;
        double sigma = 4;
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

        System.out.println(
                "\nGenerative solution for f6: " + solution + ", " + solution.getFitness());
        //        System.out.println("Eliminative solution for f1: " + sol12 + ", " +
        // sol12.getFitness());
        System.out.println(
                "---------------------------------------------------------------------------------------");
    }

    private static void function6Binary(int numberOfVariables) {

        int populationSize = 100;
        double mutationProbability = 0.9;
        int generationLimit = 100_000;
        int elitism = 2;
        int precision = 4;
        int tournamentSize = 10;
        double alpha = 0.75;
        double sigma = 4;
        int evalationLimit = 10_000_000;
        boolean verbose = true;

        Decoder decoder = new Decoder(
                IntStream.range(0, numberOfVariables).mapToDouble(i -> -50).toArray(),
                IntStream.range(0, numberOfVariables).mapToDouble(i -> 150).toArray()
        );
        IFunction f = new Function6(new double[numberOfVariables]);

        IPopulationCreator<BitVectorSolution> populationCreator =
                new BitVectorPopulationCreator(populationSize, precision, decoder);

        ISelection<BitVectorSolution> selection = new TournamentSelection<>(tournamentSize);
        ICrossover<BitVectorSolution> crossover = new OnePointCrossover(f);
        IMutation<BitVectorSolution> mutation =
                new FlipMutation(mutationProbability);

        GenerativeGeneticAlgorithm<BitVectorSolution> ga = new GenerativeGeneticAlgorithm<>(
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

        SingleObjectiveSolution solution = ga.run();

        System.out.println(
                "\nGenerative solution for f6: " + solution + ", " + solution.getFitness());
        //        System.out.println("Eliminative solution for f1: " + sol12 + ", " +
        // sol12.getFitness());
        System.out.println(
                "---------------------------------------------------------------------------------------");
    }

}
