package com.marcosavard.commons.astro.finder;

import com.marcosavard.commons.math.SafeMath;

public enum Planet {
  MERCURY(new MercuryOrbit()),
  VENUS(new VenusOrbit());

  private Orbit orbit;

  Planet(Orbit orbit) {
    this.orbit = orbit;
  }

  double getMeanDistance(double jd) {
    double a = orbit.absoluteMeanDistance + (orbit.relativeMeanDistance * jd);
    return a;
  }

  // in deg
  double getMeanAnomaly(double jd) {
    double ma = orbit.absoluteMeanAnomaly + (orbit.relativeMeanAnomaly * jd);
    ma = SafeMath.range(ma, 0, 360);
    return ma;
  }

  double getEccentricity(double jd) {
    double e = orbit.absoluteEccentricity + (orbit.relativeEccentricity * jd);
    return e;
  }

  // in deg
  double getInclination(double jd) {
    double i = orbit.absoluteInclination + (orbit.relativeInclination * jd);
    i = SafeMath.range(i, 0, 360);
    return i;
  }

  // in deg
  double getLongitudeAscendingNode(double jd) {
    double n = orbit.absoluteLongitudeAscNode + (orbit.relativeLongitudeAscNode * jd);
    n = SafeMath.range(n, 0, 360);
    return n;
  }

  double getArgumentOfPerihelion(double jd) {
    double w = orbit.absoluteArgumentOfPerihelion + (orbit.relativeArgumentOfPerihelion * jd);
    w = SafeMath.range(w, 0, 360);
    return w;
  }

  public abstract static class Orbit {
    private double absoluteMeanDistance = 1.0, relativeMeanDistance = 0.0; // a, or semi major axis
    private double absoluteMeanAnomaly, relativeMeanAnomaly = 0.0; // ma
    private double absoluteEccentricity, relativeEccentricity; // e
    private double absoluteInclination, relativeInclination; // i
    private double absoluteLongitudeAscNode, relativeLongitudeAscNode; // n
    private double absoluteArgumentOfPerihelion, relativeArgumentOfPerihelion; // w

    protected void withMeanDistance(double absoluteMeanDistance, double relativeMeanDistance) {
      this.absoluteMeanDistance = absoluteMeanDistance;
      this.relativeMeanDistance = relativeMeanDistance;
    }

    protected void withEccentricity(double absoluteEccentricity, double relativeEccentricity) {
      this.absoluteEccentricity = absoluteEccentricity;
      this.relativeEccentricity = relativeEccentricity;
    }

    protected void withMeanAnomaly(double absoluteMeanAnomaly, double relativeMeanAnomaly) {
      this.absoluteMeanAnomaly = absoluteMeanAnomaly;
      this.relativeMeanAnomaly = relativeMeanAnomaly;
    }

    protected void withArgumentOfPerihelion(
        double absoluteArgumentOfPerihelion, double relativeArgumentOfPerihelion) {
      this.absoluteArgumentOfPerihelion = absoluteArgumentOfPerihelion;
      this.relativeArgumentOfPerihelion = relativeArgumentOfPerihelion;
    }

    protected void withInclination(double absoluteInclination, double relativeInclination) {
      this.absoluteInclination = absoluteInclination;
      this.relativeInclination = relativeInclination;
    }

    protected void withLongitudeAscNode(
        double absoluteLongitudeAscNode, double relativeLongitudeAscNode) {
      this.absoluteLongitudeAscNode = absoluteLongitudeAscNode;
      this.relativeLongitudeAscNode = relativeLongitudeAscNode;
    }
  }

  private static class MercuryOrbit extends Orbit {
    private MercuryOrbit() {
      withMeanDistance(0.387098, 0.0); // a
      withEccentricity(0.205635, 5.59E-10); // e
      withMeanAnomaly(168.6562, 4.0923344368); // ma
      withArgumentOfPerihelion(29.1241, 1.01444E-5); // w
      withInclination(7.0047, 5.00E-8); // i
      withLongitudeAscNode(48.3313, 3.24587E-5); // n
    }
  }

  public static class VenusOrbit extends Orbit {
    private VenusOrbit() {
      withMeanDistance(0.723330, 0.0); // a
      withEccentricity(0.006773, -1.302E-9); // e
      withMeanAnomaly(48.0052, 1.6021302244); // ma
      withArgumentOfPerihelion(54.8910, 1.38374E-5); // w
      withInclination(3.3946, 2.75E-8); // i
      withLongitudeAscNode(76.6799, 2.46590E-5); // n
    }
  }
}
