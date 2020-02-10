package com.marcosavard.commons.time;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class EasterDemo {

  public static void main(String[] args) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM");

    for (int y = 2010; y < 2024; y++) {
      LocalDate easterDate = Easter.getEasterLocalDate(y);
      String formatted = easterDate.format(formatter);
      String year = String.format("%d", y);
      String msg = MessageFormat.format("Easter {0} is : {1}", year, formatted);
      System.out.println(msg);
    }
  }
}
