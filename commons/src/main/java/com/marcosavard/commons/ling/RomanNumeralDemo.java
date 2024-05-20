package com.marcosavard.commons.ling;

import com.marcosavard.commons.debug.Console;

public class RomanNumeralDemo {

  public static void main(String[] args) {
    Numeral numeral = new com.marcosavard.commons.ling.RomanNumeral();

    for (int i = 1; i <= 20; i++) {
      Console.println("{0} : {1}", i, numeral.getDisplayName(i));
    }
  }
}
