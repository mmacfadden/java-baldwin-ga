package edu.sdsu.cs652.si.baldwin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ChromosomeMetaData {
	
	private final List<AntigenFitnessData> antigenFitnessData;

	public ChromosomeMetaData(int antigens) {
		antigenFitnessData = new ArrayList<AntigenFitnessData>(antigens);
	}
	
	public void appendFitnessData(AntigenFitnessData data) {
		this.antigenFitnessData.add(data);
	}
	
	public List<AntigenFitnessData> getAntigenFitnessData() {
		return antigenFitnessData;
	}
	
	public double getPopulationAverageGeneticMatch() {
		double geneticMatchTotal = 0.0;
		for (Iterator<AntigenFitnessData> iterator = antigenFitnessData.iterator(); iterator.hasNext();) {
			 AntigenFitnessData antigenFitness = iterator.next();
			 geneticMatchTotal += antigenFitness.getGeneticMatchScore();
		}
		return geneticMatchTotal / antigenFitnessData.size();
	}
	
}
