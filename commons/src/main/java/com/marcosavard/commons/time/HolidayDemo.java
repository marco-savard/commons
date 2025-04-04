package com.marcosavard.commons.time;

import com.marcosavard.commons.debug.Console;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

public class HolidayDemo {

  public static void main(String[] args) {
    // printHolidayDates(Holiday.EASTER, 2010, 2028, "dd MMMM", Locale.FRENCH);
    // printHolidayDates(Holiday.CHRISTMAS, 2010, 2028, "EEEE", Locale.FRENCH);

    LocalDate from = LocalDate.of(2024, 1, 1);
    LocalDate to = LocalDate.of(2024, 12, 31);
    // printHolidaysInPeriod(from, to, "dd MMMM yyyy", Locale.FRENCH);
    printNextHolidays(LocalDate.now(), 5, "dd MMMM yyyy", Locale.FRENCH);
  }

  private static void printNextHolidays(LocalDate from, int count, String pattern, Locale display) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern).withLocale(display);
    Map<LocalDate, HolidayOld> holidays = HolidayOld.holidaysBetween(from, from.plusDays(365));
    Map<LocalDate, HolidayOld> nextHolidays = findNext(holidays, count);

    for (LocalDate date : nextHolidays.keySet()) {
      HolidayOld holiday = holidays.get(date);
      String formatted = date.format(formatter);
      String msg = MessageFormat.format("{0} : {1}", holiday.getDisplayName(display), formatted);
      Console.println(msg);
    }
  }

  private static Map<LocalDate, HolidayOld> findNext(Map<LocalDate, HolidayOld> holidays, int count) {
    Map<LocalDate, HolidayOld> nextHolidays = new TreeMap<>();
    int i = 0;

    for (LocalDate date : holidays.keySet()) {
      HolidayOld holiday = holidays.get(date);
      nextHolidays.put(date, holiday);
      i++;
      if (i >= count) {
        break;
      }
    }

    return nextHolidays;
  }

  private static void printHolidaysInPeriod(
      LocalDate from, LocalDate to, String pattern, Locale display) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern).withLocale(display);
    Map<LocalDate, HolidayOld> holidays = HolidayOld.holidaysBetween(from, to);

    for (LocalDate date : holidays.keySet()) {
      HolidayOld holiday = holidays.get(date);
      String formatted = date.format(formatter);
      String msg = MessageFormat.format("{0} : {1}", holiday.getDisplayName(display), formatted);
      Console.println(msg);
    }
  }

  private static void printHolidayDates(
          HolidayOld holiday, int fromYear, int toYear, String pattern, Locale display) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern).withLocale(display);
    String easterName = holiday.getDisplayName(display);

    for (int y = fromYear; y <= toYear; y++) {
      LocalDate holidayDate = holiday.dateOf(y);
      String formatted = holidayDate.format(formatter);
      String year = String.format("%d", y);
      String msg = MessageFormat.format("{0} {1} : {2}", easterName, year, formatted);
      Console.println(msg);
    }
  }
}
