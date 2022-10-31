package com.marcosavard.commons.money;

import com.marcosavard.commons.debug.Console;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Currency;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class LocaleCurrencyDemo {

  public static void main(String[] args) {
    Set<Currency> currencySet = Currency.getAvailableCurrencies();
    List<Currency> currencies = new ArrayList<>(currencySet);
    Comparator<Currency> comparator = new CurrencyComparator();
    Collections.sort(currencies, comparator);
    Locale fr = Locale.FRENCH;

    for (Currency currency : currencies) {
      Console.println(currency.getCurrencyCode());
      Console.println("  {0} : {1}", "en", currency.getDisplayName());
      Console.println("  {0} : {1}", "fr", currency.getDisplayName(fr));
      Console.println();
    }
  }

  private static final class CurrencyComparator implements Comparator<Currency> {
    @Override
    public int compare(Currency cur1, Currency cur2) {
      int comparison = cur1.getCurrencyCode().compareTo(cur2.getCurrencyCode());
      return comparison;
    }
  }


}
