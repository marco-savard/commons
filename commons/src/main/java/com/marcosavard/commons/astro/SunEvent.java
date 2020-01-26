package com.marcosavard.commons.astro;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.TimeZone;

/*
 * based on: http://williams.best.vwh.net/sunrise_sunset_algorithm.htm
 * 
 * Almanac for Computers, 1990 published by Nautical Almanac Office United States Naval Observatory
 * Washington, DC 20392
 */
public class SunEvent {
  private static final double EARTH_OBLIQUITY = 23.5;
  private static final double RADS_PER_DEG = Math.PI / 180.0;
  private static final long ONE_HOUR = 1000L * 60 * 60;
  private static final long ONE_DAY = 24 * ONE_HOUR;
  private static final double ZENITH = 90 * RADS_PER_DEG; // official
  private static final long SUN_CIRCLE = 6 * 60 * 1000L; // 6 minutes for Sun's circle

  private ZonedDateTime sunrise = null, sunset = null;
  private double angleAboveHorizonAtNoon;

  public static SunEvent of(LocalDate localDate, double[] coordinates, TimeZone timezone) {
    double latitude = coordinates[0];
    double longitude = coordinates[1];
    SunEvent sunEvent = of(localDate, latitude, longitude, timezone);
    return sunEvent;
  }

  public static SunEvent of(LocalDate localDate, double latitude, double longitude,
      TimeZone timezone) {
    int dayOfYear = localDate.getDayOfYear();
    Date date = AstroDates.toDate(localDate);
    long midnightTime = date.getTime();
    boolean isDaylight = timezone.inDaylightTime(date);
    SunEvent sunEvent =
        new SunEvent(dayOfYear, midnightTime, isDaylight, latitude, longitude, timezone);
    return sunEvent;
  }

  public static SunEvent of(Date date, double latitude, double longitude, TimeZone timezone) {
    int year = date.getYear() + 1900;
    int month = date.getMonth() + 1;
    int dayOfMonth = date.getDate();
    Date midnight = new Date(year, month, dayOfMonth);
    int dayOfYear = computeDayOfYear(year, month, dayOfMonth);
    long midnightTime = midnight.getTime();
    boolean isDaylight = timezone.inDaylightTime(date);
    SunEvent sunEvent =
        new SunEvent(dayOfYear, midnightTime, isDaylight, latitude, longitude, timezone);
    return sunEvent;
  }

  private SunEvent(int dayOfYear, long midnightTime, boolean isDaylight, double latitude,
      double longitude, TimeZone timezone) {
    double longitudeHour = longitude / 15.0;

    // compute sunrise
    double sunriseTime = computeSunEvent(dayOfYear, longitudeHour, 6.0);
    double sunriseMeanAnomaly = (0.9856 * sunriseTime) - 3.289;
    double sunriseTrueLongitude = computeSunTrueLongitude(sunriseMeanAnomaly);
    double sunriseRightAsc = computeRightAscention(sunriseTrueLongitude);
    double cosH = computeCosH(sunriseTrueLongitude, latitude);

    if (cosH <= 1) {
      double sunriseEventHour =
          computeSunEventHour(sunriseRightAsc, sunriseTime, longitudeHour, cosH, true);
      long sunriseTimeMs = (long) (midnightTime + (sunriseEventHour * ONE_HOUR));
      sunriseTimeMs -= SUN_CIRCLE;
      sunriseTimeMs = isDaylight ? sunriseTimeMs + ONE_HOUR : sunriseTimeMs;
      sunrise = toZonedDateTime(sunriseTimeMs / 1000L, timezone);
    }

    // compute sunset
    double sunsetTime = computeSunEvent(dayOfYear, longitudeHour, 18.0);
    double sunsetMeanAnomaly = (0.9856 * sunsetTime) - 3.289;
    double sunsetTrueLongitude = computeSunTrueLongitude(sunsetMeanAnomaly);
    double sunsetRightAsc = computeRightAscention(sunsetTrueLongitude);
    cosH = computeCosH(sunsetTrueLongitude, latitude);

    if (cosH >= -1) {
      double sunsetEventHour =
          computeSunEventHour(sunsetRightAsc, sunsetTime, longitudeHour, cosH, false);
      long sunsetTimeMs = (long) (midnightTime + (sunsetEventHour * ONE_HOUR));
      sunsetTimeMs += SUN_CIRCLE;
      sunsetTimeMs = isDaylight ? sunsetTimeMs + ONE_HOUR : sunsetTimeMs;

      if (sunsetTimeMs < midnightTime) {
        sunsetTimeMs += ONE_DAY;
      }

      sunset = toZonedDateTime(sunsetTimeMs / 1000L, timezone);
    }

    // compute angle above horizon at noon
    computeAngleAboveHorizonAtNoon(dayOfYear, latitude);
  }

  private ZonedDateTime toZonedDateTime(long epochSecond, TimeZone timezone) {
    return Instant.ofEpochSecond(epochSecond).atZone(timezone.toZoneId());
  }

  public ZonedDateTime getSunrise() {
    return sunrise;
  }

  public ZonedDateTime getSunset() {
    return sunset;
  }

  private void computeAngleAboveHorizonAtNoon(int dayOfYear, double latitude) {
    // theta, the number of days since dec 21st
    double theta = valueInRange(dayOfYear + 10, 365);

    // convert dayOfYear to degrees
    theta = (theta * 360.0) / 365.0;

    double cos = Math.cos(Math.toRadians(theta));
    angleAboveHorizonAtNoon = 90.0 - latitude - (cos * EARTH_OBLIQUITY);

    if (angleAboveHorizonAtNoon > 90) {
      angleAboveHorizonAtNoon = 180 - angleAboveHorizonAtNoon;
    }
  }

  private static int computeDayOfYear(int year, int month, int day) {
    int n1 = (int) Math.floor((275.0 * month) / 9.0);
    int n2 = (int) Math.floor((month + 9) / 12.0);
    int n3 = (int) (1 + Math.floor((year - 4.0 * Math.floor(year / 4.0) + 2.0) / 3.0));
    int dayOfYear = n1 - (n2 * n3) + day - 30;
    return dayOfYear;
  }

  private double computeSunEvent(int dayOfYear, double lngHour, double hour) {
    double time = dayOfYear + ((hour - lngHour) / 24.0);
    return time;
  }

  private double computeSunTrueLongitude(double sunMeanAnomaly) {
    double lon = sunMeanAnomaly + (1.916 * sinDeg(sunMeanAnomaly))
        + (0.02 * sinDeg(2.0 * sunMeanAnomaly)) + 282.634;

    if (lon < 0) {
      lon += 360;
    }

    if (lon > 360) {
      lon -= 360;
    }

    return lon;
  }

  private double computeRightAscention(double longitude) {
    double lonRad = RADS_PER_DEG * longitude;
    double rightAscRad = Math.atan(0.91764 * Math.tan(lonRad));
    double rightAsc = valueInRange(Math.toDegrees(rightAscRad), 360);

    double lonQuadrant = 90.0 * Math.floor(longitude / 90.0);
    double rightAscQuadrant = 90.0 * Math.floor(rightAsc / 90.0);
    rightAsc = rightAsc + (lonQuadrant - rightAscQuadrant);

    rightAsc = rightAsc / 15.0;
    return rightAsc;
  }

  private double computeSunEventHour(double rightAsc, double sunEventTime, double lngHour,
      double cosH, boolean isSunrise) {

    double lon = Math.toDegrees(Math.acos(cosH));

    if (isSunrise) {
      lon = 360.0 - lon;
    }

    double hour = lon / 15.0;
    double time = hour + rightAsc - (0.06571 * sunEventTime) - 6.622;
    double ut = valueInRange(time - lngHour, 24);

    int localOffset = -5;
    double localT = ut + localOffset;
    return localT;
  }

  private double computeCosH(double longitude, double latitude) {
    double sinDecl = 0.39782 * Math.sin(Math.toRadians(longitude));
    double cosDecl = Math.cos(Math.asin(sinDecl));

    double cosH = Math.cos(ZENITH) - sinDecl * Math.sin(Math.toRadians(latitude));
    cosH = cosH / (cosDecl * Math.cos(Math.toRadians(latitude)));

    return cosH;
  }

  private double sinDeg(double deg) {
    double rad = deg * Math.PI / 180.0;
    return Math.sin(rad);
  }

  private static double valueInRange(double value, int max) {
    // keep in range [0..max]
    value = (value > 0) ? (value % max) : (max - Math.abs(value) % max) % max;
    return value;
  }


}
