package com.marcosavard.commons.math;

/**
 * A class that converts numbers to roman numeral.
 * 
 * @author Marco
 *
 */
public class RomanNumeral {
  public static final RomanNumeral ONE = new RomanNumeral(1);
  public static final RomanNumeral TEN = new RomanNumeral(10);

  private final static int[] NUMBERS = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
  private final static String[] LETTERS =
      {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
  private int number;

  /**
   * Create a roman numeral whose toString() is its textual representation. For instance,
   * System.out.println(new RomanNumeral(10)) prints "X".
   * 
   * @param number between 1 and 3999
   */
  public static RomanNumeral of(int number) {
    if (number < 1)
      throw new IllegalArgumentException("must be positive : " + number);
    if (number > 3999)
      throw new IllegalArgumentException("must be 3999 or less : " + number);

    if (number == 1) {
      return RomanNumeral.ONE;
    } else if (number == 10) {
      return RomanNumeral.TEN;
    } else {
      return new RomanNumeral(number);
    }
  }

  private RomanNumeral(int number) {
    this.number = number;
  }

  @Override
  public String toString() {
    String romanNumeral = "";
    int n = this.number; // n, part of num that still has to be converted
    for (int i = 0; i < NUMBERS.length; i++) {
      while (n >= NUMBERS[i]) {
        romanNumeral += LETTERS[i];
        n -= NUMBERS[i];
      }
    }

    return romanNumeral;
  }
}
