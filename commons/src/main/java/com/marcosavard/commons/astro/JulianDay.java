package com.marcosavard.commons.astro;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.JulianFields;

/**
 * Returns the Julian day number that begins at noon of this day, Positive year signifies A.D.,
 * negative year B.C. Remember that the year after 1 B.C. was 1 A.D.
 *
 * ref : Numerical Recipes in C, 2nd ed., Cambridge University Press 1992
 * 
 * TODO isComparable, daysBetween(otherJd), addTo(), lessThan()
 */
public class JulianDay {
  // Gregorian Calendar adopted Oct. 15, 1582 (2299161)
  public static final LocalDate COMMON_ERA = LocalDate.of(1, 1, 1);
  public static final LocalDate GREGORIAN_ERA = LocalDate.of(1582, 10, 15);
  private static final int JGREG = 15 + 31 * (10 + 12 * 1582);
  private static final double NB_SECS_PER_DAY = 24 * 60 * 60;
  private static final double C = 1720994.0;

  private double value;

  public static JulianDay of(LocalDate date) {
    ZonedDateTime moment = ZonedDateTime.of(date, LocalTime.NOON, ZoneOffset.UTC);
    return of(moment);
  }

  public static JulianDay of(ZonedDateTime moment) {
    long jd = JulianFields.JULIAN_DAY.getFrom(moment);
    LocalTime time = moment.toLocalTime();
    double jdf = jd + time.toSecondOfDay() / (24.0 * 60.0 * 60.0) - 0.5;
    JulianDay julianDay = new JulianDay(jdf);
    return julianDay;
  }

  // https://quasar.as.utexas.edu/BillInfo/JulianDatesG.html
  // secondOfDay : in 0..NB_SECS_PER_DAY
  public static JulianDay of(int year, int month, int day) {
    double jd = getJd(year, month, day) - 0.5;
    JulianDay julianDay = new JulianDay(jd);
    return julianDay;
  }

  public static JulianDay of(int year, int month, int day, long secondOfDay) {
    double fractionOfDay = secondOfDay / NB_SECS_PER_DAY;
    double jd = getJd(year, month, day) - 0.5 + fractionOfDay;
    JulianDay julianDay = new JulianDay(jd);
    return julianDay;
  }

  private static long getJd(int year, int month, int day) {
    year = (month <= 2) ? year - 1 : year;
    month = (month <= 2) ? month + 13 : month + 1;

    int a = year / 100;
    int b = a / 4;
    int c = 2 - a + b;
    int e = (int) Math.floor(365.25 * (year + 4716));
    int f = (int) Math.floor(30.6001 * month);
    long jd = c + day + e + f - 1524;
    return jd;
  }


  // science et vie
  public static JulianDay ofOld2(LocalDate date, LocalTime time) {
    int year = date.getYear();
    int month = date.getMonthValue();
    int day = date.getDayOfMonth();

    int m = (month <= 2) ? month + 13 : month + 1;
    int c = (int) Math.floor(m * 30.6001);
    int y, a;

    if (date.isBefore(COMMON_ERA)) {
      a = (month <= 2) ? year + 1 : year;
    } else {
      a = (month <= 2) ? year - 1 : year;
    }

    if (date.isBefore(GREGORIAN_ERA)) {
      y = 0;
    } else {
      y = (int) Math.ceil(year / 100.0) - 7;
    }

    int b = (int) Math.floor(a * 365.25);
    double j = day + 0.5;
    double fractionOfDay = time.toSecondOfDay() / NB_SECS_PER_DAY;
    double jj = (C - y) + b + c + j + fractionOfDay;

    JulianDay julianDay = new JulianDay(jj);
    return julianDay;
  }

  public static JulianDay of(double value) {
    return new JulianDay(value);
  }

  public static JulianDay ofBad(LocalDate date, LocalTime time) {
    int year = date.getYear();
    int month = date.getMonthValue();
    int day = date.getDayOfMonth();

    int julianYear = (year < 0) ? year + 1 : year;

    int julianMonth = month;
    if (month > 2) {
      julianMonth++;
    } else {
      julianYear--;
      julianMonth += 13;
    }

    double julian = java.lang.Math.floor(365.25 * julianYear);
    julian += java.lang.Math.floor(30.6001 * julianMonth);
    julian += day + 1720995.0;

    if (day + 31 * (month + 12 * year) >= JGREG) {
      // change over to Gregorian calendar
      int ja = (int) (0.01 * julianYear);
      julian += 2 - ja + (0.25 * ja);
    }

    JulianDay julianDay = new JulianDay(julian);
    return julianDay;
  }

  public LocalDate toLocalDate() {
    double z = Math.floor((value - 1867216.25) / 36524.25);
    double a = (value < 2299161) ? value : value + 1 + z - Math.floor(z / 4);
    double b = a + 1524;
    double c = Math.floor((b - 122.1) / 365.25);
    double d = Math.floor(c * 365.25);
    int e = (int) Math.floor((b - d) / 30.60001);

    double f = (value - Math.floor(value)) + 0.5;
    int dayOfMonth = (int) Math.floor(b - d - Math.floor(30.60001 * e) + f);
    e = (e < 13.5) ? e - 1 : e - 13;
    int month = (int) Math.floor(e);
    int year = (int) Math.floor((e > 2.5) ? c - 4716 : c - 4715);
    LocalDate date = LocalDate.of(year, month, dayOfMonth);
    return date;
  }

  private JulianDay(double value) {
    this.value = value;
  }

  public double getValue() {
    return value;
  }

  @Override
  public boolean equals(Object other) {
    boolean equal = false;

    if (other instanceof JulianDay) {
      JulianDay otherDate = (JulianDay) other;
      equal = Math.floor(otherDate.getValue()) == Math.floor(this.getValue());
    }
    return equal;
  }

  @Override
  public int hashCode() {
    return (int) value;
  }

  @Override
  public String toString() {
    String msg = MessageFormat.format("JD = {0}", String.format("%.2f", value));
    return msg;
  }



}
