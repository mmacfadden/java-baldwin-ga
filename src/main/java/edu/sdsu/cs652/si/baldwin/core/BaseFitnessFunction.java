package edu.sdsu.cs652.si.baldwin.core;

import java.util.Random;

import org.jgap.Chromosome;
import org.jgap.FitnessFunction;
import org.jgap.IChromosome;

public  abstract class BaseFitnessFunction extends FitnessFunction {
	
	private static final long serialVersionUID = 1L;

	protected static final Random RANDOM = new Random(System.currentTimeMillis());
	
	protected final ImmuneSystemStructure immuneSystemStructure;
	protected final int numberOfAntibodies = 16;
	protected final boolean[][] antigens;
	
	public BaseFitnessFunction(boolean[][] antigens, ImmuneSystemStructure immuneSystemStructure) {
		this.antigens = antigens;
		this.immuneSystemStructure = immuneSystemStructure;
	}
	
	public double evaluate(IChromosome chromosome) {
		double total = 0;

		boolean[][] antibodies = createAntibodies((Chromosome)chromosome);

		for (int i = 0; i < antigens.length; i++) {
			int maxScore = 0;
			for (int j = 0; j < antibodies.length; j++) {
				boolean[] antibody = antibodies[j];
				maxScore = (int)Math.max(maxScore, getMatchScore(antigens[i], antibody));
			}
			total += maxScore;
		}
		
		return total / antigens.length;
	}

	public boolean[][] createAntibodies(Chromosome chromosome) {
		boolean[][] result = new boolean[numberOfAntibodies][64];
		for (int i = 0; i < result.length; i++) {
			result[i] = createRandomAntibody(chromosome);
		}
		
		return result;
	}
	
	public boolean[] createRandomAntibody(Chromosome chromosome) {
		boolean[] surface = new boolean[64];
		int index = 0;
		for( int libraryIndex = 0; libraryIndex < immuneSystemStructure.getNumberOfLibraries(); libraryIndex++) {
			int segmentIndex = RANDOM.nextInt(immuneSystemStructure.getSegmentsPerLibrary());
			for (int i = 0; i < immuneSystemStructure.getBitsPerSegment(); i++) {
				boolean bit = getBit(chromosome, libraryIndex, segmentIndex, i);
				surface[index++] = bit;
			}
		}
		return surface;
	}
	
	
	
	public double getMatchScore(boolean[] antigen, boolean[] antibody) {
		int matches = 0;
		for (int i = 0; i < antibody.length; i++) {
			if (antigen[i] != antibody[i]) {
				matches++;
			}
		}
		return matches;
	}
	
	public abstract boolean getBit(Chromosome chromosome, int libraryIndex, int segmentIndex, int bitIndex);
}
