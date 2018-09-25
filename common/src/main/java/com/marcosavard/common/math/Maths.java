package com.marcosavard.common.math;

/**
 * A utility class that defines methods missing in java.lang.Mat
 * 
 * @author Marco
 *
 */
public class Maths {
	private static final double EPSILON = 0.0001; 

	/**
	 * Tells if double numbers are equal. It is not a good practice to compare double directly, 
	 * use if (Maths.equal(d1, d2) instead of if (d1 == d2). 
	 * 
	 * @param d1 the 1st number
	 * @param d2 the 2nd number
	 * @param difference (default value EPSILON)
	 * @return true if equal
	 */
	public static boolean equal(double d1, double d2, double difference) {
		boolean equal = Math.abs(d1 - d2) < difference; 
		return equal;
	}
	
	public static boolean equal(double d1, double d2) {
		return equal(d1, d2, EPSILON); 
	}

	/**
	 * Convert an angle in radian unit to degree units
	 * 
	 * @param radian
	 * @return angle in degree
	 */
	public double radianToDegree(double radian) {
		double degrees = (radian * 180) / Math.PI; 
		degrees = (degrees > 0) ? (degrees % 360) : (degrees + 360); 
		return degrees;
	}
	
	/**
	 * Round the full precision value at a given precision. 
	 * For instance, round(Math.PI, 0.01) gives 3.14 
	 * 
	 * @param original value
	 * @param precision
	 * @return rouned values
	 */ 
	public static double round(double original, double precision) {
		double rounded = ((int)(Math.round(original / precision))) * precision; 
		return rounded; 
	}
}
 