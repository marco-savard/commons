package com.marcosavard.commons.astro;

import static com.marcosavard.commons.geog.GeoCoordinate.LatitudeHemisphere.NORTH;
import static com.marcosavard.commons.geog.GeoCoordinate.LongitudeHemisphere.WEST;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import com.marcosavard.commons.geog.GeoCoordinate;
import com.marcosavard.commons.geog.GeoCoordinate.Latitude;
import com.marcosavard.commons.geog.GeoCoordinate.Longitude;

public class SkyPositionDemo {

  public static void main(String[] args) {
    displayLondonUK();
    displayQuebecCity();
  }

  private static void displayLondonUK() {
    // position of the star M13..
    StarCoordinate starCoordinate = StarCoordinate.M13;

    // ..as seen from this location
    GeoCoordinate birminghamUK =
        GeoCoordinate.of(Latitude.of(52, 30, NORTH), Longitude.of(1, 55, WEST));

    // ..at this moment
    LocalDateTime localTime = LocalDateTime.of(1998, 8, 10, 23, 10, 0); // 2310 UT, 10th August 1998
    ZoneId utc = ZoneOffset.UTC;
    ZonedDateTime zdt = ZonedDateTime.of(localTime, utc);

    // get sky position
    SkyPosition skyPosition = GeoPosition.ofSkyPosition(starCoordinate, birminghamUK, zdt);

    System.out.println("Position of star M13 above Birmingham, UK on August 10st, 1998 at 23:10");
    System.out.println("  ..position of M13: " + skyPosition);
    System.out.println();
  }

  private static void displayQuebecCity() {
    System.out.println("Position of some stars above Quebec City on January 1st, 2019 at 6PM");

    // as seen at this location
    GeoCoordinate qcCity = GeoCoordinate.of(Latitude.of(46, 49, NORTH), Longitude.of(71, 13, WEST));

    // at this moment
    LocalDateTime localTime = LocalDateTime.of(2019, Month.JANUARY, 1, 18, 0, 0); // Jan1st, 6PM
    ZonedDateTime zonedTime = localTime.atZone(ZoneId.of("America/New_York"));

    // get sky position
    SkyPosition skyPosition = GeoPosition.ofSkyPosition(StarCoordinate.POLARIS, qcCity, zonedTime);
    System.out.println("  ..position of Polaris: " + skyPosition);

    skyPosition = GeoPosition.ofSkyPosition(StarCoordinate.URSA_MAJOR_EPSILON, qcCity, zonedTime);
    System.out.println("  ..position of Ursa Major Epsilon: " + skyPosition);

    skyPosition = GeoPosition.ofSkyPosition(StarCoordinate.SIRIUS, qcCity, zonedTime);
    System.out.println("  ..position of Sirius: " + skyPosition);

    skyPosition = GeoPosition.ofSkyPosition(StarCoordinate.ANTARES, qcCity, zonedTime);
    System.out.println("  ..position of Antares: " + skyPosition);

    skyPosition = GeoPosition.ofSkyPosition(StarCoordinate.CRUX_ALPHA, qcCity, zonedTime);
    System.out.println("  ..position of Crux Alpha: " + skyPosition);
    System.out.println();
  }
}
