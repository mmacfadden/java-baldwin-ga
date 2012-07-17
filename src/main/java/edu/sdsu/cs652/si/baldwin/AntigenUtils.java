package edu.sdsu.cs652.si.baldwin;

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
	
	public static String antigenToArrayString(boolean[] antigen) {
		StringBuffer sb = new StringBuffer();
		sb.append("new boolean[] {" );
		for (int bitIndex = 0; bitIndex < antigen.length; bitIndex++) {
			sb.append(antigen[bitIndex]?"true":"false");
			if (bitIndex < antigen.length - 1) {
				sb.append(",");
			}
		}
		sb.append("}");
		
		return sb.toString();
	}
	
	public static String antigenSpaceToString(boolean[][] antigenSpace) {
		StringBuffer sb = new StringBuffer();
		sb.append("private static final boolean[][] antigenSpace = new boolean[][] {\n" );
		for (int i = 0; i < antigenSpace.length; i++) {
			boolean[] antigen = antigenSpace[i];
			sb.append("    ");
			sb.append(antigenToArrayString(antigen));
			if (i < antigenSpace.length -1 ) {
				sb.append(",");
			}
			sb.append("\n");
		}
		sb.append("};");
		
		return sb.toString();
	}
	
	public static void main(String[] args) {
		boolean[][] antigenSpace = createRandomAntigens(64, 32);
		System.out.println(antigenSpaceToString(antigenSpace));
	}
}
