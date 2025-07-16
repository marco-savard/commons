package com.marcosavard.common.ling;

import com.marcosavard.common.debug.Console;
import com.marcosavard.common.text.DisplayText;

import java.text.MessageFormat;
import java.time.DayOfWeek;
import java.time.format.TextStyle;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class LanguageDemo {

    public static void main(String[] args) {
        Locale display = Locale.FRENCH;
        printLocales(display);
        showLanguageAPI(display);
        printWeekdaysInDifferentLanguages();

        //listLanguagesUsingJavaLocales();
        //listLanguagesUsingLanguage();

       // listLanguagesByScript();
       // listLatinScriptLanguages();
    }

    private static void showLanguageAPI(Locale display) {
        Language[] languages = new Language[] {Language.FRENCH, Language.SPANISH};

        for (Language language : languages) {
            Character.UnicodeScript script = language.getUnicodeScript();
            List<Character> accents = language.getDiacriticalCharacters();

            System.out.println("Info sur : " + language.toLocale().getDisplayLanguage(display));
            System.out.println("  alphabet : " + script.name());
            System.out.println("  accents : " + accents);
            System.out.println();
        }
    }

    private static void printLocales(Locale display) {
        printLocale(display, "ess");
    }

    private static void printLocale(Locale display, String tag) {
        System.out.println("Print locale 'es'");
        Locale language = Locale.forLanguageTag(tag);
        System.out.println(MessageFormat.format("  {0} -> {1}", tag, language.getDisplayLanguage(display)));
        System.out.println();
    }

    private static void printWeekdaysInDifferentLanguages() {
        System.out.println("printWeekdaysInDifferentLanguages : ");
        printUsingDisplayLocale(Locale.ENGLISH);
        printUsingDisplayLocale(Locale.FRENCH);
        printUsingDisplayLocale(Locale.forLanguageTag("pr"));
        printUsingDisplayLocale(Language.SPANISH.toLocale());
        System.out.println();
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
        String text = DisplayText.of(days, TextStyle.FULL, displayLocale);
        System.out.println(MessageFormat.format("  {0} : {1}", lang, text));
    }

    private static void listLanguagesUsingJavaLocales() {
        String[] languages = Locale.getISOLanguages();
        Console.println("list languages using Locale.getISOLanguages() : {0} languages found", languages.length);
        Console.indent();

        for (String language : languages) {
            Locale locale = Locale.forLanguageTag(language);

            String[] row = new String[] {
                    locale.getLanguage(),
                    locale.getDisplayLanguage(),
                    locale.getDisplayScript()
            };

            Console.println(row);
        }

        Console.unindent();
        Console.println();
    }

    private static void listLanguagesUsingLanguage() {
        Language[] languages = Language.getISOLanguages();
        Console.println("list languages using Language.getISOLanguages() : {0} languages found", languages.length);
        Console.indent();

        for (Language language : languages) {
            Locale locale = language.toLocale();

            String[] row = new String[] {
                    locale.getLanguage(),
                    locale.getDisplayLanguage(),
                    language.getUnicodeScript().name()
            };

            Console.println(row);
        }

        Console.unindent();
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
