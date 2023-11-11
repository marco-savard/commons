package com.marcosavard.commons.text;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public abstract class CardinalWordFinder {
  protected static final String MX = "es_MX";
  protected static final String KP = "ko_KP";
  protected static final String KR = "ko_KR";

  protected static final String NY = "America/New_York";
  protected static final String CHI = "America/Chicago";

  protected static final String LA = "America/Los_Angeles";

  protected static final String ANCHOR = "America/Anchorage";

  protected static final String SANTA_ISABEL = "America/Santa_Isabel";
  protected Locale locale;
  protected DateFormatSymbols symbols;
  protected String[][] zoneStrings;

  protected List<String> timezones = new ArrayList<>();

  protected Map<String, String> countryNames = new HashMap<>();
  protected Map<String, String> timezoneNames = new HashMap<>();

  public CardinalWordFinder(String code, List<String> countries, List<String> timezones) {
    locale = Locale.forLanguageTag(code);
    symbols = new DateFormatSymbols(locale);
    zoneStrings = symbols.getZoneStrings();
    this.timezones.addAll(timezones);
    Locale[] locales = Locale.getAvailableLocales();

    for (Locale locale : locales) {
      // Console.println(locale.toString());

      if (countries.contains(locale.toString())) {
        countryNames.put(locale.toString(), locale.getDisplayCountry(this.locale));
      }
    }

    for (String[] zoneString : zoneStrings) {
      String tzCode = zoneString[0];
      String displayName = zoneString[1];

      if (this.timezones.contains(tzCode)) {
        timezoneNames.put(tzCode, displayName);
      }
    }
  }

  public abstract String findNorth();

  public abstract String findSouth();

  public abstract String findEast();

  public abstract String findWest();
}
