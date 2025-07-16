package com.marcosavard.common.lang;

import com.marcosavard.common.debug.Console;

import java.text.MessageFormat;
import java.util.Locale;

public class CharacterUtilDemo {

  public static void main(String[] args) {
    demoCharacter();
    demoCharacterUtil();
  }

  private static void demoCharacter() {
    String source = "montréal";
    StringBuilder target = new StringBuilder();

    for (char ch : source.toCharArray()) {
      target.append(Character.toUpperCase(ch));
    }

    Console.println("{0} -> {1}", source, target.toString());
  }

  private static void demoCharacterUtil() {
    char c = 'à';

    Console.println("{0} is ascii : {1}", c, CharacaterUtil.isAscii(c));
    Console.println("{0} is diacritical : {1}", c, CharacaterUtil.isDiacritic(c));
    Console.println("{0} is vowel : {1}", c, CharacaterUtil.isVowel(c));

    demoAccents();
  }

  private static void demoAccents() {
    Console.println(CharacaterUtil.getDiacritics(Locale.forLanguageTag("es")));
    Console.println(CharacaterUtil.getDiacritics(Locale.FRENCH));
  }
}
