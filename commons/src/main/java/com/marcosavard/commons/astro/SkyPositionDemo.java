package com.marcosavard.commons.astro;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import com.marcosavard.commons.geog.GeoCoordinate;
import com.marcosavard.commons.geog.GeoCoordinate.Latitude;
import com.marcosavard.commons.geog.GeoCoordinate.Longitude;
import com.marcosavard.commons.time.Dates;

public class SkyPositionDemo {

  public static void main(String[] args) {
    displayLondonUK();
    displayQuebecCity();
  }

  private static void displayLondonUK() {
    System.out.println("Position of some stars above London on August 10st, 1998 at 23:10");

    // as seen at this location
    GeoCoordinate london = GeoCoordinate.of(Latitude.of(52, 30), Longitude.of(1, 55));

    // at this moment
    LocalDateTime localTime = LocalDateTime.of(1998, 8, 10, 23, 10, 0); // 2310 UT, 10th August 1998
    ZonedDateTime zonedTime = localTime.atZone(ZoneId.of("America/New_York"));
    Date moment = Dates.toDate(zonedTime);
    double lst = LocalSideralTime.getLst(moment, london.getLongitude().getValue());
    SkyPosition skyPosition;

    // compute sky position
    skyPosition = SkyPosition.compute(StarCoordinate.M13, london, lst);
    System.out.println("  ..position of M13: " + skyPosition);
    System.out.println();
  }

  private static void displayQuebecCity() {
    System.out.println("Position of some stars above Quebec City on January 1st, 2019 at 6PM");

    // as seen at this location
    GeoCoordinate qcCity = GeoCoordinate.of(Latitude.of(46, 49), Longitude.of(-71, 13));

    // at this moment
    LocalDateTime localTime = LocalDateTime.of(2019, Month.JANUARY, 1, 18, 0, 0); // Jan1st, 6PM
    ZonedDateTime zonedTime = localTime.atZone(ZoneId.of("America/New_York"));
    Date moment = Dates.toDate(zonedTime);
    double lst = LocalSideralTime.getLst(moment, qcCity.getLongitude().getValue());
    SkyPosition skyPosition;

    // compute sky position
    skyPosition = SkyPosition.compute(StarCoordinate.POLARIS, qcCity, lst);
    System.out.println("  ..position of Polaris: " + skyPosition);

    skyPosition = SkyPosition.compute(StarCoordinate.URSA_MAJOR_EPSILON, qcCity, lst);
    System.out.println("  ..position of Ursa Major Epsilon: " + skyPosition);

    skyPosition = SkyPosition.compute(StarCoordinate.SIRIUS, qcCity, lst);
    System.out.println("  ..position of Sirius: " + skyPosition);

    skyPosition = SkyPosition.compute(StarCoordinate.ANTARES, qcCity, lst);
    System.out.println("  ..position of Antares: " + skyPosition);

    skyPosition = SkyPosition.compute(StarCoordinate.CRUX_ALPHA, qcCity, lst);
    System.out.println("  ..position of Crux Alpha: " + skyPosition);
    System.out.println();
  }
}