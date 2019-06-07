package com.marcosavard.commons.time;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.EnumSet;
import java.util.concurrent.TimeUnit;

/**
 * A utility class for date-related methods.
 * 
 * @author Marco
 *
 */
public class Dates {
  public enum Time {
    BEFORE, AFTER
  };

  // get all days of week (from Monday to Sunday)
  public static EnumSet<DayOfWeek> allDaysOfWeek() {
    EnumSet<DayOfWeek> allDays = EnumSet.allOf(DayOfWeek.class);
    return allDays;
  }

  /**
   * Build a new Date from two Date-typed parameters, the first one representing an actual date
   * (yyyy-MM-dd) and a second one representing the time of of day (hh:mm:ss). For instance, if the
   * first parameter represents 2018-01-31 at 00:00:00, and the second one represents 1970-01-01 at
   * 15:30:00, then builds a Date representing 2018-01-31 at 15:30:00 and returns it.
   * 
   * @param datePart a Date object in which time-related fields are ignored.
   * @param timePart a Date object in which date-related fields are ignored. , e.g. 15:36:07
   *        (date-part ignored)
   * @return new date, e.g. 08-06-2016 15:36:07
   */
  public static Date createDate(Date datePart, Date timePart) {
    @SuppressWarnings("deprecation")
    Date date = new Date(datePart.getYear(), datePart.getMonth(), datePart.getDate(),
        timePart.getHours(), timePart.getMinutes(), timePart.getSeconds());
    return date;
  }

  /**
   * Return the number of days between two dates.
   * 
   * @param firstDate first date
   * @param secondDate second date
   * @return number of days between the two dates
   */
  public static long daysBetween(Date firstDate, Date secondDate) {
    long deltaMs = Math.abs(secondDate.getTime() - firstDate.getTime());
    long daysBefore = TimeUnit.DAYS.convert(deltaMs, TimeUnit.MILLISECONDS);
    return daysBefore;
  }


  /**
   * Get a relative date (yesterday, last week, etc.) compared to the reference date.<br>
   * <br>
   * For instance:<br>
   * <code>
   * &nbsp;&nbsp;Date aMonthAgo = Dates.getDateAfter(today, -30);<br>
   * &nbsp;&nbsp;Date aWeekAgo = Dates.getDateAfter(today, -7);<br>
   * &nbsp;&nbsp;Date yesterday = Dates.getDateAfter(today, -1);<br>
   * &nbsp;&nbsp;Date tomorrow = Dates.getDateAfter(today, 1);<br>
   * </code>
   * 
   * @param numberOfDays a positive or negative number of days
   * @return date relative to the reference date
   */
  public static Date getDateAfter(Date reference, int numberOfDays) {
    LocalDate localDate = toLocalDate(reference).plusDays(numberOfDays);
    return toDate(localDate);
  }

  public static Date getFirstSundayInMonth(int year, Month month) {
    return getFirstDayOfWeekInMonth(year, month, DayOfWeek.SUNDAY);
  }

  public static Date getFirstMondayInMonth(int year, Month month) {
    return getFirstDayOfWeekInMonth(year, month, DayOfWeek.MONDAY);
  }

  public static Date getFirstTuesdayInMonth(int year, Month month) {
    return getFirstDayOfWeekInMonth(year, month, DayOfWeek.TUESDAY);
  }

  public static Date getFirstDayOfWeekInMonth(int year, Month month, DayOfWeek dayOfWeek) {
    int dayOfWeekIdx = dayOfWeek.getValue() % 7 + 1;
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(toDate(LocalDate.of(year, month, 1)));
    calendar.set(Calendar.DAY_OF_WEEK, dayOfWeekIdx);
    calendar.set(Calendar.DAY_OF_WEEK_IN_MONTH, 1);
    return calendar.getTime();
  }

  public static Date getFirstDayInMonth(int year, Month month) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(toDate(LocalDate.of(year, month, 1)));
    calendar.set(Calendar.DAY_OF_MONTH, 1);
    return calendar.getTime();
  }

  public static Date getLastDayInMonth(int year, Month month) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(toDate(LocalDate.of(year, month, 1)));
    calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
    return calendar.getTime();
  }

  public static boolean isFirstDayOfMonth(Date date) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    boolean firstDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH) == 1;
    return firstDayOfMonth;
  }

  public static boolean isLastDayOfMonth(Date date) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    int lastDayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    boolean firstDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH) == lastDayOfMonth;
    return firstDayOfMonth;
  }

  // is year a leap year
  public static boolean isLeapYear(int year) {
    boolean leap = ((year % 4 == 0) && ((year % 100 != 0)) || (year % 400 == 0));
    return leap;
  }

  public static LocalDate of(int year, Month month, int weekInMonth, DayOfWeek dayOfWeek) {
    LocalDate firstDayOfMonth = LocalDate.of(year, month, 1);
    int daysToAdd = dayOfWeek.getValue();
    daysToAdd -= firstDayOfMonth.getDayOfWeek().getValue();
    daysToAdd += (weekInMonth - 1) * 7;
    LocalDate date = firstDayOfMonth.plusDays(daysToAdd);
    return date;
  }

  public static Calendar toCalendar(Date date) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    return calendar;
  }

  /**
   * Convert a Date into a LocalDate instance.
   * 
   * @param date to convert
   * @return the LocalDate instance
   */
  public static LocalDate toLocalDate(Date date) {
    return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
  }

  /**
   * Convert a LocalDate into a Date instance.
   * 
   * @param localDate to convert
   * @return the Date instance
   */
  public static Date toDate(LocalDate localDate) {
    return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
  }



}
