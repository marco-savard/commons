package com.marcosavard.commons.ling;

import com.marcosavard.commons.debug.Console;

import java.util.Locale;

public class NumeralDemo {

  public static void main(String[] args) {
    Locale display = Locale.FRENCH;
    Numeral numeral = new RomanNumeral();

    for (int i = 1; i <= 100; i++) {
      String num = numeral.getDisplayName(i, Numeral.Category.ORDINAL);
      Console.println("{0} : {1}", Integer.toString(i), num);
    }
  }
}
