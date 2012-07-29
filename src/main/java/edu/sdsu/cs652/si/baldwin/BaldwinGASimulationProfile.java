package edu.sdsu.cs652.si.baldwin;

public class BaldwinGASimulationProfile {
	int antigenUniverseSize;
	int exposedAntigensPerIndividual;
	int numberOfGenerations;
	int maxLearningGuesses;
	double learningPenalty;
	
	
	
	public BaldwinGASimulationProfile(int antigenUniverseSize,
			int exposedAntigensPerIndividual, int numberOfGenerations,
			int maxLearningGuesses, double learningPenalty) {
		super();
		this.antigenUniverseSize = antigenUniverseSize;
		this.exposedAntigensPerIndividual = exposedAntigensPerIndividual;
		this.numberOfGenerations = numberOfGenerations;
		this.maxLearningGuesses = maxLearningGuesses;
		this.learningPenalty = learningPenalty;
	}
	
	public int getAntigenUniverseSize() {
		return antigenUniverseSize;
	}
	public int getExposedAntigensPerIndividual() {
		return exposedAntigensPerIndividual;
	}
	public int getNumberOfGenerations() {
		return numberOfGenerations;
	}
	public int getMaxLearningGuesses() {
		return maxLearningGuesses;
	}
	public double getLearningPenalty() {
		return learningPenalty;
	}
	
	

}
