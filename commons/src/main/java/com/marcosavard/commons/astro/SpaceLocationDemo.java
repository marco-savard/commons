package com.marcosavard.commons.astro;

import static com.marcosavard.commons.geog.GeoCoordinate.LatitudeHemisphere.NORTH;
import static com.marcosavard.commons.geog.GeoCoordinate.LongitudeHemisphere.WEST;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import org.junit.Assert;
import com.marcosavard.commons.geog.GeoCoordinate;
import com.marcosavard.commons.geog.GeoCoordinate.Latitude;
import com.marcosavard.commons.geog.GeoCoordinate.Longitude;

public class SpaceLocationDemo {

  public static void main(String[] args) {
    // System.out.println(" Position of Polaris: " + StarAlmanach.POLARIS);

    demoConversion();
    demoBirminghamUK();
    demoQuebecCity();
  }

  private static void demoConversion() {

    GeoCoordinate madrid = GeoCoordinate.of(41, -4);
    LocalDate date = LocalDate.of(1983, 2, 1);
    LocalTime time = LocalTime.of(22, 0);
    ZonedDateTime moment = ZonedDateTime.of(date, time, ZoneOffset.UTC);
    SpaceLocation sl =
        Astronomy.findSpaceLocationOf(SkyPosition.ZENITH, moment, madrid.toCoordinates());
    String msg = MessageFormat.format("({0}) is above ({1}) at {2}", sl, madrid, moment);
    System.out.println(msg);

    sl = SpaceLocation.findZenithPositionAbove(madrid, moment);
    msg = MessageFormat.format("({0}) is above ({1}) at {2}", sl, madrid, moment);
    System.out.println(msg);


    // SpaceLocation ec = SpaceLocation.of(6.2, 23.4);
    // LocalDate date = LocalDate.of(1982, 10, 3);
    // LocalTime time = LocalTime.of(1, 0);
    // ZonedDateTime moment = ZonedDateTime.of(date, time, ZoneOffset.UTC);
    //
    // GeoCoordinate coordinate2 = Astronomy.findGeoCoordinateForZenithOf(ec, moment);
    // SpaceLocation spaceLocation2 =
    // Astronomy.findSpaceLocationOf(SkyPosition.ZENITH, moment, coordinate2.toCoordinates());
    //
    // GeoCoordinate coordinate = ec.getZenithAt(moment);
    // String msg = MessageFormat.format("({0}) is above ({1}) at {2}", ec, coordinate, moment);
    // System.out.println(msg);
    //

    //
    // GeoCoordinate kourou = GeoCoordinate.of(5.2, -52.7);
    // ec = SpaceLocation.of(14.05, 5.1);
    // date = LocalDate.of(1982, 10, 16);
    // moment = ec.getMomentZenithAt(date, kourou.getLongitude().getValue());
    // msg = MessageFormat.format("({0}) is above ({1}) at {2}", ec, kourou, moment);
    // System.out.println(msg);

    System.out.println();
  }

  private static void demoBirminghamUK() {
    // position of the star M13..
    SpaceLocation starM13 = StarAlmanach.M13;
    SpaceLocation position =
        SpaceLocation.of(starM13.getRightAscensionHour(), starM13.getDeclination());

    // ..as seen from this location
    GeoCoordinate birminghamUK =
        GeoCoordinate.of(Latitude.of(52, 30, NORTH), Longitude.of(1, 55, WEST));

    // ..at this moment
    LocalDateTime localTime = LocalDateTime.of(1998, 8, 10, 23, 10, 0); // 2310 UT, 10th August 1998
    ZonedDateTime moment = ZonedDateTime.of(localTime, ZoneOffset.UTC);

    // get sky position
    SkyPosition skyPosition2 =
        Astronomy.findSkyPositionOf(starM13, moment, birminghamUK.toCoordinates());

    SkyPosition skyPosition = Astronomy.findSkyPositionOf(starM13, moment, birminghamUK);
    Assert.assertEquals(skyPosition.getAzimuth(), 269.146, 0.01);
    Assert.assertEquals(skyPosition.getHorizon(), 49.169, 0.01);

    System.out.println("Position of star M13 above Birmingham, UK on August 10st, 1998 at 23:10");
    System.out.println("  ..position of M13: " + skyPosition);
    System.out.println();

    GeoCoordinate coordinate = position.getZenithAt(moment);
    String msg = MessageFormat.format("({0}) is above ({1}) at {2}", position, coordinate, moment);
    System.out.println(msg);
  }

  private static void demoQuebecCity() {
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

    Assert.assertEquals(skyPosition.getAzimuth(), 220.889, 0.01);
    Assert.assertEquals(skyPosition.getHorizon(), -50.569, 0.01);
  }

}
