package com.marcosavard.commons.astro;

import com.marcosavard.commons.astro.space.SpaceCoordinate;
import com.marcosavard.commons.astro.time.JulianDay;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;

// based on http://www.stjarnhimlen.se/comp/tutorial.html
public class PlanetoryPositionTest {

  @Test
  public void test1990april19() {
    LocalDate date = LocalDate.of(1990, 4, 19);
    JulianDay jd = JulianDay.of(date);
    Assert.assertEquals(2448000.5, jd.getValue(), 0.1);
  }

  @Test
  public void testSunLocation() {
    LocalDate date = LocalDate.of(1990, 4, 19);
    Orbit sunApparentOrbit = Orbit.SUN_APPARENT_ORBIT;

    SpaceCoordinate equatorial = sunApparentOrbit.findEquatorialCoordinateOn(date);
    double ra = equatorial.getRightAscensionDegree();
    double decl = equatorial.getDeclinationDegree();

    Assert.assertEquals(26.6580, ra, 0.01);
    Assert.assertEquals(11.0084, decl, 0.01);
  }
}
