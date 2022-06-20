package com.marcosavard.commons.astro;

import static com.marcosavard.commons.geog.GeoLocation.LatitudeHemisphere.NORTH;
import static com.marcosavard.commons.geog.GeoLocation.LongitudeHemisphere.WEST;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import com.marcosavard.commons.astro.SpaceLocation.Unit;
import com.marcosavard.commons.debug.Console;
import com.marcosavard.commons.geog.GeoLocation;

public class SpaceLocationDemo {

  public static void main(String[] args) {
    demoEquinoxPrecession();
    demoConversion();
    demoFindPolaris();

    // demoBirminghamUK();
    // demoQuebecCity();

  }

  private static void demoFindPolaris() {
    SpaceLocation northPole = SpaceLocation.of(0, 90);

    List<SpaceLocation> almanach = new ArrayList<>();
    almanach.add(StarAlmanach.ANTARES);
    almanach.add(StarAlmanach.BETELGEUSE);
    almanach.add(StarAlmanach.CENTAURI_ALPHA);
    almanach.add(StarAlmanach.CRUX_ALPHA);
    almanach.add(StarAlmanach.POLARIS);

    for (SpaceLocation location : almanach) {
      double distanceRad = location.distanceFrom(northPole);
      double distance = Math.toDegrees(distanceRad);
      Console.println("  " + distance);
    }

  }

  private static void demoConversion() {

    GeoLocation madrid = GeoLocation.of(41, -4);
    LocalDate date = LocalDate.of(1983, 2, 1);
    LocalTime time = LocalTime.of(22, 0);
    ZonedDateTime moment = ZonedDateTime.of(date, time, ZoneOffset.UTC);
    SpaceLocation sl =
        Astronomy.findSpaceLocationOf(SkyPosition.ZENITH, moment, madrid.toCoordinates());
    String msg = MessageFormat.format("({0}) is above ({1}) at {2}", sl, madrid, moment);
    System.out.println(msg);

    sl = SpaceLocation.findZenithPositionAbove(madrid.toCoordinates(), moment);
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
    GeoLocation birminghamUK = GeoLocation.of(52, 30, NORTH, 1, 55, WEST);

    // ..at this moment
    LocalDateTime localTime = LocalDateTime.of(1998, 8, 10, 23, 10, 0); // 2310 UT, 10th August 1998
    ZonedDateTime moment = ZonedDateTime.of(localTime, ZoneOffset.UTC);

    // get sky position
    SkyPosition skyPosition2 =
        Astronomy.findSkyPositionOf(starM13, moment, birminghamUK.toCoordinates());

    SkyPosition skyPosition = Astronomy.findSkyPositionOf(starM13, moment, birminghamUK);
    System.out.println("Position of star M13 above Birmingham, UK on August 10st, 1998 at 23:10");
    System.out.println("  ..position of M13: " + skyPosition);
    System.out.println();

    double[] coordinates = position.getZenithAt(moment);
    GeoLocation location = GeoLocation.of(coordinates[0], coordinates[1]); 
    String msg = MessageFormat.format("({0}) is above ({1}) at {2}", position, location, moment);
    System.out.println(msg);
  }

  private static void demoQuebecCity() {
    System.out.println("Position of some stars above Quebec City on January 1st, 2019 at 6PM");

    // as seen at this location
    GeoLocation qcCity = GeoLocation.of(46, 49, NORTH, 71, 13, WEST);

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

  private static void demoEquinoxPrecession() {
    SpaceLocation alcyone1950 = SpaceLocation.of(3, 44, 30, Unit.HOUR, 24, 0, 0, Unit.DEGREE);
    LocalDate year1950 = LocalDate.of(1950, 1, 1);
    System.out.println("alcyone1950 : " + alcyone1950.toString());

    LocalDate year1981 = LocalDate.of(1981, 7, 2);
    SpaceLocation alcyone1981 = SpaceLocation.findSpaceLocation(alcyone1950, year1950, year1981);
    System.out.println("alcyone1981 : " + alcyone1981.toString());

    LocalDate year2000 = LocalDate.of(2000, 1, 1);
    SpaceLocation alcyone2000 = SpaceLocation.findSpaceLocation(alcyone1950, year1950, year2000);
    System.out.println("alcyone2020 : " + alcyone2000.toString());
  }
}
