package com.marcosavard.commons.astro;

import java.util.Date;
import com.marcosavard.commons.astro.legacy.AstroDates;

public class AstroDatesTest {

  /*
  @Test
  public void testDateUnixEra() {
    int year = 1970 - 1900;
    int month = 0;
    int day = 1;
    Date unixEra = new Date(year, month, day);
    long offsetInMinutes = unixEra.getTimezoneOffset();
    double decimalOffset = (offsetInMinutes / 60.0) / 24.0;
    double expected = 2440587.5 + decimalOffset;
    testJulianDate(unixEra, expected);
  }

  @Test
  public void testDateUnixEra2() {
    long secondsSinceUnixEra = 0L;
    Date y2k = new Date(secondsSinceUnixEra);
    testJulianDate(y2k, 2440587.5);
  }

  @Test
  public void testJulianDateY2K() {
    int year = 2000 - 1900;
    int month = 0;
    int day = 1;
    Date y2k = new Date(year, month, day, 18, 0, 0);
    long offsetInMinutes = y2k.getTimezoneOffset();
    double decimalOffset = (offsetInMinutes / 60.0) / 24.0;
    double expected = 2451545.25 + decimalOffset;
    testJulianDate(y2k, expected);
  }

  private void printResults(double expected, double result) {
    System.out.println("expected : " + expected);
    System.out.println("result : " + result);
    System.out.println();
  }

  private void testJulianDate(Date date, double expected) {
    double delta = 0.001;
    double result = AstroDates.toJulianDay(date);

    printResults(expected, result);
    Assert.assertEquals(expected, result, delta);
  }
*/
}

