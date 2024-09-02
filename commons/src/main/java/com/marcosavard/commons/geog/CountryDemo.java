package com.marcosavard.commons.geog;

import com.marcosavard.commons.debug.Console;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

public class CountryDemo {

  public static void main(String[] args) {
    Locale display = Locale.FRENCH;
    printCountryNames(display);
    //  printCountryNamesWithArticle(display);
    //  printCountryCompactInfos(display);
    // printCountryDetailedInfos(display);
  }

  private static void printCountryNames(Locale display) {
    for (Country country : Country.values()) {
      String countryName = country.getDisplayName(display);
      Console.println("{0} : {1}", country.getCode(), countryName);
    }
  }

  private static void printCountryNamesWithArticle(Locale display) {
    printCountryNames(display, Country.Style.WITH_ARTICLE);
  }

  private static void printCountryNames(Locale display, Country.Style style) {
    for (Country country : Country.values()) {
      String countryName = country.getDisplayName(display, style);
      Console.println("{0} : {1}", country.getCode(), countryName);
    }
  }

  private static void printCountryCompactInfos(Locale display) {
    for (Country country : Country.values()) {
      String code = country.getCode();
      String name = country.getDisplayName(display);
      char number = country.getCountryName(display).getGrammaticalNumber();

      String languages = String.join(", ", country.getLanguages());
      String scripts = String.join(", ", country.getScriptNames());

      Currency currency = country.getCurrency();
      Continent continent = Continent.ofCountry(code);
      Console.println(
          "{0} {1} {2} ({3}): {4} {5} {6}",
          code, name, number, continent, currency, languages, scripts);
    }
  }

  private static void printCountryDetailedInfos(Locale display) {
    Map<String, Country> countriesByName = new TreeMap<>();

    for (Country country : Country.values()) {
      String name = country.getDisplayName(display);
      countriesByName.put(name, country);
    }

    for (String name : countriesByName.keySet()) {
      Country country = countriesByName.get(name);
      Console.println(name);
      printContinent(country, display);
      printCountryCurrency(country, display);
      printInhabitant(country, display);
      printDomain(country, display);
      printCountryLanguages(country, display);
      printCountryNames(country, display);
      Console.println();
    }
  }

  private static void printInhabitant(Country country, Locale display) {
    List<Locale> locales = country.getLocales();
    Locale locale = locales.isEmpty() ? null : country.getLocales().get(0);
    CurrencyGlossary currencyGlossary = (locale == null) ? null : CurrencyGlossary.of(locale);
    String code = country.getCode();
    String[] empty = new String[] {"?"};
    String[] adjective =
        (currencyGlossary == null) ? empty : currencyGlossary.getAdjective(code, display);
    Console.println("  Habitant : {0}", adjective[0]);
  }

  private static void printDomain(Country country, Locale display) {
    String code = country.getCode();
    Console.println("  Domaine internet : {0}", code);
  }

  private static void printContinent(Country country, Locale display) {
    Continent continent = Continent.ofCountry(country.getCode());
    String displayName = (continent == null) ? "?" : continent.getDisplayName(display);
    Console.println("  Continent : {0}", displayName);
  }

  private static void printCountryCurrency(Country country, Locale display) {
    Currency currency = country.getCurrency();
    String currencyName = currency.getDisplayName(display);
    String currencyCode = currency.getCurrencyCode();
    Console.println("  Devise : {0} ({1})", currencyName, currencyCode);
  }

  private static void printCountryLanguages(Country country, Locale display) {
    List<Locale> locales = country.getLanguageLocales();
    List<String> languageNames = new ArrayList<>();

    for (Locale locale : locales) {
      String languageName = locale.getDisplayLanguage(display);
      languageNames.add(languageName);
    }

    String joined = String.join(", ", languageNames);
    Console.println("  Langues : {0}", joined);
  }

  private static void printCountryNames(Country country, Locale display) {
    List<Locale> locales = country.getLanguageLocales();
    List<String> countryNames = new ArrayList<>();

    for (Locale locale : locales) {
      String countryName = country.getDisplayName(locale);
      String language = locale.getDisplayLanguage(display);
      String entry = MessageFormat.format("{0} ({1})", countryName, language);
      countryNames.add(entry);
    }

    String joined = String.join(", ", countryNames);
    Console.println("  Autres noms : {0}", joined);
  }
}
