package com.marcosavard.common.lang;

import com.marcosavard.common.debug.Console;

import java.util.Locale;

public class CharacterUtilDemo {

  public static void main(String[] args) {
    demoCharacter();
    demoCharacterUtil();
  }

  private static void demoCharacter() {
    String s = "montréal";
    Console.println("{0} -> {1}", s, s.toUpperCase());
  }

  private static void demoCharacterUtil() {
    char c = 'à';

    Console.println("{0} is ascii : {1}", c, CharacterUtil.isAscii(c));
    Console.println("{0} is diacritical : {1}", c, CharacterUtil.isDiacritical(c));
    Console.println("{0} is vowel : {1}", c, CharacterUtil.isVowel(c));

    demoAccents();
  }

  private static void demoAccents() {
    Console.println(CharacterUtil.getDiacriticals(Locale.forLanguageTag("es")));
    Console.println(CharacterUtil.getDiacriticals(Locale.FRENCH));
  }
}
