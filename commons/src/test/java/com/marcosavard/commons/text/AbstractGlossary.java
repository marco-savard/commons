package com.marcosavard.commons.text;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class AbstractGlossary {
  private static final String EMPTY = "";
  private static final int INDEX_NOT_FOUND = -1;

  private List<String> wordsToIgnore = new ArrayList<>();

  protected String findLongestWord(List<String> locDisplayNames) {
    String substring = findLongestSubstring(locDisplayNames);
    List<String> words = List.of(substring.split("\\s+"));
    String longest = words.stream().max(Comparator.comparingInt(String::length)).get();
    return longest;
  }

  protected String findLongestSubstring(List<String> locDisplayNames) {
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

    String longest = commons.stream().max(Comparator.comparingInt(String::length)).orElse("");
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

  public String difference(String str1, String str2) {
    if (str1 == null) {
      return str2;
    }
    if (str2 == null) {
      return str1;
    }
    int at = indexOfDifference(str1, str2);
    if (at == INDEX_NOT_FOUND) {
      return EMPTY;
    }
    return str2.substring(at);
  }

  public static int indexOfDifference(CharSequence cs1, CharSequence cs2) {
    if (cs1 == cs2) {
      return INDEX_NOT_FOUND;
    }
    if (cs1 == null || cs2 == null) {
      return 0;
    }
    int i;
    for (i = 0; i < cs1.length() && i < cs2.length(); ++i) {
      if (cs1.charAt(i) != cs2.charAt(i)) {
        break;
      }
    }
    if (i < cs2.length() || i < cs1.length()) {
      return i;
    }
    return INDEX_NOT_FOUND;
  }
}
