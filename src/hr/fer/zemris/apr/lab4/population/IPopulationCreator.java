package hr.fer.zemris.apr.lab4.population;

import hr.fer.zemris.apr.lab4.solution.SingleObjectiveSolution;

import java.util.List;

/**
 * Created by generalic on 17/12/16.
 */
public interface IPopulationCreator<T extends SingleObjectiveSolution> {

    List<T> create();

}
