package com.marcosavard.commons.time.format;

import com.marcosavard.commons.debug.Console;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class TextDateTimeFormatterDemo {

  public static void main(String[] args) {
    printFormattedDates();
    printFormattedDateTimes();
  }

  private static void printFormattedDates() {
    Locale display = Locale.FRENCH;
    LocalDate date = LocalDate.of(2020, 1, 1);

    // le mercredi 1 janvier de l'an 2020 (standard Java)
    String pattern = "'le' eeee d MMMM 'de l''an' yyyy";
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern, display);
    Console.println("{0} : {1}", date.toString(), formatter.format(date));

    // le mercredi premier janvier de l'an MMXX
    pattern = "'le' eeee #ddd MMMM 'de l''an' #yyy";
    TextDateTimeFormatter formatter2 = TextDateTimeFormatter.ofPattern(pattern, display);
    Console.println("{0} : {1}", date.toString(), formatter2.format(date));

    // 2020 est la 20e année du vingt-et-unième siècle
    pattern = "yyyy 'est la' #y 'année du' #cccc 'siècle'";
    formatter2 = TextDateTimeFormatter.ofPattern(pattern, display);
    Console.println("{0} : {1}", date.toString(), formatter2.format(date));


  }

  private static void printFormattedDateTimes() {
    Locale display = Locale.FRENCH;
    LocalDate date = LocalDate.of(2020, 1, 1);
    LocalDateTime dateTime = LocalDateTime.of(date, LocalTime.of(10, 0));

    // le janvier 2020, 10 heures du matin
    String pattern = "'le' d MMMM yyyy, h 'heures' B";
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern, display);
    Console.println("{0} : {1}", date.toString(), formatter.format(dateTime));
  }
}
