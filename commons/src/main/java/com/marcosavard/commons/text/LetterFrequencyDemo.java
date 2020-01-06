package com.marcosavard.commons.text;

import java.util.Locale;
import java.util.Map;

public class LetterFrequencyDemo {

  public static void main(String[] args) {
    LetterFrequency letterFrequency = new LetterFrequency();
    Map<Character, Double> frequency = letterFrequency.getFrequencyOfLocale(Locale.ENGLISH);

    String s = frequency.toString();
    System.out.println("Letter frequency: " + s);
  }

}
