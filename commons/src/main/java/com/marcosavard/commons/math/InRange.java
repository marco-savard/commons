package com.marcosavard.commons.math;

public class InRange {

  // for instance, normalize to -180 .. + 180 is ranged = (value + 540) % 360 - 180;
  public static double range(double value, double lower, double upper) {
    // keep in range [lower..max]
    double ranged = (value + upper * 2 - lower) % (upper - lower * 2);
    return ranged;
  }

  public static double rangeOld(double value, double lower, double upper) {
    // keep in range [0..max]
    double range = upper - lower;
    double shifted = value - lower;
    shifted = (shifted >= 0) ? (shifted % range) : (range - Math.abs(shifted) % range) % range;
    double ranged = shifted + lower;
    return ranged;
  }

}
