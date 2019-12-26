package com.marcosavard.commons.text;

import java.util.ArrayList;
import java.util.List;

public class Unicode {
  public enum Accent {
    GRAVE, ACUTE, CIRCUMFLEX, TILDE, UML, ANGSTROM, CEDILLA
  }

  //@formatter:off
  private static final char[] LETTERS_WITH_ACCENTS = new char[] {'a', 'c', 'e', 'i', 'n', 'o', 'u', 'y'};
  private static final List<Character> LETTER_WITH_ACCENT_LIST = new ArrayList<>();
  private static final int[] START_INDEX = new int[] {0, 7, 8, 12, 17, 18, 25, 29};
  
  private static final int[][] ACCENTS_PER_VOWEL = new int[][] {
    new int[] {0, 1, 2, 3, 4, 5, -1},   //for letter A
    new int[] {-1, -1, -1, -1, -1, -1, 0},   //for letter C
    new int[] {0, 1, 2, -1, 3, -1, -1}, //for letter E
    new int[] {0, 1, 2, -1, 3, -1, -1}, //for letter I
    new int[] {-1, -1, -1, 0, -1, -1, -1},   //for letter N
    new int[] {0, 1, 2, 3, 4, -1, -1}, //for letter O
    new int[] {0, 1, 2, -1, 3, -1, -1}, //for letter U
    new int[] {-1, 0, -1, -1, 2, -1, -1}, //for letter Y
  };
  //@formatter:on

  static {
    for (char c : LETTERS_WITH_ACCENTS) {
      LETTER_WITH_ACCENT_LIST.add(c);
    }
  }

  public static char[] getLettersWithAccent() {
    return LETTERS_WITH_ACCENTS;
  }

  public static int codeOf(char c, Accent accent) {
    boolean isLower = Character.isLowerCase(c);
    int lowerIdx = isLower ? 32 : 0;
    int vowelIdx = LETTER_WITH_ACCENT_LIST.indexOf(Character.toLowerCase(c));
    int letterIdx = START_INDEX[vowelIdx];
    int accentIdx = ACCENTS_PER_VOWEL[vowelIdx][accent.ordinal()];
    int unicode = (accentIdx == -1) ? (int) '?' : 0x00c0 + lowerIdx + letterIdx + accentIdx;
    return unicode;
  }

  public static char charOf(char c, Accent accent) {
    return (char) codeOf(c, accent);
  }

  //
  // private
  //



}
