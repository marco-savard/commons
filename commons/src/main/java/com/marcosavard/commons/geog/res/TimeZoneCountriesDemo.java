package com.marcosavard.commons.geog.res;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import com.marcosavard.commons.geog.res.TimeZoneCountries.TimeZoneEntry;

public class TimeZoneCountriesDemo {

  public static void main(String[] args) {
    TimeZoneCountries timeZones = new TimeZoneCountries();

    // listAll(timeZones);
    listCountry(timeZones, "CA");
    listCountry(timeZones, "US");
    listCountry(timeZones, "MX");
    listCountry(timeZones, "AU");
    listCountry(timeZones, "NZ");
    listCountry(timeZones, "RU");
    listCountry(timeZones, "CN");
    listCountry(timeZones, "FR");
    listCountry(timeZones, "TR");
    listCountry(timeZones, "GB");
    listCountry(timeZones, "IL");
    listCountry(timeZones, "CH");
    listCountry(timeZones, "GR");
    listCountry(timeZones, "AQ");
    listCountry(timeZones, "AR");

  }

  private static void listAll(TimeZoneCountries timeZones) {
    List<TimeZoneEntry> timezones = timeZones.getRows();

    for (TimeZoneEntry timezone : timezones) {
      System.out.println(timezone);
    }
  }

  private static void listCountry(TimeZoneCountries timeZones, String country) {
    List<TimeZoneEntry> timezones = timeZones.getRows();
    List<TimeZoneEntry> countryTimezones =
        timezones.stream().filter(t -> t.country.equals(country)).collect(Collectors.toList());

    List<String> areas = findAreas(countryTimezones);
    List<String> cities = findCities(countryTimezones);

    System.out.println(areas);
    System.out.println(cities);
    System.out.println();

  }

  private static List<String> findAreas(List<TimeZoneEntry> timezones) {
    List<String> areas = new ArrayList<>();

    for (TimeZoneEntry timezone : timezones) {
      String area = substringBefore(timezone.timezone, '/');

      if (!areas.contains(area)) {
        areas.add(area);
      }
    }

    return areas;
  }


  private static List<String> findCities(List<TimeZoneEntry> timezones) {
    List<String> cities = new ArrayList<>();

    for (TimeZoneEntry timezone : timezones) {
      String city = substringAfterLast(timezone.timezone, '/');
      city = city.replaceAll("_", " ");

      if (!cities.contains(city)) {
        cities.add(city);
      }
    }

    Collections.sort(cities);
    return cities;
  }

  private static String substringBefore(String original, char ch) {
    int idx = original.indexOf(ch);
    String substring = original.substring(0, idx);
    return substring;
  }

  private static String substringAfterLast(String original, char ch) {
    int idx = original.lastIndexOf(ch);
    String substring = original.substring(idx + 1);
    return substring;
  }



}
