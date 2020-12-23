package com.marcosavard.commons.astro;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.JulianFields;
import org.junit.Assert;
import com.marcosavard.commons.debug.StopWatch;

public class JulianDayDemo {

  // Get day count since Monday, January 1, 4713 BC https://en.wikipedia.org/wiki/Julian_day
  // validate w/ https://core2.gsfc.nasa.gov/time/julian.html
  // https://quasar.as.utexas.edu/BillInfo/JulianDatesG.html
  public static void main(String[] args) {
    testJulianDay(1968, 5, 23, 2439999.5);
    testJulianDay(1969, 7, 21, 2440423.5);
    testJulianDay(1980, 1, 1, 2444239.5);
    testJulianDay(1982, 1, 1, 2444970.5);
    testJulianDay(2000, 1, 1, 2451544.5);

    demoCriticalDates();
    // demoList20thCentury();
    // testPerformance();

    /*
     * demoBeforeGregorianEra();
     */
  }


  private static void testJulianDay(int year, int month, int dayOfMonth, double expected) {
    LocalDate date = LocalDate.of(year, month, dayOfMonth);
    JulianDay jd = JulianDay.of(date);

    Assert.assertEquals(expected, jd.getValue(), 0.1);
    Assert.assertEquals(date, jd.toLocalDate());
    System.out.println(date + " : " + jd);

    ZonedDateTime moment = ZonedDateTime.of(date, LocalTime.NOON, ZoneOffset.UTC);
    long julianDay = JulianFields.JULIAN_DAY.getFrom(moment);
    System.out.println(date + " : " + julianDay);
    System.out.println();
  }

  private static void demoCriticalDates() {
    LocalDate date;
    JulianDay jd;

    jd = JulianDay.of(JulianDay.GREGORIAN_ERA);
    double expected = 2299160.5;
    Assert.assertEquals(expected, jd.getValue(), 0.1);
    System.out.println(JulianDay.GREGORIAN_ERA + " : " + jd);

    ZonedDateTime moment =
        ZonedDateTime.of(JulianDay.GREGORIAN_ERA, LocalTime.NOON, ZoneOffset.UTC);
    long julianDay = JulianFields.JULIAN_DAY.getFrom(moment);
    System.out.println("JD : " + julianDay);
    System.out.println();
  }

  private static void demoList20thCentury() {
    for (int i = 0; i <= 10; i++) {
      // Jan 01
      int year = 1900 + 10 * i;
      LocalDate date = LocalDate.of(year, 1, 1);
      JulianDay jd = JulianDay.of(date);
      JulianDay jd2 = JulianDay.of(year, 1, 1, 0L);
      Assert.assertEquals(date, jd.toLocalDate());
      Assert.assertEquals(date, jd2.toLocalDate());
      System.out.println(" " + date + " : " + jd);

      ZonedDateTime moment = ZonedDateTime.of(date, LocalTime.NOON, ZoneOffset.UTC);
      long julianDay = JulianFields.JULIAN_DAY.getFrom(moment);
      System.out.println(date + " : " + julianDay);
      System.out.println();

      // Jul 01
      date = LocalDate.of(year, 7, 1);
      jd = JulianDay.of(date);
      Assert.assertEquals(date, jd.toLocalDate());
      System.out.println(" " + date + " : " + jd);

      moment = ZonedDateTime.of(date, LocalTime.NOON, ZoneOffset.UTC);
      julianDay = JulianFields.JULIAN_DAY.getFrom(moment);
      System.out.println(date + " : " + julianDay);
      System.out.println();
    }
  }

  private static void demoBeforeGregorianEra() {
    // date = LocalDate.of(-4713, 1, 1);
    // jd = JulianDay.of(date);
    // System.out.println(date + " : " + jd);
  }

  private static void testPerformance() {
    int nb = 0;
    StopWatch sw = new StopWatch();
    sw.start();

    for (int y = 1600; y <= 2020; y++) {
      for (int m = 1; m <= 12; m++) {
        for (int d = 1; d < 29; d++) {
          LocalDate date = LocalDate.of(y, m, d);
          JulianDay jd = JulianDay.of(date);
          nb++;
        }
      }
    }

    sw.end();
    long time = sw.getTime();
    String msg = MessageFormat.format("Computing {0} Julian days takes {1} ms", nb, time);
    System.out.println(msg);
    nb = 0;

    sw = new StopWatch();
    sw.start();
    for (int y = 1600; y <= 2020; y++) {
      for (int m = 1; m <= 12; m++) {
        for (int d = 1; d < 29; d++) {
          JulianDay jd = JulianDay.of(y, m, d);
          nb++;
        }
      }
    }
    sw.end();
    time = sw.getTime();
    msg = MessageFormat.format("Computing {0} Julian days takes {1} ms", nb, time);
    System.out.println(msg);

    System.out.println();
  }


}
