package org.commons.util;

public final class ScoreUtil {

	private ScoreUtil() {}

	public static double score (double parVictoires, double parDefaites) {
		return parVictoires * 5 - parDefaites;
	}
	
	public static int score (int parVictoires, int parDefaites) {
		return parVictoires * 5 - parDefaites;
	}

}
