package com.marcosavard.commons.ling;

import java.util.Arrays;

public class Hyphenator {
  private final static String VOWELS = "aeiouy";
  private final static String[] UNBREAKABLE_SEQUENCES = new String[] {//
      "ch", "gh", "ph", "qu", "rh", "sh", "th"};

  public String hyphenate(String word) {
    String hyphenated = hyphenBeforeConsonant(word);
    hyphenated = mergeSyllableWithoutVowels(hyphenated);
    hyphenated = mergeShortSyllables(hyphenated);
    hyphenated = balanceConsonants(hyphenated);

    return hyphenated;
  }

  private String hyphenBeforeConsonant(String word) {
    StringBuilder hyphenated = new StringBuilder();
    int nb = word.length();
    char previous = '?';

    for (int i = 0; i < nb; i++) {
      char current = word.charAt(i);

      // if previous is vowel and current is consonant, add hyphen
      boolean previousVoyel = isVowel(previous);
      boolean currentConsonant = isConsonant(current);

      if (previousVoyel && currentConsonant) {
        hyphenated.append('-');
      }

      // add current
      hyphenated.append(current);

      previous = current;
    }

    return hyphenated.toString();
  }

  private String mergeSyllableWithoutVowels(String original) {
    StringBuilder hyphenated = new StringBuilder();
    String[] syllables = original.split("-");
    boolean first = true;

    for (int i = 0; i < syllables.length; i++) {
      String syllable = syllables[i];
      boolean hasNoVowels = hasNoVowels(syllable);

      if ((!first) && (!hasNoVowels)) {
        hyphenated.append("-");
      }

      hyphenated.append(syllable);
      first = false;
    }

    return hyphenated.toString();
  }

  private String mergeShortSyllables(String original) {
    StringBuilder hyphenated = new StringBuilder();
    String[] syllables = original.split("-");
    boolean first = true;
    boolean shortSyllable = false;

    for (int i = 0; i < syllables.length; i++) {
      String syllable = syllables[i];
      boolean last = (i == syllables.length - 1);

      if (last && (syllable.length() <= 2) && (syllable.endsWith("e"))) {
        shortSyllable = true;
      }

      if ((!first) && (!shortSyllable)) {
        hyphenated.append("-");
      }

      hyphenated.append(syllable);
      first = false;
      shortSyllable = syllable.length() == 1;
    }

    return hyphenated.toString();
  }

  private String balanceConsonants(String original) {
    StringBuilder hyphenated = new StringBuilder();
    String[] syllables = original.split("-");
    boolean first = true;

    for (int i = 0; i < syllables.length; i++) {
      String syllable = syllables[i];
      int idx = findSplittingIndex(syllable);

      if (idx > 0) {
        String start = syllable.substring(0, idx);
        hyphenated.append(start);
      }

      if (!first) {
        hyphenated.append("-");
      }

      String end = syllable.substring(idx);
      hyphenated.append(end);
      first = false;

    }

    return hyphenated.toString();
  }

  private int findSplittingIndex(String syllable) {
    int len = syllable.length();
    int idx = 0;

    if (len >= 3) {
      String sequence = syllable.substring(0, 2);
      boolean breakable = isBreakable(sequence);
      boolean vowel;

      if (breakable) {
        vowel = isVowel(syllable.charAt(1));
        idx = vowel ? 0 : 1;
      } else {
        vowel = isVowel(syllable.charAt(2));
        idx = vowel ? 0 : 2;
      }


    }

    return idx;
  }

  private boolean isBreakable(String sequence) {
    boolean breakable = !Arrays.asList(UNBREAKABLE_SEQUENCES).contains(sequence);
    return breakable;
  }

  private boolean hasNoVowels(String syllable) {
    boolean hasVowels = false;

    for (int i = 0; i < syllable.length(); i++) {
      char ch = syllable.charAt(i);
      if (isVowel(ch)) {
        hasVowels = true;
        break;
      }
    }

    return !hasVowels;
  }

  private boolean isVowel(char ch) {
    boolean vowel = Character.isLetter(ch) && (VOWELS.indexOf(ch) >= 0);
    return vowel;
  }

  private boolean isConsonant(char ch) {
    boolean consonant = Character.isLetter(ch) && (VOWELS.indexOf(ch) == -1);
    return consonant;
  }



}
