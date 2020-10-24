package com.marcosavard.commons.phy;

import java.text.MessageFormat;

// TODO to kipoPascal
// TODO compute dew point, compute boiling point of water for pressure
public class AtmosphericPressure {
  private static final AtmosphericPressure AT_SEA_LEVEL = AtmosphericPressure.ofMillibars(1013.25);
  private double millibars;

  public static AtmosphericPressure ofMillibars(double millibars) {
    return new AtmosphericPressure(millibars);
  }

  public static AtmosphericPressure ofMillimeterHg(double millimetersHg) {
    double millibars = millimetersHg / 0.75;
    return new AtmosphericPressure(millibars);
  }

  public static AtmosphericPressure atAltitude(double meters) {
    double p0 = AT_SEA_LEVEL.toMb();
    double dh = meters / 1000;
    double p1 = p0 * Math.pow((1 - dh / 44.3), 5.26);
    AtmosphericPressure pressure = new AtmosphericPressure(p1);
    return pressure;
  }

  private AtmosphericPressure(double millibars) {
    this.millibars = millibars;
  }

  public Object toKpa() {
    return (this.millibars / 10.0);
  }

  // to millibars
  public double toMb() {
    return this.millibars;
  }

  // to millimeters of mercury
  public double toMmHg() {
    return (this.millibars * 0.75);
  }

  // TODO toPsi()

  public static AtmosphericPressure atSeaLevel() {
    return AT_SEA_LEVEL;
  }

  public double differenceInMb(AtmosphericPressure p1) {
    double diff = this.millibars - p1.toMb();
    return diff;
  }

  @Override
  public String toString() {
    String msg = MessageFormat.format("{0} mb", String.format("%.2f", millibars));
    return msg;
  }

  // elevation in meters
  public static double findElevation(double startMb, double startAlt, double endMb) {
    AtmosphericPressure normalPressure = atAltitude(startAlt);
    double p4 = normalPressure.toMb() - startMb + endMb;
    double elevation = 44.3 * (1 - Math.pow(p4 / 1013.25, 0.19));
    return elevation * 1000;
  }



}
