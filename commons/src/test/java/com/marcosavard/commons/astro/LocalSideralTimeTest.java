package com.marcosavard.commons.astro;

import com.marcosavard.commons.astro.time.LocalSideralTime;
import com.marcosavard.commons.geog.GeoLocation;
import junit.framework.Assert;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import static com.marcosavard.commons.geog.GeoLocation.LatitudeHemisphere.NORTH;
import static com.marcosavard.commons.geog.GeoLocation.LongitudeHemisphere.WEST;

public class LocalSideralTimeTest {

  // @Test
  public void test() {
    LocalDate date = LocalDate.of(1982, 9, 5); // 5 sept
    LocalTime time = LocalTime.of(19, 6); //
    ZonedDateTime moment = ZonedDateTime.of(date, time, ZoneOffset.UTC);
    LocalSideralTime lst = LocalSideralTime.of(moment);
    Assert.assertEquals(18.262, lst.hours(), 0.2);
  }

  //  @Test
  public void test2() {
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
    /*
    SpaceCoordinate spaceCoordinate = StarAlmanach.M13;
    SkyPosition skyPosition2 = Astronomy.findSkyPositionOf(spaceCoordinate, moment, city);

    // .. can be seen at
    System.out.println("can be seen at: " + skyPosition2);
    Assert.assertEquals(skyPosition2.getAzimuth(), 269.146, 0.01);
    Assert.assertEquals(skyPosition2.getHorizon(), 49.169, 0.01);*/
  }
}
