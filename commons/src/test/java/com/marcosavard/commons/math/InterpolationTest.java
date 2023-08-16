package com.marcosavard.commons.math;

import com.marcosavard.commons.math.algebra.Interpolation;
import junit.framework.Assert;
import org.junit.Test;

public class InterpolationTest {
  private static double DELTA = 0.01;

  @Test
  public void givenMiles_whenInterpolateToKm_thanEqual() {
    Interpolation mileToKmConverter = new Interpolation();
    mileToKmConverter.define(65.0, 104.607);

    Assert.assertEquals(1.609, mileToKmConverter.interpolate(1.0), DELTA);
    Assert.assertEquals(8.047, mileToKmConverter.interpolate(5.0), DELTA);
    Assert.assertEquals(160.933, mileToKmConverter.interpolate(100), DELTA);
    Assert.assertEquals(209.214, mileToKmConverter.interpolate(130), DELTA);
  }

  @Test
  public void givenFahrenheit_whenInterpolateToCelcius_thanEqual() {
    Interpolation fahrenheitToCelciusConverter = new Interpolation(32.0, 0.0);
    fahrenheitToCelciusConverter.define(212, 100);

    Assert.assertEquals(0.0, fahrenheitToCelciusConverter.interpolate(32.0), DELTA);
    Assert.assertEquals(100.0, fahrenheitToCelciusConverter.interpolate(212.0), DELTA);
  }
}
