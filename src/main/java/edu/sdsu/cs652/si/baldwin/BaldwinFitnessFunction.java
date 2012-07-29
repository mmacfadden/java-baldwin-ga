package edu.sdsu.cs652.si.baldwin;

import java.util.Random;

import org.jgap.FitnessFunction;
import org.jgap.IChromosome;

public class BaldwinFitnessFunction extends FitnessFunction {

	private static final long serialVersionUID = 1L;
	protected static final Random RANDOM = new Random(System.currentTimeMillis());
	
	protected final AntigenUniverse antigenUniverse;
	protected final  double learningPenalty;
	private final int maxGuesses;
	
	public BaldwinFitnessFunction(AntigenUniverse antigenUniverse, int maxGuesses, double learningPenalty) {
		this.antigenUniverse = antigenUniverse;
		this.maxGuesses = maxGuesses;
		this.learningPenalty = learningPenalty;
	}
	
	public double evaluate(IChromosome chromosome) {
		double tottalFitnessValue = 0;
		boolean[][] antigens = antigenUniverse.getAnitgenSubset(8);
		//boolean[][] antibodies = PhenotypeExpressor.createAntibodies((Chromosome)chromosome);
		
		ChromosomeMetaData metaData = new ChromosomeMetaData(antigens.length);
		
		for (int i = 0; i < antigens.length; i++) {
			boolean[] antigen = antigens[i];
			// Find the antibody that matches the best.
			GeneticMatchResult match = findBestGeneticMatch(antigen, chromosome);
			int geneticComponent = match.getGeneticMatchScore();
			int shortFall = 48 - geneticComponent;
			LearningResult learningResult = calculateLearnedComponet(maxGuesses,shortFall);
			int totalMatchScore = geneticComponent + learningResult.getNumberOfLearnedBits();
			double bindingValue = calculateBindingValue(totalMatchScore);
			double learningPenalty = calculateLearningPenalty(learningResult.getNumberOfGuessesUsed());
			double fitnessValue = Math.max(0.0, bindingValue - learningPenalty);
			
			AntigenFitnessData data = new AntigenFitnessData(
					antigen,
					match.getAntibody(),
					geneticComponent, 
					learningResult.getNumberOfLearnedBits(), 
					bindingValue, 
					learningPenalty,
					fitnessValue);
			metaData.appendFitnessData(data);
			
			tottalFitnessValue += fitnessValue;
		}
		
		chromosome.setApplicationData(metaData);
		
		return tottalFitnessValue / antigens.length;
	}
	
	public LearningResult calculateLearnedComponet(int maxGuesses, int shortFall) {
		int numberOfLearnedBits = 0;
		int numberOfGuessesUsed = 0;
		
		while(numberOfGuessesUsed < maxGuesses && numberOfLearnedBits < shortFall) {
			numberOfGuessesUsed++;
			if(RANDOM.nextBoolean()) {
				numberOfLearnedBits++;
			}
		}
		return new LearningResult(numberOfGuessesUsed, numberOfLearnedBits);
	}
	
	private class LearningResult {
		private int numberOfGuessesUsed;
		private int numberOfLearnedBits;
		
		public LearningResult(int numberOfGuesses, int correctGuesses) {
			super();
			this.numberOfGuessesUsed = numberOfGuesses;
			this.numberOfLearnedBits = correctGuesses;
		}

		public int getNumberOfGuessesUsed() {
			return numberOfGuessesUsed;
		}

		public int getNumberOfLearnedBits() {
			return numberOfLearnedBits;
		}
	}
	
	public double calculateLearningPenalty(int usedGuesses) {
		return usedGuesses * this.learningPenalty;
	}
	
	public double calculateBindingValue(int matchScore) {
//		if (matchScore < 44) {
//			return 0.0;
//		} else if (matchScore == 44) {
//			return 0.02;
//		} else if (matchScore == 45) {
//			return 0.05;
//		} else if (matchScore == 46) {
//			return 0.92;
//		} else if (matchScore == 47) {
//			return 0.98;
//		} else {
//			return 1.0;
//		}
		
		if (matchScore < 45) {
			return 0.0;
		} else if (matchScore == 45) {
			return 0.5;
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
	
	public GeneticMatchResult findBestGeneticMatch(boolean[] antigen, IChromosome chromosome) {
		boolean[] bestMatch = new boolean[Constants.ANTIBODY_LENGTH];
		
		fillInBestMatchingSegment(Constants.LIBRARY_1, antigen, chromosome, bestMatch);
		fillInBestMatchingSegment(Constants.LIBRARY_2, antigen, chromosome, bestMatch);
		fillInBestMatchingSegment(Constants.LIBRARY_3, antigen, chromosome, bestMatch);
		fillInBestMatchingSegment(Constants.LIBRARY_4, antigen, chromosome, bestMatch);
		
		int matchScore = getGeneticMatchScore(antigen, bestMatch);
		
		return new GeneticMatchResult(bestMatch, matchScore);
	}
	
	private void fillInBestMatchingSegment(int libraryIndex, boolean[] antigen,IChromosome chromosome, boolean[] antibody) {
		boolean[] bestSegment = findBestSegment(libraryIndex, antigen,chromosome);
		int offset = libraryIndex * Constants.BITS_PER_SEGMENT;
		for(int i = 0; i < Constants.BITS_PER_SEGMENT; i++) {
			antibody[offset + i] = bestSegment[i];
		}
	}
	

	private boolean[] findBestSegment(int libraryIndex, boolean[] antigen, IChromosome chromosome) {
		boolean[] bestMatch = null;
		int bestMatchScore = -1;
		for(int i = 0; i < Constants.SEGMENTS_PER_LIBRARY; i++) {
			boolean[] antibodySegment = PhenotypeExpressor.getSegment(chromosome, libraryIndex, i);
			int match = getSegmentGeneticMatchScore(libraryIndex, antigen, antibodySegment);
			if (match > bestMatchScore) {
				bestMatchScore = match;
				bestMatch = antibodySegment;
			}
		}
		return bestMatch;
	}
	
	public int getSegmentGeneticMatchScore(int libraryIndex, boolean[] antigen, boolean[] antibodySegment) {
		int offset = libraryIndex * Constants.BITS_PER_SEGMENT;
		int matches = 0;
		for (int i = 0; i < antibodySegment.length; i++) {
			if (antigen[offset+i] != antibodySegment[i]) {
				matches++;
			}
		}
		return matches;
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