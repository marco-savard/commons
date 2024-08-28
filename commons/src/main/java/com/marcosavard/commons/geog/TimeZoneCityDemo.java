package com.marcosavard.commons.geog;

import com.marcosavard.commons.debug.Console;
import com.marcosavard.commons.geog.TimeZoneCity.Continent;
import com.marcosavard.commons.ling.Language;

import java.text.MessageFormat;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class TimeZoneCityDemo {

  public static void main(String[] args) {
    Locale display = Language.FRENCH.toLocale();
    printTimeZones(display);
    // printTimeZoneCities(display);
  }

  private static void printTimeZoneCities(Locale display) {
    List<TimeZoneCity> cities = TimeZoneCity.getWorldCities();

    for (TimeZoneCity city : cities) {
      if (city.getContinent().equals(Continent.AMERICA)) {
        Console.println(city.toString());
      }
    }
  }

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

  private static void printTimeZones(Locale displayLocale) {
    Console.println(displayLocale.getDisplayLanguage(displayLocale));
    String[] ids = TimeZone.getAvailableIDs();
    boolean daylight = false;

    for (String timezoneId : ids) {
      TimeZone timezone = TimeZone.getTimeZone(timezoneId);
      String shortName = timezone.getDisplayName(daylight, TimeZone.SHORT, displayLocale);
      String longName = timezone.getDisplayName(daylight, TimeZone.LONG, displayLocale);
      Console.println("id={0} short={1} long={2}", timezoneId, shortName, longName);
    }
  }

  private static void listAll() {
    // Get cities in Americas
    List<TimeZoneCity> americaCities = TimeZoneCity.getWorldCitiesByContinent(Continent.AMERICA);

    // for each time zone, from Greenwich to west
    for (int i = 0; i < 12; i++) {
      List<TimeZoneCity> cities = TimeZoneCity.filterByHourOffset(americaCities, -i);

      if (!cities.isEmpty()) {
        String msg = MessageFormat.format("Cities in timezone {0}", -i);
        System.out.println(msg);

        for (TimeZoneCity city : cities) {
          System.out.println("  .." + city);
        }

        System.out.println();
      }
    }
  }
}
