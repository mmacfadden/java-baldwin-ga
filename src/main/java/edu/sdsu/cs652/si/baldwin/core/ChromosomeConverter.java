package edu.sdsu.cs652.si.baldwin.core;

import java.util.BitSet;

import org.jgap.IChromosome;

public interface ChromosomeConverter {
	
	public BitSet toBits(IChromosome chromosome, ImmuneSystemStructure immuneSystemStructure);

}
