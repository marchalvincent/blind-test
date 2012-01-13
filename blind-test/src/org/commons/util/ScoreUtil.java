package org.commons.util;

public final class ScoreUtil {

	private ScoreUtil() {}

	public static double score (double parVictoires, double parDefaites) {
		return computeVictoire(parVictoires) - computeDefaite(parDefaites);
	}
	
	public static double computeDefaite(final double parScore) {
		return parScore;
	}
	
	public static double computeVictoire(final double parScore) {
		return parScore * 5.0;
	}
	
	public static int score (int parVictoires, int parDefaites) {
		return computeVictoire(parVictoires) - computeDefaite(parDefaites);
	}
	
	public static int computeDefaite(final int parScore) {
		return parScore;
	}
	
	public static int computeVictoire(final int parScore) {
		return parScore * 5;
	}
}
