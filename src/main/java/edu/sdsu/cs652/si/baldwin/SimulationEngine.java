package edu.sdsu.cs652.si.baldwin;

import java.util.LinkedList;
import java.util.List;

import org.jgap.InvalidConfigurationException;

public class SimulationEngine {
	private List<SimulationProfile> simulationProfiles;
	private int testsPerProfile = 1;
	private int antigenUniverseSize;
	private int antigenExposureNumber;
	private int simulationGenerations;
	
	public SimulationEngine(int antigenUniverseSize, int antigenExposureNumber, int simulationGenerations) {
		this.simulationProfiles = new LinkedList<SimulationProfile>();
		this.antigenExposureNumber = antigenExposureNumber;
		this.antigenUniverseSize = antigenUniverseSize;
		this.simulationGenerations = simulationGenerations;
	}
	
	public void addProfile(SimulationProfile simulationProfile) {
		this.simulationProfiles.add(simulationProfile);
	}
	
	public void setTestsPerProfile(int testsPerProfile) {
		this.testsPerProfile = testsPerProfile;
	}
	
	public void runSimulations() throws InvalidConfigurationException {
		for (SimulationProfile simulationProfile : simulationProfiles) {
			BaldwinGASimulationProfile bgaProfile = new BaldwinGASimulationProfile(antigenUniverseSize, antigenExposureNumber, simulationGenerations, simulationProfile.getMaxLearningGuesses(), simulationProfile.getLearningPenalty());
			StringBuilder sb = new StringBuilder();
			sb.append("Running Simulation {maxLearningGuesses: ");
			sb.append(simulationProfile.getMaxLearningGuesses());
			sb.append(", learniingPenalty: ");
			sb.append(simulationProfile.getLearningPenalty());
			sb.append("}: ");
			System.out.print(sb.toString());
			double populationAverage = runSimluationProfile(bgaProfile);
			System.out.println(populationAverage);
		}
	}
	
	private double runSimluationProfile(BaldwinGASimulationProfile simulationProfile) throws InvalidConfigurationException {
		BaldwinGA bga = new BaldwinGA(simulationProfile);
		double total = 0.0;
		for(int i = 0; i < this.testsPerProfile; i++) {
			total += bga.runSimulation(true);
		}
		return total / testsPerProfile;
	}
	
	public static void main(String[] args) throws InvalidConfigurationException {
		SimulationEngine engine = new SimulationEngine(32,8,100);
		engine.setTestsPerProfile(30);
		engine.addProfile(new SimulationProfile(0, 0.05));
		engine.addProfile(new SimulationProfile(5, 0.05));
		engine.addProfile(new SimulationProfile(8, 0.05));
		engine.addProfile(new SimulationProfile(10, 0.05));
		engine.addProfile(new SimulationProfile(12, 0.05));
		engine.addProfile(new SimulationProfile(15, 0.05));
		engine.addProfile(new SimulationProfile(20, 0.05));
		
		engine.runSimulations();
	}
}
