package com.marcosavard.commons.geog;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class TimeZoneGlossary extends Glossary {

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
    String[] mexican = CurrencyGlossary.of(locale).getAdjective("MX", locale);
    word = word.replace('-', ' ');
    word = word.replace(',', ' ');
    word = word.replace('(', ' ');
    word = word.replace(')', ' ');
    word = word.replace(getStandardWord(locale), "");
    word = word.replace(getTimeWord(locale), "");
    word = word.replace(mexico, "");
    word = word.replace(mexican[0], "");
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

  public String getAfricaWord(Locale locale) {
    boolean daylight = false;
    TimeZone timezone1 = TimeZone.getTimeZone("Africa/Djibouti");
    TimeZone timezone2 = TimeZone.getTimeZone("Africa/Brazzaville");
    String eastern = timezone1.getDisplayName(daylight, TimeZone.LONG, locale).toLowerCase();
    String western = timezone2.getDisplayName(daylight, TimeZone.LONG, locale).toLowerCase();
    eastern = eastern.replace(getTimeWord(locale), "");
    eastern = eastern.replace(getStandardWord(locale), "");
    western = western.replace(getTimeWord(locale), "");
    western = western.replace(getStandardWord(locale), "");
    eastern = eastern.replace('-', ' ');
    western = western.replace('-', ' ');
    String word = findCommonWords(eastern, western);
    word = removeShortWords(word, 4).trim();
    return word;
  }

  public String getEastWord(Locale locale) {
    if (oneOf(locale, "it", "ro")) {
      return getEastWord(Locale.FRENCH);
    } else if (oneOf(locale, "es")) {
      return "este";
    } else if (oneOf(locale, "pt")) {
      return "leste";
    } else {
      boolean daylight = false;
      TimeZone timezone = TimeZone.getTimeZone("Africa/Djibouti"); // east africa
      String eastern = timezone.getDisplayName(daylight, TimeZone.LONG, locale).toLowerCase();
      eastern = eastern.replace('’', ' ');
      eastern = eastern.replace('-', ' ');
      eastern = eastern.replace(getTimeWord(locale), "");
      eastern = eastern.replace(getStandardWord(locale), "").trim();

      String african = getAfricanWord(locale);
      String africa = Continent.AFRICA.getDisplayName(locale).toLowerCase();
      String east = eastern.replace(african, "").trim();
      east = east.replace(africa, "").trim();
      east = WordUtil.removeShortWords(east, 3).trim();
      return east;
    }
  }

  public String getWestWord(Locale locale) {
    if (oneOf(locale, "it")) {
      return "ovest";
    } else if (oneOf(locale, "es", "pt")) {
      return "oeste";
    } else if (oneOf(locale, "ro")) {
      return "vest";
    } else {
      boolean daylight = false;
      TimeZone timezone = TimeZone.getTimeZone("Africa/Brazzaville"); // west africa
      String westAfrica = timezone.getDisplayName(daylight, TimeZone.LONG, locale).toLowerCase();
      westAfrica = westAfrica.replace('’', ' ');
      westAfrica = westAfrica.replace('-', ' ');
      westAfrica = westAfrica.replace(getTimeWord(locale), "");
      westAfrica = westAfrica.replace(getStandardWord(locale), "").trim();

      String african = getAfricanWord(locale);
      String africa = Continent.AFRICA.getDisplayName(locale).toLowerCase();
      String west = westAfrica.replace(african, "").trim();
      west = west.replace(africa, "").trim();
      west = WordUtil.removeShortWords(west, 3).trim();
      return west;
    }
  }

  public String getEastearnWordOld(Locale locale) {
    boolean daylight = false;
    TimeZone timezone1 = TimeZone.getTimeZone("Australia/Sydney"); // eastern australia
    String word = timezone1.getDisplayName(daylight, TimeZone.LONG, locale).toLowerCase();
    String[] australian = CurrencyGlossary.of(locale).getAdjective("AU", locale);
    australian[0] = australian[0].substring(0, australian[0].length() - 1);
    Locale locale1 = Country.localeOf("AU");
    String australia = locale1.getDisplayCountry(locale).toLowerCase().substring(0, 8);
    word = word.replace(australian[0], " ");
    word = word.replace(australia, " ");
    word = word.replace(getStandardWord(locale), "");
    word = word.replace(getTimeWord(locale), "");
    word = word.replace('’', ' ');
    word = word.replace('-', ' ');
    word = removeShortWords(word, 3).trim();
    return word;
  }

  private String getAfricanWord(Locale locale) {
    CountryGlossary countryGlossary = new CountryGlossary();
    String[] southAfrican = CurrencyGlossary.of(locale).getAdjective("ZA", locale);
    southAfrican[0] = southAfrican[0].replace('-', ' ');
    String south = countryGlossary.getSouthWord(locale);
    String african = super.replaceFirstIgnoreAccents(southAfrican[0], south, "").trim();

    if (oneOf(locale, "es")) {
      String southFr = countryGlossary.getSouthWord(Locale.FRENCH);
      african = super.replaceFirstIgnoreAccents(african, southFr, "").trim();
    } else if (oneOf(locale, "de")) {
      if (african.endsWith("er")) {
        african = african.substring(0, african.length() - 2);
      }
    }

    african = WordUtil.removeShortWords(african, 3).trim();
    return african;
  }

  private String getLongest(List<String> strings) {
    String longest = strings.stream().max(Comparator.comparingInt(String::length)).get();
    return longest;
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
