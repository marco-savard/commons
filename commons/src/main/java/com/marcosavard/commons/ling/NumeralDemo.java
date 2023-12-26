package com.marcosavard.commons.ling;

import com.marcosavard.commons.debug.Console;

public class NumeralDemo {

  public static void main(String[] args) {
    Numeral numeral = new Numeral();

    for (int i = 1; i <= 1_000_000; i++) {
      Console.println("{0} : {1}", Integer.toString(i), numeral.of(i));
    }
  }
}
