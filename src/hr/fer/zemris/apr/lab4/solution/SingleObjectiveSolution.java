package hr.fer.zemris.apr.lab4.solution;

/**
 * Abstraktan razred koji predstavlja rješenje funkcije.
 *
 * @author Boris Generalic
 *
 */
public abstract class SingleObjectiveSolution implements Comparable<SingleObjectiveSolution> {

    /** Dobrota rješenja */
    public double fitness;

    @Override
    public int compareTo(SingleObjectiveSolution o) {
        return Double.compare(this.fitness, o.fitness);
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    public abstract double[] getPoints();

    /**
     * Vraća kopiju rješenja.
     * @return
     */
    public abstract SingleObjectiveSolution duplicate();

}