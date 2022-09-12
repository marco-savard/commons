package com.marcosavard.commons.geog;

import java.util.*;

public class CurrencyZoneDemo {

    public static void main(String[] args) {
        List<Currency> currencies = Currency.getAvailableCurrencies().stream()
                .sorted(Comparator.comparing(Currency::getCurrencyCode))
                .toList();

        Map<Currency, List<Locale>> currencyZones = CurrencyZone.getZones();
        Locale locale = Locale.FRENCH;

        for (Currency currency : currencies) {
            System.out.println(currency + " (" + currency.getDisplayName(locale) + ") : " + currencyZones.get(currency));
        }
    }
}
