package com.marcosavard.commons.astro.finder;

import java.text.MessageFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * A class that computes sunrise, local noon and sunset times
 *
 * <p>References: https://stjarnhimlen.se/comp/riset.html
 * https://www.edwilliams.org/sunrise_sunset_example.htm
 *
 * @author Marco
 */
public class SunTimes {
  public static final double OFFICIAL_HORIZON = 90.833;
  public static final double CIVIL_HORIZON = 96;
  public static final double NAUTICAL_HORIZON = 102;
  public static final double ASTRONOMICAL_HORIZON = 108;

  private static final Logger logger = Logger.getLogger(SunTimes.class.getName());

  // input
  private LocalDate date;
  private double lat, lon;
  private ZoneId zoneId;
  private double zenith;

  // results
  private LocalTime sunrise, localNoon, sunset;
  private double dayLength;
  private double sunriseDirection, sunsetDirection;
  private Map<String, LocalTime> values = new LinkedHashMap<>();

  public static SunTimes of(LocalDate date, double[] location, ZoneId zoneId, double zenith) {
    SunTimes sunTimes = new SunTimes(date, location[0], location[1], zoneId, zenith);
    return sunTimes;
  }

  private SunTimes(LocalDate date, double lat, double lon, ZoneId zoneId, double zenith) {
    this.date = date;
    this.lat = lat;
    this.lon = lon;
    this.zoneId = zoneId;
    this.zenith = zenith;
    compute();
  }

  private void compute() {
    // day of year
    int dayOfYear = date.getDayOfYear();

    // convert the longitude to hour
    double lngHour = this.lon / 15;

    // convert approximate times
    double tRisingApprox = dayOfYear + ((6 - lngHour) / 24);
    double tSettingApprox = dayOfYear + ((18 - lngHour) / 24);

    // compute sun location at rising, setting
    SunLocation risingLocation = SunLocation.at(tRisingApprox, lat, true);
    SunLocation settingLocation = SunLocation.at(tSettingApprox, lat, false);

    // calculate the Sun's local hour angle
    double h1 = risingLocation.getLocalHourAngle(lat, zenith);
    double h2 = settingLocation.getLocalHourAngle(lat, zenith);

    // compute rising and setting times, universal time
    double hRising = 360 - h1;
    double hSetting = h2;

    double hRisingHour = hRising / 15;
    double hSettingHour = hSetting / 15;

    double raHour1 = risingLocation.getRightAscensionInHours();
    double raHour2 = settingLocation.getRightAscensionInHours();

    double tRising = range(hRisingHour + raHour1 - (0.06571 * tRisingApprox) - 6.622, 24);
    double tSetting = range(hSettingHour + raHour2 - (0.06571 * tSettingApprox) - 6.622, 24);

    double utRising = range(tRising - lngHour, 24);
    double utSetting = range(tSetting - lngHour, 24);

    System.out.println("  utRising = " + utRising);
    System.out.println("  utSetting = " + utSetting);

    // find offset
    Instant instant = date.atStartOfDay(zoneId).toInstant();
    ZoneOffset zoneOffSet = zoneId.getRules().getOffset(instant);
    double localOffset = zoneOffSet.getTotalSeconds() / 3600;

    // compute rising and setting times, local time
    double localTRising = range(utRising + localOffset, 24);
    double localTSetting = range(utSetting + localOffset, 24);
    double localNoonT = range((localTRising + localTSetting) / 2, 24);

    System.out.println("  localTRising = " + localTRising);
    System.out.println("  localTSetting = " + localTSetting);

    sunrise = toLocalTime(localTRising);
    sunset = toLocalTime(localTSetting);
    localNoon = toLocalTime(localNoonT);
    sunriseDirection = risingLocation.getDirection();
    sunsetDirection = settingLocation.getDirection();

    dayLength = Math.abs(localTSetting - localTRising);

    if (Double.isNaN(localTRising)) {
      dayLength = risingLocation.getDayLength();
    }

    values.put("sunrise", sunrise);
    values.put("localNoon", localNoon);
    values.put("sunset", sunset);
  }

  private LocalTime toLocalTime(double decimalHour) {
    decimalHour = range(decimalHour, 24);
    int hours = (int) Math.round(Math.floor(decimalHour));
    int minutes = (int) Math.round(Math.floor((decimalHour - hours) * 60));
    LocalTime localTime = LocalTime.of(hours, minutes);
    return localTime;
  }

  private static double asind(double degree) {
    return range(Math.toDegrees(Math.asin(degree)), 360);
  }

  private static double acosd(double degree) {
    return range(Math.toDegrees(Math.acos(degree)), 360);
  }

  private static double atan2d(double y, double x) {
    return range(Math.toDegrees(Math.atan2(y, x)), 360);
  }

  private static double tand(double degree) {
    return Math.tan(Math.toRadians(degree));
  }

  private static double sind(double degree) {
    return Math.sin(Math.toRadians(degree));
  }

  private static double cosd(double degree) {
    return Math.cos(Math.toRadians(degree));
  }

  private static double range(double value, double range) {
    double ranged = value - Math.floor(value / range) * range;
    return ranged;
  }

  public LocalTime getSunRise() {
    return sunrise;
  }

  @Override
  public String toString() {
    String s =
        MessageFormat.format(
            "{0} {1} riseAt={2}, setAt{3} dayLenth={4}", //
            values.toString(), zoneId.getId(), sunriseDirection, sunsetDirection, dayLength);
    return s;
  }

  private static class SunLocation {
    private double declination;
    private double rightAscension;
    private double dayLength;
    private double direction;

    public static SunLocation at(double time, double lat, boolean sunrise) {
      SunLocation location = new SunLocation(time, lat, sunrise);
      return location;
    }

    private SunLocation(double time, double lat, boolean sunrise) {
      // convert meanAnomaly
      double m1 = (0.9856 * time) - 3.289;

      // convert trueLongitude
      double lon1 = range(m1 + (1.916 * sind(m1)) + (0.020 * sind(2 * m1)) + 282.634, 360);

      // compute right ascension, in degrees
      double tanLon1 = tand(lon1);
      rightAscension = atan2d(0.91764, 1 / tanLon1);

      // compute declination, in degrees
      double sinDec1 = 0.39782 * sind(lon1);
      double cosLat = cosd(lat);
      declination = asind(sinDec1);

      // compute direction (90=east, 270=west)
      double sinA = sinDec1 / cosLat;
      double a = asind(sinA);
      direction = sunrise ? 90 - a : 270 + a;
    }

    public double getLocalHourAngle(double lat, double zenith) {
      double sinV1 = sind(declination) * sind(lat);
      double cosV1 = cosd(declination) * cosd(lat);
      double cosH = (cosd(zenith) - sinV1) / cosV1;
      double h = acosd(cosH);

      if (cosH > 1) {
        dayLength = 0; // polar night
      } else if (cosH < 1) {
        dayLength = 24; // midnight sun
      }

      return h;
    }

    public double getRightAscensionInHours() {
      return (rightAscension / 15);
    }

    public double getDayLength() {
      return dayLength;
    }

    public double getDirection() {
      return direction;
    }
  }
}
