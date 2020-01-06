package com.marcosavard.commons.text;

import java.io.PrintStream;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class MapPrinter<T> {
  private String rankWord = "#";
  private String key = "ABC";
  private String frequency = "n";

  public void printOccurrences(Map<T, Integer> wordsByFrequency) {
    printOccurrences(wordsByFrequency, rankWord, key, frequency);
  }

  public void printOccurrences(Map<T, Integer> wordsByFrequency, String rankWord, String keyWord,
      String frequencyWord) {
    printOccurrences(wordsByFrequency, rankWord, keyWord, frequencyWord, System.out);
  }

  public void printPercents(Map<T, Double> wordsByFrequency, String rankWord, String keyWord,
      String frequencyWord) {
    printPercents(wordsByFrequency, rankWord, keyWord, frequencyWord, System.out);
  }

  public void printOccurrences(Map<T, Integer> map, String rankWord, String keyWord,
      String frequencyWord, PrintStream output) {
    // compute columns length
    Set<T> keys = map.keySet();
    int rankLen = (int) Math.floor(1 + Math.log10(map.keySet().size()));
    int freqLen = (int) Math.floor(1 + Math.log10(findGreatestInteger(map.values())));
    int len1 = Math.max(rankWord.length(), rankLen);
    int len2 = Math.max(keyWord.length(), findLongestString(keys).length());
    int len3 = (int) Math.max(frequencyWord.length(), freqLen);

    // print header
    String header1 = justify(rankWord, len1);
    String header2 = justify(keyWord, len2);
    String header3 = justify(frequencyWord, len3);
    String msg = MessageFormat.format("{0}  {1}  {2}", header1, header2, header3);
    output.println(msg);

    String line1 = buildString('=', len1);
    String line2 = buildString('=', len2);
    String line3 = buildString('=', len3);
    msg = MessageFormat.format("{0}  {1}  {2}", line1, line2, line3);
    output.println(msg);

    // print values
    int rank = 1;
    for (T value : keys) {
      Integer count = map.get(value);
      String fmt = MessageFormat.format("%{0}d  %-{1}s  %{2}d", len1, len2, len3);
      msg = String.format(fmt, rank++, value.toString(), count);
      output.println(msg);
    }

    output.println();
  }

  public void printPercents(Map<T, Double> map, String rankWord, String keyWord,
      String frequencyWord, PrintStream output) {
    // compute columns length
    Set<T> keys = map.keySet();
    int rankLen = (int) Math.floor(1 + Math.log10(map.keySet().size()));
    int freqLen = (int) Math.floor(1 + Math.log10(findGreatestDouble(map.values())));
    int len1 = Math.max(rankWord.length(), rankLen);
    int len2 = Math.max(keyWord.length(), findLongestString(keys).length());
    int len3 = (int) Math.max(frequencyWord.length(), freqLen);

    // print header
    String header1 = justify(rankWord, len1);
    String header2 = justify(keyWord, len2);
    String header3 = justify(frequencyWord, len3);
    String msg = MessageFormat.format("{0}  {1}  {2}", header1, header2, header3);
    output.println(msg);

    String line1 = buildString('=', len1);
    String line2 = buildString('=', len2);
    String line3 = buildString('=', len3);
    msg = MessageFormat.format("{0}  {1}  {2}", line1, line2, line3);
    output.println(msg);

    // print values
    int rank = 1;
    for (T value : keys) {
      Double count = map.get(value);
      String fmt = MessageFormat.format("%{0}d  %-{1}s  %{2}.2f", len1, len2, len3);
      msg = String.format(fmt, rank++, value.toString(), count);
      output.println(msg);
    }

    output.println();
  }

  private String justify(String text, int len) {
    int before = (len - text.length()) / 2;
    int after = (len - text.length()) - before;
    String justified = buildString(' ', before) + text + buildString(' ', after);
    return justified;
  }

  private String findLongestString(Set<T> keys) {
    String longestString = "";

    for (T s : keys) {
      if (s.toString().length() > longestString.length()) {
        longestString = s.toString();
      }
    }

    return longestString;
  }

  private int findGreatestInteger(Collection<Integer> values) {
    int greatest = Integer.MIN_VALUE;

    for (Integer v : values) {
      if (v > greatest) {
        greatest = v;
      }
    }
    return greatest;
  }

  private double findGreatestDouble(Collection<Double> values) {
    double greatest = -Double.MAX_VALUE;

    for (Double v : values) {
      if (v > greatest) {
        greatest = v;
      }
    }
    return greatest;
  }

  private String buildString(char fill, int length) {
    String line = new String(new char[length]).replace('\0', fill);
    return line;
  }



}
