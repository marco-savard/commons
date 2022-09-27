package com.marcosavard.commons.text.locale;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class LocaleUtil {

    public static final Character.UnicodeScript[] CAUCASIAN_SCRIPTS = new Character.UnicodeScript[] {
            Character.UnicodeScript.ARMENIAN, Character.UnicodeScript.GEORGIAN
    };

    public static final Character.UnicodeScript[] INDIAN_SCRIPTS = new Character.UnicodeScript[] {
            Character.UnicodeScript.BENGALI,
            Character.UnicodeScript.DEVANAGARI,
            Character.UnicodeScript.GUJARATI,
            Character.UnicodeScript.GURMUKHI,
            Character.UnicodeScript.KANNADA,
            Character.UnicodeScript.MALAYALAM,
            Character.UnicodeScript.SINHALA,
            Character.UnicodeScript.TAMIL,
            Character.UnicodeScript.TELUGU
    };

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

    public static List<Locale> getLocalesForScript(List<Locale> allLocales, Character.UnicodeScript script) {
        List<Locale> locales = allLocales
                .stream()
                .filter(l -> script.equals(getUnicodeScript(l)))
                .collect(Collectors.toList());
        return locales;
    }


    public static List<Locale> getLocalesForScript(Character.UnicodeScript script) {
        List<Locale> allLocales = Arrays.asList(Locale.getAvailableLocales());
        List<Locale> locales = allLocales
                .stream()
                .filter(l -> script.equals(getUnicodeScript(l)))
                .collect(Collectors.toList());
        return locales;
    }

    public static List<Locale> getLocalesForScripts(List<Locale> allLocales, Character.UnicodeScript[] scripts) {
        List<Locale> locales = allLocales
                .stream()
                .filter(l -> Arrays.asList(scripts).contains(getUnicodeScript(l)))
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

    public static Locale[] getISOLanguageLocales() {
        String[] isoLanguages = Locale.getISOLanguages();
        List<Locale> locales = new ArrayList<>();

        for (String language : isoLanguages) {
            Locale locale = Locale.forLanguageTag(language);
            locales.add(locale);
        }

        Locale[] array = locales.toArray(new Locale[0]);
        return array;
    }

    public static Locale[] getAdditionalLocales() {
        Locale[] additionalLocales = new Locale[] {
         new Locale.Builder().setLanguage("aa").setRegion("DJ").build(),
        new Locale.Builder().setLanguage("ab").setRegion("GE").build(),
        new Locale.Builder().setLanguage("ae").setRegion("IR").build(),
        new Locale.Builder().setLanguage("an").setRegion("ES").build(),
        new Locale.Builder().setLanguage("av").setRegion("GE").build(),
        new Locale.Builder().setLanguage("ay").setRegion("BO").build(),
       new Locale.Builder().setLanguage("ba").setRegion("RU").build(),
       new Locale.Builder().setLanguage("bh").setRegion("IN").build(),
        new Locale.Builder().setLanguage("bi").setRegion("VU").build(),
        new Locale.Builder().setLanguage("ch").setRegion("GU").build(),
        new Locale.Builder().setLanguage("co").setRegion("FR").build(),
        new Locale.Builder().setLanguage("cr").setRegion("CA").build(),
        new Locale.Builder().setLanguage("cv").setRegion("RU").build(),
        new Locale.Builder().setLanguage("dv").setRegion("MV").build(),
        new Locale.Builder().setLanguage("fj").setRegion("FJ").build(),
       new Locale.Builder().setLanguage("gn").setRegion("PY").build(),
       new Locale.Builder().setLanguage("ho").setRegion("PG").build(),
        new Locale.Builder().setLanguage("ht").setRegion("HT").build(),
        new Locale.Builder().setLanguage("hz").setRegion("NA").build(),
        new Locale.Builder().setLanguage("ik").setRegion("US").build(),
        new Locale.Builder().setLanguage("iu").setRegion("CA").build(),
        new Locale.Builder().setLanguage("kg").setRegion("CD").build(),
        new Locale.Builder().setLanguage("kj").setRegion("NA").build(),
        new Locale.Builder().setLanguage("kr").setRegion("NG").build(),
       new Locale.Builder().setLanguage("kv").setRegion("RU").build(),
       new Locale.Builder().setLanguage("li").setRegion("NL").build(),
       new Locale.Builder().setLanguage("mh").setRegion("MH").build(),
       new Locale.Builder().setLanguage("mo").setRegion("MD").build(),
       new Locale.Builder().setLanguage("na").setRegion("NR").build(),
        new Locale.Builder().setLanguage("ng").setRegion("NA").build(),
        new Locale.Builder().setLanguage("nr").setRegion("ZA").build(),
       new Locale.Builder().setLanguage("nv").setRegion("US").build(),
        new Locale.Builder().setLanguage("ny").setRegion("MW").build(),
        new Locale.Builder().setLanguage("oc").setRegion("FR").build(),
       new Locale.Builder().setLanguage("oj").setRegion("CA").build(),
        new Locale.Builder().setLanguage("pi").setRegion("IN").build(),
       new Locale.Builder().setLanguage("sa").setRegion("IN").build(),
       new Locale.Builder().setLanguage("sc").setRegion("IT").build(),
        new Locale.Builder().setLanguage("sm").setRegion("WS").build(),
      new Locale.Builder().setLanguage("ss").setRegion("ZA").build(),
       new Locale.Builder().setLanguage("st").setRegion("ZA").build(),
        new Locale.Builder().setLanguage("tl").setRegion("PH").build(),
        new Locale.Builder().setLanguage("tn").setRegion("ZA").build(),
        new Locale.Builder().setLanguage("ts").setRegion("ZA").build(),
        new Locale.Builder().setLanguage("tw").setRegion("GH").build(),
        new Locale.Builder().setLanguage("ty").setRegion("PF").build(),
        new Locale.Builder().setLanguage("ve").setRegion("ZA").build(),
        new Locale.Builder().setLanguage("wa").setRegion("BE").build(),
        new Locale.Builder().setLanguage("za").setRegion("CN").build()
        };

        return additionalLocales;
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


    public static List<Locale> getWorldLanguages(List<Locale> locales) {
        //List<String> worlds = Arrays.asList(new String[] {"001"});
        List<Locale> allLocales = Arrays.asList(Locale.getAvailableLocales());
        List<Locale> worldLocales = new ArrayList<>();

        for (Locale locale : locales) {
            String language = locale.getLanguage();
            List<Locale> countries = allLocales.stream().filter(l -> language.equals(l.getLanguage()) ).collect(Collectors.toList());
            Locale foundLocale = countries.stream().filter(l -> l.getCountry() != null).findFirst().orElse(null);

            if (foundLocale == null) {
                worldLocales.add(locale);
            }
        }

        return worldLocales;
    }
}
