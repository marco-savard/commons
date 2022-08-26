package com.marcosavard.commons.astro.time;

import static com.marcosavard.commons.geog.GeoLocation.LatitudeHemisphere.NORTH;
import static com.marcosavard.commons.geog.GeoLocation.LongitudeHemisphere.WEST;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
//import org.junit.Assert;
import com.marcosavard.commons.astro.Astronomy;
import com.marcosavard.commons.astro.SkyPosition;
import com.marcosavard.commons.astro.SpaceLocation;
import com.marcosavard.commons.astro.StarAlmanach;
import com.marcosavard.commons.geog.GeoLocation;


public class LocalSideralTimeDemo {

  // see http://www.stargazing.net/kepler/altaz.html

  public static void main(String[] args) {
    test0();
    test1();
    test2();
  }

  private static void test0() {
    LocalDate date = LocalDate.of(1982, 9, 5); // 5 sept
    LocalTime time = LocalTime.of(19, 6); //
    ZonedDateTime moment = ZonedDateTime.of(date, time, ZoneOffset.UTC);
    LocalSideralTime lst = LocalSideralTime.of(moment);
    System.out.println("gst : " + lst);
  }

  // https://www.webastro.net/ephemerides/conversion_temps/#resultat
  private static void test1() {
    // ..spring equinox
    LocalDate date = LocalDate.of(2020, 3, 21); // 21th March
    LocalTime time = LocalTime.MIDNIGHT; //
    ZoneId utc = ZoneOffset.UTC;
    ZonedDateTime moment = ZonedDateTime.of(date, time, utc);

    // Ephemerides
    System.out.println("Moment : " + moment);
    System.out.println("Julian day : " + JulianDay.of(moment));
    System.out.println("gst : " + LocalSideralTime.of(moment));
  }

  private static void test2() {
    // at this location (Birmingham UK)
    GeoLocation city = GeoLocation.of(52, 30, NORTH, 1, 55, WEST);

    // .. at this moment
    LocalDate date = LocalDate.of(1998, 8, 10); // 10th August 1998
    LocalTime time = LocalTime.of(23, 10); // 23:10 UT
    ZonedDateTime moment = ZonedDateTime.of(date, time, ZoneOffset.UTC);
    System.out.println("at this moment : " + moment);

    // .. at local sideral time
    double latitude = city.getLatitude().getValue();
    double longitude = city.getLongitude().getValue();
    LocalSideralTime lst = LocalSideralTime.of(moment, longitude);
    System.out.println("at this local sideral time : " + lst);

    // .. the position of the star M13..
    SpaceLocation spaceCoordinate = StarAlmanach.M13;
    SkyPosition skyPosition2 = Astronomy.findSkyPositionOf(spaceCoordinate, moment, city);

    // .. can be seen at
    System.out.println("can be seen at: " + skyPosition2);
  }
}
