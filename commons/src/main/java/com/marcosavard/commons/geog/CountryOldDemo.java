package com.marcosavard.commons.geog;

import com.marcosavard.commons.debug.Console;
import com.marcosavard.commons.ling.Language;
import com.marcosavard.commons.text.DisplayText;

import java.text.MessageFormat;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

public class CountryOldDemo {

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
    for (Locale locale : locales) {
      displayCountriesByContinent(locale);
    }
    Console.println();

    // displayCountryInfo(Country.of("US"), french);
    // displayAllCountries(french);

    List<String> countries = Continent.EUROPE.getCountries();
    for (String country : countries) {
      // displayCountryInfo(Country.of(country), french);
    }
  }

  private static void displayCountriesByContinent(Locale display) {
    String[] countries = Locale.getISOCountries();
    String lang = display.getLanguage();
    Console.println(lang);

    for (String code : countries) {
      CountryOld country = CountryOld.of(code);
      Locale locale = CountryOld.localeOf(code);

      if (locale != null) {
        String countryName = country.getDisplayNameWithArticle(display, "à");
        Continent continent = Continent.ofCountry(code);
        Console.println("{0} {1} : {2}", country, countryName, continent);
      }
    }

    Console.println();
  }

  private static void displayAllCountries(Locale displayLocale) {
    String[] isoCountries = Locale.getISOCountries();
    List<CountryOld> countries = CountryOld.getCountries(isoCountries);

    for (CountryOld country : countries) {
      displayCountryInfo(country, displayLocale);
    }
  }

  private static void displayCountryInfo(CountryOld country, Locale displayLocale) {
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

  private static void displayAlternateNames(CountryOld country) {
    String[] nativeNames = country.getDisplayNames(country.getLanguageLocales());
    Console.println("  Autres noms : " + DisplayText.of(nativeNames));
  }
}
