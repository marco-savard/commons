package com.marcosavard.commons.text;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class TimeZoneGlossary {
  private static TimeZoneGlossary singleInstance = null;

  private Map<Locale, Glossary> localeGlossaries = new HashMap<>();

  private TimeZoneGlossary() {}

  public static TimeZoneGlossary getInstance() {
    if (singleInstance == null) {
      singleInstance = new TimeZoneGlossary();
    }

    return singleInstance;
  }

  public String getTimeWord(Locale locale) {
    Glossary glossary = localeGlossaries.get(locale);

    if (glossary == null) {
      glossary = new Glossary(locale);
      localeGlossaries.put(locale, glossary);
    }

    return glossary.getTimeWord();
  }

  private static class Glossary {
    private static final List<String> UNIVERSALS =
        List.of("Antarctica", "Etc", "UCT", "UTC", "Universal", "Zulu");
    private String timeWord;

    Glossary(Locale locale) {
      DateFormatSymbols symbols = new DateFormatSymbols(Locale.ENGLISH);
      DateFormatSymbols locSymbols = new DateFormatSymbols(locale);
      String[][] zoneStrings = symbols.getZoneStrings();
      String[][] locZoneStrings = locSymbols.getZoneStrings();
      Map<String, Map<Locale, String>> map = new TreeMap<>();

      for (int i = 0; i < zoneStrings.length; i++) {
        String code = zoneStrings[i][0];
        String displayName = zoneStrings[i][1];
        Map<Locale, String> displayNames = new LinkedHashMap<>();
        displayNames.put(Locale.ENGLISH, displayName);
        map.put(code, displayNames);
      }

      for (int i = 0; i < locZoneStrings.length; i++) {
        String code = locZoneStrings[i][0];
        String displayName = locZoneStrings[i][1];
        Map<Locale, String> displayNames = map.get(code);
        displayNames.put(locale, displayName);
      }

      List<String> locDisplayNames = new ArrayList<>();

      for (String key : map.keySet()) {
        Map<Locale, String> displayNames = map.get(key);
        String displayName = displayNames.get(Locale.ENGLISH);
        String locDisplayName = displayNames.get(locale);
        boolean translated =
            !((locale.equals(Locale.ENGLISH)) || displayName.equals(locDisplayName));

        if (locDisplayName.startsWith("Punta")) {
          int i = 0;
        }

        if (locDisplayName != null) {
          if (!UNIVERSALS.stream().filter(s -> key.startsWith(s)).findAny().isPresent()) {
            if (translated) {
              locDisplayNames.add(locDisplayName);

              if (!locDisplayName.toLowerCase().contains("heure")) {
                String value = map.get(key).toString();
                String text = String.join(" : ", key, value);
                // Console.println(text);
              }
            }
          }
        }
      }

      Set<String> commons = new HashSet<>();
      String previous = locDisplayNames.get(0).toLowerCase();
      commons.add(previous);

      for (String locDisplayName : locDisplayNames) {
        Set<String> newCommons = new HashSet<>();
        // Console.println(locDisplayName);

        for (String common : commons) {
          Set<String> substrings = findCommonSubstrings(common, locDisplayName.toLowerCase());
          for (String substring : substrings) {
            if (!newCommons.contains(substring)) {
              newCommons.add(substring);
            }
          }
        }
        commons = newCommons;
      }

      String longest = commons.stream().max(Comparator.comparingInt(String::length)).get();
      timeWord = longest;
    }

    private static Set<String> findCommonSubstrings(String s, String t) {
      int[][] table = new int[s.length()][t.length()];
      int length = 0;
      Set<String> result = new HashSet<>();

      for (int i = 0; i < s.length(); i++) {
        for (int j = 0; j < t.length(); j++) {
          if (s.charAt(i) != t.charAt(j)) {
            continue;
          }

          table[i][j] = (i == 0 || j == 0) ? 1 : 1 + table[i - 1][j - 1];
          length = table[i][j];
          String substring = s.substring(i - length + 1, i + 1).trim();

          if (substring.length() > 1 && !result.contains(substring)) {
            result.add(substring);
          }
        }
      }

      return result;
    }

    public String getTimeWord() {
      return timeWord;
    }
  }
}