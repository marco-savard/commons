package com.marcosavard.commons.text;

import com.marcosavard.commons.debug.Console;

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

  public String getAfricaWord(Locale locale) {
    Glossary glossary = loadGlossary(locale);
    return glossary.getAfricaWord();
  }

  public String getEastWord(Locale locale) {
    Glossary glossary = loadGlossary(locale);
    return glossary.getEastWord();
  }

  public String getTimeWord(Locale locale) {
    Glossary glossary = loadGlossary(locale);
    return glossary.getTimeWord();
  }

  public String getStandardTimeWord(Locale locale) {
    Glossary glossary = loadGlossary(locale);
    return glossary.getStandardTimeWord();
  }

  private Glossary loadGlossary(Locale locale) {
    Glossary glossary = localeGlossaries.get(locale);

    if (glossary == null) {
      glossary = new Glossary(locale);
      localeGlossaries.put(locale, glossary);
    }

    return glossary;
  }

  private static class Glossary {
    private static final List<String> UNIVERSALS =
        List.of("Antarctica", "Etc", "UCT", "UTC", "Universal", "Zulu");
    private static final List<String> TIME_TIMEZONES =
        List.of(
            "America/Chicago",
            "America/Denver",
            "America/Los_Angeles",
            "America/New_York",
            "Asia/Tokyo",
            "Africa/Johannesburg",
            "Europe/Paris",
            "Europe/London");
    private static final List<String> AFRICA_TIMEZONES =
        List.of("Africa/Johannesburg", "Africa/Lagos", "Africa/Nairobi", "Africa/Harare ");

    private static final List<String> EAST_TIMEZONES =
        List.of("Africa/Djibouti", "America/New_York", "Australia/Sydney", "Europe/Athens");
    private static final List<String> STANDART_TIMES =
        List.of(
            "America/Chicago",
            "America/Denver",
            "America/Los_Angeles",
            "America/New_York",
            "Asia/Tokyo",
            "Europe/Paris");
    // private static final String ASIA = "Asia";

    private String standardTimeWord, timeWord, africaWord, eastWord;

    Glossary(Locale locale) {
      DateFormatSymbols symbols = new DateFormatSymbols(Locale.ENGLISH);
      DateFormatSymbols locSymbols = new DateFormatSymbols(locale);
      String[][] zoneStrings = symbols.getZoneStrings();
      String[][] locZoneStrings = locSymbols.getZoneStrings();
      Map<String, Map<Locale, String>> map = new TreeMap<>();

      // save en display names
      for (int i = 0; i < zoneStrings.length; i++) {
        String code = zoneStrings[i][0];
        String displayName = zoneStrings[i][1];
        Map<Locale, String> displayNames = new LinkedHashMap<>();
        displayNames.put(Locale.ENGLISH, displayName);
        map.put(code, displayNames);
      }

      // save locale display names
      for (int i = 0; i < locZoneStrings.length; i++) {
        String code = locZoneStrings[i][0];
        String displayName = locZoneStrings[i][1];
        Map<Locale, String> displayNames = map.get(code);
        displayNames.put(locale, displayName);
      }

      List<String> locDisplayNames = new ArrayList<>();
      WordFinder timeFinder = new WordFinder(TIME_TIMEZONES);
      WordFinder standardTimeFinder = new WordFinder(STANDART_TIMES);
      WordFinder eastFinder = new WordFinder(EAST_TIMEZONES);
      WordFinder africaFinder = new WordFinder(AFRICA_TIMEZONES);

      for (String key : map.keySet()) {
        Map<Locale, String> displayNames = map.get(key);
        String displayName = displayNames.get(Locale.ENGLISH);
        String locDisplayName = displayNames.get(locale);
        boolean translated = locale.equals(Locale.ENGLISH) || !displayName.equals(locDisplayName);

        if (locDisplayName != null) {
          if (!UNIVERSALS.stream().filter(s -> key.startsWith(s)).findAny().isPresent()) {
            if (translated) {
              locDisplayNames.add(locDisplayName);

              timeFinder.examine(key, locDisplayName);
              standardTimeFinder.examine(key, locDisplayName);
              eastFinder.examine(key, locDisplayName);
              africaFinder.examine(key, locDisplayName);

              String value = map.get(key).toString();
              String text = String.join(" : ", key, value);
              Console.println(text);
            }
          }
        }
      }

      standardTimeWord = standardTimeFinder.getFoundWord();
      timeWord = timeFinder.getFoundWord();
      africaFinder.filter(standardTimeWord);
      eastFinder.filter(standardTimeWord);

      africaWord = africaFinder.getFoundWord();
      eastWord = eastFinder.getFoundWord();
      // Console.println(standardTimeWord);
    }

    public String getTimeWord() {
      return timeWord;
    }

    public String getStandardTimeWord() {
      return standardTimeWord;
    }

    public String getAfricaWord() {
      return africaWord;
    }

    public String getEastWord() {
      return eastWord;
    }

    private static class WordFinder {
      private List<String> timezones;
      private List<String> displayNames = new ArrayList<>();
      private List<String> wordsToIgnore = new ArrayList<>();

      public WordFinder(List<String> timezones) {
        this.timezones = timezones;
      }

      public void examine(String key, String locDisplayName) {
        if (timezones.contains(key)) {
          displayNames.add(locDisplayName);
        }
      }

      public void filter(String words) {
        String[] parts = words.split("\\s+");

        for (String part : parts) {
          wordsToIgnore.add(part);
        }
      }

      public String getFoundWord() {
        return findLongestSubstring(displayNames);
      }

      private String findLongestSubstring(List<String> locDisplayNames) {
        Set<String> commons = new HashSet<>();
        String previous = locDisplayNames.get(0).toLowerCase();
        commons.add(previous);

        for (String locDisplayName : locDisplayNames) {
          locDisplayName = locDisplayName.toLowerCase();
          Set<String> newCommons = new HashSet<>();

          for (String word : wordsToIgnore) {
            locDisplayName = locDisplayName.replace(word, "").trim();
          }

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
        longest = removeIsolatedLetters(longest);
        return longest;
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

      private String removeIsolatedLetters(String longest) {
        List<String> words = new ArrayList<>();
        longest = longest.replace('’', ' ');
        longest = longest.replace('-', ' ');
        String[] parts = longest.split("\\s+");

        for (String part : parts) {
          if (part.length() > 1) {
            words.add(part);
          }
        }

        return String.join(" ", words);
      }
    }
  }
}
