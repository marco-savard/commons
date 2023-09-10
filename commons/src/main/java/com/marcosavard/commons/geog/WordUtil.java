package com.marcosavard.commons.geog;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WordUtil {

  public static String findLonguestCommonWord(String str1, String str2) {
    String[] words1 = str1.split(" ");
    String[] words2 = str2.split(" ");
    String common;
    boolean commonWords = (words1.length == words2.length) && (words1.length > 1);

    if (commonWords) {
      common = findCommonWords(words1, words2);
    } else {
      common = findLonguestCommonSequence(str1, str2);
    }

    return common;
  }

  public static Set<String> findCommonSubstrings(String s, String t) {
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

  public static String findCommonWords(String[] words1, String[] words2) {
    List<String> commonWords = new ArrayList<>();

    for (int i = 0; i < words1.length; i++) {
      if (words1[i].equals(words2[i])) {
        commonWords.add(words1[i]);
      }
    }

    return String.join(" ", commonWords);
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

  public static String removeSubstring(String original, String substring) {
    String remaining = original.replaceFirst(substring, "");
    return remaining;
  }

  public static String findLongestWord(List<String> words) {
    return words.stream().max(Comparator.comparingInt(String::length)).orElse("");
  }
}
