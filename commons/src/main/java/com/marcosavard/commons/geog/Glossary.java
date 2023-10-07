package com.marcosavard.commons.geog;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public abstract class Glossary {

  protected List<String> findCommon(String first, String second) {
    List<String> list1 = List.of(first.split("\\s+"));
    List<String> list2 = List.of(second.split("\\s+"));
    List<String> common = findCommon(list1, list2);
    return common;
  }

  private List<String> findCommon(List<String> first, List<String> second) {
    List<String> common;

    if (first.size() == 1) {
      String word = first.get(0);
      word = (second == null) ? word : findLonguestCommonSequence(second.get(0), word);
      common = List.of(word);
    } else {
      common = findCommonWords(first, second);
    }

    return common;
  }

  private static String findLonguestCommonSequence(String s1, String s2) {
    Set<String> substrings = WordUtil.findCommonSubstrings(s1, s2);
    String longest = substrings.stream().max(Comparator.comparingInt(String::length)).orElse("");
    return longest;
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

  protected String toSingular(String word, Locale locale) {
    String singular;

    if (locale.getLanguage().equals("fr")) {
      singular = toSingularFrench(word);
    } else if (locale.getLanguage().equals("es")) {
      singular = toSingularSpanish(word);
    } else if (locale.getLanguage().equals("pt")) {
      singular = toSingularPortuguese(word);
    } else {
      singular = word;
    }

    return singular;
  }

  protected String toMasculine(String word, Locale locale) {
    String masculine = word;

    if (locale.getLanguage().equals("es")) {
      masculine = toMasculineSpanish(word);
    } else if (locale.getLanguage().equals("pt")) {
      masculine = toMasculinePortuguese(word);
    }

    return masculine;
  }

  private String toMasculineSpanish(String word) {
    String singular;

    if (word.endsWith("a")) {
      singular = word.substring(0, word.length() - 1) + "o";
    } else {
      singular = word;
    }

    return singular;
  }

  private String toMasculinePortuguese(String word) {
    String singular;

    if (word.endsWith("a")) {
      singular = word.substring(0, word.length() - 1) + "o";
    } else {
      singular = word;
    }

    return singular;
  }

  private String toSingularFrench(String word) {
    String singular;

    if (word.endsWith("s")) {
      singular = word.substring(0, word.length() - 1);
    } else {
      singular = word;
    }

    return singular;
  }

  private String toSingularSpanish(String word) {
    String singular;

    if (word.endsWith("s")) {
      singular = word.substring(0, word.length() - 1);
    } else {
      singular = word;
    }

    return singular;
  }

  private String toSingularPortuguese(String word) {
    String singular;

    if (word.endsWith("s")) {
      singular = word.substring(0, word.length() - 1);
    } else {
      singular = word;
    }

    return singular;
  }
}
