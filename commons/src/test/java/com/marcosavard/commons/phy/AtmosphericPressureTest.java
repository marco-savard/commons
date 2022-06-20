package com.marcosavard.commons.phy;

import org.junit.Assert;
import org.junit.Test;

import java.text.MessageFormat;

public class AtmosphericPressureTest {

  @Test
  public void givenPressure_whenComputingBoilingPoint_thanEqual() {

    for (int altitude = 0; altitude <= 9000; altitude += 1000) {
      AtmosphericPressure pa1 = AtmosphericPressure.atAltitude(altitude);
      double boilingPoint = pa1.computeWaterBoilingTemperature();
      AtmosphericPressure pa2 = AtmosphericPressure.computeWaterBoilingPressure(boilingPoint);
      Assert.assertEquals(pa1, pa2);
    }
  }

  @Test
  public void givenPressure_whenAltitudeIs1050m_thenPressureIs120Mb() {
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

  @Test
  public void givenPressure_whenPressureLoss_thenAltitudeIs1034m() {
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
