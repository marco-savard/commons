package com.marcosavard.commons.phy;

import java.text.MessageFormat;

public class AtmosphericPressure {
  private static final AtmosphericPressure AT_SEA_LEVEL = AtmosphericPressure.ofMillibar(1013.25);
  private double millibars;

  private AtmosphericPressure(double millibars) {
    this.millibars = millibars;
  }

  public static AtmosphericPressure ofMillibar(double millibars) {
    return new AtmosphericPressure(millibars);
  }

  public static AtmosphericPressure atAltitude(double meters) {
    double p0 = AT_SEA_LEVEL.inMillibars();
    double dh = meters / 1000;
    double p1 = p0 * Math.pow((1 - dh / 44.3), 5.26);
    AtmosphericPressure pressure = new AtmosphericPressure(p1);
    return pressure;
  }

  public double inMillibars() {
    return this.millibars;
  }

  public double findElevationDifference(AtmosphericPressure p4) {
    // TODO Auto-generated method stub
    return 0;
  }

  public static AtmosphericPressure atSeaLevel() {
    return AT_SEA_LEVEL;
  }

  public double differenceInMb(AtmosphericPressure p1) {
    double diff = this.millibars - p1.inMillibars();
    return diff;
  }

  @Override
  public String toString() {
    String msg = MessageFormat.format("{0} mb", String.format("%.2f", millibars));
    return msg;
  }

  public double toMb() {
    return this.millibars;
  }

  // elevation in meters
  public static double findElevation(double startMb, double startAlt, double endMb) {
    AtmosphericPressure pStart = atAltitude(startAlt);

    double p4 = pStart.toMb() - startMb + endMb;

    double elevation = 44.3 * (1 - Math.pow(p4 / 1013.25, 0.19));

    return elevation * 1000;
  }

}
