package com.marcosavard.commons.phy;

import org.junit.Assert;
import org.junit.Test;

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

}
