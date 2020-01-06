package com.marcosavard.commons.text;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

public class LanguageGuesser {
  private LetterFrequency letterFrequency;

  public LanguageGuesser() {
    letterFrequency = new LetterFrequency();
  }

  private static final Double[] ENGLISH_FREQUENCY = new Double[] { //
      8.55, 1.60, 3.16, 3.87, 12.10, 2.18, 2.09, 4.96, 7.33, 0.22, // A-J
      0.81, 4.21, 2.53, 7.17, 07.47, 2.07, 0.10, 6.33, 6.73, 8.94, // K-T
      2.28, 1.06, 1.83, 0.19, 01.72, 0.11 // U-Z
  };
  private static final Double[] FRENCH_FREQUENCY = new Double[] { //
      7.64, 0.90, 3.26, 3.67, 14.72, 1.07, 0.87, 0.74, 7.53, 0.61, // A-J
      0.07, 5.46, 2.97, 7.01, 05.77, 2.52, 1.37, 6.69, 7.95, 7.24, // K-T
      6.31, 1.84, 0.05, 0.43, 00.82, 0.37 // U-Z
  };

  // private static final LanguageLetterFrequency[] LANGAGES = new LanguageLetterFrequency[] { //
  // new LanguageLetterFrequency(Locale.ENGLISH, ENGLISH_FREQUENCY), //
  // new LanguageLetterFrequency(Locale.FRENCH, FRENCH_FREQUENCY), //
  // };

  public Map<String, Double> getCandidateLanguages(Map<Character, Double> sampleFrequency) {
    Map<String, Double> candidates = new LinkedHashMap<>();
    Map<String, Map<Character, Double>> frequencies = letterFrequency.getFrequencyOfAllLanguages();

    for (String languageCode : frequencies.keySet()) {
      Map<Character, Double> languageFrequency = frequencies.get(languageCode);
      double divergence = computeDivergence(sampleFrequency, languageFrequency);
      candidates.put(languageCode, divergence);
    }

    candidates = sortByValue(candidates);
    return candidates;
  }


  private static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
    List<Entry<K, V>> list = new ArrayList<>(map.entrySet());
    list.sort(Entry.comparingByValue());

    Map<K, V> result = new LinkedHashMap<>();
    for (Entry<K, V> entry : list) {
      result.put(entry.getKey(), entry.getValue());
    }

    return result;
  }

  private String guess(Map<Character, Double> sampleFrequency) {
    String guessed = "?";
    double lowestDivergence = Double.MAX_VALUE;
    Map<String, Map<Character, Double>> frequencies = letterFrequency.getFrequencyOfAllLanguages();

    for (String languageCode : frequencies.keySet()) {
      Map<Character, Double> languageFrequency = frequencies.get(languageCode);
      double divergence = computeDivergence(sampleFrequency, languageFrequency);
      if (divergence < lowestDivergence) {
        lowestDivergence = divergence;
        guessed = languageCode;
      }
    }

    return guessed;
  }

  private double computeDivergence(Map<Character, Double> sampleFrequencies,
      Map<Character, Double> languageFrequencies) {
    double divergence = 0.0;

    for (Character c : sampleFrequencies.keySet()) {
      double sampleFrequency = sampleFrequencies.get(c);
      double languageFrequency = languageFrequencies.get(c);
      double delta = Math.abs(languageFrequency - sampleFrequency) / 100.0;
      divergence += delta;
    }

    return divergence;
  }

  private static class LanguageLetterFrequency {
    private Locale language;
    private Double[] letterFrequency;

    public LanguageLetterFrequency(Locale language, Double[] letterFrequency) {
      this.language = language;
      this.letterFrequency = letterFrequency;
    }

    public Double[] getLetterFrequency() {
      return letterFrequency;
    }

  }



}
