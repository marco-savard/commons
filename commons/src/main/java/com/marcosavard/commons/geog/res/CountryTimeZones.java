package com.marcosavard.commons.geog.res;

import com.marcosavard.commons.geog.res.CountryTimeZoneData.TimeZoneEntry;
import com.marcosavard.commons.util.collection.UniqueList;

import java.text.MessageFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.stream.Collectors;

public class CountryTimeZones {
  private static CountryTimeZoneData instance;
  private static Map<String, CountryTimeZones> countryTimeZones = new HashMap<>();
  private String countryCode;
  private Locale countryLocale;
  private List<TimeZone> timezones = new ArrayList<>();
  private List<String> areas = new UniqueList<>();
  private List<String> cities = new ArrayList<>();

  public static CountryTimeZones of(String countryCode) {
    CountryTimeZones timeZones = countryTimeZones.get(countryCode);

    if (timeZones == null) {
      timeZones = new CountryTimeZones(countryCode);
      countryTimeZones.put(countryCode, timeZones);
    }

    return timeZones;
  }

  @Override
  public String toString() {
    List<String> ids = new ArrayList<>();

    for (TimeZone timeZone : timezones) {
      ids.add(timeZone.getID());
    }

    String joined = String.join(", ", ids);
    return joined;
  }

  private CountryTimeZones(String countryCode) {
    this.countryCode = countryCode;
    this.countryLocale = findLocale(countryCode);
    CountryTimeZoneData data = getInstance();
    List<TimeZoneEntry> rows = data.getRows();
    List<TimeZoneEntry> entries =
        rows.stream().filter(e -> e.country.equals(countryCode)).collect(Collectors.toList());

    for (TimeZoneEntry entry : entries) {
      TimeZone timeZone = TimeZone.getTimeZone(entry.timezone);
      String area = readArea(timeZone);
      String city = readCity(timeZone);
      timezones.add(timeZone);
      areas.add(area);
      cities.add(city);
    }

    Collections.sort(cities);
  }

  private Locale findLocale(String countryCode) {
    List<Locale> locales = Arrays.asList(Locale.getAvailableLocales());
    Locale locale =
        locales.stream()
            .filter(l -> l.getCountry().equalsIgnoreCase(countryCode))
            .findFirst()
            .orElse(null);
    return locale;
  }

  private String readArea(TimeZone timeZone) {
    String timeZoneId = timeZone.getID();
    String area = substringBefore(timeZoneId, '/');
    return area;
  }

  private String readCity(TimeZone timeZone) {
    String timeZoneId = timeZone.getID();
    String city = substringAfterLast(timeZoneId, '/');
    city = city.replaceAll("_", " ");
    return city;
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

  private static CountryTimeZoneData getInstance() {
    if (instance == null) {
      instance = new CountryTimeZoneData();
    }

    return instance;
  }

  public List<TimeZone> getTimeZones() {
    return timezones;
  }

  public List<String> getCities() {
    return cities;
  }

  public String getDisplayName() {
    return getDisplayName(Locale.getDefault());
  }

  public String getDisplayName(Locale displayLocale) {
    String displayName = countryLocale.getDisplayCountry(displayLocale);
    return displayName;
  }

  public List<String> getAreas() {
    return areas;
  }

  public static String getDisplayNames(List<TimeZone> timezones) {
    return getDisplayNames(timezones, Locale.getDefault());
  }

  public static String getDisplayNames(List<TimeZone> timezones, Locale displayLocale) {
    List<String> displayNames = new UniqueList<>();

    for (TimeZone tz : timezones) {
      String displayName = tz.getDisplayName(displayLocale);
      displayNames.add(displayName);
    }

    String joined = String.join(", ", displayNames);
    return joined;
  }

  public static String getTimeRange(List<TimeZone> timezones) {
    int minOffset = Integer.MAX_VALUE;
    int maxOffset = Integer.MIN_VALUE;

    for (TimeZone tz : timezones) {
      minOffset = Math.min(minOffset, tz.getRawOffset());
      maxOffset = Math.max(maxOffset, tz.getRawOffset());
    }

    String min = formatHourMinute(minOffset);
    String max = formatHourMinute(maxOffset);
    String timeRange = MessageFormat.format("[GMT{0}, GMT{1}]", min, max);
    return timeRange;
  }

  private static String formatHourMinute(int offset) {
    Duration time = Duration.ofMinutes(offset / (60 * 1000));
    long hours = time.toHours();
    long minPart = Math.abs(time.toMinutesPart());
    String formatted = String.format("%+02d:%02d", hours, minPart);
    return formatted;
  }
}
