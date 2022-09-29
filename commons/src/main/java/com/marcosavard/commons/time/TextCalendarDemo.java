package com.marcosavard.commons.time;

import java.util.Locale;

public class TextCalendarDemo {
  public static void main(String[] args) {
    for (int m = 1; m <= 12; m++) {
      TextCalendar calendar = TextCalendar.of(2022, m, Locale.ENGLISH);
      calendar.print(System.out);
      System.out.println();
    }
  }
}
