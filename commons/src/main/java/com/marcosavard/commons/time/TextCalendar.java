package com.marcosavard.commons.time;

import java.io.PrintStream;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import com.marcosavard.commons.math.type.IntegerList;
import com.marcosavard.commons.util.Array;

public class TextCalendar {
  private static final int LINE_LENGTH = 7 * 4;

  private Locale locale;
  private LocalDate firstOfMonth;
  private List<String> days;

  public static TextCalendar of(int year, int month) {
    TextCalendar calendar = new TextCalendar(year, month, Locale.ENGLISH);
    return calendar;
  }

  public static TextCalendar of(int year, int month, Locale locale) {
    TextCalendar calendar = new TextCalendar(year, month, locale);
    return calendar;
  }

  private TextCalendar(int year, int month, Locale locale) {
    this.locale = locale;
    this.firstOfMonth = LocalDate.of(year, month, 1);

    // build calendar
    int dayOfWeek = firstOfMonth.getDayOfWeek().getValue() % 7;
    int lengthOfMonth = YearMonth.from(firstOfMonth).lengthOfMonth();
    days = IntegerList.of(dayOfWeek).multiplyBy(0).format("%d");
    days.addAll(IntegerList.of(lengthOfMonth).format("%2d"));
    Collections.replaceAll(days, "0", "  ");
  }



  private String indentCenter(String line, int lineLength) {
    int prefixLen = (LINE_LENGTH / 2) - line.length() / 2;
    String prefix = buildString(' ', prefixLen);
    return prefix + line;
  }

  private String buildString(char ch, int len) {
    char[] chars = new char[len];
    Arrays.fill(chars, ch);
    String s = new String(chars);
    return s;
  }

  public void print(PrintStream out) {
    List<String> weeks = buildWeeks();

    for (String line : weeks) {
      out.println(line);
    }
  }

  private List<String> buildWeeks() {
    List<String> weeks = new ArrayList<>();

    // build year month
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY MMM").withLocale(locale);
    String monthYear = firstOfMonth.format(formatter);
    String header = indentCenter(monthYear, LINE_LENGTH);
    weeks.add(header);

    // build weekday names
    String weekDayRow = buildWeekDayRow(firstOfMonth);
    weeks.add(weekDayRow);

    // build days
    List<List<String>> weekList = Array.of(7, days).toList();

    for (List<String> week : weekList) {
      String weekRow = String.join("  ", week);
      weeks.add(weekRow);
    }

    return weeks;
  }

  private String buildWeekDayRow(LocalDate firstOfMonth) {
    int year = firstOfMonth.getYear();
    int month = firstOfMonth.getMonth().getValue();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ccc").withLocale(locale);
    int dayOfWeek = firstOfMonth.getDayOfWeek().getValue();
    List<String> days = new ArrayList<>();

    for (int d = 0; d < 7; d++) {
      LocalDate firstDayOfWeek = LocalDate.of(year, month, (7 + d - dayOfWeek) % 7 + 1);
      String dayOfWeekFmt = firstDayOfWeek.format(formatter).substring(0, 3);
      days.add(dayOfWeekFmt);
    }

    String weekDayRow = String.join(" ", days);
    return weekDayRow;
  }

  public void replace(String original, String replacement) {
    Collections.replaceAll(days, original, replacement);
  }

}
