package edu.sdsu.cs652.si.baldwin.core;

import java.util.BitSet;

import org.jgap.IChromosome;

public class ChromosomePrinter {
	
	public static String toString(IChromosome chromosome, ChromosomeConverter converter, ImmuneSystemStructure immuneSystemStructure) {
		StringBuffer sb = new StringBuffer();
		BitSet bits = converter.toBits(chromosome, immuneSystemStructure);
		int totalIndex = 0;
		for(int libraryIndex = 0; libraryIndex < immuneSystemStructure.getNumberOfLibraries(); libraryIndex++) {
			sb.append("Library ").append(libraryIndex + 1).append(" {\n");
			for(int segmentIndex = 0; segmentIndex < immuneSystemStructure.getSegmentsPerLibrary(); segmentIndex++) {
				sb.append("  Segment ").append(segmentIndex + 1).append(": ");
				for (int bitIndex = 0; bitIndex < immuneSystemStructure.getBitsPerSegment(); bitIndex++) {
					sb.append(bits.get(totalIndex++)?"1":"0");
				}
				sb.append("\n");
			}
			sb.append("}\n");
		}
		return sb.toString();
	}

}
