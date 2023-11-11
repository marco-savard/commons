package com.marcosavard.commons.geog;

import com.marcosavard.commons.debug.Console;
import com.marcosavard.commons.ling.Language;

import java.util.Locale;

public class CountryGlossaryDemo {
  private static final Locale[] locales =
      new Locale[] {
        Locale.FRENCH,
        Locale.ENGLISH,
        Locale.GERMAN,
        Locale.ITALIAN,
        Language.SPANISH.toLocale(),
        Language.PORTUGUESE.toLocale(),
        Language.ROMANIAN.toLocale(),
        Language.DUTCH.toLocale(),
        Language.SWEDISH.toLocale()
      };

  public static void main(String[] args) {
    for (Locale display : locales) {
      CountryGlossary countryGlossary = new CountryGlossary();
      String word = countryGlossary.getNorthWord(display);
      Console.println(display.getLanguage() + " : " + word);
    }
  }
}
