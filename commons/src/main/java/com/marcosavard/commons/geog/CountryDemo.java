package com.marcosavard.commons.geog;

import java.util.List;
import java.util.Locale;

public class CountryDemo {

  public static void main(String[] args) {
    // displayCountry("US");
    // displayCountry("JP");
    // displayAllCountries();
    displayAllCountries2();
  }

  private static void displayAllCountries2() {
    Locale.getISOCountries();

    Locale[] allLocales = Locale.getAvailableLocales();
    for (Locale locale : allLocales) {
      System.out.println(locale + " : " + locale.getDisplayCountry());
    }

  }

  private static void displayCountry(String countryCode) {
    Locale fr = Locale.FRENCH;
    Country country = Country.of(countryCode);
    displayCountryInfo(country, fr);
  }


  private static void displayAllCountries() {
    List<Country> countries = Country.getCountries();
    Locale fr = Locale.FRENCH;

    for (Country country : countries) {
      displayCountryInfo(country, fr);
    }
  }

  private static void displayCountryInfo(Country country, Locale locale) {
    String countryName = country.getDisplayName(locale);
    String languageNames = country.getLanguageNames(locale);

    System.out.println(countryName);
    System.out.println("  Langues : " + languageNames);
    System.out.println();
  }



}
