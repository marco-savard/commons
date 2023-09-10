package com.marcosavard.commons.geog;

import com.marcosavard.commons.debug.Console;
import com.marcosavard.commons.ling.Language;

import java.util.Comparator;
import java.util.Currency;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

public class CurrencyZoneDemo {

  public static void main(String[] args) {
    List<Currency> currencies =
        Currency.getAvailableCurrencies().stream()
            .sorted(Comparator.comparing(Currency::getCurrencyCode))
            .collect(Collectors.toList());

    Map<Currency, List<Locale>> currencyZones = CurrencyZone.getZones();
    Locale locale = Language.DUTCH.toLocale();
    Console.println(locale.getLanguage());

    for (Currency currency : currencies) {
      String displayName = currency.getDisplayName(locale);
      Console.println(currency + " (" + displayName + ") : " + currencyZones.get(currency));
    }
  }
}
