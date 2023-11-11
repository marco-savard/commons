package com.marcosavard.commons.geog;

import com.marcosavard.commons.debug.Console;
import com.marcosavard.commons.ling.Language;

import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CardinalPointDemo {

  private static final Locale[] locales =
      new Locale[] {
        Locale.FRENCH,
        Locale.ENGLISH,
        Locale.GERMAN,
        Locale.ITALIAN,
        Language.SPANISH.toLocale(),
        Language.PORTUGUESE.toLocale(),
        Language.ROMANIAN.toLocale(),
        Language.DUTCH.toLocale(),
        Language.SWEDISH.toLocale()
      };

  public static void main(String[] args) {
    for (Locale display : locales) {
      printCardinalPoint(display);
    }
  }

  private static void printCardinalPoint(Locale display) {
    Console.print("{0} : ", display.getISO3Language());
    List<String> items = new ArrayList<>();

    for (CardinalPoint point : CardinalPoint.values()) {
      items.add(point.getDisplayName(TextStyle.SHORT, display));
    }

    String joined = String.join(", ", items);
    Console.println(joined);
  }
}
