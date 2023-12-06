package com.marcosavard.commons.geog;

import com.marcosavard.commons.debug.Console;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class ContinentDemo {
  public static void main(String[] args) {
    Locale fr = Locale.FRENCH;
    Continent[] continents = Continent.values();

    for (Continent continent : continents) {
      displayContinent(fr, continent);
    }
  }

  private static void displayContinent(Locale displayLocale, Continent continent) {
    List<String> countries = continent.getCountries();
    TimeZone[] timeZones = continent.getTimeZones();

    Console.println(continent.getDisplayName(displayLocale));
    Console.println("  " + toString(countries, displayLocale));
    Console.println("  " + toString(timeZones));
    Console.println();
  }

  private static String toString(List<String> countries, Locale displayLocale) {
    List<String> names = new ArrayList<>();

    for (String country : countries) {
      Locale locale = Country.localesOf(country).get(0);
      String name = locale.getDisplayCountry(displayLocale);

      if (!names.contains(name)) {
        names.add(name);
      }
    }

    String str = "[" + String.join(", ", names) + "]";
    return str;
  }

  private static String toString(TimeZone[] timeZones) {
    List<String> names = new ArrayList<>();

    for (TimeZone timeZone : timeZones) {
      String id = timeZone.getID();
      int idx = id.indexOf('/');
      String name = id.substring(idx + 1);
      names.add(name);
    }

    String str = "[" + String.join(", ", names) + "]";
    return str;
  }
}
