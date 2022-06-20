package com.marcosavard.commons.geog;

import org.junit.Assert;
import org.junit.Test;

import static com.marcosavard.commons.geog.GeoLocation.LatitudeHemisphere.NORTH;
import static com.marcosavard.commons.geog.GeoLocation.LongitudeHemisphere.WEST;

public class GeoLocationTest {

  @Test
  public void testQuebec() {
    GeoLocation qc = GeoLocation.of(46, 49, NORTH, 71, 13, WEST);
    GeoLocation mtl = GeoLocation.of(45, 30, NORTH, 73, 34, WEST);
    double distance = qc.findDistanceFrom(mtl);
    double expected = 232.782;
    Assert.assertEquals(expected, distance, 0.01);

    GeoLocation midPoint = qc.findMidpointTo(mtl);
    GeoLocation midPoint2 = qc.findIntermediatePointTo(mtl, 0.5);
    Assert.assertEquals(midPoint, midPoint2);
  }

  @Test
  public void testFrance() {
    GeoLocation paris = GeoLocation.of(48, 73, NORTH, 2, 38, WEST);
    GeoLocation nice = GeoLocation.of(43, 67, NORTH, 7, 21, WEST);
    double distance = paris.findDistanceFrom(nice);
    double expected = 671.358;
    Assert.assertEquals(expected, distance, 0.01);

    GeoLocation midPoint = paris.findMidpointTo(nice);
    GeoLocation midPoint2 = paris.findIntermediatePointTo(nice, 0.5);
    Assert.assertEquals(midPoint, midPoint2);
  }

  @Test
  public void testMoon() {
    GeoLocation appolo11 = GeoLocation.of(0.73, 23.4);
    GeoLocation luna15 = GeoLocation.of(17.0, 60.0);
    double moonRadius = 1738;
    double distance = appolo11.findDistanceFrom(luna15, moonRadius);
    double expected = 1198.797;
    Assert.assertEquals(expected, distance, 0.01);

    GeoLocation midPoint = appolo11.findMidpointTo(luna15);
    GeoLocation midPoint2 = appolo11.findIntermediatePointTo(luna15, 0.5);
    Assert.assertEquals(midPoint, midPoint2);
  }

    @Test
    public void demoBearing() {
        GeoLocation baghdad = GeoLocation.of(35, 45);
        GeoLocation osaka = GeoLocation.of(35, 125);
        double initial = baghdad.findInitialBearingTo(osaka);
        double terminal = baghdad.findTerminalBearingTo(osaka);

        Assert.assertEquals(60, initial, 1.0); // initial heading of 60°
        Assert.assertEquals(120, terminal, 1.0); // terminal heading of 120°
    }


}
