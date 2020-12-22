package com.marcosavard.commons.geog;

import java.text.MessageFormat;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import com.marcosavard.commons.debug.Console;
import com.marcosavard.commons.geog.TimeZoneCity.Continent;

public class TimeZoneCityDemo {

  public static void main(String[] args) {
    printTimeZone();

    // listAll();


  }

  private static void printTimeZone() {
    Locale fr = Locale.FRENCH;
    String[] ids = TimeZone.getAvailableIDs();

    for (String id : ids) {
      Console.println(id);
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
