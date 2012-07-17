package edu.sdsu.cs652.si.baldwin;

public class AntigenFitnessData {
	
	private final boolean[] antigen;
	private final boolean[] bestAntibody;
	
	private int geneticMatchScore;
	private int learnedScore;
	private final double bindingValue;
	private final double learningPenalty;
	private final double fitnessValue;
	
	public AntigenFitnessData(boolean[] antigen, boolean[] bestAntibody,
			int geneticMatchScore, int learnedScore,
			double bindingValue, double learningPenalty,
			double fitnessValue) {
		super();
		this.geneticMatchScore = geneticMatchScore;
		this.learnedScore = learnedScore;
		this.bindingValue = bindingValue;
		this.learningPenalty = learningPenalty;
		this.fitnessValue = fitnessValue;
		this.antigen = antigen;
		this.bestAntibody = bestAntibody;
	}

	public int getGeneticMatchScore() {
		return geneticMatchScore;
	}

	public int getLearnedScore() {
		return learnedScore;
	}

	public double getBindingValue() {
		return bindingValue;
	}

	public double getLearningPenalty() {
		return learningPenalty;
	}
	
	public double getFitnessValue() {
		return fitnessValue;
	}
	
	public boolean[] getAntigen() {
		return antigen;
	}
	
	public boolean[] getBestAntibody() {
		return this.bestAntibody;
	}
}
