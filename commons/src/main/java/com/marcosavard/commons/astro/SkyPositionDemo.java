package com.marcosavard.commons.astro;

import com.marcosavard.commons.astro.space.SpaceCoordinate;
import com.marcosavard.commons.astro.star.Star;
import com.marcosavard.commons.debug.Console;
import com.marcosavard.commons.geog.GeoLocation;
import com.marcosavard.commons.time.JulianDay;
import com.marcosavard.commons.time.StandardZoneId;

import java.time.*;
import java.time.temporal.ChronoUnit;

import static com.marcosavard.commons.geog.GeoLocation.LatitudeHemisphere.NORTH;
import static com.marcosavard.commons.geog.GeoLocation.LongitudeHemisphere.WEST;

public class SkyPositionDemo {

  public static void main(String[] args) {
    displayLondonUK();
    displayQuebecCity(LocalDate.now());
  }

  // http://www.stargazing.net/kepler/altaz.html
  private static void displayLondonUK() {
    // position of the star M13..
   SpaceCoordinate spaceCoord = Star.M13.coordinate();

    // ..as seen from this location
    GeoLocation birminghamUK = GeoLocation.of(52, 30, NORTH, 1, 55, WEST);

    // ..at this moment
    LocalDateTime localTime = LocalDateTime.of(1998, 8, 10, 23, 10, 0); // 2310 UT, 10th August 1998
    ZonedDateTime moment = ZonedDateTime.of(localTime, ZoneOffset.UTC);

    // get sky position
    double[] coordinates = birminghamUK.toCoordinates();
    SkyPosition skyPosition = Astronomy.findSkyPositionOf(spaceCoord, moment, coordinates);

    SpaceCoordinate spaceCoordinate = Astronomy.findSpaceCoordinateOf(skyPosition, moment, coordinates);

    Console.println("Position of star M13 above Birmingham, UK on August 10st, 1998 at 23:10");
    Console.println("  ..position of M13: " + skyPosition);
    Console.println("  ..spaceCoordinate of M13: " + spaceCoordinate);
    Console.println();
  }

  private static void displayQuebecCity(LocalDate date) {
    LocalDateTime localTime = LocalDateTime.of(date, LocalTime.MIDNIGHT);
    ZonedDateTime moment = localTime.atZone(StandardZoneId.AMERICA_NEW_YORK.getZoneId());
    Console.println("Position of some stars above Quebec City on {0}", moment);

    // as seen at this location
    GeoLocation qcCity = GeoLocation.of(46, 49, NORTH, 71, 13, WEST);
    double[] coordinates = qcCity.toCoordinates();

    // at this moment
    SkyPosition skyPosition = Astronomy.findSkyPositionOf(Star.POLARIS.coordinate(), moment, coordinates);
    SpaceCoordinate coord = Astronomy.findSpaceCoordinateOf(skyPosition, moment, coordinates);
    Console.println("  ..position of Polaris: {0} [{1}]", skyPosition, coord);

    skyPosition = Astronomy.findSkyPositionOf(Star.ALIOTH.coordinate(), moment, coordinates);
    Console.println("  ..position of Ursa Major Epsilon: " + skyPosition);

    skyPosition = Astronomy.findSkyPositionOf(Star.SIRIUS.coordinate(), moment, coordinates);
    Console.println("  ..position of Sirius: " + skyPosition);

    skyPosition = Astronomy.findSkyPositionOf(Star.ANTARES.coordinate(), moment, coordinates);
    Console.println("  ..position of Antares: " + skyPosition);

    skyPosition = Astronomy.findSkyPositionOf(Star.ACRUX.coordinate(), moment, coordinates);
    Console.println("  ..position of Crux Alpha: " + skyPosition);



    Console.println();

  }
}
