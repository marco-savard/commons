package com.marcosavard.commons.phy;

import java.text.MessageFormat;
import org.junit.Assert;

public class AtmosphericPressureDemo {

  public static void main(String[] args) {
    demoPressureAtAltitude();
    demoFindPressureCorrection();
    demoFindAltitudeFromPressureLoss();
  }

  private static void demoPressureAtAltitude() {
    double altitude = 0;
    AtmosphericPressure pa = AtmosphericPressure.atAltitude(altitude);
    String msg =
        MessageFormat.format("Pressure at {0} m is normally {1} Kpa", altitude, pa.toKpa());
    System.out.println(msg);

    altitude = 8800;
    pa = AtmosphericPressure.atAltitude(altitude);
    msg = MessageFormat.format("Pressure at {0} m is normally {1} Kpa", altitude, pa.toKpa());
    System.out.println(msg);

    System.out.println();
  }

  private static void demoFindPressureCorrection() {
    AtmosphericPressure p0 = AtmosphericPressure.atSeaLevel();

    int meters = 1050;
    AtmosphericPressure p1 = AtmosphericPressure.atAltitude(meters);
    double differenceMb = p0.toMb() - p1.toMb();

    String msg = MessageFormat.format("Difference of pressure at {0} meters of altitude : {1} mb", //
        meters, String.format("%.2f", differenceMb));
    System.out.println(msg);
    System.out.println("  Sea level : " + p0);
    System.out.println("  At " + meters + " : " + p1);
    System.out.println();
    double expected = 120;
    Assert.assertEquals(expected, differenceMb, 1);
  }

  private static void demoFindAltitudeFromPressureLoss() {
    double startMb = 922; // 922 mb
    double startAlt = 700; // 700 m
    double endMb = 885; // 885 mb
    double elevation = AtmosphericPressure.findElevation(startMb, startAlt, endMb);
    double endAlt = startAlt + elevation;

    String msg = MessageFormat.format("Given {0} mb at {1} meters:", startMb, startAlt);
    System.out.println(msg);

    msg = MessageFormat.format("  if pressure is {0} mb then new altitude is {1} m", endMb, endAlt);
    System.out.println(msg);
    double expected = 1034;
    Assert.assertEquals(expected, elevation, 1);
  }



}
