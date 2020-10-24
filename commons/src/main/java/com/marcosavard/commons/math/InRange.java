package com.marcosavard.commons.math;

public class InRange {

  public static double range(double value, double lower, double upper) {
    // keep in range [lower..max]
    double span = upper - lower;
    double ranged = lower + ((value - lower) % span + span) % span;
    return ranged;
  }


  public static double range2(double value, double lower, double upper) {
    // keep in range [lower..max]
    double span = upper - lower;
    double offset = value - lower;
    double ranged = lower + offset - Math.floor(offset / span) * span;
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
