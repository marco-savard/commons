package com.marcosavard.commons.geog;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

public class Country {
  private static Map<String, Country> loadedCountries;
  private String code;
  private Locale countryLocale;
  private List<Locale> languageLocales;

  public static List<Country> getCountries() {
    Map<String, Country> countryMap = getLoadedCountries();
    List<Country> countries = new ArrayList<>(countryMap.values());
    return countries;
  }

  public static Country of(String code) {
    Map<String, Country> countryMap = getLoadedCountries();
    Country country = countryMap.get(code);
    return country;
  }

  private static Map<String, Country> getLoadedCountries() {
    if (loadedCountries == null) {
      String[] countryCodes = Locale.getISOCountries();
      Locale[] allLocales = Locale.getAvailableLocales();
      loadedCountries = new TreeMap<>();

      for (String code : countryCodes) {
        Locale countryLocale = new Locale("", code);
        List<Locale> languages = findLanguageLocales(allLocales, code);
        Country country = new Country(code, countryLocale, languages);
        loadedCountries.put(code, country);
      }
    }

    return loadedCountries;
  }

  private static List<Locale> findLanguageLocales(Locale[] locales, String code) {
    List<Locale> foundLocales = new ArrayList<>();

    for (Locale locale : locales) {
      String countryCode = locale.getCountry();
      if (countryCode.equals(code)) {
        foundLocales.add(locale);
      }
    }

    return foundLocales;
  }

  private Country(String code, Locale countryLocale, List<Locale> languageLocales) {
    this.code = code;
    this.countryLocale = countryLocale;
    this.languageLocales = languageLocales;
  }

  @Override
  public String toString() {
    return this.code;
  }

  public String getDisplayName() {
    return getDisplayName(Locale.getDefault());
  }

  public String getDisplayName(Locale displayLocale) {
    String displayName = countryLocale.getDisplayCountry(displayLocale);
    return displayName;
  }

  public List<Locale> getLanguageLocales() {
    return languageLocales;
  }

  public String getLanguageNames() {
    return getLanguageNames(Locale.getDefault());
  }

  public String getLanguageNames(Locale displayLocale) {
    List<String> nameList = new ArrayList<>();
    for (Locale languageLocale : languageLocales) {
      String languageName = languageLocale.getDisplayLanguage(displayLocale);
      String scriptName = getScriptName(languageLocale, displayLocale);

      if (!scriptName.isEmpty()) {
        languageName += " (" + scriptName + ")";
      }

      nameList.add(languageName);
    }

    String concatenation = String.join(", ", nameList);
    return concatenation;
  }

  public String getScriptNames() {
    return getScriptNames(Locale.getDefault());
  }

  public String getScriptNames(Locale displayLocale) {
    List<String> nameList = new ArrayList<>();
    for (Locale languageLocale : languageLocales) {
      String scriptName = languageLocale.getDisplayScript(displayLocale);
      nameList.add(scriptName);
    }

    String concatenation = String.join(", ", nameList);
    return concatenation;
  }

  public String getScriptName(Locale languageLocale, Locale displayLocale) {
    String scriptName = languageLocale.getDisplayScript(displayLocale);
    if (scriptName.isEmpty()) {
      scriptName = languageLocale.getDisplayVariant(displayLocale);
    }

    return scriptName;
  }



}


