package com.marcosavard.commons.text;

import com.marcosavard.commons.debug.Console;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class ContinentFinderDemo {
  public static void main(String[] args) {
    // find latin
    List<String> codes = List.of("it", "es", "ro", "pt", "fr", "en", "de", "nl", "sv", "no");
    // List<String> codes = List.of("it", "es", "ro", "pt", "fr");
    for (String code : codes) {
      findAsia(code);
      // findAmerica(code);
    }
  }

  private static void findAsia(String code) {
    Locale locale = Locale.forLanguageTag(code);

    Locale indonesia =
        Arrays.stream(Locale.getAvailableLocales())
            .filter(l -> "id_ID".equals(l.toString()))
            .findFirst()
            .orElse(null);
    String s1 = indonesia.getDisplayCountry(locale).toLowerCase();
    s1 = s1.replace('z', 's');

    int idx = s1.lastIndexOf('s');
    char c = s1.charAt(idx - 1);
    String s2 = "a" + s1.substring(idx);

    //   String common = WordFinder.findLongestSubstring(s1, s2);
    Console.println(String.join(", ", code, s1, Character.toString(c), s2));
  }

  private static void findAmerica(String code) {
    Locale locale = Locale.forLanguageTag(code);
    Locale latin =
        Arrays.stream(Locale.getAvailableLocales())
            .filter(l -> "bs_BA_#Latn".equals(l.toString()))
            .findFirst()
            .orElse(null);
    String s1 = latin.getDisplayScript(locale).toLowerCase();

    // bs_BA_#Latn
    Locale latinAmerica =
        Arrays.stream(Locale.getAvailableLocales())
            .filter(l -> "es_419".equals(l.toString()))
            .findFirst()
            .orElse(null);
    String s2 = latinAmerica.getDisplayCountry(locale).toLowerCase();
    s2 = s2.replace('-', ' ');

    String ss = WordFinder.findLongestSubstring(s1, s2);

    String america = WordFinder.removeFrom(s2, ss);
    america = findLongestWord(america);

    Console.println(String.join(", ", code, s1, s2, ss, america));
  }

  private static String findLongestWord(String america) {
    String[] parts = america.split("\\s+");
    String longest = Arrays.stream(parts).max(Comparator.comparingInt(String::length)).orElse("");
    return longest;
  }
}
