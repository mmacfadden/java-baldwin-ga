package edu.sdsu.cs652.si.baldwin.core;

import org.jgap.Configuration;
import org.jgap.Genotype;
import org.jgap.IChromosome;
import org.jgap.InvalidConfigurationException;
import org.jgap.Population;
import org.jgap.impl.CrossoverOperator;
import org.jgap.impl.DefaultConfiguration;
import org.jgap.impl.MutationOperator;
import org.jgap.impl.WeightedRouletteSelector;

public class ImmuneSystemGA {
	
	private final ImmuneSystemModel immuneSystemModel;
	
	private final int numberOfEvolutions;
	private final ImmuneSystemStructure immuneSystemStructure;
	
	private final Genotype genotype;
	private final Configuration gaConf;
	
	
	public ImmuneSystemGA(ImmuneSystemModel immuneSystemModel, ImmuneSystemStructure immuneSystemStructure, int numberOfAntigens, int numberOfEvolutions) throws InvalidConfigurationException {
		this.immuneSystemModel = immuneSystemModel;
		this.immuneSystemStructure = immuneSystemStructure;
		this.numberOfEvolutions = numberOfEvolutions;
		
		gaConf = new DefaultConfiguration();
		gaConf.getGeneticOperators().clear();
		gaConf.setPreservFittestIndividual(false);
		gaConf.setKeepPopulationSizeConstant(true);
		
		WeightedRouletteSelector selector = new WeightedRouletteSelector(gaConf);
		gaConf.addNaturalSelector(selector, false);

		CrossoverOperator crossoverOperator = new CrossoverOperator(gaConf);
		gaConf.addGeneticOperator(crossoverOperator);

		MutationOperator mutationOperator = new MutationOperator(gaConf);
		mutationOperator.setMutationRate(100);
		gaConf.addGeneticOperator(mutationOperator);

		
		IChromosome sampleChromosome = immuneSystemModel.createEmptyChromosome(immuneSystemStructure, gaConf);
		gaConf.setSampleChromosome(sampleChromosome);
		gaConf.setPopulationSize(50);
		
		boolean[][] antigens = AntigenUtils.createRandomAntigens(immuneSystemStructure.getBitsPerSegment() * immuneSystemStructure.getNumberOfLibraries(), numberOfAntigens);
		
		System.out.println("Antigen Space");
		System.out.println("-------------\n");
		for (int i = 0; i < antigens.length; i++) {
			boolean[] antigen = antigens[i];
			System.out.println("Antigen " + (i + 1) + ": " + AntigenUtils.antigenToString(antigen));
		}
		System.out.println("\n");
		
		gaConf.setFitnessFunction(immuneSystemModel.createFitnessFunction(antigens,immuneSystemStructure));
		Population population = new Population(gaConf);
		for (int i = 0; i < 50; i++) {
			population.addChromosome(immuneSystemModel.createEmptyChromosome(immuneSystemStructure, gaConf));
		}

		genotype = new Genotype(gaConf, population);
	}
	
	public void run() throws InvalidConfigurationException {
		int percentEvolution = numberOfEvolutions / 100;
		for (int i = 0; i < numberOfEvolutions; i++) {
			genotype.evolve();
			// Print progress.
			// ---------------
			if (percentEvolution > 0 && i % percentEvolution == 0) {
				IChromosome fittest = genotype.getFittestChromosome();
				double fitness = fittest.getFitnessValue();
				System.out.println("Genertion "+i+" average fitness: "
						+ fitness);
				//printChromosome(fittest);
				if (fitness >= 512) {
					break;
				}
			}
		}
		// Print summary.
		// --------------
		IChromosome fittest = genotype.getFittestChromosome();
		System.out.println("Fittest Chromosome has fitness "
				+ fittest.getFitnessValue());
		printChromosome(fittest);
	}
	
	private void printChromosome(IChromosome chromosome) {
		String printed = ChromosomePrinter.toString(chromosome, immuneSystemModel.getChromosomeConverter(), immuneSystemStructure);
		System.out.println(printed);
	}
}
