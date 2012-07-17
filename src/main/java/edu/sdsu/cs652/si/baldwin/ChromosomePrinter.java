package edu.sdsu.cs652.si.baldwin;

import java.util.BitSet;

import org.jgap.IChromosome;


public class ChromosomePrinter {
	
	public static String toString(IChromosome chromosome) {
		StringBuffer sb = new StringBuffer();
		BitSet bits = toBits(chromosome);
		int totalIndex = 0;
		for(int libraryIndex = 0; libraryIndex < Constants.NUMBER_OF_LIBRARIES; libraryIndex++) {
			sb.append("Library ").append(libraryIndex + 1).append(" {\n");
			for(int segmentIndex = 0; segmentIndex < Constants.SEGMENTS_PER_LIBRARY; segmentIndex++) {
				sb.append("  Segment ").append(segmentIndex + 1).append(": ");
				for (int bitIndex = 0; bitIndex < Constants.BITS_PER_SEGMENT; bitIndex++) {
					sb.append(bits.get(totalIndex++)?"1":"0");
				}
				sb.append("\n");
			}
			sb.append("}\n");
		}
		return sb.toString();
	}
	
	public static BitSet toBits(IChromosome chromosome) {
		BitSet result = new BitSet(Constants.CHROMOSOME_LENGTH);
		MyFixedBinaryGene fbg = (MyFixedBinaryGene)chromosome.getGene(0);
		for(int i = 0; i < fbg.getLength(); i++) {
			result.set(i, fbg.getBit(i));
		}
		return result;
	}

}
