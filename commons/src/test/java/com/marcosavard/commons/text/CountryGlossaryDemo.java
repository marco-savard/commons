package com.marcosavard.commons.text;

import java.util.List;
import java.util.Locale;

public class CountryGlossaryDemo {
  private static final List<String> countryCodes =
      List.of("pt" /*"en", "fr", "it", "es", "ro", "pt", "de", "nl", "sv"*/);

  public static void main(String[] args) {
    CountryGlossary countryGlossary = CountryGlossary.getInstance();

    for (String code : countryCodes) {
      Locale locale = Locale.forLanguageTag(code);
      String north = countryGlossary.getNorthWord(locale);
    }
  }
}
