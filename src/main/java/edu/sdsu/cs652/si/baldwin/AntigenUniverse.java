package edu.sdsu.cs652.si.baldwin;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

public class AntigenUniverse {
	
	private static final Random RANDOM = new Random(System.currentTimeMillis());
	
	private static final boolean[][] antigenSpace = new boolean[][] {
	    new boolean[] {false,true,false,false,true,true,false,false,true,false,true,false,false,false,true,false,true,false,true,true,true,true,true,true,true,true,false,true,false,true,true,false,true,false,false,true,true,false,true,false,false,false,false,true,false,true,false,false,true,true,true,false,false,true,false,true,true,false,true,false,false,true,true,false},
	    new boolean[] {true,true,true,true,true,false,false,false,true,false,false,true,true,false,true,false,false,false,true,false,false,false,true,false,false,true,false,false,true,true,true,false,true,true,true,false,true,false,false,true,false,false,true,false,true,false,true,false,true,false,false,false,true,false,false,false,true,true,true,false,false,true,true,true},
	    new boolean[] {true,false,true,true,true,false,false,true,false,false,true,false,false,false,true,false,true,false,true,true,true,false,true,true,false,false,false,false,true,false,false,true,false,false,true,true,true,true,false,false,false,true,true,false,false,true,true,false,false,false,true,true,true,true,false,true,true,true,true,true,false,true,true,false},
	    new boolean[] {false,false,true,false,true,true,false,false,false,true,true,false,false,false,true,true,false,false,false,false,true,true,true,true,true,true,true,true,false,true,true,false,true,false,false,false,false,true,false,false,false,false,true,false,false,true,false,true,true,false,true,true,true,false,false,false,false,true,false,false,true,true,false,true},
	    new boolean[] {false,false,true,true,false,true,true,false,true,false,false,true,false,true,false,true,true,false,true,true,false,false,false,true,true,false,true,true,true,true,true,true,false,true,true,false,true,true,false,false,false,false,false,false,false,false,false,true,false,true,true,true,true,false,true,false,false,false,false,true,true,false,false,false},
	    new boolean[] {true,true,false,true,false,true,true,false,false,false,false,true,false,false,false,true,false,true,true,false,false,true,true,true,true,true,false,false,false,false,true,false,false,false,false,false,true,false,false,false,true,false,false,false,false,true,true,false,true,false,true,false,true,true,false,false,false,false,true,false,false,true,true,false},
	    new boolean[] {false,true,false,false,true,false,false,true,true,true,true,true,true,true,false,true,false,true,true,true,false,true,false,false,false,false,true,true,true,true,false,true,true,false,false,false,true,false,false,true,false,false,false,true,false,false,true,false,false,false,false,true,true,false,true,true,false,true,true,false,true,false,false,true},
	    new boolean[] {false,true,false,false,false,false,false,false,true,false,false,false,true,false,true,true,true,false,false,false,true,false,false,false,true,false,true,true,true,false,true,false,true,false,false,true,false,true,false,false,true,false,false,false,true,true,true,false,false,false,false,true,false,true,false,false,false,false,false,true,true,true,false,false},
	    new boolean[] {false,false,true,false,true,true,false,true,false,true,false,true,true,true,false,false,true,false,true,false,true,true,false,false,true,false,false,true,true,false,true,false,true,false,true,false,false,true,true,false,false,true,false,false,false,true,true,true,false,false,true,false,true,false,false,true,false,true,false,false,true,true,false,true},
	    new boolean[] {false,false,true,false,true,false,true,false,false,false,false,true,false,true,true,false,false,false,true,false,false,false,false,false,false,false,false,false,true,false,false,false,false,false,false,false,false,true,false,true,true,false,false,false,true,false,false,true,false,false,false,true,true,false,true,true,true,false,false,true,false,false,true,false},
	    new boolean[] {false,false,false,true,true,false,false,false,true,false,true,true,false,false,true,false,false,true,true,false,false,false,true,true,false,true,true,true,false,false,true,true,true,true,false,false,false,true,false,true,true,true,false,true,true,true,false,false,false,true,true,true,true,true,false,true,true,false,false,true,false,true,false,true},
	    new boolean[] {true,true,false,true,false,true,true,true,true,false,false,false,true,false,false,true,false,true,false,false,false,false,true,false,true,false,true,true,false,true,false,true,true,true,false,false,false,true,true,true,true,true,true,false,false,true,false,true,true,false,true,false,false,false,false,true,true,true,false,true,false,false,false,false},
	    new boolean[] {false,false,false,false,false,true,false,true,false,false,false,false,false,true,false,true,false,false,false,true,true,true,false,false,false,true,false,false,true,true,false,true,false,true,true,true,false,true,false,true,false,true,true,true,true,false,true,true,true,false,false,false,false,false,false,false,true,false,true,true,false,false,false,false},
	    new boolean[] {false,true,false,true,true,true,true,true,false,true,false,true,true,true,false,false,true,true,true,false,false,true,true,true,true,false,false,true,true,true,false,false,false,false,false,true,false,false,false,false,false,true,true,false,true,false,false,true,true,false,true,false,true,true,true,true,true,false,true,false,false,true,true,false},
	    new boolean[] {true,false,true,false,true,true,false,false,false,true,false,true,true,false,false,false,false,false,true,true,false,true,true,true,true,false,true,true,false,true,false,true,false,false,false,true,false,false,false,false,false,true,false,false,false,true,true,false,false,true,true,true,false,false,false,true,true,false,false,false,false,true,true,false},
	    new boolean[] {true,false,true,false,true,true,false,false,true,true,false,false,false,true,false,false,true,true,true,false,false,true,true,false,false,false,true,false,true,false,false,false,false,false,false,true,false,true,false,false,true,true,false,true,true,false,false,false,false,false,true,false,true,false,false,true,true,true,false,true,false,false,true,false},
	    new boolean[] {true,false,false,true,false,false,false,true,true,false,true,true,false,false,false,true,false,true,true,false,false,false,true,true,false,true,true,true,true,false,false,false,true,false,false,false,false,true,true,true,false,true,false,true,false,true,false,true,true,true,true,true,false,true,false,true,false,false,false,true,true,true,true,true},
	    new boolean[] {true,true,false,true,true,false,true,true,true,false,true,false,true,false,false,true,false,false,false,true,true,false,false,false,false,true,true,false,true,false,false,false,false,false,true,true,false,true,true,false,true,false,false,true,true,false,true,true,false,true,true,true,true,false,true,false,true,false,true,true,true,true,false,true},
	    new boolean[] {false,true,false,false,true,false,true,true,true,true,true,false,false,false,false,false,true,false,true,true,true,false,true,true,true,false,true,true,true,true,true,true,false,true,false,true,false,false,false,true,false,false,false,true,false,false,false,true,true,false,false,true,true,true,true,false,true,true,false,true,false,false,true,false},
	    new boolean[] {true,true,true,true,false,false,true,true,true,false,false,false,false,false,true,false,true,true,false,false,false,false,false,true,false,false,true,true,true,true,true,true,true,false,false,true,true,true,true,true,false,true,true,false,false,true,false,true,true,false,true,false,false,true,false,true,false,true,false,false,false,true,false,false},
	    new boolean[] {false,true,false,false,false,false,true,true,true,false,true,true,false,true,true,true,false,false,false,true,true,false,true,false,true,false,true,false,false,true,false,true,false,true,false,true,true,false,true,false,false,true,false,true,true,false,false,false,false,false,true,false,false,true,false,true,true,true,false,true,false,true,false,true},
	    new boolean[] {false,false,true,true,true,false,true,true,false,false,true,false,false,false,true,true,true,true,false,false,false,false,false,false,false,true,true,true,true,true,false,true,false,true,true,true,true,true,false,true,false,false,false,false,true,true,true,false,true,false,false,true,true,false,false,true,true,false,true,false,false,true,true,false},
	    new boolean[] {false,true,true,true,false,false,true,false,false,true,false,true,true,true,false,true,false,true,true,false,false,true,false,true,true,true,false,true,true,false,true,true,true,true,false,false,false,false,false,false,true,false,false,false,false,false,false,true,true,false,true,true,false,true,false,false,true,true,false,false,true,false,true,false},
	    new boolean[] {false,true,false,true,true,false,false,false,false,false,true,false,false,true,true,true,true,true,true,false,true,false,false,false,true,true,false,false,true,false,false,false,false,true,true,false,true,true,false,true,true,true,false,true,true,false,true,true,false,true,true,true,true,false,true,false,true,false,true,true,true,true,false,false},
	    new boolean[] {true,false,true,false,true,true,true,false,false,false,true,false,true,false,false,false,true,true,false,true,false,false,true,false,false,true,true,false,true,false,false,false,false,true,false,false,true,false,false,true,true,false,false,false,false,false,false,true,false,false,false,false,false,true,true,false,false,false,true,false,true,true,false,true},
	    new boolean[] {false,false,false,true,true,true,true,false,false,false,false,true,false,true,false,false,false,false,false,false,true,false,false,true,false,true,true,false,false,true,true,false,false,true,true,false,false,true,true,false,false,false,true,true,false,false,false,false,true,false,false,true,false,true,false,false,false,true,true,false,true,false,true,false},
	    new boolean[] {false,true,true,true,false,true,false,true,false,false,true,false,false,true,false,false,false,false,true,true,false,true,false,true,false,false,false,false,false,false,true,false,true,true,true,true,true,false,false,false,true,true,false,true,true,true,false,true,false,false,false,false,true,true,false,true,true,false,true,false,false,true,true,true},
	    new boolean[] {false,false,true,true,false,true,true,true,true,false,true,true,false,false,false,true,false,false,false,true,true,true,true,false,false,true,true,false,true,true,true,true,true,false,false,false,false,true,false,false,false,true,true,false,false,false,false,false,true,false,false,false,false,true,false,false,false,false,true,true,true,true,true,false},
	    new boolean[] {false,true,true,false,false,true,false,false,true,false,true,false,false,false,false,false,false,false,true,true,false,false,false,true,true,false,true,false,false,true,true,false,true,true,false,false,false,true,false,false,false,true,true,true,true,true,true,false,true,true,true,false,true,false,true,false,false,false,true,true,true,true,false,true},
	    new boolean[] {false,false,true,false,false,true,false,false,true,false,true,false,false,false,false,true,false,false,false,true,false,false,true,false,true,false,true,true,true,false,true,true,false,false,true,false,true,false,true,false,true,false,false,false,true,false,false,true,false,true,false,false,false,false,true,false,false,false,false,false,true,false,true,false},
	    new boolean[] {true,true,false,true,false,true,true,true,false,false,false,true,true,true,false,true,true,false,false,false,false,true,true,true,false,false,true,false,false,true,false,true,true,true,false,false,true,true,false,false,true,false,true,true,false,false,false,true,true,false,false,false,true,false,false,true,false,false,false,false,true,false,false,true},
	    new boolean[] {false,true,false,true,true,true,true,true,false,false,false,true,true,false,false,false,true,false,false,true,true,false,false,false,true,true,false,true,true,true,true,true,false,false,true,true,true,false,false,false,true,true,false,false,false,true,false,true,true,false,true,false,false,false,true,false,false,false,true,false,false,false,false,true}
	};
	
	public static boolean[][] getAnitgenSubset(int numAntigens) {
		boolean[][] result = new boolean[numAntigens][64];
		
		HashSet<Integer> indecies = new HashSet<Integer>();
		while(indecies.size() < numAntigens) {
			indecies.add(RANDOM.nextInt(antigenSpace.length));
		}
		
		int index = 0;
		for (Iterator<Integer> iterator = indecies.iterator(); iterator.hasNext();) {
			Integer antigenIndex = (Integer) iterator.next();
			result[index++] = antigenSpace[antigenIndex];
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
	
}
