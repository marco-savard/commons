package com.marcosavard.commons.text;

import com.marcosavard.commons.debug.Console;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class CountryGlossary {
  private static CountryGlossary countryGlossary = null;

  private static final List<String> north_codes = List.of("ko_KP", "mk_MK");
  private static final List<String> south_codes = List.of("ko_KR", "en_SS" /*, "en_ZA"*/);

  private static final List<String> korea_codes = List.of("ko_KP", "ko_KR");

  private Map<Locale, Glossary> countryGlossaries = new HashMap<>();

  public static CountryGlossary getInstance() {
    if (countryGlossary == null) {
      countryGlossary = new CountryGlossary();
    }

    return countryGlossary;
  }

  private CountryGlossary() {}

  public String getCommonWord(Locale locale) {
    Glossary glossary = loadGlossary(locale);
    return glossary.getCommonWord();
  }

  public String getNorthWord(Locale locale) {
    Glossary glossary = loadGlossary(locale);
    return glossary.getNorthWord();
  }

  public String getSouthWord(Locale locale) {
    Glossary glossary = loadGlossary(locale);
    return glossary.getSouthWord();
  }

  private Glossary loadGlossary(Locale locale) {
    Glossary glossary = countryGlossaries.get(locale);

    if (glossary == null) {
      glossary = new Glossary(locale);
      countryGlossaries.put(locale, glossary);
    }

    return glossary;
  }

  private static class Glossary extends AbstractGlossary {
    private String common, north, south;

    public Glossary(Locale displayLocale) {
      Locale[] locales = Locale.getAvailableLocales();
      List<String> koreaCountries = new ArrayList<>();

      WordFinder commonWordFinder = new WordFinder(north_codes, south_codes);
      WordFinder northWordFinder = new WordFinder(north_codes);
      WordFinder southWordFinder = new WordFinder(south_codes);

      for (Locale locale : locales) {
        String country = locale.getDisplayCountry(displayLocale);
        Console.println(locale + " : " + country);

        if (koreaCountries.contains(locale.toString())) {
          koreaCountries.add(country);
          Console.println(country);
        }

        commonWordFinder.examine(locale.toString(), country);
        northWordFinder.examine(locale.toString(), country);
        southWordFinder.examine(locale.toString(), country);
      }

      common = commonWordFinder.findLongestCommonWord();
      northWordFinder.addWordToIgnore(common);
      southWordFinder.addWordToIgnore(common);
      north = northWordFinder.findLongestCommonWord();
      south = southWordFinder.findLongestCommonWord();

      Console.println(displayLocale.getDisplayLanguage());
      Console.println(north);
      Console.println(south);
    }

    public String getCommonWord() {
      return common;
    }

    public String getNorthWord() {
      return north;
    }

    public String getSouthWord() {
      return south;
    }
  }
}
