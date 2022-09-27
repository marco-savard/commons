package com.marcosavard.commons.text.analysis;

import com.marcosavard.commons.util.collection.TreeValueMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class TextAnalysis {
  private Locale locale;

  private Map<Character, Integer> frequencyByLetter = new TreeValueMap<>();
  private Map<Character, Integer> frequencyByDiacritic = new TreeValueMap<>();
  
  private long wordCount, letterCount;

  private List<Character> discriticals = new ArrayList<>();

  public static TextAnalysis of(Locale locale, List<String> words) {
    return new TextAnalysis(locale, words);
  }

  public TextAnalysis(Locale locale, List<String> words) {
    this.locale = locale;
    this.wordCount = words.size();

    for (String word : words) {
      this.letterCount += word.length(); 
      
      for (int i = 0; i < word.length(); i++) {
        char ch = word.charAt(i);
        boolean diacritic = isDiacritical(ch);

        int letterFrequency = frequencyByLetter.containsKey(ch) ? frequencyByLetter.get(ch) : 0;
        frequencyByLetter.put(ch, 1 + letterFrequency);

        if (diacritic) {
          int frequency = 1;

          if (frequencyByDiacritic.containsKey(ch)) {
            frequency = 1 + frequencyByDiacritic.get(ch);
          }

          frequencyByDiacritic.put(ch, frequency);
        }
      }
    }

    for (char diacritic : frequencyByDiacritic.keySet()) {
      if (! discriticals.contains(diacritic)) {
        discriticals.add(diacritic);
      }
    }

    //TODO sort diacritics
  }

  public List<Character> findDiacriticals() {
    return discriticals;
  }

  public String findFrequentLetters() {
    String frequentLetters = "";

    for (char ch : frequencyByLetter.keySet()) {
      frequentLetters += ch;
    }

    return frequentLetters;
  }

  private static boolean isDiacritical(char ch) {
    boolean diacritical = Character.isLetter(ch) && !isAscii(ch);
    return diacritical;
  }

  private static boolean isAscii(char ch) {
    boolean ascii = (ch >= 'A') && (ch <= 'Z');
    ascii = ascii || (ch >= 'a') && (ch <= 'z');
    return ascii;
  }

  private static boolean isVowel(char ch) {
    boolean vowel = "aeiou".indexOf(ch) != -1;
    return vowel;
  }


  public double findWordLength() {
    double wordLength = (letterCount * 10) / wordCount;
    wordLength = wordLength / 10.0;
    return wordLength;
  }

  public double findVowelPercentage() {
    int nbVowels = 0, nbConsonants = 0;

    for (Character ch : frequencyByLetter.keySet()) {
      boolean vowel = isVowel(ch);
      int count = frequencyByLetter.get(ch);
      nbVowels += vowel ? count : 0;
      nbConsonants += vowel ? 0 : count;
    }

    int total = nbVowels + nbConsonants;
    double percent = (nbVowels * 1000) / total;
    percent = percent / 10.0;
    return percent;
  }
}
