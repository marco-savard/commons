package com.marcosavard.commons.game.sport;

import com.marcosavard.commons.debug.Console;

import java.util.Locale;

// https://www.fileformat.info/info/unicode/category/So/list.htm
public class SportDemo {
  public static void main(String[] args) {
    Locale display = Locale.FRENCH;

    for (Sport sport : Sport.values()) {
      String name = sport.getDisplayName(display);
      String countries = String.join(",", sport.getCountries());
      Console.println("{0} ({1}): {2}", name, sport.getSymbol(), countries);
    }
  }
}
