package com.marcosavard.commons.astro.time;

import java.text.MessageFormat;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class LocalSideralTime {
  public static final double JULIAN_DAY_Y2K_AT_NOON_UTC = 2451545.0;
  private double degrees;

  public static LocalSideralTime of(ZonedDateTime moment) {
    return of(moment, 0.0);
  }

  /**
   * Return the local sideral time at a given moment, for a given logitude
   * 
   * @param moment a give moment
   * @param longitude from -180 to +180
   * @return lst (0 to 360)
   */
  public static LocalSideralTime of(ZonedDateTime moment, double longitude) {
	ZonedDateTime momentUt = TimeConverter.toZonedDateTime(moment, ZoneOffset.UTC);
    double jd = JulianDay.of(momentUt).getValue();
    double ut = toDecimalHours(momentUt);
    double d = (jd - JULIAN_DAY_Y2K_AT_NOON_UTC);
    double degrees = 100.46 + 0.985647 * d + longitude + 15 * ut;
    degrees = valueInRange(degrees, 360);
    LocalSideralTime lst = new LocalSideralTime(degrees);
    return lst;
  }

  public LocalSideralTime(double degrees) {
    this.degrees = degrees;
  }

  public double degrees() {
    return degrees;
  }

  public double hours() {
    double hours = (degrees / 360) * 24;
    return hours;
  }

  private static double toDecimalHours(ZonedDateTime zdt) {
    double decimalHours = zdt.getHour();
    decimalHours += zdt.getMinute() / 60.0;
    decimalHours += zdt.getSecond() / 3600.0;
    return decimalHours;
  }

  // keep value within the range [0..range]
  // for instances:
  // valueInRange(90, 360) gives 90
  // valueInRange(-90, 360) gives 270
  private static double valueInRange(double value, int max) {
    // keep in range [0..max]
    value = (value > 0) ? (value % max) : (max - Math.abs(value) % max) % max;
    return value;
  }

  @Override
  public String toString() {
    String msg = MessageFormat.format("{0} hr", String.format("%.2f", hours()));
    return msg;
  }


}
