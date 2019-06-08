package com.marcosavard.commons.astro;

import java.util.Date;
import java.util.TimeZone;
import com.marcosavard.commons.geog.GeoCoordinate;

/*
 * based on: http://williams.best.vwh.net/sunrise_sunset_algorithm.htm
 * 
 * Almanac for Computers, 1990 published by Nautical Almanac Office United States Naval Observatory
 * Washington, DC 20392
 */
public class SunEvent {
  private static final double EARTH_OBLIQUITY = 23.5;
  private static final double RADS_PER_DEG = Math.PI / 180.0;
  private static final double DEG_PER_RAD = 180.0 / Math.PI;
  private static final long ONE_HOUR = 1000L * 60 * 60;
  private static final long ONE_DAY = 24 * ONE_HOUR;
  private static final double ZENITH = 90 * RADS_PER_DEG; // official
  private static final long SUN_CIRCLE = 6 * 60 * 1000L; // 6 minutes for Sun's circle

  private GeoCoordinate coordinate;
  private TimeZone timezone;
  private double latitude;
  private int dayOfYear;
  private long sunriseTimeMs = 0, sunsetTimeMs = 0;

  public SunEvent(GeoCoordinate coord, TimeZone timezone, Date date) {
    this.coordinate = coord;
    this.timezone = timezone;
    computeSunEvents(date);
  }

  public Date getSunrise() {
    Date sunrise = (sunriseTimeMs == Long.MAX_VALUE) ? null : new Date(sunriseTimeMs);
    return sunrise;
  }

  public Date getSunset() {
    Date sunrise = (sunsetTimeMs == Long.MAX_VALUE) ? null : new Date(sunsetTimeMs);
    return sunrise;
  }

  public double getAngleAboveHorizonAtNoon() {
    double theta = dayOfYear + 10; // 21st dec

    if (theta > 365) {
      theta -= 365;
    }

    theta = (theta * 360.0) / 365.0;
    double cos = Math.cos(theta * RADS_PER_DEG);
    double angle = 90.0 - latitude - (cos * EARTH_OBLIQUITY);

    if (angle > 90) {
      angle = 180 - angle;
    }
    return angle;
  }


  private void computeSunEvents(Date date) {
    int year = 1900 + date.getYear();
    int month = 1 + date.getMonth();
    int day = date.getDate();
    dayOfYear = computeDayOfYear(year, month, day);
    Date midnight = new Date(year - 1900, month - 1, day);
    long midnightTime = midnight.getTime();
    boolean daylightTime = timezone.inDaylightTime(date);

    latitude = coordinate.getLatitude().getValue();
    double longitudeHour = coordinate.getLongitude().getValue() / 15.0;

    double sunriseTime = computeSunEvent(dayOfYear, longitudeHour, 6.0);
    double sunriseMeanAnomaly = (0.9856 * sunriseTime) - 3.289;
    double sunriseTrueLongitude = computeSunTrueLongitude(sunriseMeanAnomaly);
    double sunriseRightAsc = computeRightAscention(sunriseTrueLongitude);
    double cosH = computeCosH(sunriseTrueLongitude, latitude);

    if (cosH <= 1) {
      double sunriseEventHour =
          computeSunEventHour(sunriseRightAsc, sunriseTime, longitudeHour, cosH, true);
      sunriseTimeMs = (long) (midnightTime + (sunriseEventHour * ONE_HOUR));
      sunriseTimeMs -= SUN_CIRCLE;
      sunriseTimeMs = daylightTime ? sunriseTimeMs + ONE_HOUR : sunriseTimeMs;
    } else {
      sunriseTimeMs = Long.MAX_VALUE;
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
      sunsetTimeMs = (long) (midnightTime + (sunsetEventHour * ONE_HOUR));
      sunsetTimeMs += SUN_CIRCLE;
      sunsetTimeMs = daylightTime ? sunsetTimeMs + ONE_HOUR : sunsetTimeMs;

      if (sunsetTimeMs < midnightTime) {
        sunsetTimeMs += ONE_DAY;
      }
    }
  }

  private int computeDayOfYear(int year, int month, int day) {
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
    double rightAsc = rightAscRad * DEG_PER_RAD;

    if (rightAsc < 0) {
      rightAsc += 360;
    }

    if (rightAsc > 360) {
      rightAsc -= 360;
    }

    double lonQuadrant = 90.0 * Math.floor(longitude / 90.0);
    double rightAscQuadrant = 90.0 * Math.floor(rightAsc / 90.0);
    rightAsc = rightAsc + (lonQuadrant - rightAscQuadrant);

    rightAsc = rightAsc / 15.0;
    return rightAsc;
  }

  private double computeSunEventHour(double rightAsc, double sunEventTime, double lngHour,
      double cosH, boolean isSunrise) {

    double lon = DEG_PER_RAD * Math.acos(cosH);

    if (isSunrise) {
      lon = 360.0 - lon;
    }

    double hour = lon / 15.0;
    double time = hour + rightAsc - (0.06571 * sunEventTime) - 6.622;
    double ut = time - lngHour;

    if (ut < 0) {
      ut += 24;
    }

    if (ut > 24) {
      ut -= 24;
    }

    int localOffset = -5;
    double localT = ut + localOffset;
    return localT;
  }

  private double computeCosH(double longitude, double latitude) {
    double sinDecl = 0.39782 * Math.sin(longitude * RADS_PER_DEG);
    double cosDecl = Math.cos(Math.asin(sinDecl));

    double cosH = Math.cos(ZENITH) - sinDecl * Math.sin(RADS_PER_DEG * latitude);
    cosH = cosH / (cosDecl * Math.cos(RADS_PER_DEG * latitude));

    return cosH;
  }

  private double sinDeg(double deg) {
    double rad = deg * Math.PI / 180.0;
    return Math.sin(rad);
  }


}
