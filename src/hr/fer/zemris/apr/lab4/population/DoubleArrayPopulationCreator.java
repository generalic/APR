package hr.fer.zemris.apr.lab4.population;

import hr.fer.zemris.apr.lab4.solution.Decoder;
import hr.fer.zemris.apr.lab4.solution.DoubleArraySolution;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by generalic on 17/12/16.
 */
public class DoubleArrayPopulationCreator implements IPopulationCreator<DoubleArraySolution> {

    private int populationSize;
    private Decoder decoder;

    public DoubleArrayPopulationCreator(int populationSize, Decoder decoder) {
        this.populationSize = populationSize;
        this.decoder = decoder;
    }

    @Override
    public List<DoubleArraySolution> create() {
        return IntStream.range(0, populationSize)
                        .boxed()
                        .map(i -> new DoubleArraySolution(decoder))
                        .collect(Collectors.toList());
    }
}
