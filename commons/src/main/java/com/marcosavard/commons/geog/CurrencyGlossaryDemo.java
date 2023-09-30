package com.marcosavard.commons.geog;

import com.marcosavard.commons.debug.Console;
import com.marcosavard.commons.ling.Language;

import java.util.List;
import java.util.Locale;

public class CurrencyGlossaryDemo {

  public static void main(String[] args) {

    Locale[] locales =
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

    String[] countries = Locale.getISOCountries();
    List<Locale> allLocales = List.of(Locale.getAvailableLocales());

    for (Locale locale : locales) {
      CurrencyGlossary glossary = CurrencyGlossary.of(locale);
      String word = glossary.getNewWord(locale);
      // Console.println(word);
    }

    for (Locale locale : locales) {
      CurrencyGlossary glossary = CurrencyGlossary.of(locale);

      for (String country : countries) {
        Locale countryLocale = findCountryLocale(allLocales, country);

        if (countryLocale != null) {
          String word = glossary.getAdjective(country, locale);
          if (word != null) {
            Console.println(word);
          }
        }
      }

      Console.println();
    }
  }

  private static Locale findCountryLocale(List<Locale> allLocales, String country) {
    Locale countryLocale =
            allLocales.stream()
                    .filter(l -> l.getCountry().equals(country))
                    .findFirst()
                    .orElse(null);
    return countryLocale;
  }
}
