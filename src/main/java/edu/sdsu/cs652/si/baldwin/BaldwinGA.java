package edu.sdsu.cs652.si.baldwin;

import java.util.HashMap;
import java.util.List;

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

	private final BaldwinGASimulationProfile simulationProfile;

	public BaldwinGA(BaldwinGASimulationProfile simulationProfile) throws InvalidConfigurationException {
		this.simulationProfile = simulationProfile;
	}

	public double runSimulation(boolean quiet) throws InvalidConfigurationException {
		Configuration.reset();
		
		DefaultConfiguration gaConf = new DefaultConfiguration();
		gaConf.getGeneticOperators().clear();
		gaConf.setPreservFittestIndividual(false);
		gaConf.setKeepPopulationSizeConstant(true);

		gaConf.getNaturalSelectors(false).clear();

		WeightedRouletteSelector selector = new WeightedRouletteSelector(gaConf);
		gaConf.addNaturalSelector(selector, false);

		// RankProportionalRouletteSelector rankSelector = new
		// RankProportionalRouletteSelector(gaConf);
		// gaConf.addNaturalSelector(rankSelector, false);

		// RankProportionalSelector rankSelector = new
		// RankProportionalSelector(gaConf);
		// gaConf.addNaturalSelector(rankSelector, false);

		CrossoverOperator crossoverOperator = new CrossoverOperator(gaConf, 0.6);
		gaConf.addGeneticOperator(crossoverOperator);

		MutationOperator mutationOperator = new MutationOperator(gaConf);
		mutationOperator.setMutationRate(500);
		gaConf.addGeneticOperator(mutationOperator);

		IChromosome sampleChromosome = createEmptyChromosome(gaConf);
		gaConf.setSampleChromosome(sampleChromosome);
		gaConf.setPopulationSize(50);

		AntigenUniverse antigenUniverse = AntigenUniverse.createAntigenUnivers(simulationProfile.getAntigenUniverseSize(),
				Constants.ANTIBODY_LENGTH);

		BaldwinFitnessFunction fitnessFunction = new BaldwinFitnessFunction(antigenUniverse, simulationProfile.getMaxLearningGuesses(), simulationProfile.getLearningPenalty());

		gaConf.setFitnessFunction(fitnessFunction);
		Population population = new Population(gaConf);
		for (int i = 0; i < 50; i++) {
			population.addChromosome(createEmptyChromosome(gaConf));
		}

		Genotype genotype = new Genotype(gaConf, population);
		
		int percentEvolution = 10;
		for (int i = 0; i < simulationProfile.getNumberOfGenerations(); i++) {
			genotype.evolve();
			if (!quiet && percentEvolution > 0 && i % percentEvolution == 0) {
				IChromosome fittest = genotype.getFittestChromosome();
				double fitness = fittest.getFitnessValue();
				System.out.print("Genertion " + (i + percentEvolution)
						+ " best fitness: " + fitness + " ");
				ChromosomeMetaData metaData = (ChromosomeMetaData) fittest
						.getApplicationData();
				for (int j = 0; j < 8; j++) {
					System.out.print(metaData.getAntigenFitnessData().get(j)
							.getGeneticMatchScore()
							+ " ");
				}
				System.out.println();

			}
		}

		return getPopulationAverageFitness(antigenUniverse, genotype, fitnessFunction);
	}
	
	public double getPopulationAverageFitness(AntigenUniverse antigenUniverse, Genotype genotype, BaldwinFitnessFunction fitnessFunction) {
		List<IChromosome> chromosomes = genotype.getPopulation()
				.getChromosomes();
		HashMap<IChromosome, boolean[][]> chromosomeAntibodyMap = new HashMap<IChromosome, boolean[][]>();
		for (IChromosome chromosome : chromosomes) {
			boolean[][] antibodies = PhenotypeExpressor
					.createAntibodies((Chromosome) chromosome);
			chromosomeAntibodyMap.put(chromosome, antibodies);
		}

		boolean[][] antigens = antigenUniverse.getAntigents();
		double populationTotal = 0.0;
		for (boolean[] antigen : antigens) {
			double totalAntigenMatch = 0.0;
			for (IChromosome chromosome : chromosomes) {
				boolean[][] antibodies = chromosomeAntibodyMap.get(chromosome);
				GeneticMatchResult result = fitnessFunction.findBestGeneticMatch(antigen, antibodies);
				totalAntigenMatch += result.getGeneticMatchScore();
			}
			double antigenAverage = totalAntigenMatch / chromosomes.size();
			populationTotal += antigenAverage;
		}
		return populationTotal / antigens.length;
	}

	
	
	public Chromosome createEmptyChromosome(Configuration gaConfig)
			throws InvalidConfigurationException {
		MyFixedBinaryGene gene = new MyFixedBinaryGene(gaConfig,
				Constants.CHROMOSOME_LENGTH);
		for (int i = 0; i < Constants.CHROMOSOME_LENGTH; i++) {
			gene.setBit(i, false);
		}
		Chromosome result = new Chromosome(gaConfig, new Gene[] { gene });
		return result;
	}

	public static void main(String[] args) throws InvalidConfigurationException {
		BaldwinGASimulationProfile profile = new BaldwinGASimulationProfile(32, 8, 1000, 0, 0);
		BaldwinGA isga = new BaldwinGA(profile);
		double average = isga.runSimulation(false);
		System.out.println("Average: " + average);
		
		average = isga.runSimulation(false);
		System.out.println("Average: " + average);
	}

}
