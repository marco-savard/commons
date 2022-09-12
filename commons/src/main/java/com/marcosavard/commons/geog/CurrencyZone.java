package com.marcosavard.commons.geog;

import java.util.*;

public class CurrencyZone {
    private static Map<Currency, List<Locale>> currencyZones;

    public static Map<Currency, List<Locale>> getZones() {
        if (currencyZones == null) {
            currencyZones = init();
        }

        return currencyZones;
    }

    private static Map<Currency, List<Locale>> init() {
        String[] isoCountries = Locale.getISOCountries();
        Locale[] allLocales = Locale.getAvailableLocales();
        Map<Currency, List<Locale>> currencyZones = new HashMap<>();

        for (String isoCountry: isoCountries) {
            List<Locale> locales = Arrays.stream(allLocales).filter(l -> l.getCountry().equals(isoCountry)).toList();

            for (Locale locale : locales) {
                Currency currency = (locale == null) ? null : Currency.getInstance(locale);

                if (currency != null) {
                    if (! currencyZones.containsKey(currency)) {
                        currencyZones.put(currency, new ArrayList<>());
                    }

                    List<Locale> currentLocales = currencyZones.get(currency);
                    currentLocales.add(locale);
                }
            }
        }

        return currencyZones;
    }
}
