package com.marcosavard.commons.astro;

import static com.marcosavard.commons.math.SafeMath.*;

// http://www.stjarnhimlen.se/comp/ppcomp.html
// kepler, modulo
public class AstroMath {
  public static final double ECLIPTIC = 23.4393; // Earth's ecliptic, in degrees
  public static final double ASTRO_UNIT = 149_600_000; // 1 AU = 149 000 000 km

  private static final double KEPLER_ACCURACY = 1E-6; // Accuracy of the Kepler equation.

  // e=eccentricity, ma=mean anomaly, epsilon=max approx
  public static double computeEccentricAnomaly(double e, double ma, double epsilon) {
    double delta;
    double e0 = ma + (180 / Math.PI) * e * sind(ma) * (1 + e * cosd(ma));
    double e1;

    do {
      e1 = e0 - (e0 - (180 / Math.PI) * e * sind(e0) - ma) / (1 - e * cosd(e0));
      delta = Math.abs(e1 - e0);
      e0 = e1;
    } while (delta > epsilon);

    return e1;
  }

  public static double computeAbsoluteMagnitude(double apparentMagnitude, double lightYears) {
    double parsec = 3.26 * lightYears;
    double expDist = Math.pow((parsec / 10), 2);
    double absoluteMagnitude = apparentMagnitude - 2.5 * Math.log(expDist);
    return absoluteMagnitude;
  }

  // kepler - solve the equation of Kepler
  public static double kepler(double degrees, double ecc, double keplerAccuracy) {
    double rads = Math.toRadians(degrees);
    double e, delta;

    e = rads;
    do {
      delta = e - ecc * Math.sin(e) - rads;
      e -= delta / (1 - ecc * Math.cos(e));
    } while (Math.abs(delta) > keplerAccuracy);
    return e;
  }

  public static double kepler(double degrees, double ecc) {
    return kepler(degrees, ecc, KEPLER_ACCURACY);
  }
}
