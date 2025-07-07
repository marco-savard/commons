package com.marcosavard.common.ling;

import com.marcosavard.common.debug.Console;
//import com.marcosavard.common.text.DisplayText;

import java.text.MessageFormat;
import java.time.DayOfWeek;
import java.time.format.TextStyle;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class LanguageDemo {

    public static void main(String[] args) {
        printLocales();
        //printWeekdaysInDifferentLanguages();
        //listLanguagesByScript();
        //listLanguagesUsingJavaLocales();
        //listLanguagesUsingLanguage();
       // listLatinScriptLanguages();
    }

    private static void printLocales() {
        Locale display = Locale.FRENCH;
        printLocale(display, "es");
    }

    private static void printLocale(Locale display, String tag) {
        Locale language = Locale.forLanguageTag(tag);
        System.out.println(MessageFormat.format("{0} -> {1}", tag, language.getDisplayLanguage(display)));
    }

    private static void printWeekdaysInDifferentLanguages() {
        printUsingDisplayLocale(Locale.ENGLISH);
        printUsingDisplayLocale(Locale.FRENCH);
        printUsingDisplayLocale(Language.SPANISH.toLocale());
    }

    private static void listLanguagesByScript() {
        Character.UnicodeScript[] scripts = Character.UnicodeScript.values();

        for (Character.UnicodeScript script : scripts) {
            Language[] languages = Language.ofScript(script);
          //  String languageText = (languages.length == 0) ? "[]" : DisplayText.of(languages);

            System.out.println(script);
          //  System.out.println("  " + languageText);
            System.out.println();
        }
    }

    private static void printUsingDisplayLocale(Locale displayLocale) {
        String lang = displayLocale.getDisplayLanguage(displayLocale);

        DayOfWeek[] days = DayOfWeek.values();
       // System.out.println(MessageFormat.format("{0} : {1}", lang, DisplayText.of(days, TextStyle.FULL, displayLocale)));
    }


    private static void listLanguagesUsingJavaLocales() {
        String[] languages = Locale.getISOLanguages();

        for (String language : languages) {
            Locale locale = Locale.forLanguageTag(language);

            String[] row = new String[] {
                    locale.getLanguage(),
                    locale.getDisplayLanguage(),
                    locale.getDisplayScript()
            };

            Console.println(row);
        }

        Console.println();
    }

    private static void listLanguagesUsingLanguage() {
        Language[] languages = Language.getISOLanguages();

        for (Language language : languages) {
            Locale locale = language.toLocale();

            String[] row = new String[] {
                    locale.getLanguage(),
                    locale.getDisplayLanguage(),
                    language.getUnicodeScript().name()
            };

            Console.println(row);
        }

        Console.println();
    }

    private static void listLatinScriptLanguages() {
        List<Language> languages = Arrays.asList(Language.getISOLanguages());
        //List<Language> foundLanguages = languages.stream().filter(l -> l.getUnicodeScript().equals(Character.UnicodeScript.LATIN)).toList();
        List<Language> foundLanguages = languages
                .stream()
             //   .filter(l -> l.isLatinScript() &&  ! (l.isOceanianLanguage() || l.isAmericanLanguage() || l.isEuropeanLanguage() || l.isWorldLanguage() || l.isIndianLanguage() || l.isFarEastLanguage() ||  l.isCentralAsiaLanguage() || l.isSouthAfricaLanguage() || l.isCentralAfricaLanguage() || l.isEastAfricaLanguage()))
                .filter(l -> l.isLatinScript() &&  l.isCentralEuropeanLanguage( ))
                .collect(Collectors.toList());

        for (Language language : foundLanguages) {
            Locale locale = language.toLocale();

            String[] row = new String[] {
                    locale.getLanguage(),
                    locale.getDisplayLanguage(),
                    language.getUnicodeScript().name(),
                    language.getDiacriticalCharacters().toString()
            };

            Console.println(row);
        }

    }
}
