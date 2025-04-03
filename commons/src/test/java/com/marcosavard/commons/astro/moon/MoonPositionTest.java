package com.marcosavard.commons.astro.moon;

import junit.framework.Assert;
import org.junit.jupiter.api.Test;
//import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class MoonPositionTest {

  // https://mooncalendar.astro-seek.com/moon-phases-calendar-february-1984
   @Test
   void test19840202() {
    LocalDate date = LocalDate.of(1984, 2, 2);
    LocalTime time = LocalTime.MIDNIGHT;
    ZonedDateTime moment = ZonedDateTime.of(date, time, ZoneOffset.UTC);
    double moonAge = MoonPosition.at(moment).getMoonAge();
    Assert.assertEquals(0, moonAge, 1.0);
  }

  // https://mooncalendar.astro-seek.com/moon-phases-calendar-january-2020
  @Test
   void test20200110() {
    LocalDate date = LocalDate.of(2020, 1, 1);
    ZonedDateTime fullMoon = MoonPosition.findNextFullMoon(date);

    LocalDate expectedDate = LocalDate.of(2020, 1, 10);
    LocalTime expectedTime = LocalTime.of(19, 21);
    ZonedDateTime expected = ZonedDateTime.of(expectedDate, expectedTime, ZoneOffset.UTC);

    long epsilon = 15 * 60; // 15 minutes
    System.out.println("fullMoon " + fullMoon);
    Assert.assertEquals(expected.toEpochSecond(), fullMoon.toEpochSecond(), epsilon);
  }
}
