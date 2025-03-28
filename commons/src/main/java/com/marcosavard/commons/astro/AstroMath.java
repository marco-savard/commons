package com.marcosavard.commons.astro;

// http://www.stjarnhimlen.se/comp/ppcomp.html
// kepler, astro_unit, normalizeAngle, modulo
public class AstroMath {
  public static final double ECLIPTIC = 23.4393; // Earth's ecliptic, in degrees
  public static final double ASTRO_UNIT = 149_600_000; // 1 AU = 149 000 000 km

  public static double range(double value, double range) {
    double ranged = value - Math.floor(value / range) * range;
    return ranged;
  }

  // keep in range [min..max]
  public static double range(double value, int min, int max) {
    double span = max - min;
    double ranged = min + ((value - min) % span + span) % span;
    return ranged;
  }

  public static double sind(double degree) {
    return Math.sin(Math.toRadians(degree));
  }

  public static double cosd(double degree) {
    return Math.cos(Math.toRadians(degree));
  }

  public static double tand(double degree) {
    return Math.tan(Math.toRadians(degree));
  }

  // cosecant
  public static double cosecd(double degree) {
    return 1 / sind(degree);
  }

  // secant
  public static double secd(double degree) {
    return 1 / cosd(degree);
  }

  // cotangant
  public static double cotand(double degree) {
    return 1 / tand(degree);
  }

  public static double asind(double value) {
    return Math.toDegrees(Math.asin(value));
  }

  public static double acosd(double value) {
    return Math.toDegrees(Math.acos(value));
  }

  public static double atand(double value) {
    return Math.toDegrees(Math.atan(value));
  }

  public static double atan2d(double y, double x) {
    return Math.toDegrees(Math.atan2(y, x));
  }

  // haversine function
  public static double haversined(double degree) {
    return (1 - cosd(degree)) / 2;
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
