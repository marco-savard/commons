package com.marcosavard.commons.time;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.Date;

public class DatesDemo {

  public static void main(String[] args) {
    printLeapYears(1880, 1920);
    printMonth(2019, Month.JANUARY);
    listDatesInMonth(2019, Month.JANUARY);
    convertToLocalDate(Dates.toDate(LocalDate.of(2019, 1, 15)));
  }

  private static void printLeapYears(int startYear, int endYear) {
    for (int year = startYear; year <= endYear; year += 10) {
      boolean leap = Dates.isLeapYear(year);
      System.out.println(year + " is " + (leap ? "leap" : "not leap"));
    }
    System.out.println();
  }

  private static void printMonth(int year, Month month) {
    LocalDate lastOfMonth = Dates.toLocalDate(Dates.getLastDayInMonth(year, month));
    boolean beforeEndOfMonth = true;
    int weekInMonth = 1;

    String title = padLeft(month.toString() + " " + year, 20);
    System.out.println(title);

    for (DayOfWeek day : Dates.allDaysOfWeek()) {
      String dayOfWeek = day.toString().substring(0, 2);
      System.out.print(dayOfWeek + "  ");
    }
    System.out.println();

    do {
      for (DayOfWeek day : Dates.allDaysOfWeek()) {
        LocalDate date = Dates.of(2019, Month.JANUARY, weekInMonth, day);
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
    LocalDate localDate = Dates.toLocalDate(original);
    Date converted = Dates.toDate(localDate);

    System.out.println("Original Date: " + original);
    System.out.println("In Local Date: " + localDate);
    System.out.println("Back to Date: " + converted);
    System.out.println("Equal: " + original.equals(converted));
    System.out.println();
  }

  private static void listDatesInMonth(int year, Month month) {
    System.out.print("1st in month: ");
    System.out.println(Dates.getFirstDayInMonth(year, month));

    System.out.print("1st Sunday in month: ");
    System.out.println(Dates.getFirstSundayInMonth(year, month));

    System.out.print("1st Monday in month: ");
    System.out.println(Dates.getFirstMondayInMonth(year, month));

    System.out.print("1st Tuesday in month: ");
    System.out.println(Dates.getFirstTuesdayInMonth(year, month));

    System.out.print("Last day in month: ");
    System.out.println(Dates.getLastDayInMonth(year, month));
    System.out.println();
  }

  private static String padLeft(String s, int n) {
    return String.format("%" + n + "s", s);
  }

}
