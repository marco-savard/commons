package com.marcosavard.commons.geog;

import com.marcosavard.commons.debug.Console;

import java.util.Currency;
import java.util.Locale;

public class CountryDemo {

  public static void main(String[] args) {
    Locale display = Locale.FRENCH;

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
}
