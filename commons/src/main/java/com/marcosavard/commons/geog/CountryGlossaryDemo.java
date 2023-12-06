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
      String lang = display.getLanguage();
      CountryGlossary countryGlossary = new CountryGlossary();
      String word = countryGlossary.getNorthWord(display);
      // Console.println(lang + " : " + word);

      String island = countryGlossary.getIslandWord(display);
      String islands = countryGlossary.getIslandsWord(display);
      String words = String.join(" ", island, islands);
      Console.println(lang + " : " + words);
    }
  }
}
