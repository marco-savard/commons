package com.marcosavard.commons.ling;

import com.marcosavard.commons.debug.Console;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class LanguageDemo {

    public static void main(String[] args) {
        //listLanguagesUsingLocales();
        //listLanguagesUsingLanguage();
        listLatinScriptLanguages();
    }

    private static void listLanguagesUsingLocales() {
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
            Locale locale = language.getLocale();

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
                .filter(l -> l.isLatinScript() &&  l.isEuropeanLanguage() && ! (l.isRomanceLanguage() || l.isCelticLanguage() || l.isGermanicLanguage() || l.isSlavicLanguage() ))
                .toList();

        for (Language language : foundLanguages) {
            Locale locale = language.getLocale();

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
