package com.marcosavard.commons.text;

import com.marcosavard.commons.debug.Console;

import java.util.Currency;
import java.util.Locale;
import java.util.Set;

public class CurrencyDemo {
  public static void main(String[] args) {
    String code = "pt";
    Locale locale = Locale.forLanguageTag(code);
    Set<Currency> currencies = Currency.getAvailableCurrencies();

    for (Currency currency : currencies) {
      String name = currency.getDisplayName(locale);
      Console.println(name);
    }
  }
}
