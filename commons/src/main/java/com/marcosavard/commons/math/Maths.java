package com.marcosavard.commons.math;

/**
 * A utility class that defines methods missing in java.lang.Mat
 * 
 * @author Marco
 *
 */
public class Maths {
  private static final double EPSILON = 0.0001;

  /**
   * Tells if double numbers are equal. It is not a good practice to compare double directly, use if
   * (Maths.equal(d1, d2) instead of if (d1 == d2).
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

  // gives N!
  public static long factorial(int N) {
    long multi = 1;
    for (int i = 1; i <= N; i++) {
      multi = multi * i;
    }
    return multi;
  }

  /**
   * Round the full precision value at a given precision. For instance, round(Math.PI, 0.01) gives
   * 3.14
   * 
   * @param original value
   * @param precision (0.01 to round at the nearest 0.01 value)
   * @return rounded values
   */
  public static double round(double original, double precision) {
    double rounded = ((int) (Math.round(original / precision))) * precision;
    return rounded;
  }

  /**
   * Keep in range [min..max]. Return value within the range [0..max]. For instance :
   *   range(90, 360) gives 90
   *   range(361, 360) gives 1
   *   range(-90, 360) gives 270
   * 
   * @param value a given value
   * @param min the lower bound of the range
   * @param max the upper bound of the range
   * 
   * @return the ranged value
   * 
   */
	public static double range(double value, double min, double max) {
	    double span = max - min;
	    double ranged = min + ((value - min) % span + span) % span;
	    return ranged;
	}
	
}
