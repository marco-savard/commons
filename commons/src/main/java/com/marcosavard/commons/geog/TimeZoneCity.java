package com.marcosavard.commons.geog;

import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;

/**
 * A city extracted from Java TimeZone IDs.
 *
 * @author Marco
 */
public class TimeZoneCity {
  public enum Continent {
    AFRICA,
    AMERICA,
    ASIA,
    EUROPE,
    AUSTRALIA
  };

  private static List<TimeZoneCity> cities = new ArrayList<>();
  private String cityName;
  private Continent continent;
  private int offsetMinutes;

  private TimeZoneCity(String cityName, Continent continent, int offsetSeconds) {
    this.cityName = cityName.replaceAll("_", " ");
    this.continent = continent;
    this.offsetMinutes = offsetSeconds;
  }

  public int getOffsetInMinutes() {
    return offsetMinutes;
  }

  public Continent getContinent() {
    return continent;
  }

  @Override
  public String toString() {
    double offsetHours = (offsetMinutes / 60.0);
    return cityName + " " + offsetHours;
  }

  public static List<TimeZoneCity> getWorldCities() {
    if (cities.isEmpty()) {
      String[] ids = TimeZone.getAvailableIDs();

      for (String id : ids) {
        TimeZone tz = TimeZone.getTimeZone(id);
        int offsetMinutes = tz.getRawOffset() / (1000 * 60);

        if (id.startsWith("Africa/")) {
          TimeZoneCity city = createCity(id, Continent.AFRICA, offsetMinutes);
          cities.add(city);
        } else if (id.startsWith("America/")) {
          TimeZoneCity city = createCity(id, Continent.AMERICA, offsetMinutes);
          cities.add(city);
        } else if (id.startsWith("Asia/")) {
          TimeZoneCity city = createCity(id, Continent.ASIA, offsetMinutes);
          cities.add(city);
        } else if (id.startsWith("Europe/")) {
          TimeZoneCity city = createCity(id, Continent.EUROPE, offsetMinutes);
          cities.add(city);
        } else if (id.startsWith("Australia/")) {
          TimeZoneCity city = createCity(id, Continent.EUROPE, offsetMinutes);
          cities.add(city);
        }
      }
    }

    return cities;
  }

  public static List<TimeZoneCity> getWorldCitiesByHourOffset(int offsetInHours) {
    List<TimeZoneCity> allCities = getWorldCities();
    return filterByHourOffset(allCities, offsetInHours);
  }

  public static List<TimeZoneCity> filterByHourOffset(
      List<TimeZoneCity> cities, int offsetInHours) {
    int offsetInMinutes = offsetInHours * 60;
    List<TimeZoneCity> tzCities =
        cities.stream()
            .filter(c -> c.getOffsetInMinutes() == offsetInMinutes)
            .collect(Collectors.toList());
    return tzCities;
  }

  public static List<TimeZoneCity> getWorldCitiesByContinent(Continent continent) {
    List<TimeZoneCity> allCities = getWorldCities();
    List<TimeZoneCity> cities =
        allCities.stream().filter(c -> c.getContinent() == continent).collect(Collectors.toList());
    return cities;
  }

  private static TimeZoneCity createCity(String id, Continent continent, int offsetSeconds) {
    int idx = id.lastIndexOf('/');
    String cityName = id.substring(idx + 1);
    TimeZoneCity city = new TimeZoneCity(cityName, continent, offsetSeconds);
    return city;
  }
}
