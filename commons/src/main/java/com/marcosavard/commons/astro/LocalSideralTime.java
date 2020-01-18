package com.marcosavard.commons.astro;

import java.time.ZonedDateTime;
import java.util.Date;

public class LocalSideralTime {
  public static final double JULIAN_DAY_Y2K_AT_NOON_UTC = 2451545.0;

  /**
   * Return the local sideral time at a given moment, for a given logitude
   * 
   * @param moment a give moment
   * @param longitude from -180 to +180
   * @return lst (0 to 360)
   */
  public static double of(ZonedDateTime zdt, double longitude) {
    // computation
    double jd = AstroDates.toJulianDay(zdt);
    double ut = toDecimalHours(zdt);
    double d = (jd - JULIAN_DAY_Y2K_AT_NOON_UTC);
    double lst = 100.46 + 0.985647 * d + longitude + 15 * ut;
    double positiveLst = valueInRange(lst, 360);
    return positiveLst;
  }

  public static double of(Date moment, double longitude) {
    // computation
    double jd = AstroDates.toJulianDay(moment);
    double ut = toDecimalHours(moment);
    double d = (jd - JULIAN_DAY_Y2K_AT_NOON_UTC);
    double lst = 100.46 + 0.985647 * d + longitude + 15 * ut;
    lst = valueInRange(lst, 360);
    return lst;
  }

  private static double toDecimalHours(ZonedDateTime zdt) {
    double decimalHours = zdt.getHour();
    decimalHours += zdt.getMinute() / 60.0;
    decimalHours += zdt.getSecond() / 3600.0;
    return decimalHours;
  }

  private static double toDecimalHours(Date datetime) {
    double decimalHours = datetime.getHours();
    decimalHours += datetime.getMinutes() / 60.0;
    decimalHours += datetime.getSeconds() / 3600.0;
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


}
