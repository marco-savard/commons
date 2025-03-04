package com.marcosavard.commons.time.calendar;

public class RomanNumeral  {
  private static final int[] NUMBERS = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
  private static final String[] LETTERS = {
    "M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"
  };

  /**
   * Create a roman numeral whose toString() is its textual representation. For instance,
   * System.out.println(romanNumeral.getDisplayName(10)) prints "X".
   *
   * @param number between 1 and 3999
   */
  public static String of(int number) {
    if (number < 1) throw new IllegalArgumentException("must be positive : " + number);
    if (number > 3999) throw new IllegalArgumentException("must be 3999 or less : " + number);
    String displayName = "";

    for (int i = 0; i < NUMBERS.length; i++) {
      while (number >= NUMBERS[i]) {
        displayName += LETTERS[i];
        number -= NUMBERS[i];
      }
    }

    return displayName;
  }
}
