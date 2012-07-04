package edu.sdsu.cs652.si.baldwin.core;

import org.jgap.Chromosome;
import org.jgap.Configuration;
import org.jgap.FitnessFunction;
import org.jgap.InvalidConfigurationException;

public interface ImmuneSystemModel {
	public Chromosome createEmptyChromosome(ImmuneSystemStructure immuneSystemStructure, Configuration gaConfig) throws InvalidConfigurationException;
	public FitnessFunction createFitnessFunction(boolean[][] antigens, ImmuneSystemStructure immuneSystemStructure);
	public ChromosomeConverter getChromosomeConverter();
}
