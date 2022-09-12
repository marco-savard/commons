package com.marcosavard.commons.geog;

import com.marcosavard.commons.lang.Text;

import java.text.MessageFormat;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

public class CountryDemo {

  public static void main(String[] args) {
    displayCountry(Country.of("US"));
    displayAllCountries();
  }

  private static void displayAllCountries() {
    String[] isoCountries = Locale.getISOCountries();
    List<Country> countries = Country.getCountries(isoCountries);

    for (Country country : countries) {
      displayCountry(country);
    }
  }

  private static void displayCountry(Country country) {
    Locale fr = Locale.FRENCH;
    displayCountryInfo(country, fr);
  }

  private static void displayCountryInfo(Country country, Locale displayLocale) {
    String countryName = country.getDisplayName(displayLocale);

    String languageNames = country.getLanguageNames(displayLocale);
    Currency currency = country.getCurrency();
    String currencyName = (currency == null) ? "Inconnu" : MessageFormat.format("{0} ({1})", currency.getDisplayName(displayLocale), currency.getCurrencyCode());

    System.out.println(countryName);
    System.out.println("  Monnaie : " + currencyName);
    System.out.println("  Langues : " + languageNames);
    displayAlternateNames(country);
    System.out.println();
  }

  private static void displayAlternateNames(Country country) {
    String[] nativeNames = country.getDisplayNames(country.getLanguageLocales());
    System.out.println("  Autres noms : " + Text.toString(nativeNames));
  }


}
