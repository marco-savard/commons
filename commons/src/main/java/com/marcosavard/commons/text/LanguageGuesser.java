package com.marcosavard.commons.text;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class LanguageGuesser {
  private LetterFrequency letterFrequency;

  public LanguageGuesser() {
    letterFrequency = new LetterFrequency();
  }

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

}
