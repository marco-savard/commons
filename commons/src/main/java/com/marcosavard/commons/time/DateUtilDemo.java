package com.marcosavard.commons.time;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.Date;

public class DateUtilDemo {
  private static final LocalDate GIVEN_DATE = LocalDate.of(2019, 1, 15);

  public static void main(String[] args) {
    // conversions
    // convertToLocalDate(DateUtil.toDate(GIVEN_DATE));
    // printLeapYears(1880, 1920);
    // printMonth(2019, Month.JANUARY);
    listDatesInMonth(2019, Month.JANUARY);
  }

  private static void printLeapYears(int startYear, int endYear) {
    for (int year = startYear; year <= endYear; year += 10) {
      boolean leap = DateUtil.isLeapYear(year);
      System.out.println(year + " is " + (leap ? "leap" : "not leap"));
    }
    System.out.println();
  }

  private static void printMonth(int year, Month month) {
    LocalDate lastOfMonth = DateUtil.toLocalDate(DateUtil.getLastDayInMonth(year, month));
    boolean beforeEndOfMonth = true;
    int weekInMonth = 1;

    String title = padLeft(month.toString() + " " + year, 20);
    System.out.println(title);

    for (DayOfWeek day : DateUtil.allDaysOfWeek()) {
      String dayOfWeek = day.toString().substring(0, 2);
      System.out.print(dayOfWeek + "  ");
    }
    System.out.println();

    do {
      for (DayOfWeek day : DateUtil.allDaysOfWeek()) {
        LocalDate date = DateUtil.of(2019, Month.JANUARY, weekInMonth, day);
        String dayOfMonth = String.format("%2d", date.getDayOfMonth());
        System.out.print(dayOfMonth + "  ");
        beforeEndOfMonth = date.isBefore(lastOfMonth);
      }

      System.out.println();
      weekInMonth++;
    } while (beforeEndOfMonth);

    System.out.println();
  }

  private static void convertToLocalDate(Date original) {
    LocalDate localDate = DateUtil.toLocalDate(original);
    Date converted = DateUtil.toDate(localDate);

    System.out.println("Original Date: " + original);
    System.out.println("In Local Date: " + localDate);
    System.out.println("Back to Date: " + converted);
    System.out.println("Equal: " + original.equals(converted));
    System.out.println();
  }

  private static void listDatesInMonth(int year, Month month) {
    System.out.print("1st in month: ");
    System.out.println(DateUtil.getFirstDayInMonth(year, month));

    System.out.print("1st Sunday in month: ");
    System.out.println(DateUtil.getFirstSundayInMonth(year, month));

    System.out.print("1st Monday in month: ");
    System.out.println(DateUtil.getFirstMondayInMonth(year, month));

    System.out.print("1st Tuesday in month: ");
    System.out.println(DateUtil.getFirstTuesdayInMonth(year, month));

    System.out.print("Last day in month: ");
    System.out.println(DateUtil.getLastDayInMonth(year, month));
    System.out.println();
  }

  private static String padLeft(String s, int n) {
    return String.format("%" + n + "s", s);
  }
}
