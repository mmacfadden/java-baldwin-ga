package edu.sdsu.cs652.si.baldwin;

public interface Constants {
	public static final int NUMBER_OF_LIBRARIES = 4;
	public static final int SEGMENTS_PER_LIBRARY = 8;
	public static final int BITS_PER_SEGMENT = 16;
	
	public static final int CHROMOSOME_LENGTH = NUMBER_OF_LIBRARIES * SEGMENTS_PER_LIBRARY * BITS_PER_SEGMENT;
	public static final int ANTIBODY_LENGTH = NUMBER_OF_LIBRARIES * BITS_PER_SEGMENT;
	
	public static final int NUMBER_OF_ANIBODIES = SEGMENTS_PER_LIBRARY * SEGMENTS_PER_LIBRARY * SEGMENTS_PER_LIBRARY * SEGMENTS_PER_LIBRARY;

	public static final int LIBRARY_1 = 0;
	public static final int LIBRARY_2 = 1;
	public static final int LIBRARY_3 = 2;
	public static final int LIBRARY_4 = 3;
}
