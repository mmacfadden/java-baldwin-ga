package edu.sdsu.cs652.si.baldwin;

public class GeneticMatchResult {
	private final boolean[] antibody;
	private final int geneticMatchScore;
	
	public GeneticMatchResult(boolean[] antibody, int geneticMatchScore) {
		super();
		this.antibody = antibody;
		this.geneticMatchScore = geneticMatchScore;
	}

	public boolean[] getAntibody() {
		return antibody;
	}

	public int getGeneticMatchScore() {
		return geneticMatchScore;
	}

	
}
