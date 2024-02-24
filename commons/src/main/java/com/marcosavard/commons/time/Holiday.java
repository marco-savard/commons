package com.marcosavard.commons.time;

import com.marcosavard.commons.time.res.ReligiousHolidayName;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;

public enum Holiday {
  NEW_YEAR(Month.JANUARY, 1),
  VALENTINES_DAY(Month.FEBRUARY, 14),
  FAMILY_DAY(Month.FEBRUARY, DayOfWeek.MONDAY, 3),
  DAYLIGHT_SAVING_ON(Month.MARCH, DayOfWeek.SUNDAY, 2),
  SAINT_PATRICK(Month.MARCH, 17),
  MARDI_GRAS(-40),
  ASH_WEDNESDAY(-39),
  PALM_SUNDAY(-7),
  GOOD_FRIDAY(-2),
  EASTER(0),
  MOTHERS_DAY(Month.MAY, DayOfWeek.SUNDAY, 2),
  VICTORIA_DAY(Month.MAY, DayOfWeek.MONDAY, 3),
  FATHERS_DAY(Month.JUNE, DayOfWeek.SUNDAY, 3),
  ASCENSION(39),
  PENTECOST(49),
  TRINITY_SUNDAY(56),
  FEAST_OF_CORPUS_CHRISTI(60),
  SAINT_JOHN_BAPTIST(Month.JUNE, 24),
  CANADA_DAY(Month.JULY, 1),
  US_INDEPENDANCE_DAY(Month.JULY, 4),
  CIVIC_DAY(Month.AUGUST, DayOfWeek.MONDAY, 1),
  LABOR_DAY(Month.SEPTEMBER, DayOfWeek.MONDAY, 1),
  CANADIAN_THANKSGIVING(Month.OCTOBER, DayOfWeek.MONDAY, 2),
  HALLOWEEN(Month.OCTOBER, 31),
  ALL_SAINTS_DAY(Month.NOVEMBER, 1),
  DAYLIGHT_SAVING_OFF(Month.NOVEMBER, DayOfWeek.SUNDAY, 1),
  AMERICAN_THANKSGIVING(Month.NOVEMBER, DayOfWeek.THURSDAY, 4),
  CHRISTMAS(Month.DECEMBER, 25);

  private final int daysAfterEaster;
  private final int dayOfMonth;
  private final int weekOfMonth;
  private final Month month;
  private final DayOfWeek dayOfWeek;

  Holiday(int daysAfterEaster) {
    this.daysAfterEaster = daysAfterEaster;
    this.dayOfMonth = 0;
    this.weekOfMonth = 0;
    this.month = null;
    this.dayOfWeek = null;
  }

  Holiday(Month month, int dayOfMonth) {
    this.dayOfMonth = dayOfMonth;
    this.month = month;
    this.daysAfterEaster = 0;
    this.weekOfMonth = 0;
    this.dayOfWeek = null;
  }

  Holiday(Month month, DayOfWeek dayOfWeek, int weekOfMonth) {
    this.month = month;
    this.dayOfWeek = dayOfWeek;
    this.daysAfterEaster = 0;
    this.dayOfMonth = 0;
    this.weekOfMonth = weekOfMonth;
  }

  public String getDisplayName(Locale display) {
    Class claz = ReligiousHolidayName.class;
    String basename = claz.getName().replace('.', '/');
    ResourceBundle bundle = ResourceBundle.getBundle(basename, display);
    String name = this.name().toLowerCase();
    String displayName = bundle.containsKey(name) ? bundle.getString(name) : name;
    return displayName;
  }

  public LocalDate dateOf(int year) {
    LocalDate holidayDate;

    if (dayOfWeek != null) {
      holidayDate = findHoliday(year, month, dayOfWeek, weekOfMonth);
    } else if (month != null) {
      holidayDate = LocalDate.of(year, month, dayOfMonth);
    } else {
      DateField fields = computeDateFields(year);
      LocalDate easter = LocalDate.of(fields.year, fields.month, fields.day);
      holidayDate = easter.plusDays(daysAfterEaster);
    }

    return holidayDate;
  }

  private LocalDate findHoliday(int year, Month month, DayOfWeek dayOfWeek, int weekOfMonth) {
    int dayOfMonth = 1 + (weekOfMonth - 1) * 7;
    LocalDate holiday =
        LocalDate.of(year, month, dayOfMonth) //
            .with(TemporalAdjusters.nextOrSame(dayOfWeek));
    return holiday;
  }

  public static Map<LocalDate, Holiday> holidaysBetween(LocalDate from, LocalDate to) {
    Map<LocalDate, Holiday> holidaysByDate = new TreeMap<>();
    Holiday[] holidays = Holiday.values();

    for (Holiday holiday : holidays) {
      List<LocalDate> dates = holiday.between(from, to);
      for (LocalDate date : dates) {
        holidaysByDate.put(date, holiday);
      }
    }

    return holidaysByDate;
  }

  public List<LocalDate> between(LocalDate from, LocalDate to) {
    int fromYear = from.getYear();
    int toYear = to.getYear();
    List<LocalDate> dates = new ArrayList<>();

    for (int year = fromYear; year <= toYear; year++) {
      LocalDate date = dateOf(year);
      boolean between = !date.isBefore(from) && !date.isAfter(to);

      if (between) {
        dates.add(date);
      }
    }

    return dates;
  }

  //
  // private method
  //
  private static DateField computeDateFields(int year) {
    int golden = (year % 19) + 1; // E1: metonic cycle of 19 years
    int century = (year / 100) + 1; // E2: e.g. 1984 was in 20th C
    int x = (3 * century / 4) - 12; // E3: leap year correction
    int z = ((8 * century + 5) / 25) - 5; // E3: sync with moon's orbit
    int d = (5 * year / 4) - x - 10;
    int epact = (11 * golden + 20 + z - x) % 30; // E5: epact

    if ((epact == 25 && golden > 11) || epact == 24) epact++;

    int n = 44 - epact;
    n += 30 * (n < 21 ? 1 : 0); // E6
    n += 7 - ((d + n) % 7);

    DateField fields = new DateField();
    fields.year = year;
    fields.month = (n <= 31) ? 3 : 4; // March or April
    fields.day = (n <= 31) ? n : n - 31; // day of month
    return fields;
  }

  private static class DateField {
    int year, month, day;
  }
}
