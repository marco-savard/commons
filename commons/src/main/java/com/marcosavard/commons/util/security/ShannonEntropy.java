package com.marcosavard.commons.util.security;

import com.marcosavard.commons.util.collection.FrequencyMap;

import java.util.Map;

// credits : https://rosettacode.org/wiki/Entropy#Java
public class ShannonEntropy {

  public static double getEntropy(String str) {
    FrequencyMap<Character> frequencies = new FrequencyMap<>();
    int n = str.length();

    for (int i = 0; i < n; ++i) {
      char ch = str.charAt(i);
      frequencies.add(ch);
    }

    double e = 0.0;
    for (Map.Entry<Character, Integer> entry : frequencies.entrySet()) {
      double p = (double) entry.getValue() / n;
      e += p * log2(p);
    }

    return -e;
  }

  private static double log2(double a) {
    return Math.log(a) / Math.log(2);
  }
}
