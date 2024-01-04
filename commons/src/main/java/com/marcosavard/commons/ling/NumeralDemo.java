package com.marcosavard.commons.ling;

import com.marcosavard.commons.debug.Console;

import java.util.Locale;

public class NumeralDemo {

  public static void main(String[] args) {
    Locale display = Locale.ENGLISH;
    Numeral numeral = Numeral.of(display);

    for (int i = 1; i <= 10_000; i++) {
      Console.println("{0} : {1}", Integer.toString(i), numeral.getDisplayName(i));
    }
  }
}
