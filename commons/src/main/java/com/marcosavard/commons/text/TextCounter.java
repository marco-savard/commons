package com.marcosavard.commons.text;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Adapter from http://www.rosettacode.org/wiki/Word_frequency#Java
 * 
 * @author Marco
 *
 */
public class TextCounter {
  private Pattern pattern;

  public enum Characters {
    ALL, ONLY_LETTERS
  };

  public enum Frequency {
    NUMBER, PERCENT
  };

  public TextCounter() {
    pattern = Pattern.compile("\\p{javaLowerCase}+");
  }

  public TextCounterResult<String> countWords(String content, int limit) {
    TextCounter.Frequency number = TextCounter.Frequency.NUMBER;
    return countWords(content, limit, number);
  }

  public TextCounterResult<String> countWords(String text, int limit,
      TextCounter.Frequency frequency) {
    Matcher matcher = pattern.matcher(text);
    Map<String, Integer> freq = new HashMap<>();
    int total = 0;

    while (matcher.find()) {
      String word = matcher.group();
      Integer current = freq.getOrDefault(word, 0);
      freq.put(word, current + 1);
      total++;
    }

    List<Map.Entry<String, Integer>> entries = freq.entrySet() //
        .stream() //
        .sorted((i1, i2) -> Integer.compare(i2.getValue(), i1.getValue())) //
        .limit(limit) //
        .collect(Collectors.toList()); //

    Map<String, Integer> wordsByFrequency = new LinkedHashMap<>();
    Map<String, Double> wordsByPercent = new LinkedHashMap<>();

    for (Map.Entry<String, Integer> entry : entries) {
      int value = entry.getValue();
      double percent = (value * 100.0) / total;
      wordsByFrequency.put(entry.getKey(), value);
      wordsByPercent.put(entry.getKey(), percent);
    }

    TextCounterResult<String> result =
        new TextCounterResult<>(total, wordsByFrequency, wordsByPercent);
    return result;
  }

  public TextCounterResult<Character> countLetters(String content, int limit) {
    TextCounter.Characters characters = TextCounter.Characters.ONLY_LETTERS;
    return countCharacters(content, limit, characters);
  }

  public TextCounterResult<Character> countCharacters(String content, int limit,
      TextCounter.Characters type) {
    Map<Character, Integer> occurrences = new HashMap<>();

    int length = content.length();
    int total = 0;
    boolean onlyLetters = (type == TextCounter.Characters.ONLY_LETTERS);

    // build occurrence map
    for (int i = 0; i < length; i++) {
      char ch = content.charAt(i);
      boolean countable = !onlyLetters || Character.isLetter(ch);

      if (countable) {
        Integer current = occurrences.getOrDefault(ch, 0);
        occurrences.put(ch, current + 1);
        total++;
      }
    }

    List<Map.Entry<Character, Integer>> entries = occurrences.entrySet() //
        .stream() //
        .sorted((i1, i2) -> Integer.compare(i2.getValue(), i1.getValue())) //
        .limit(limit) //
        .collect(Collectors.toList()); //

    Map<Character, Integer> lettersByFrequency = new LinkedHashMap<>();
    Map<Character, Double> lettersByPercent = new LinkedHashMap<>();

    for (Map.Entry<Character, Integer> entry : entries) {
      int value = entry.getValue();
      double percent = (value * 100.0) / total;
      lettersByFrequency.put(entry.getKey(), value);
      lettersByPercent.put(entry.getKey(), percent);
    }

    TextCounterResult<Character> result =
        new TextCounterResult<>(total, lettersByFrequency, lettersByPercent);
    return result;
  }

  public static class TextCounterResult<T> {
    private final int total;
    private final Map<T, Integer> valuesByOccurrences;
    private final Map<T, Double> valuesByPercent;

    public TextCounterResult(int total, //
        Map<T, Integer> valuesByOccurrences, //
        Map<T, Double> valuesByPercent) {
      this.total = total;
      this.valuesByOccurrences = valuesByOccurrences;
      this.valuesByPercent = valuesByPercent;
    }

    public Map<T, Integer> getValuesByOccurrences() {
      return valuesByOccurrences;
    }

    public Map<T, Double> getValuesByPercent() {
      return valuesByPercent;
    }

    public int getTotal() {
      return total;
    }
  }

}
