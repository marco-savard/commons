package com.marcosavard.commons.geog;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class TimeZoneGlossary {
  public String getStandardWord(Locale locale) {
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

  public String getTimeWord(Locale locale) {
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

  public String getPacificWord(Locale locale) {
    boolean daylight = false;
    TimeZone timezone1 = TimeZone.getTimeZone("Mexico/BajaSur");
    String word = timezone1.getDisplayName(daylight, TimeZone.LONG, locale).toLowerCase();
    Locale locale1 = Country.localeOf("MX");
    String mexico = locale1.getDisplayCountry(locale).toLowerCase();
    String mexican = CurrencyGlossary.of(locale).getAdjective("MX", locale);
    word = word.replace('-', ' ');
    word = word.replace(',', ' ');
    word = word.replace('(', ' ');
    word = word.replace(')', ' ');
    word = word.replace(getStandardWord(locale), "");
    word = word.replace(getTimeWord(locale), "");
    word = word.replace(mexico, "");
    word = word.replace(mexican, "");
    word = word.replace("zone", "");
    word = removeShortWords(word, 5).trim();
    return word;
  }

  public String getAtlanticWord(Locale locale) {
    boolean daylight = false;
    TimeZone timezone1 = TimeZone.getTimeZone("America/Martinique");
    String word = timezone1.getDisplayName(daylight, TimeZone.LONG, locale).toLowerCase();
    word = word.replace(getStandardWord(locale), "");
    word = word.replace(getTimeWord(locale), "");
    word = word.replace("zona", "");
    word = word.replace("nord", "");
    word = word.replace("america", "");
    word = word.replace("amerika", "");
    word = removeShortWords(word, 5).trim();
    return word;
  }

  public String getIndianOceanWord(Locale locale) {
    boolean daylight = false;
    TimeZone timezone1 = TimeZone.getTimeZone("Indian/Chagos");
    String word = timezone1.getDisplayName(daylight, TimeZone.LONG, locale).toLowerCase();
    word = word.replace(getStandardWord(locale), "");
    word = word.replace(getTimeWord(locale), "");
    word = word.replace("britti", "");
    word = word.replace("dell", "");
    word = removeShortWords(word, 4).trim();
    return word;
  }

  public String getCentralWord(Locale locale) {
    boolean daylight = false;
    TimeZone timezone1 = TimeZone.getTimeZone("Australia/Darwin");
    String word = timezone1.getDisplayName(daylight, TimeZone.LONG, locale).toLowerCase();
    Locale locale1 = Country.localeOf("AU");
    String australia = locale1.getDisplayCountry(locale).toLowerCase().substring(0, 8);

    word = word.replace(australia, " ");
    word = word.replace(getStandardWord(locale), "");
    word = word.replace(getTimeWord(locale), "");
    word = word.replace("britti", "");
    word = word.replace("dell", "");
    word = removeShortWords(word, 5).trim();
    return word;
  }

  private String findCommonWords(String words1, String words2) {
    String[] array1 = words1.split(" ");
    String[] array2 = words2.split(" ");
    int len = Math.max(array1.length, array2.length);

    String common =
        (len == 1) ? findLonguestCommonSequence(words1, words2) : findCommonWords(array1, array2);
    return common;
  }

  private String findCommonWords(String[] words1, String[] words2) {
    List<String> commonWords = findCommonWords(List.of(words1), List.of(words2));
    return String.join(" ", commonWords);
  }

  private List<String> findCommonWords(List<String> previous, List<String> words) {
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

  private String removeShortWords(String longest, int minLength) {
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

  public String findLonguestCommonSequence(String... strings) {
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
}
