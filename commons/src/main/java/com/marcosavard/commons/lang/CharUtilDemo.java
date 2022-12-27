package com.marcosavard.commons.lang;

import com.marcosavard.commons.debug.Console;

import java.util.Locale;

public class CharUtilDemo {

  public static void main(String[] args) {
    char c = 'à';

    Console.println("{0} is ascii : {1}", c, CharUtil.isAscii(c));
    Console.println("{0} is diacritical : {1}", c, CharUtil.isDiacritical(c));
    Console.println("{0} is vowel : {1}", c, CharUtil.isVowel(c));

    demoAccents();
  }

  private static void demoAccents() {
    for (int i = 0; i < 256; i++) {
      char ch = (char) i;

      if (CharUtil.isDiacritical(ch, Locale.FRENCH)) {
        Console.println(ch);
      }
    }
  }
}
