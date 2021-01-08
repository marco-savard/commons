package com.marcosavard.commons.math.type;

import java.text.MessageFormat;

public class RomanNumeralDemo {

  public static void main(String[] args) {
    for (int i = 1; i <= 10; i++) {
      String msg = MessageFormat.format("{0} : {1}", i, RomanNumeral.of(i));
      System.out.println(msg);
    }
  }
}
