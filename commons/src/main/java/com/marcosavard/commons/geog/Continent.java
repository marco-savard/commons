package com.marcosavard.commons.geog;

import com.marcosavard.commons.lang.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public enum Continent {
  AFRICA,
  AMERICA,
  ANTARCTICA,
  ASIA,
  AUSTRALIA,
  EUROPE;

  private static final Locale ANTARCTICA_LOCALE = new Locale("en", "AQ");
  private static final Locale INDONESIA_LOCALE = new Locale("in", "ID");
  private static Map<Continent, List<TimeZone>> timezonesByContinent;

  public TimeZone[] getTimeZones() {
    init();
    List<TimeZone> timeZones = timezonesByContinent.get(this);
    return timeZones.toArray(new TimeZone[0]);
  }

  private void init() {
    if (timezonesByContinent == null) {
      timezonesByContinent = new HashMap<>();
      String[] ids = TimeZone.getAvailableIDs();

      for (String id : ids) {
        initTimezone(id);
      }
    }
  }

  private void initTimezone(String id) {
    int idx = id.indexOf('/');
    String prefix = (idx < 1) ? "" : id.substring(0, idx);
    Continent foundContinent =
        Arrays.stream(Continent.values())
            .filter(c -> c.name().equalsIgnoreCase(prefix))
            .findFirst()
            .orElse(null);

    if (foundContinent != null) {
      if (!timezonesByContinent.containsKey(foundContinent)) {
        timezonesByContinent.put(foundContinent, new ArrayList<>());
      }

      List<TimeZone> timeZones = timezonesByContinent.get(foundContinent);
      TimeZone timezone = TimeZone.getTimeZone(id);
      timeZones.add(timezone);
    }
  }

  public String getDisplayName(Locale displayLocale) {
    String displayName;

    if (ordinal() == AFRICA.ordinal()) {
      displayName = ContinentName.getDisplayAfrica(displayLocale);
    } else if (ordinal() == AMERICA.ordinal()) {
      displayName = ContinentName.getDisplayAmerica(displayLocale);
    } else if (ordinal() == ANTARCTICA.ordinal()) {
      displayName = ContinentName.getDisplayAntarctica(displayLocale);
    } else if (ordinal() == ASIA.ordinal()) {
      displayName = ContinentName.getDisplayAsia(displayLocale);
    } else if (ordinal() == AUSTRALIA.ordinal()) {
      displayName = ContinentName.getDisplayAustralia(displayLocale);
    } else if (ordinal() == EUROPE.ordinal()) {
      displayName = ContinentName.getDisplayEurope(displayLocale);
    } else {
      displayName = StringUtil.capitalize(this.name().toLowerCase(displayLocale), displayLocale);
    }

    return displayName;
  }
}
