package edu.sdsu.cs652.si.baldwin;

import java.util.ArrayList;
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
	
}
