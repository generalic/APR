package hr.fer.zemris.apr.test4;

public interface IFunction {

	public int getDimension();
	
	public double[] getStart();
	
	public double fitness(double[] point);

	public double valueAt(double[] point);
	
	public int getEval();
	
	public void reset();

}
