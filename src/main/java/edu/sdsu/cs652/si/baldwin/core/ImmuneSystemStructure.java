package edu.sdsu.cs652.si.baldwin.core;

public class ImmuneSystemStructure {
	
	int numberOfLibraries = 4;
	int segmentsPerLibrary = 8;
	int bitsPerSegment = 16;
	
	public ImmuneSystemStructure(int numberOfLibraries, int segmentsPerLibrary,
			int bitsPerSegment) {
		this.numberOfLibraries = numberOfLibraries;
		this.segmentsPerLibrary = segmentsPerLibrary;
		this.bitsPerSegment = bitsPerSegment;
	}

	public int getNumberOfLibraries() {
		return numberOfLibraries;
	}

	public void setNumberOfLibraries(int numberOfLibraries) {
		this.numberOfLibraries = numberOfLibraries;
	}

	public int getSegmentsPerLibrary() {
		return segmentsPerLibrary;
	}

	public void setSegmentsPerLibrary(int segmentsPerLibrary) {
		this.segmentsPerLibrary = segmentsPerLibrary;
	}

	public int getBitsPerSegment() {
		return bitsPerSegment;
	}

	public void setBitsPerSegment(int bitsPerSegment) {
		this.bitsPerSegment = bitsPerSegment;
	}
	
	public int getTotalBits() {
		return this.numberOfLibraries * this.segmentsPerLibrary * this.bitsPerSegment;
	}
}
