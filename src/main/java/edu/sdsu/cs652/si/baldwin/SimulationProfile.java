package edu.sdsu.cs652.si.baldwin;

public class SimulationProfile {
	private final int maxLearningGuesses;
	private final double learningPenalty;
	
	
	public SimulationProfile(int maxLearningGuesses, double learningPenalty) {
		super();
		this.maxLearningGuesses = maxLearningGuesses;
		this.learningPenalty = learningPenalty;
	}
	
	public int getMaxLearningGuesses() {
		return maxLearningGuesses;
	}
	
	public double getLearningPenalty() {
		return learningPenalty;
	}
}