package edu.sdsu.cs652.si.baldwin;

import java.util.BitSet;

import org.jgap.Chromosome;
import org.jgap.Configuration;
import org.jgap.FitnessFunction;
import org.jgap.Gene;
import org.jgap.IChromosome;
import org.jgap.InvalidConfigurationException;

import edu.sdsu.cs652.si.baldwin.core.BaseFitnessFunction;
import edu.sdsu.cs652.si.baldwin.core.ChromosomeConverter;
import edu.sdsu.cs652.si.baldwin.core.ImmuneSystemGA;
import edu.sdsu.cs652.si.baldwin.core.ImmuneSystemModel;
import edu.sdsu.cs652.si.baldwin.core.ImmuneSystemStructure;
import edu.sdsu.cs652.si.baldwin.core.MyFixedBinaryGene;

public class BaldwinImmuneSystemModel implements ImmuneSystemModel {
	
	public static class ImmuneSystemFitnessFunction extends BaseFitnessFunction {

		private static final long serialVersionUID = 1L;
		

		public ImmuneSystemFitnessFunction(boolean[][] antigens, ImmuneSystemStructure immuneSystemStructure) {
			super(antigens, immuneSystemStructure);
		}

		public boolean getBit(Chromosome chromosome, int libraryIndex, int segmentIndex, int bitIndex) {
			MyFixedBinaryGene fbg = (MyFixedBinaryGene)chromosome.getGene(0);
			int libraryStart = libraryIndex * immuneSystemStructure.getSegmentsPerLibrary();
			int segmentStart = libraryStart + (segmentIndex * immuneSystemStructure.getBitsPerSegment());
			int bitOffset = segmentStart + bitIndex;
			return fbg.getBit(bitOffset);
		}
		
		public double getMatchScore(boolean[] antigen, boolean[] antibody) {
			double matches = super.getMatchScore(antigen, antibody);
			
			double bindingValue = 0.0;
			
			if (matches < 44) {
				bindingValue = 0.0;
			} else if (matches == 45) {
				bindingValue = .2;
			}  else if (matches == 46) {
				bindingValue = .8;
			} else {
				bindingValue = 1.0;
			}
			
			return bindingValue;
		}
	}
	
	public class FixedChromosomeConverter implements ChromosomeConverter {
		public BitSet toBits(IChromosome chromosome, ImmuneSystemStructure immuneSystemStructure) {
			BitSet result = new BitSet(immuneSystemStructure.getTotalBits());
			MyFixedBinaryGene fbg = (MyFixedBinaryGene)chromosome.getGene(0);
			for(int i = 0; i < fbg.getLength(); i++) {
				result.set(i, fbg.getBit(i));
			}
			return result;
		}
	}
	
	public Chromosome createEmptyChromosome(ImmuneSystemStructure immuneSystemStructure, Configuration gaConfig) throws InvalidConfigurationException {
		int totalBits = immuneSystemStructure.getNumberOfLibraries() * immuneSystemStructure.getSegmentsPerLibrary() * immuneSystemStructure.getBitsPerSegment();
		MyFixedBinaryGene gene = new MyFixedBinaryGene(gaConfig, totalBits);
		for (int i = 0; i < totalBits; i++) {
			gene.setBit(i, false);
		}
		Chromosome result = new Chromosome(gaConfig, new Gene[] {gene});
		return result;
	}
	

	public FitnessFunction createFitnessFunction(boolean[][] antigens, ImmuneSystemStructure immuneSystemStructure) {
		return new ImmuneSystemFitnessFunction(antigens, immuneSystemStructure);
	}

	public ChromosomeConverter getChromosomeConverter() {
		return new FixedChromosomeConverter();
	}
	
	public static void main(String[] args) throws InvalidConfigurationException {
		ImmuneSystemModel immuneSystemModelFactory = new BaldwinImmuneSystemModel();
		ImmuneSystemStructure immuneSystemStructure = new ImmuneSystemStructure(4, 8, 16);
		ImmuneSystemGA isga = new ImmuneSystemGA(immuneSystemModelFactory, immuneSystemStructure, 8, 1000);
		isga.run();
	}
}
