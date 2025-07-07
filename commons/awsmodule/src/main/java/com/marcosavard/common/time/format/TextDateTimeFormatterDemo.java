package com.marcosavard.common.time.format;

import com.marcosavard.common.debug.Console;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class TextDateTimeFormatterDemo {

  public static void main(String[] args) {
    printFormattedDates();
    printFormattedDateTimes();
    //TODO quinze heure moins quart
  }

  private static void printFormattedDates() {
    Locale display = Locale.FRENCH;
    LocalDate date = LocalDate.of(2020, 1, 1);

    // le mercredi 1 janvier de l'an 2020 (standard Java)
    String pattern = "eee d MMMM yyyy";
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern, display);
    Console.println("{0} : {1}", date.toString(), formatter.format(date));

    // le mercredi 1 janvier de l'an 2020 (standard Java)
    pattern = "'le' eeee d MMMM 'de l''an' yyyy";
    formatter = DateTimeFormatter.ofPattern(pattern, display);
    Console.println("{0} : {1}", date.toString(), formatter.format(date));

    // le mercredi 1er janvier de l'an MMXX
    pattern = "'le' eeee #dd MMMM 'de l''an' #yyyyy";
    TextDateTimeFormatter textFormatter = TextDateTimeFormatter.ofPattern(pattern, display);
    Console.println("{0} : {1}", date.toString(), textFormatter.format(date));

    // le mercredi premier janvier de l'an deux milles vingt
    pattern = "'le' eeee #ddd MMMM 'de l''an' #yyy";
    textFormatter = TextDateTimeFormatter.ofPattern(pattern, display);
    Console.println("{0} : {1}", date.toString(), textFormatter.format(date));

    // 2020 est la 20e année du vingt-et-unième siècle
    pattern = "yyyy 'est la' #y 'année du' #cccc 'siècle'";
    textFormatter = TextDateTimeFormatter.ofPattern(pattern, display);
    Console.println("{0} : {1}", date.toString(), textFormatter.format(date));
    Console.println();
  }

  private static void printFormattedDateTimes() {
    Locale display = Locale.FRENCH;
    LocalDate date = LocalDate.of(2020, 1, 1);
    LocalDateTime dateTime = LocalDateTime.of(date, LocalTime.of(10, 0));

    // le janvier 2020, 10 heures du matin
    String pattern = "'le' d MMMM yyyy, h 'heures' B";
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern, display);
    Console.println("{0} : {1}", date.toString(), formatter.format(dateTime));

    // le janvier 2020, 10 heures du matin
    pattern = "'le' #ddd MMMM #yy #cc, h 'heures' B";
    TextDateTimeFormatter textFormatter = TextDateTimeFormatter.ofPattern(pattern, display);
    Console.println("{0} : {1}", date.toString(), textFormatter.format(dateTime));
    Console.println();
  }
}
