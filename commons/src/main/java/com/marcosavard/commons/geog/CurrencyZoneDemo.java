package com.marcosavard.commons.geog;

import java.util.*;
import java.util.stream.Collectors;

public class CurrencyZoneDemo {

    public static void main(String[] args) {
        List<Currency> currencies = Currency.getAvailableCurrencies().stream()
                .sorted(Comparator.comparing(Currency::getCurrencyCode))
                .collect(Collectors.toList());

        Map<Currency, List<Locale>> currencyZones = CurrencyZone.getZones();
        Locale locale = Locale.FRENCH;

        for (Currency currency : currencies) {
            System.out.println(currency + " (" + currency.getDisplayName(locale) + ") : " + currencyZones.get(currency));
        }
    }
}
