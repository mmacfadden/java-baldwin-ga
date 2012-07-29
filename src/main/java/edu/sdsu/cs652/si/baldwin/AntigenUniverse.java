package edu.sdsu.cs652.si.baldwin;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

public class AntigenUniverse {
	
	private static final Random RANDOM = new Random(System.currentTimeMillis());
	
	public static AntigenUniverse createAntigenUnivers(int numAntigens, int antigenLength) {
		return new AntigenUniverse(createRandomAntigens(numAntigens, antigenLength));
	}
	
	private static boolean[][] createRandomAntigens(int numAntigens, int antigenLength) {
		boolean[][] antigens = new boolean[numAntigens][antigenLength];
		for (int i = 0; i < numAntigens; i++) {
			antigens[i] = createRandomBooleanArray(antigenLength);
		}
		return antigens;
	}

	private static boolean[] createRandomBooleanArray(int length) {
		boolean[] result = new boolean[length];
		for (int i = 0; i < result.length; i++) {
			result[i] = RANDOM.nextBoolean();
		}
		return result;
	}
	
	public static String antigenToString(boolean[] antigen) {
		StringBuffer sb = new StringBuffer();
		for (int bitIndex = 0; bitIndex < antigen.length; bitIndex++) {
			sb.append(antigen[bitIndex]?"1":"0");
		}
		return sb.toString();
	}
	
	public static String xorToString(boolean[] antigen, boolean[] antibody) {
		boolean[] xor = new boolean[antigen.length];
		for (int i = 0; i < antigen.length; i++) {
			xor[i] = antigen[i] ^ antibody[i];
		}
		return antigenToString(xor);
	}
	
	private final boolean[][] antigens;
	
	private AntigenUniverse(final boolean[][] antigens) {
		this.antigens = antigens;
	}
	
	public boolean[][] getAntigents() {
		return this.antigens;
	}
	
	public boolean[][] getAnitgenSubset(int numAntigens) {
		boolean[][] result = new boolean[numAntigens][64];
		
		HashSet<Integer> indecies = new HashSet<Integer>();
		while(indecies.size() < numAntigens) {
			indecies.add(RANDOM.nextInt(antigens.length));
		}
		
		int index = 0;
		for (Iterator<Integer> iterator = indecies.iterator(); iterator.hasNext();) {
			Integer antigenIndex = (Integer) iterator.next();
			result[index++] = antigens[antigenIndex];
		}
		
		return result;
	}	
}
