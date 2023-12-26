package com.marcosavard.commons.geog;

import com.marcosavard.commons.lang.StringUtil;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class CountryOld {
  private static Map<String, CountryOld> loadedCountries;
  private String code;
  private Locale countryLocale;
  private Currency currency;

  private List<Locale> languageLocales;

  public static List<CountryOld> getCountries(String[] isoCountries) {
    List<CountryOld> countries = new ArrayList<>();

    for (String isoCountry : isoCountries) {
      countries.add(CountryOld.of(isoCountry));
    }

    return countries;
  }

  public static List<CountryOld> getCountries() {
    Map<String, CountryOld> countryMap = getLoadedCountries();
    List<CountryOld> countries = new ArrayList<>(countryMap.values());
    return countries;
  }

  public static CountryOld of(String code) {
    Map<String, CountryOld> countryMap = getLoadedCountries();
    CountryOld country = countryMap.get(code);
    return country;
  }

  private static Map<String, CountryOld> getLoadedCountries() {
    if (loadedCountries == null) {
      String[] countryCodes = Locale.getISOCountries();
      Locale[] allLocales = Locale.getAvailableLocales();
      loadedCountries = new TreeMap<>();

      for (String code : countryCodes) {
        Locale countryLocale = new Locale("", code);
        List<Locale> languages = findLanguageLocales(allLocales, code);
        Currency currency = findCurrency(countryLocale);
        CountryOld country = new CountryOld(code, countryLocale, languages, currency);
        loadedCountries.put(code, country);
      }
    }

    return loadedCountries;
  }

  private static Currency findCurrency(Locale countryLocale) {
    Currency currency;
    try {
      currency = Currency.getInstance(countryLocale);
    } catch (IllegalArgumentException ex) {
      currency = null;
    }

    return currency;
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

  private CountryOld(
      String code, Locale countryLocale, List<Locale> languageLocales, Currency currency) {
    this.code = code;
    this.countryLocale = countryLocale;
    this.languageLocales = languageLocales;
    this.currency = currency;
  }

  public static List<Locale> localesOf(String country) {
    List<Locale> locales =
        List.of(Locale.getAvailableLocales()).stream()
            .filter(l -> country.equals(l.getCountry()))
            .collect(Collectors.toList());
    return locales;
  }

  public static Locale localeOf(String country) {
    List<Locale> locales = localesOf(country);
    return locales.isEmpty() ? null : locales.get(0);
  }

  @Override
  public String toString() {
    return this.code;
  }

  public Currency getCurrency() {
    return currency;
  }

  public String getDisplayName() {
    return getDisplayName(Locale.getDefault());
  }

  public String getDisplayName(Locale displayLocale) {
    String displayName = countryLocale.getDisplayCountry(displayLocale);
    return displayName;
  }

  public String[] getDisplayNames(List<Locale> locales) {
    List<String> displayNames = new ArrayList<>();

    for (Locale locale : locales) {
      displayNames.add(countryLocale.getDisplayName(locale));
    }

    String[] array = displayNames.toArray(new String[0]);
    return array;
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

      if (!nameList.contains(languageName)) {
        nameList.add(languageName);
      }
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
      // languageLocale.
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

  public String getDisplayNameWithArticle(Locale display) {
    return getDisplayNameWithArticle(display, "");
  }

  public String getDisplayNameWithArticle(Locale display, String preposition) {
    Locale locale = CountryOld.localesOf(code).get(0);
    String countryName = locale.getDisplayCountry(display);
    char gen = findGrammaticalGender(code, countryName);
    char num = findGrammaticalNumber(code, countryName);
    String article = (gen == 'F') ? "la" : (gen == 'M') ? "le" : "";
    article = (num == 'P') ? "les" : article;
    article = article.isEmpty() ? preposition : preposition + " " + article;

    String lower = StringUtil.stripAccents(countryName.toLowerCase());
    char firstLetter = lower.charAt(0);
    char lastLetter = article.length() == 0 ? ' ' : article.charAt(article.length() - 1);
    boolean startVowel = "aeiouy".indexOf(firstLetter) >= 0;
    boolean endVowel = "aeiouy".indexOf(lastLetter) >= 0;
    article = startVowel && endVowel ? article.substring(0, article.length() - 1) + "\'" : article;

    article = article.equals("de le") ? "du" : article;
    article = article.equals("de les") ? "des" : article;
    article = article.equals("à l'") ? "en" : article;
    article = article.equals("à la") ? "en" : article;
    article = article.equals("à le") ? "au" : article;
    article = article.equals("à les") ? "aux" : article;

    lastLetter = article.length() == 0 ? ' ' : article.charAt(article.length() - 1);
    String withArticle = (lastLetter == '\'') ? article + countryName : article + " " + countryName;
    return withArticle.trim();
  }

  private static char findGrammaticalNumber(String code, String countryName) {
    boolean plural = countryName.endsWith("s");
    plural = plural || countryName.toLowerCase().contains("îles");
    plural = List.of("CX", "HN", "LA").contains(code) ? false : plural;
    char number = plural ? 'P' : 'S';
    return number;
  }

  private static char findGrammaticalGender(String code, String countryName) {
    char number = findGrammaticalNumber(code, countryName);
    boolean feminine = (number == 'S') ? countryName.endsWith("e") : countryName.endsWith("es");
    feminine = List.of("BZ", "KH", "MX", "MZ", "ZW").contains(code) ? false : feminine;
    feminine = List.of("KP", "KR", "MK").contains(code) ? true : feminine;
    char gender = feminine ? 'F' : 'M';
    List<String> list;
    list =
        List.of(
            "AD", "AW", "CU", "CW", "DJ", "FJ", "GU", "HT", "JE", //
            "MC", "MT", "OM", "PM", "PR", "RE", "ST", "SX", "WF", "YT");
    gender = list.contains(code) ? 'N' : gender;
    return gender;
  }
}
