package hr.fer.zemris.apr.lab4.mutation;

import hr.fer.zemris.apr.lab4.solution.SingleObjectiveSolution;

public interface IMutation<T extends SingleObjectiveSolution> {

    void mutate(T child);

}
