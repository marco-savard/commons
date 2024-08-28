package com.marcosavard.commons.astro.time;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.JulianFields;
// import org.junit.Assert;

public class JulianDayDemo {

  public static void main(String[] args) {
    testJulianDay(1968, 5, 23, 2439999.5);
    testJulianDay(1969, 7, 21, 2440423.5);
    testJulianDay(1980, 1, 1, 2444239.5);
    testJulianDay(1982, 1, 1, 2444970.5);
    testJulianDay(2000, 1, 1, 2451544.5);

    demoList20thCentury();
  }

  private static void testJulianDay(int year, int month, int dayOfMonth, double expected) {
    LocalDate date = LocalDate.of(year, month, dayOfMonth);
    JulianDay jd = JulianDay.of(date);
    System.out.println(date + " : " + jd);

    ZonedDateTime moment = ZonedDateTime.of(date, LocalTime.NOON, ZoneOffset.UTC);
    long julianDay = JulianFields.JULIAN_DAY.getFrom(moment);
    System.out.println(date + " : " + julianDay);
    System.out.println();
  }

  private static void demoList20thCentury() {
    for (int i = 0; i <= 10; i++) {
      // Jan 01
      int year = 1900 + 10 * i;
      LocalDate date = LocalDate.of(year, 1, 1);
      JulianDay jd = JulianDay.of(date);
      JulianDay jd2 = JulianDay.of(year, 1, 1, 0L);
      System.out.println(" " + date + " : " + jd);

      ZonedDateTime moment = ZonedDateTime.of(date, LocalTime.NOON, ZoneOffset.UTC);
      long julianDay = JulianFields.JULIAN_DAY.getFrom(moment);
      System.out.println(date + " : " + julianDay);
      System.out.println();

      // Jul 01
      date = LocalDate.of(year, 7, 1);
      jd = JulianDay.of(date);
      System.out.println(" " + date + " : " + jd);

      moment = ZonedDateTime.of(date, LocalTime.NOON, ZoneOffset.UTC);
      julianDay = JulianFields.JULIAN_DAY.getFrom(moment);
      System.out.println(date + " : " + julianDay);
      System.out.println();
    }
  }
}
