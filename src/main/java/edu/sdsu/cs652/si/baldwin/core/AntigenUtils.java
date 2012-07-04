package edu.sdsu.cs652.si.baldwin.core;

import java.util.Random;

public class AntigenUtils {
	private static final Random rand = new Random(System.currentTimeMillis());
	
	public static boolean[][] createRandomAntigens(int length, int num) {
		boolean[][] antigens = new boolean[num][length];
		for (int i = 0; i < num; i++) {
			antigens[i] = createRandomBooleanArray(length);
		}
		return antigens;
	}

	public static boolean[] createRandomBooleanArray(int length) {
		boolean[] result = new boolean[length];
		for (int i = 0; i < result.length; i++) {
			result[i] = rand.nextBoolean();
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
}
