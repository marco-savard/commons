package com.marcosavard.commons.text;

import com.marcosavard.commons.debug.Console;

import java.util.List;
import java.util.Locale;

public class CountryGlossaryDemo {
  private static final List<String> countryCodes =
      List.of("fr", "en", "it", "es", "ro", "pt", "de", "nl", "sv");

  public static void main(String[] args) {
    CountryGlossary glossary = CountryGlossary.getInstance();

    for (String code : countryCodes) {
      Locale locale = Locale.forLanguageTag(code);
      String africa = glossary.getAfricaWord(locale);
      Console.println(africa);
    }
  }
}
