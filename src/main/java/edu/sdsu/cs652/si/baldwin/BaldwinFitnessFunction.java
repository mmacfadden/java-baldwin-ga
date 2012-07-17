package edu.sdsu.cs652.si.baldwin;

import java.util.Random;

import org.jgap.Chromosome;
import org.jgap.FitnessFunction;
import org.jgap.IChromosome;

public class BaldwinFitnessFunction extends FitnessFunction {

	private static final long serialVersionUID = 1L;
	protected static final Random RANDOM = new Random(System.currentTimeMillis());
	
	protected final boolean[][] antigens;
	protected double learningPenalty = .2;
	
	public BaldwinFitnessFunction(boolean[][] antigens) {
		this.antigens = antigens;
	}
	
	public double evaluate(IChromosome chromosome) {
		double tottalFitnessValue = 0;
		boolean[][] antibodies = PhenotypeExpressor.createAntibodies((Chromosome)chromosome);
		
		ChromosomeMetaData metaData = new ChromosomeMetaData(antigens.length);
		
		for (int i = 0; i < antigens.length; i++) {
			boolean[] antigen = antigens[i];
			// Find the antibody that matches the best.
			GeneticMatchResult match = findBestGeneticMatch(antigen, antibodies);
			int geneticComponent = match.getGeneticMatchScore();
			int learnedComponent = calculateLearnedComponet(12);
			int totalMatchScore = geneticComponent + learnedComponent;
			double bindingValue = calculateBindingValue(totalMatchScore);
			double learningPenalty = calculateLearningPenalty(geneticComponent, learnedComponent);
			
			double fitnessValue = Math.max(0.0, bindingValue - learningPenalty);
			
			AntigenFitnessData data = new AntigenFitnessData(
					antigen,
					match.getAntibody(),
					geneticComponent, 
					learnedComponent, 
					bindingValue, 
					learningPenalty,
					fitnessValue);
				metaData.appendFitnessData(data);
			
			tottalFitnessValue += fitnessValue;
		}
		
		chromosome.setApplicationData(metaData);
		
		return tottalFitnessValue / antigens.length;
	}
	
	public int calculateLearnedComponet(int numberOfGuesses) {
		int learnedScore = 0;
		for(int i = 0; i < numberOfGuesses; i++) {
			if(RANDOM.nextBoolean()) {
				learnedScore++;
			}
		}
		return learnedScore;
	}
	
	public double calculateLearningPenalty(int geneticMatchScore,int correctGuesses) {
		if (geneticMatchScore >= 48) {
			return 0;
		}
		
		int shortfall = 48 - geneticMatchScore;
		
		int usedGuesses = Math.min(shortfall, correctGuesses);
		
		return usedGuesses * this.learningPenalty;
	}
	
	public double calculateBindingValue(int matchScore) {
		if (matchScore < 44) {
			return 0.0;
		} else if (matchScore == 44) {
			return 0.02;
		} else if (matchScore == 45) {
			return 0.05;
		} else if (matchScore == 46) {
			return 0.92;
		} else if (matchScore == 47) {
			return 0.98;
		} else {
			return 1.0;
		}
	}
	
	public GeneticMatchResult findBestGeneticMatch(boolean[] antigen, boolean[][] antibodies) {
		int bestMatchScore = -1;
		boolean[] bestMatch = null;
		
		for (int i = 0; i < antibodies.length; i++) {
			boolean[] antibody = antibodies[i];
			int score = getGeneticMatchScore(antigen, antibody);
			if (score > bestMatchScore) {
				bestMatchScore = score;
				bestMatch = antibody;
			}
		}
		return new GeneticMatchResult(bestMatch, bestMatchScore);
	}
	
	public int getGeneticMatchScore(boolean[] antigen, boolean[] antibody) {
		int matches = 0;
		for (int i = 0; i < antibody.length; i++) {
			if (antigen[i] != antibody[i]) {
				matches++;
			}
		}
		return matches;
	}
}