package hr.fer.zemris.apr.lab4.population;

import hr.fer.zemris.apr.lab4.solution.BitVectorSolution;
import hr.fer.zemris.apr.lab4.solution.Decoder;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by generalic on 17/12/16.
 */
public class BitVectorPopulationCreator implements IPopulationCreator<BitVectorSolution> {

    private int populationSize;
    private int precision;
    private Decoder decoder;

    public BitVectorPopulationCreator(int populationSize, int precision,
            Decoder decoder) {
        this.populationSize = populationSize;
        this.precision = precision;
        this.decoder = decoder;
    }

    @Override
    public List<BitVectorSolution> create() {
        return IntStream.range(0, populationSize)
                 .boxed()
                 .map(i -> new BitVectorSolution(decoder, precision))
                 .collect(Collectors.toList());
    }
}
