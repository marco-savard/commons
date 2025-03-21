package com.marcosavard.commons.astro;

import com.marcosavard.commons.astro.space.SpaceCoordinate;
import com.marcosavard.commons.astro.space.SpaceCoordinate.Declination;
import com.marcosavard.commons.astro.space.SpaceCoordinate.RightAscension;

public class StarAlmanach {

  // some famous stars
  public static final SpaceCoordinate ANTARES =
      SpaceCoordinate.of(RightAscension.of(16, 29, 24.5), Declination.of(-26, -25, -55.2));
  public static final SpaceCoordinate BETELGEUSE =
      SpaceCoordinate.of(RightAscension.of(5, 55, 10.3), Declination.of(7, 24, 25.4));
  public static final SpaceCoordinate CENTAURI_ALPHA =
      SpaceCoordinate.of(RightAscension.of(14, 39, 36.5), Declination.of(-60, -50, -2.3));
  public static final SpaceCoordinate CRUX_ALPHA =
      SpaceCoordinate.of(RightAscension.of(12, 26, 35.9), Declination.of(-63, -5, -56.7));
  public static final SpaceCoordinate M13 =
      SpaceCoordinate.of(RightAscension.of(16, 41, 42), Declination.of(36, 28, 0));
  public static final SpaceCoordinate POLARIS =
      SpaceCoordinate.of(RightAscension.of(2, 31, 48.7), Declination.of(89, 15, 51));
  public static final SpaceCoordinate SIRIUS =
      SpaceCoordinate.of(RightAscension.of(6, 45, 8.9), Declination.of(-16, -42, -58));
  public static final SpaceCoordinate URSA_MAJOR_EPSILON =
      SpaceCoordinate.of(RightAscension.of(12, 54, 1.6), Declination.of(55, 57, 34.4));
}
