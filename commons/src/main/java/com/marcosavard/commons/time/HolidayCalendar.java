package com.marcosavard.commons.time;

import java.util.Calendar;
import java.util.Date;

/**
 * Class that gives a calendar of holidays (and other events).
 *
 * <p>TODO convert Date to LocalDate
 *
 * @author Marco
 */
public class HolidayCalendar {
  public enum Holiday {
    NEW_YEAR,
    VALENTINES_DAY,
    FAMILY_DAY,
    DAYLIGHT_SAVING,
    SAINT_PATRICK,
    EASTER,
    MOTHERS_DAY,
    VICTORIA_DAY,
    FATHERS_DAY,
    SAINT_JEAN_BAPTISTE,
    CANADA_DAY,
    CIVIC_DAY,
    LABOR_DAY,
    CANADIAN_THANKSGIVING,
    HALLOWEEN,
    DAYLIGHT_SAVING_OFF,
    CHRISTMAS
  };

  /**
   * Find the next occurrence of a given holiday
   *
   * @param holiday to find
   * @return date of this holiday
   */
  public static Date findNextHoliday(Holiday holiday) {
    Date today = new Date();
    return findHolidayAfterDate(holiday, today);
  }

  /**
   * Find the date that a given holiday occurs
   *
   * @param holiday to find
   * @param date the date after which the holiday occurs
   * @return find date of holiday after a given date
   */
  public static Date findHolidayAfterDate(Holiday holiday, Date date) {
    int year = date.getYear() + 1900;
    Date holidayDate = findHolidayOfYear(holiday, year);
    holidayDate = holidayDate.before(date) ? findHolidayOfYear(holiday, year + 1) : holidayDate;
    return holidayDate;
  }

  /**
   * Find the date that a given holiday occurs, in the given year
   *
   * @param holiday to find
   * @param year the year in which the holiday occurs (e.g. 2018)
   * @return find date of holiday in a given year
   */
  public static Date findHolidayOfYear(Holiday holiday, int year) {
    Date date = null;

    if (holiday == Holiday.NEW_YEAR) {
      date = findNewYearDay(year);
    } else if (holiday == Holiday.VALENTINES_DAY) {
      date = findSaintValentineDay(year);
    } else if (holiday == Holiday.FAMILY_DAY) {
      date = findFamilyDay(year);
    } else if (holiday == Holiday.SAINT_PATRICK) {
      date = findSaintPatrickDay(year);
    } else if (holiday == Holiday.DAYLIGHT_SAVING) {
      date = findDaylightSavingDate(year);
    } else if (holiday == Holiday.EASTER) {
      date = findEasterDate(year);
    } else if (holiday == Holiday.MOTHERS_DAY) {
      date = findMothersDay(year);
    } else if (holiday == Holiday.VICTORIA_DAY) {
      date = findVictoriaDay(year);
    } else if (holiday == Holiday.FATHERS_DAY) {
      date = findFathersDay(year);
    } else if (holiday == Holiday.SAINT_JEAN_BAPTISTE) {
      date = findSaintJeanBaptisteDay(year);
    } else if (holiday == Holiday.CANADA_DAY) {
      date = findCanadaDay(year);
    } else if (holiday == Holiday.CIVIC_DAY) {
      date = findCivicDay(year);
    } else if (holiday == Holiday.LABOR_DAY) {
      date = findLaborDay(year);
    } else if (holiday == Holiday.CANADIAN_THANKSGIVING) {
      date = findCanadianThanksgiving(year);
    } else if (holiday == Holiday.HALLOWEEN) {
      date = findHalloween(year);
    } else if (holiday == Holiday.DAYLIGHT_SAVING_OFF) {
      date = findDaylightSavingOffDate(year);
    } else if (holiday == Holiday.CHRISTMAS) {
      date = findChristmas(year);
    }

    return date;
  }

  /**
   * Find the new Year's date of the year
   *
   * @param year a given year (e.g. 2018)
   * @return new Year's date
   */
  public static Date findNewYearDay(int year) {
    return new Date(year - 1900, 1 - 1, 1);
  }

  /**
   * Find the Valentine day of the year
   *
   * @param year a given year (e.g. 2018)
   * @return Valentine day of the year
   */
  public static Date findSaintValentineDay(int year) {
    return new Date(year - 1900, 2 - 1, 14);
  }

  /**
   * Find the family day of the year
   *
   * @param year a given year (e.g. 2018)
   * @return family day of the year
   */
  public static Date findFamilyDay(int year) {
    return findThirdDayOfWeekInMonth(year, 2, Calendar.MONDAY);
  }

  /**
   * Find the Saint Patrick's day of the year
   *
   * @param year a given year (e.g. 2018)
   * @return Saint Patrick's day of the year
   */
  public static Date findSaintPatrickDay(int year) {
    return new Date(year - 1900, 3 - 1, 17);
  }

  /**
   * Find the date of the year when Daylight Saving Time starts
   *
   * @param year a given year (e.g. 2018)
   * @return date of the year when Daylight Saving Time starts
   */
  public static Date findDaylightSavingDate(int year) {
    return findSecondDayOfWeekInMonth(year, 3, Calendar.SUNDAY);
  }

  /**
   * Find the Easter date of the year
   *
   * @param year a given year (e.g. 2018)
   * @return Easter date of the year
   */
  public static Date findEasterDate(int year) {
    return null; // ReligiousHoliday.EASTER.dateOf(year);
  }

  public static Date findMothersDay(int year) {
    return findSecondDayOfWeekInMonth(year, 5, Calendar.SUNDAY);
  }

  /**
   * Find the Victoria day of the year
   *
   * @param year a given year (e.g. 2018)
   * @return Victoria day of the year
   */
  private static Date findVictoriaDay(int year) {
    return findThirdDayOfWeekInMonth(year, 5, Calendar.MONDAY);
  }

  /**
   * Find the Father's day of the year
   *
   * @param year a given year (e.g. 2018)
   * @return Father's day of the year
   */
  public static Date findFathersDay(int year) {
    return findThirdDayOfWeekInMonth(year, 6, Calendar.SUNDAY);
  }

  /**
   * Find the Saint-Jean Baptiste day of the year
   *
   * @param year a given year (e.g. 2018)
   * @return Saint-Jean Baptiste day of the year
   */
  public static Date findSaintJeanBaptisteDay(int year) {
    return new Date(year - 1900, 6 - 1, 24);
  }

  /**
   * Find the Canada day of the year
   *
   * @param year a given year (e.g. 2018)
   * @return Canada day of the year
   */
  private static Date findCanadaDay(int year) {
    return new Date(year - 1900, 7 - 1, 1);
  }

  /**
   * Find the Civic holiday of the year
   *
   * @param year a given year (e.g. 2018)
   * @return Civic holiday of the year
   */
  public static Date findCivicDay(int year) {
    return findFirstDayOfWeekInMonth(year, 8, Calendar.MONDAY);
  }

  /**
   * Find the Labor day of the year
   *
   * @param year a given year (e.g. 2018)
   * @return Labor day of the year
   */
  public static Date findLaborDay(int year) {
    return findFirstDayOfWeekInMonth(year, 9, Calendar.MONDAY);
  }

  /**
   * Find the Canadian Thanksgiving of the year
   *
   * @param year a given year (e.g. 2018)
   * @return Canadian Thanksgiving of the year
   */
  public static Date findCanadianThanksgiving(int year) {
    return findSecondDayOfWeekInMonth(year, 10, Calendar.MONDAY);
  }

  /**
   * Find the Halloween of the year
   *
   * @param year a given year (e.g. 2018)
   * @return Halloween of the year
   */
  private static Date findHalloween(int year) {
    return new Date(year - 1900, 10 - 1, 31);
  }

  /**
   * Find the date of the year when Daylight Saving Time ends
   *
   * @param year a given year (e.g. 2018)
   * @return date of the year when Daylight Saving Time ends
   */
  public static Date findDaylightSavingOffDate(int year) {
    return findFirstDayOfWeekInMonth(year, 11, Calendar.SUNDAY);
  }

  /**
   * Find the date on which Christmas occurs, at the year
   *
   * @param year a given year (e.g. 2018)
   * @return date on which Christmas occurs
   */
  public static Date findChristmas(int year) {
    return new Date(year - 1900, 12 - 1, 25);
  }

  /**
   * Find the first specified day of week, at a given month and year
   *
   * @param year a given year (e.g. 2018)
   * @param month a given month in the range [1..12]
   * @param dayOfWeek from 1 (Calendar.SUNDAY) to 7 (Calendar.SATURDAY)
   * @return date on which the specified date occurs
   */
  public static Date findFirstDayOfWeekInMonth(int year, int month, int dayOfWeek) {
    return findNDayOfWeekInMonth(year, month, dayOfWeek, 1);
  }

  /**
   * Find the second specified day of week, at a given month and year
   *
   * @param year a given year (e.g. 2018)
   * @param month a given month in the range [1..12]
   * @param dayOfWeek from 1 (Calendar.SUNDAY) to 7 (Calendar.SATURDAY)
   * @return date on which the specified date occurs
   */
  public static Date findSecondDayOfWeekInMonth(int year, int month, int dayOfWeek) {
    return findNDayOfWeekInMonth(year, month, dayOfWeek, 2);
  }

  /**
   * Find the third specified day of week, at a given month and year
   *
   * @param year a given year (e.g. 2018)
   * @param month a given month in the range [1..12]
   * @param dayOfWeek from 1 (Calendar.SUNDAY) to 7 (Calendar.SATURDAY)
   * @return date on which the specified date occurs
   */
  public static Date findThirdDayOfWeekInMonth(int year, int month, int dayOfWeek) {
    return findNDayOfWeekInMonth(year, month, dayOfWeek, 3);
  }

  /**
   * Date date = findNDayOfWeekInMonth(2018, 5, Calendar.SUNDAY, 2) return the 2nd Sunday of May
   * (5th month) 2018
   *
   * @param year a given year (e.g. 2018)
   * @param month a given month in the range [1..12]
   * @param dayOfWeek from 1 (Calendar.SUNDAY) to 7 (Calendar.SATURDAY)
   * @param rank first, second, thirf or fourth of the month
   * @return date of the holiday
   */
  public static Date findNDayOfWeekInMonth(int year, int month, int dayOfWeek, int rank) {
    Date firstOfMonth = new Date(year - 1900, month - 1, 1);
    int firstDayOfWeek = firstOfMonth.getDay() + 1;
    int daysOffset = dayOfWeek - firstDayOfWeek;
    daysOffset = (daysOffset > 0) ? daysOffset : (daysOffset + 7) % 7;
    daysOffset += (rank - 1) * 7;
    Date firstDayOfWeekInMonth = new Date(year - 1900, month - 1, 1 + daysOffset);
    return firstDayOfWeekInMonth;
  }
}
