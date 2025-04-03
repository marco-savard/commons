package com.marcosavard.commons.astro;

import static com.marcosavard.commons.math.SafeMath.*;

// http://www.stjarnhimlen.se/comp/ppcomp.html
// kepler, astro_unit, normalizeAngle, modulo
public class AstroMath {
  public static final double ECLIPTIC = 23.4393; // Earth's ecliptic, in degrees
  public static final double ASTRO_UNIT = 149_600_000; // 1 AU = 149 000 000 km

  public static double range(double value, double range) {
    double ranged = value - Math.floor(value / range) * range;
    return ranged;
  }


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

  // TODO
  public static double computeAbsoluteMagnitude(double apparentMagnitude, double lightYears) {
    double parsec = 3.26 * lightYears;
    double expDist = Math.pow((parsec / 10), 2);
    double absoluteMagnitude = apparentMagnitude - 2.5 * Math.log(expDist);
    return absoluteMagnitude;
  }
}
