package edu.sdsu.cs652.si.baldwin;

import org.jgap.Chromosome;
import org.jgap.IChromosome;


public class PhenotypeExpressor {
	public static boolean[][] createAntibodies(Chromosome chromosome) {
		boolean[][] result = new boolean[Constants.NUMBER_OF_ANIBODIES][Constants.ANTIBODY_LENGTH];
		int index = 0;
		for (int lib1Segment = 0; lib1Segment < Constants.SEGMENTS_PER_LIBRARY; lib1Segment++) {
			for (int lib2Segment = 0; lib2Segment < Constants.SEGMENTS_PER_LIBRARY; lib2Segment++) {
				for (int lib3Segment = 0; lib3Segment < Constants.SEGMENTS_PER_LIBRARY; lib3Segment++) {
					for (int lib4Segment = 0; lib4Segment < Constants.SEGMENTS_PER_LIBRARY; lib4Segment++) {
						result[index++] = createAntiBody(chromosome, lib1Segment, lib2Segment, lib3Segment, lib4Segment);
					}
				}
			}
		}
		
		return result;
	}
	
	public static boolean[] createAntiBody(Chromosome chromosome, int lib1Segment, int lib2Segment, int lib3Segment, int lib4Segment) {
		boolean[] surface = new boolean[64];
		int index = 0;
		
		for (int i = 0; i < Constants.BITS_PER_SEGMENT; i++) {
			boolean bit = getBit(chromosome, Constants.LIBRARY_1, lib1Segment, i);
			surface[index++] = bit;
		}
		
		for (int i = 0; i < Constants.BITS_PER_SEGMENT; i++) {
			boolean bit = getBit(chromosome, Constants.LIBRARY_2, lib2Segment, i);
			surface[index++] = bit;
		}
		
		for (int i = 0; i < Constants.BITS_PER_SEGMENT; i++) {
			boolean bit = getBit(chromosome, Constants.LIBRARY_3, lib3Segment, i);
			surface[index++] = bit;
		}
		
		for (int i = 0; i < Constants.BITS_PER_SEGMENT; i++) {
			boolean bit = getBit(chromosome, Constants.LIBRARY_4, lib4Segment, i);
			surface[index++] = bit;
		}
		
		return surface;
	}
	
	public static boolean getBit(Chromosome chromosome, int libraryIndex, int segmentIndex, int bitIndex) {
		MyFixedBinaryGene fbg = (MyFixedBinaryGene)chromosome.getGene(0);
		int libraryStart = libraryIndex * Constants.SEGMENTS_PER_LIBRARY;
		int segmentStart = libraryStart + (segmentIndex * Constants.BITS_PER_SEGMENT);
		int bitOffset = segmentStart + bitIndex;
		return fbg.getBit(bitOffset);
	}
	
	public static boolean[] getSegment(IChromosome chromosome, int libraryIndex, int segmentIndex) {
		boolean[] segment = new boolean[16];
		int libraryStart = libraryIndex * Constants.SEGMENTS_PER_LIBRARY;
		int offset = libraryStart + (segmentIndex * Constants.BITS_PER_SEGMENT);
		MyFixedBinaryGene fbg = (MyFixedBinaryGene)chromosome.getGene(0);
		for (int i = 0; i < Constants.BITS_PER_SEGMENT; i++) {
			segment[i] = fbg.getBit(offset++);
		}
		return segment;
	}
}
