package com.marcosavard.commons.geog;

import com.marcosavard.commons.debug.Console;
import com.marcosavard.commons.ling.Language;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class TimeZoneGlossaryDemo {

  public static void main(String[] args) {
    Locale[] locales =
        new Locale[] {
          Locale.FRENCH,
          Locale.ENGLISH,
          Locale.GERMAN,
          Locale.ITALIAN,
          Language.SPANISH.toLocale(),
          Language.PORTUGUESE.toLocale(),
          Language.ROMANIAN.toLocale(),
          Language.DUTCH.toLocale(),
          Language.SWEDISH.toLocale()
        };

    for (Locale locale : locales) {
      String word = getTimeWord(locale);
      String standard = getPacificWord(locale);
      Console.println("{0} : {1}", locale.getLanguage(), standard);
    }
  }

  private static String getStandardWord(Locale locale) {
    boolean daylight = false;
    String timeWord = getTimeWord(locale);
    TimeZone timezone1 = TimeZone.getTimeZone("America/Denver");
    TimeZone timezone2 = TimeZone.getTimeZone("America/Juneau");
    String longName1 = timezone1.getDisplayName(daylight, TimeZone.LONG, locale);
    String longName2 = timezone2.getDisplayName(daylight, TimeZone.LONG, locale);
    longName1 = longName1.toLowerCase().replace(timeWord, "");
    longName2 = longName2.toLowerCase().replace(timeWord, "");
    longName1 = longName1.replace('-', ' ').toLowerCase();
    longName2 = longName2.replace('-', ' ').toLowerCase();
    String word = findCommonWords(longName1, longName2);
    word = removeShortWords(word, 3).trim();
    return word;
  }

  private static String getTimeWord(Locale locale) {
    boolean daylight = false;
    TimeZone timezone1 = TimeZone.getTimeZone("Antarctica/Davis");
    TimeZone timezone2 = TimeZone.getTimeZone("Antarctica/Vostok");
    String longName1 = timezone1.getDisplayName(daylight, TimeZone.LONG, locale);
    String longName2 = timezone2.getDisplayName(daylight, TimeZone.LONG, locale);
    longName1 = longName1.replace('-', ' ').toLowerCase();
    longName2 = longName2.replace('-', ' ').toLowerCase();
    String timeWord = findCommonWords(longName1, longName2);
    timeWord = timeWord.replace("din", "");
    timeWord = removeShortWords(timeWord, 3).trim();
    return timeWord;
  }

  private static String getPacificWord(Locale locale) {
    boolean daylight = false;
    String timeWord = getTimeWord(locale);
    String standardWord = getStandardWord(locale);
    TimeZone timezone1 = TimeZone.getTimeZone("Mexico/BajaSur");
    TimeZone timezone2 = TimeZone.getTimeZone("America/Los_Angeles");
    String longName1 = timezone1.getDisplayName(daylight, TimeZone.LONG, locale);
    String longName2 = timezone2.getDisplayName(daylight, TimeZone.LONG, locale);
    longName1 = longName1.toLowerCase().replace(timeWord, "");
    longName2 = longName2.toLowerCase().replace(standardWord, "");
    longName1 = longName1.replace('-', ' ').toLowerCase();
    longName2 = longName2.replace('-', ' ').toLowerCase();
    String word = findCommonWords(longName1, longName2);
    word = removeShortWords(word, 4).trim();
    return word;
  }

  private static String findCommonWords(String words1, String words2) {
    String[] array1 = words1.split(" ");
    String[] array2 = words2.split(" ");
    int len = Math.max(array1.length, array2.length);

    String common =
        (len == 1) ? findLonguestCommonSequence(words1, words2) : findCommonWords(array1, array2);
    return common;
  }

  private static String findCommonWords(String[] words1, String[] words2) {
    List<String> commonWords = findCommonWords(List.of(words1), List.of(words2));
    return String.join(" ", commonWords);
  }

  private static List<String> findCommonWords(List<String> previous, List<String> words) {
    List<String> commonWords = new ArrayList<>();
    if (previous == null) {
      commonWords.addAll(words);
    } else {
      for (String word : words) {
        if (previous.contains(word)) {
          commonWords.add(word);
        }
      }
    }

    return commonWords;
  }

  public static String findLonguestCommonSequence(String... strings) {
    String commonStr = "";
    String smallStr = "";

    // identify smallest String
    for (String s : strings) {
      if (smallStr.length() < s.length()) {
        smallStr = s;
      }
    }

    String tempCom = "";
    char[] smallStrChars = smallStr.toCharArray();
    for (char c : smallStrChars) {
      tempCom += c;

      for (String s : strings) {
        if (!s.contains(tempCom)) {
          tempCom = "";
          break;
        }
      }

      if (tempCom != "" && tempCom.length() > commonStr.length()) {
        commonStr = tempCom;
      }
    }

    return commonStr;
  }

  public static String removeShortWords(String longest, int minLength) {
    List<String> words = new ArrayList<>();
    longest = longest.replace('’', ' ');
    longest = longest.replace('-', ' ');
    String[] parts = longest.split("\\s+");

    for (String part : parts) {
      if (part.length() >= minLength) {
        words.add(part);
      }
    }

    return String.join(" ", words);
  }
}
