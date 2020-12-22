package com.marcosavard.commons.geog;

import java.util.List;
import java.util.Locale;
import com.marcosavard.commons.debug.Console;

public class CountryDemo {

  public static void main(String[] args) {
    displayCountry("US");
    displayCountry("JP");
    displayAllCountries();
  }

  private static void displayCountry(String countryCode) {
    Locale fr = Locale.FRENCH;
    Country country = Country.of(countryCode);
    System.out.println(country.getDisplayName(fr));
    List<Locale> languages = country.getLanguageLocales();

    for (Locale language : languages) {
      String languageName = language.getDisplayLanguage(fr);
      Console.println("  langue : {0}", languageName);
    }

    System.out.println();
  }


  private static void displayAllCountries() {
    List<Country> countries = Country.getCountries();
    Locale fr = Locale.FRENCH;

    for (Country country : countries) {
      String languageNames = country.getLanguageNames(fr);
      String scriptNames = country.getScriptNames(fr);

      if (!languageNames.isEmpty()) {
        System.out.println(country.getDisplayName(fr));
        System.out.println("  Langues : " + languageNames);
        System.out.println();
      }
    }
  }



}
