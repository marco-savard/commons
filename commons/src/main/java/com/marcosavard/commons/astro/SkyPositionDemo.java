package com.marcosavard.commons.astro;

import static com.marcosavard.commons.geog.GeoCoordinate.LatitudeHemisphere.NORTH;
import static com.marcosavard.commons.geog.GeoCoordinate.LongitudeHemisphere.WEST;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import org.junit.Assert;
import com.marcosavard.commons.geog.GeoCoordinate;
import com.marcosavard.commons.geog.GeoCoordinate.Latitude;
import com.marcosavard.commons.geog.GeoCoordinate.Longitude;

public class SkyPositionDemo {

  public static void main(String[] args) {
    displayLondonUK();
    displayQuebecCity();
  }

  // http://www.stargazing.net/kepler/altaz.html
  private static void displayLondonUK() {
    // position of the star M13..
    SpaceLocation coordinate = StarAlmanach.M13;

    // ..as seen from this location
    GeoCoordinate birminghamUK =
        GeoCoordinate.of(Latitude.of(52, 30, NORTH), Longitude.of(1, 55, WEST));

    // ..at this moment
    LocalDateTime localTime = LocalDateTime.of(1998, 8, 10, 23, 10, 0); // 2310 UT, 10th August 1998
    ZonedDateTime moment = ZonedDateTime.of(localTime, ZoneOffset.UTC);

    // get sky position
    SkyPosition skyPosition =
        Astronomy.findSkyPositionOf(coordinate, moment, birminghamUK.toCoordinates());
    Assert.assertEquals(skyPosition.getAzimuth(), 269.146, 0.01);
    Assert.assertEquals(skyPosition.getHorizon(), 49.169, 0.01);

    System.out.println("Position of star M13 above Birmingham, UK on August 10st, 1998 at 23:10");
    System.out.println("  ..position of M13: " + skyPosition);
    System.out.println();

    SkyPosition skyPosition2 =
        Astronomy.findSkyPositionOfOld(coordinate, moment, birminghamUK.toCoordinates());
    System.out.println("  ..position2 of M13: " + skyPosition2);
    System.out.println();
  }

  private static void displayQuebecCity() {
    System.out.println("Position of some stars above Quebec City on January 1st, 2019 at 6PM");

    // as seen at this location
    GeoCoordinate qcCity = GeoCoordinate.of(Latitude.of(46, 49, NORTH), Longitude.of(71, 13, WEST));

    // at this moment
    LocalDateTime localTime = LocalDateTime.of(2019, Month.JANUARY, 1, 18, 0, 0); // Jan1st, 6PM
    ZonedDateTime moment = localTime.atZone(ZoneId.of("America/New_York"));

    // get sky position
    SkyPosition skyPosition = Astronomy.findSkyPositionOf(StarAlmanach.POLARIS, moment, qcCity);
    System.out.println("  ..position of Polaris: " + skyPosition);

    skyPosition = Astronomy.findSkyPositionOf(StarAlmanach.URSA_MAJOR_EPSILON, moment, qcCity);
    System.out.println("  ..position of Ursa Major Epsilon: " + skyPosition);

    skyPosition = Astronomy.findSkyPositionOf(StarAlmanach.SIRIUS, moment, qcCity);
    System.out.println("  ..position of Sirius: " + skyPosition);

    skyPosition = Astronomy.findSkyPositionOf(StarAlmanach.ANTARES, moment, qcCity);
    System.out.println("  ..position of Antares: " + skyPosition);

    skyPosition = Astronomy.findSkyPositionOf(StarAlmanach.CRUX_ALPHA, moment, qcCity);
    System.out.println("  ..position of Crux Alpha: " + skyPosition);

    System.out.println();
  }
}
