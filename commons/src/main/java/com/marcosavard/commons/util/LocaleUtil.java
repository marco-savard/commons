package com.marcosavard.commons.util;

import com.marcosavard.commons.lang.StringUtil;

import java.util.*;

public class LocaleUtil {
    private static Map<String, Locale> localeByCountryCode = new HashMap<>();
    private static Map<String, String> alternateNameMap;
    private static List<String> alternateNameList = List.of( //
             "by:belarus;bielorussie",
             "cd:congo-kinshasa;republique democratique du congo;democratic republic of the congo;zaïre",
             "cg:congo-brazzaville;republique du congo;republic of the congo",
             "ci:cote d'ivoire;ivory coast",
             "cv:cabo verde;cape verde;cap vert",
             "cz:tchequie;republique tcheque;czechia;czech republic",
             "eh:western sahara;sahara ocidental;sahrawi arab democratic republic",
             "fm:micronesie;micronesia;etats federes de micronesie",
             "gb:great britain;united kingdom;grande-bretagne;royaume-uni",
             "iq:irak;iraq",
             "kp:coree du nord;republique populaire democratique de coree;democratic people's republic of korea",
             "kr:coree du sud;republique de coree;republic of korea",
             "kg:kirghizie;kirghizstan;kirghizistan;kyrgyzstan;kyrgyz republic",
             "md:moldavie;moldova",
             "mk:macedoine;macedonia;north macedonia;republic of north macedonia",
             "mm:birmanie;myanmar;burma",
             "pr:puerto rico;porto rico",
             "sr:suriname;surinam",
             "sz:swaziland;eswatini",
             "tl:timor-leste;east timor;timor oriental",
             "tr:turkey;türkiye;turquie",
             "va:vatican;saint siege;holy see",
             "vn:vietnam;viet nam"
            );


    public static Locale forCountryTag(String countryCode) {
        Locale country = localeByCountryCode.get(countryCode);

        if (country == null) {
            country = new Locale("", countryCode);
            localeByCountryCode.put(countryCode, country);
        }

        return country;
    }

    public static List<Locale> localesOf(String country) {
        List<Locale> locales = new ArrayList<>();
        List<Locale> allLocales = List.of(Locale.getAvailableLocales());
        locales.addAll(allLocales.stream().filter(l -> country.equals(l.getCountry())).toList());

        if (locales.isEmpty()) {
            locales.add(forCountryTag(country));
        }
        return locales;
    }

    public static String toDisplayNames(Locale[] locales, Locale display) {
        return String.join(", ", toDisplayNameArray(locales, display));
    }

    private static String[] toDisplayNameArray(Locale[] locales, Locale display) {
        String[] names = new String[locales.length];

        for (int i=0; i<locales.length; i++) {
            names[i] = toDisplayName(locales[i], display);
        }

        return names;
    }

    private static String toDisplayName(Locale locale, Locale display) {
        String displayName = locale.getDisplayLanguage(display);
        return displayName;
    }

    public static Locale forCountryName(String name, Locale display) {
        name = normalize(name).toLowerCase();
        Locale foundCountry = forAlternateCountryName(name);

        if (foundCountry == null) {
            String[] countries = Locale.getISOCountries();

            for (String country : countries) {
                Locale locale = forCountryTag(country);
                String countryName = normalize(locale.getDisplayCountry(display)).toLowerCase();

                if (name.equals(countryName)) {
                    foundCountry = locale;
                    break;
                }
            }
        }

        return foundCountry;
    }

    private static String normalize(String name) {
        name = name.replace('\'', '’');
        name = name.replace('-', ' ');
        name = StringUtil.stripAccents(name);
        return name;
    }

    private static Locale forAlternateCountryName(String name) {
        Locale foundLocale = null;

        if (alternateNameMap == null) {
            alternateNameMap = new HashMap<>();
            for (String item : alternateNameList) {
                String[] values = item.split(":");
                alternateNameMap.put(values[0], values[1]);
            }
        }

        for (String country : alternateNameMap.keySet()) {
            List<String> names = Arrays.asList(alternateNameMap.get(country).split(";"));

            if (names.contains(name)) {
                foundLocale = forCountryTag(country);
                break;
            }
        }

        return foundLocale;
    }
}
