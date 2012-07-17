package edu.sdsu.cs652.si.baldwin;

import org.jgap.Chromosome;
import org.jgap.Configuration;
import org.jgap.Gene;
import org.jgap.Genotype;
import org.jgap.IChromosome;
import org.jgap.InvalidConfigurationException;
import org.jgap.Population;
import org.jgap.impl.CrossoverOperator;
import org.jgap.impl.DefaultConfiguration;
import org.jgap.impl.MutationOperator;
import org.jgap.impl.WeightedRouletteSelector;


public class BaldwinGA {
	
	private final int numberOfEvolutions;
	
	private final Genotype genotype;
	private final Configuration gaConf;
	private final boolean[][] antigens;

	private BaldwinFitnessFunction fitnessFunction;
	
	
	public BaldwinGA(int numberOfAntigens, int numberOfEvolutions) throws InvalidConfigurationException {
		this.numberOfEvolutions = numberOfEvolutions;
		
		gaConf = new DefaultConfiguration();
		gaConf.getGeneticOperators().clear();
		gaConf.setPreservFittestIndividual(false);
		gaConf.setKeepPopulationSizeConstant(true);
		
		gaConf.getNaturalSelectors(false).clear();
		//WeightedRouletteSelector selector = new WeightedRouletteSelector(gaConf);
		
		RankProportionalRouletteSelector rankSelector = new RankProportionalRouletteSelector(gaConf);
		gaConf.addNaturalSelector(rankSelector, false);

		
		CrossoverOperator crossoverOperator = new CrossoverOperator(gaConf, 0.6);
		gaConf.addGeneticOperator(crossoverOperator);

		MutationOperator mutationOperator = new MutationOperator(gaConf);
		mutationOperator.setMutationRate(500);
		gaConf.addGeneticOperator(mutationOperator);

		
		IChromosome sampleChromosome = createEmptyChromosome(gaConf);
		gaConf.setSampleChromosome(sampleChromosome);
		gaConf.setPopulationSize(50);
		
		antigens = AntigenUniverse.getAnitgenSubset(8);
		
		System.out.println("Antigen Space");
		System.out.println("-------------\n");
		for (int i = 0; i < antigens.length; i++) {
			boolean[] antigen = antigens[i];
			System.out.println("Antigen " + (i + 1) + ": " + AntigenUniverse.antigenToString(antigen));
		}
		System.out.println("\n");
		
		this.fitnessFunction = new BaldwinFitnessFunction(antigens);
		
		gaConf.setFitnessFunction(fitnessFunction);
		Population population = new Population(gaConf);
		for (int i = 0; i < 50; i++) {
			population.addChromosome(createEmptyChromosome(gaConf));
		}

		genotype = new Genotype(gaConf, population);
	}
	
	public void run() throws InvalidConfigurationException {
		int percentEvolution = 10;
		for (int i = 0; i < numberOfEvolutions; i++) {
			genotype.evolve();
			if (percentEvolution > 0 && i % percentEvolution == 0) {
				IChromosome fittest = genotype.getFittestChromosome();
				double fitness = fittest.getFitnessValue();
				System.out.print("Genertion " + i + " best fitness: " + fitness + " ");
				ChromosomeMetaData metaData = (ChromosomeMetaData)fittest.getApplicationData();
				for (int j = 0; j < antigens.length; j++) {
					System.out.print(metaData.getAntigenFitnessData().get(j).getGeneticMatchScore() + " ");
				}
				System.out.println();
				if (fitness >= 512) {
					break;
				}
			}
		}
		
		System.out.println();
		System.out.println("---------");
		System.out.println(" Summary ");
		System.out.println("---------");
		System.out.println();
		
		// Print summary.
		Chromosome fittest = (Chromosome)genotype.getFittestChromosome();
		boolean[][] antibodies = PhenotypeExpressor.createAntibodies((Chromosome)fittest);
		double totalMatch = 0;
		for (int i = 0; i < this.antigens.length; i++) {
			boolean[] antigen = this.antigens[i];
			GeneticMatchResult result = fitnessFunction.findBestGeneticMatch(antigen, antibodies);
			System.out.print("Antigen " + (i+1) + ":  ");
			System.out.println(AntigenUniverse.antigenToString(antigen));
			System.out.print("Best Match: ");
			System.out.println(AntigenUniverse.antigenToString(result.getAntibody()));
			System.out.print("XOR:        ");
			System.out.print(AntigenUniverse.xorToString(antigen, result.getAntibody()));
			System.out.println(" (Score: " + result.getGeneticMatchScore() + ")\n");
			totalMatch += result.getGeneticMatchScore();
		}
		totalMatch = totalMatch / antigens.length;
		
		System.out.println("Average Fitness " + fittest.getFitnessValue());
		System.out.println("Average Match Score " + totalMatch);
		System.out.println();
		
		printChromosome(fittest);
	}
	
	private void printChromosome(IChromosome chromosome) {
		String printed = ChromosomePrinter.toString(chromosome);
		System.out.println(printed);
	}
	
	public Chromosome createEmptyChromosome(Configuration gaConfig) throws InvalidConfigurationException {
		MyFixedBinaryGene gene = new MyFixedBinaryGene(gaConfig, Constants.CHROMOSOME_LENGTH);
		for (int i = 0; i < Constants.CHROMOSOME_LENGTH; i++) {
			gene.setBit(i, false);
		}
		Chromosome result = new Chromosome(gaConfig, new Gene[] {gene});
		return result;
	}
	
	public static void main(String[] args) throws InvalidConfigurationException {
		BaldwinGA isga = new BaldwinGA(8, 1000);
		isga.run();
	}

}
