package com.marcosavard.commons.time.format;

import com.marcosavard.commons.debug.Console;

import java.time.LocalDate;
import java.util.Locale;

public class NumeralDateTimeFormatterDemo {

  public static void main(String[] args) {
    printFormattedDates();
  }

  private static void printFormattedDates() {
    Locale display = Locale.FRENCH;
    LocalDate date = LocalDate.of(2020, 1, 1);

    // le mercredi 1 janvier de l'an 2020
    String pattern = "'le' eeee d MMMM 'de l''an' yyyy";
    NumeralDateTimeFormatter formatter = NumeralDateTimeFormatter.ofPattern(pattern, display);
    Console.println("{0} : {1}", date.toString(), formatter.format(date));

    // le mercredi premier janvier de l'an MMXX
    pattern = "'le' eeee #d MMMM 'de l''an' #yyyy";
    formatter = NumeralDateTimeFormatter.ofPattern(pattern, display);
    Console.println("{0} : {1}", date.toString(), formatter.format(date));

    pattern = "yyyy 'est l''an' yy 'du' #cccc 'siecle'";
    formatter = NumeralDateTimeFormatter.ofPattern(pattern, display);
    Console.println("{0} : {1}", date.toString(), formatter.format(date));

    // d 1 janvier
    // #dO 1er janvier
    // #dT un janvier
    // #dTO premier janvier
    // #dR I janvier
    // #dRO Ie janvier

    // yyyy 2020
    // #yyyyO 2020e
    // #yyyyT deux milles vingt
    // #yyyyTO deux milles vingtieme
    // #yyyyR MMXX
    // #yyyyRO MMXXe
  }
}
