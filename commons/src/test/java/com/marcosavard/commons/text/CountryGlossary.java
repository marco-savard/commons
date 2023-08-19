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

  private Map<Locale, Glossary> countryGlossaries = new HashMap<>();

  public static CountryGlossary getInstance() {
    if (countryGlossary == null) {
      countryGlossary = new CountryGlossary();
    }

    return countryGlossary;
  }

  private CountryGlossary() {}

  public String getAfricaWord(Locale locale) {
    Glossary glossary = loadGlossary(locale);
    return glossary.getAfricaWord();
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
    public Glossary(Locale displayLocale) {
      Locale[] locales = Locale.getAvailableLocales();
      List<String> northCountries = new ArrayList<>();
      List<String> southCountries = new ArrayList<>();

      for (Locale locale : locales) {
        if (north_codes.contains(locale.toString())) {
          String country = locale.getDisplayCountry(displayLocale);
          northCountries.add(country);
          Console.println("{0} {1}", locale, locale.getDisplayCountry(displayLocale));
        } else if (south_codes.contains(locale.toString())) {
          String country = locale.getDisplayCountry(displayLocale);
          southCountries.add(country);
          Console.println("{0} {1}", locale, locale.getDisplayCountry(displayLocale));
        }
      }

      String north = super.findLongestSubstring(northCountries);
      String south = super.findLongestSubstring(southCountries);

      Console.println(north);
      Console.println(south);
    }

    private String findCommonSubstring(List<String> countries) {
      return "";
    }

    public String getAfricaWord() {
      return "";
    }
  }
}
