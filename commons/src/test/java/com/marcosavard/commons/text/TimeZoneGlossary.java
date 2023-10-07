package com.marcosavard.commons.text;

import com.marcosavard.commons.debug.Console;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
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
        List.of("America/New_York" /*"Africa/Djibouti", , "Australia/Sydney", "Europe/Athens"*/);
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

      standardTimeWord = standardTimeFinder.findCommonWord();
      timeWord = timeFinder.findLongestCommonWord();
      africaWord = africaFinder.findLongestCommonWord();

      eastFinder.addWordToIgnore(standardTimeWord);
      eastWord = eastFinder.findLongestCommonWord();
      // standardTimeWord = standardTimeFinder.getFoundWord();
      // timeWord = timeFinder.getFoundWord();
      // africaFinder.filter(standardTimeWord);
      //   eastFinder.filter(standardTimeWord);

      //   africaWord = africaFinder.getFoundWord();
      //   eastWord = eastFinder.getFoundWord();
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
  }
}
