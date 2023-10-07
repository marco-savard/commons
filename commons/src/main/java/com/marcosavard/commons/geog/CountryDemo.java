package com.marcosavard.commons.geog;

import com.marcosavard.commons.debug.Console;
import com.marcosavard.commons.text.DisplayText;

import java.text.MessageFormat;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

public class CountryDemo {

  public static void main(String[] args) {
    Locale french = Locale.FRENCH;
    displayCountriesByContinent(french);
    // displayCountryInfo(Country.of("US"), french);
    // displayAllCountries(french);

    List<String> countries = Continent.EUROPE.getCountries();
    for (String country : countries) {
      // displayCountryInfo(Country.of(country), french);
    }
  }

  private static void displayCountriesByContinent(Locale display) {
    String[] countries = Locale.getISOCountries();

    for (String country : countries) {
      Locale locale = Country.localeOf(country);
      if (locale != null) {
        Continent continent = Continent.ofCountry(country);
        Console.println("{0} {1} : {2}", country, locale.getDisplayCountry(display), continent);
      }
    }
  }

  private static void displayAllCountries(Locale displayLocale) {
    String[] isoCountries = Locale.getISOCountries();
    List<Country> countries = Country.getCountries(isoCountries);

    for (Country country : countries) {
      displayCountryInfo(country, displayLocale);
    }
  }

  private static void displayCountryInfo(Country country, Locale displayLocale) {
    String countryName = country.getDisplayName(displayLocale);
    Continent continent = Continent.ofCountry(country.toString());

    String languageNames = country.getLanguageNames(displayLocale);
    Currency currency = country.getCurrency();
    String currencyName =
        (currency == null)
            ? "Inconnu"
            : MessageFormat.format(
                "{0} ({1})", currency.getDisplayName(displayLocale), currency.getCurrencyCode());

    Console.println(countryName);
    Console.println("  Continent : " + continent.getDisplayName(displayLocale));
    Console.println("  Monnaie : " + currencyName);
    Console.println("  Langues : " + languageNames);
    displayAlternateNames(country);
    Console.println();
  }

  private static void displayAlternateNames(Country country) {
    String[] nativeNames = country.getDisplayNames(country.getLanguageLocales());
    Console.println("  Autres noms : " + DisplayText.of(nativeNames));
  }
}
