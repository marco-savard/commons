package com.marcosavard.commons.text.locale;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class LocaleUtil {

    public static final Character.UnicodeScript[] FAR_EAST_SCRIPTS = new Character.UnicodeScript[] {
            Character.UnicodeScript.HAN,
            Character.UnicodeScript.HANGUL,
            Character.UnicodeScript.KHMER,
            Character.UnicodeScript.LAO,
            Character.UnicodeScript.MYANMAR,
            Character.UnicodeScript.THAI,
            Character.UnicodeScript.TIBETAN,
            Character.UnicodeScript.YI
    };

    public static List<Locale> getLocalesForScript(Character.UnicodeScript script) {
        List<Locale> allLocales = Arrays.asList(Locale.getAvailableLocales());
        List<Locale> locales = allLocales
                .stream()
                .filter(l -> script.equals(getUnicodeScript(l)))
                .collect(Collectors.toList());
        return locales;
    }

    public static List<Locale> getLocalesForScripts(Character.UnicodeScript[] scripts) {
        List<Locale> allLocales = Arrays.asList(Locale.getAvailableLocales());
        List<Locale> locales = allLocales
                .stream()
                .filter(l -> Arrays.asList(scripts).contains(getUnicodeScript(l)))
                .collect(Collectors.toList());
        return locales;
    }

    public static Character.UnicodeScript getUnicodeScript(Locale locale) {
        String displayName = locale.getDisplayName(locale);
        char firstLetter = (displayName.isBlank()) ? ' ' : displayName.charAt(0);
        Character.UnicodeScript script = Character.UnicodeScript.of(firstLetter);

        if (script == Character.UnicodeScript.UNKNOWN) {
            script = toScript(locale);
        }

        return script;
    }

    public static List<Locale> getSubLocales(Locale languageLocale) {
        String language = languageLocale.getLanguage();
        List<Locale> allLocales = Arrays.asList(Locale.getAvailableLocales());
        List<Locale> subLocales = new ArrayList<>();

        for (Locale locale : allLocales) {
            if (language.equals(locale.getLanguage())) {
                subLocales.add(locale);
            }
        }

        return subLocales;
    }

    public static Locale findCountryOf(Locale locale) {
        List<Locale> allLocales = Arrays.asList(Locale.getAvailableLocales());
        String language = locale.getLanguage();
        String country = findCountryCodeOf(locale);
        Locale foundLocale = allLocales.stream().filter(l -> language.equals(l.getLanguage()) && country.equals(l.getCountry())).findFirst().orElse(null);
        return foundLocale;
    }

    public static String findCountryCodeOf(Locale locale) {
        String language = locale.getLanguage();
        String countryCode;

        switch(language) {
            case "cs" :
                countryCode = "CZ";
                break;
            case "da" :
                countryCode = "DK";
                break;
            case "in" :
                countryCode = "ID";
                break;
            case "sl" :
                countryCode = "SI";
                break;
            case "sq" :
                countryCode = "AL";
                break;
            case "sr" :
                countryCode = "RS";
                break;
            case "sv" :
                countryCode = "SE";
                break;
            case "vi" :
                countryCode = "VN";
                break;
            default:
                countryCode = language.toUpperCase();
        }

        return countryCode;
    }

    private static Character.UnicodeScript toScript(Locale locale) {
        Character.UnicodeScript script;
        String scriptName = locale.getScript();
        String language = locale.getLanguage();

        if (scriptName.equals("Adlm")) {
            script = Character.UnicodeScript.ADLAM;
        } else if ("ccp".equals(language)) {
            script = Character.UnicodeScript.CHAKMA;
        } else {
            script = Character.UnicodeScript.UNKNOWN;
        }

        return script;
    }



}
