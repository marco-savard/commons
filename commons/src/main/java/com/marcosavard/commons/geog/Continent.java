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
  private static final List<String> AFRICA_COUNTRIES =
      List.of(
          "AO", "BF", "BI", "BJ", "BW", "CD", "CF", "CG", "CI", "CM", "CV", "DJ", "DZ", "EG", "EH",
          "ER", "ET", "GA", "GH", "GM", "GN", "GQ", "GW", "KE", "KM", "LY", "MA", "MG", "ML", "MR",
          "MU", "MW", "MZ", "NA", "NE", "NG", "RE", "RW", "SC", "SD", "SL", "SN", "SO", "SS", "ST",
          "SZ", "TD", "TG", "TN", "TZ", "UG", "YT", "ZA", "ZM", "ZW");
  private static final List<String> AMERICA_COUNTRIES =
      List.of(
          "AG", "AI", "AR", "AS", "AW", "BB", "BL", "BM", "BO", "BQ", "BR", "BS", "BZ", "CA", "CL",
          "CO", "CR", "CU", "CW", "DM", "DO", "EC", "FK", "GD", "GL", "GP", "GT", "GU", "GY", "HN",
          "HT", "JM", "KN", "KY", "LC", "LR", "LS", "MF", "MH", "MQ", "MS", "MX", "NI", "PA", "PE",
          "PM", "PR", "PY", "SH", "SR", "SV", "SX", "TC", "TT", "UM", "US", "UY", "VC", "VE", "VG",
          "VI");
  private static final List<String> ASIA_COUNTRIES =
      List.of(
          "AE", "AF", "AZ", "BD", "BH", "BN", "BT", "CN", "HK", "ID", "IL", "IN", "IO", "IQ", "IR",
          "JO", "JP", "KG", "KH", "KP", "KR", "KW", "KZ", "LA", "LB", "LK", "MM", "MN", "MO", "MY",
          "NP", "OM", "PG", "PH", "PK", "PS", "QA", "SA", "SG", "SY", "TH", "TJ", "TL", "TM", "TR",
          "TW", "UZ", "VN", "YE");

  private static final List<String> AUSTRALIA_COUNTRIES =
      List.of(
          "AU", "CC", "CK", "CX", "FJ", "FM", "GF", "KI", "MP", "NC", "NF", "NR", "NU", "NZ", "PF",
          "PN", "PW", "SB", "TK", "TO", "TV", "VU", "WF", "WS");
  private static final List<String> EUROPE_COUNTRIES =
      List.of(
          "AD", "AL", "AM", "AT", "AX", "BA", "BE", "BG", "BY", "CH", "CY", "CZ", "DE", "DK", "EE",
          "ES", "FI", "FO", "FR", "GB", "GE", "GG", "GI", "GR", "HR", "HU", "IE", "IM", "IS", "IT",
          "JE", "LI", "LT", "LU", "LV", "MC", "MD", "ME", "MK", "MT", "NL", "NO", "PL", "PT", "RO",
          "RS", "RU", "SE", "SI", "SJ", "SK", "SM", "UA", "VA");

  private static Map<Continent, List<TimeZone>> timezonesByContinent;
  private static Map<Continent, List<String>> countriesByContinent;

  public static Continent ofCountry(String country) {
    Continent foundContinent = null;

    for (Continent continent : Continent.values()) {
      if (continent.getCountries().contains(country)) {
        foundContinent = continent;
        break;
      }
    }

    return foundContinent;
  }

  public List<String> getCountries() {
    if (countriesByContinent == null) {
      countriesByContinent = createCountriesByContinent();
    }

    return countriesByContinent.get(this);
  }

  private static Map<Continent, List<String>> createCountriesByContinent() {
    Map<Continent, List<String>> countriesByContinent = new HashMap<>();
    countriesByContinent.put(Continent.AFRICA, AFRICA_COUNTRIES);
    countriesByContinent.put(Continent.AMERICA, AMERICA_COUNTRIES);
    countriesByContinent.put(Continent.ANTARCTICA, List.of());
    countriesByContinent.put(Continent.AUSTRALIA, AUSTRALIA_COUNTRIES);
    countriesByContinent.put(Continent.ASIA, ASIA_COUNTRIES);
    countriesByContinent.put(Continent.EUROPE, EUROPE_COUNTRIES);
    return countriesByContinent;
  }

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
