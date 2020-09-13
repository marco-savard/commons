package com.marcosavard.commons.astro;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.JulianFields;
import java.util.Date;

public class AstroDates {

  public static int getDayOfYear(LocalDate date) {
    return date.getDayOfYear();
  }

  public static int getDayOfYear(Date date) {
    int year = date.getYear() + 1900;
    int month = date.getMonth() + 1;
    int day = date.getDate();
    int n1 = (int) Math.floor((275.0 * month) / 9.0);
    int n2 = (int) Math.floor((month + 9) / 12.0);
    int n3 = (int) (1 + Math.floor((year - 4.0 * Math.floor(year / 4.0) + 2.0) / 3.0));
    int dayOfYear = n1 - (n2 * n3) + day - 30;
    return dayOfYear;
  }

  public static long toJulianDayNumber(ZonedDateTime dateTime) {
    long julianDay = JulianFields.JULIAN_DAY.getFrom(dateTime);
    return julianDay;
  }

  private static long toJulianDayNumber(Date date) {
    int offset = date.getTimezoneOffset();
    Date utcDate = date;

    if (offset > 0) {
      long offsetInMillis = offset * 60L * 1000L;
      utcDate = new Date(date.getTime() + offsetInMillis);
    }

    int year = utcDate.getYear() + 1900;
    int month = utcDate.getMonth() + 1;
    long dayOfMonth = utcDate.getDate();

    int a = (14 - month) / 12;
    int y = year + 4800 - a;
    int m = month + (12 * a) - 3;
    long julianDayNumber = y * 365 + (y / 4) - (y / 100) + (y / 400) - 32045;
    julianDayNumber += (153 * m + 2) / 5;
    julianDayNumber += dayOfMonth;
    return julianDayNumber;
  }

  static double toJulianDay(ZonedDateTime dateTime) {
    long julianDayNumber = toJulianDayNumber(dateTime);
    int hour = dateTime.getHour();
    int minute = dateTime.getMinute();
    int second = dateTime.getSecond();
    double julianDay = computeJulianDay(julianDayNumber, hour, minute, second);
    return julianDay;
  }

  public static double toJulianDay(Date date) {
    long julianDayNumber = toJulianDayNumber(date);
    long offsetInMinutes = date.getTimezoneOffset();
    Date utcDate = date;

    if (offsetInMinutes > 0) {
      long offsetInMillis = offsetInMinutes * 60L * 1000L;
      utcDate = new Date(date.getTime() + offsetInMillis);
    }

    int hour = utcDate.getHours();
    int minute = utcDate.getMinutes();
    int second = utcDate.getSeconds();
    double julianDay = computeJulianDay(julianDayNumber, hour, minute, second);
    return julianDay;
  }

  private static double computeJulianDay(long julianDayNumber, int hour, int minute, int second) {
    double julianDay = julianDayNumber;
    julianDay += (hour - 12) / 24.0;
    julianDay += (minute / 1440.0);
    julianDay += (second / 86400.0);
    return julianDay;
  }

  public static LocalDate toLocalDate(Date date) {
    return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
  }

  public static Date toDate(LocalDate localDate) {
    return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
  }



}
