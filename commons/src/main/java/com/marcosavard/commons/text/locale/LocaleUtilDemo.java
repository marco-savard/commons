package com.marcosavard.commons.text.locale;

import com.marcosavard.commons.debug.Console;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class LocaleUtilDemo {

    public static void main(String[] args) {
        displayLocaleLanguages();

        Locale fr = Locale.FRENCH;
        displayFacts(fr);
    }

    private static void displayLocaleLanguages() {
        String[] isoLanguages = Locale.getISOLanguages();
        List<Locale> isoLanguageLocales = Arrays.asList(LocaleUtil.getISOLanguageLocales());

        Console.println("{0} ISO languages : {1}", isoLanguageLocales.size(), toString(isoLanguageLocales));

        List<Locale> latinLanguages = LocaleUtil.getLocalesForScript(isoLanguageLocales, Character.UnicodeScript.LATIN);
        Console.println("  {0} latin languages : {1}", latinLanguages.size(), toString(latinLanguages));
        
        displayLatinLanguages(latinLanguages);

        List<Locale> cyrillicLanguages = LocaleUtil.getLocalesForScript(isoLanguageLocales, Character.UnicodeScript.CYRILLIC);
        Console.println("  {0} cyrillic languages : {1}", cyrillicLanguages.size(), toString(cyrillicLanguages));

        List<Locale> arabicLanguages = LocaleUtil.getLocalesForScript(isoLanguageLocales, Character.UnicodeScript.ARABIC);
        Console.println("  {0} arabic languages : {1}", arabicLanguages.size(), toString(arabicLanguages));

        List<Locale> indianLanguages = LocaleUtil.getLocalesForScripts(isoLanguageLocales, LocaleUtil.INDIAN_SCRIPTS);
        Console.println("  {0} indian languages : {1}", indianLanguages.size(), toString(indianLanguages));

        List<Locale> farEastLanguages = LocaleUtil.getLocalesForScripts(isoLanguageLocales, LocaleUtil.FAR_EAST_SCRIPTS);
        Console.println("  {0} far east languages : {1}", arabicLanguages.size(), toString(farEastLanguages));

        List<Locale> remainingLanguages = new ArrayList<>();
        remainingLanguages.addAll(isoLanguageLocales);
        remainingLanguages.removeAll(latinLanguages);
        remainingLanguages.removeAll(cyrillicLanguages);
        remainingLanguages.removeAll(arabicLanguages);
        remainingLanguages.removeAll(indianLanguages);
        remainingLanguages.removeAll(farEastLanguages);
        Console.println("  {0} remaining languages : {1}", remainingLanguages.size(), toString(remainingLanguages));
        Console.println();
    }

    private static void displayLatinLanguages(List<Locale> latinLanguages) {
        List<Locale> worldLanguages = LocaleUtil.getWorldLanguages(latinLanguages);
        Console.println("    {0} world languages : {1}", worldLanguages.size(), toString(worldLanguages));
    }

    private static String toString(Locale[] locales) {
        return toString(Arrays.asList(locales));
    }

    private static String toString(List<Locale> locales) {
        List<String> displayNames = new ArrayList<>();

        for (Locale locale : locales) {
            displayNames.add(locale.getDisplayLanguage());
        }

        return String.join(", ", displayNames);
    }

    private static void displayFacts(Locale locale) {
        Locale country = LocaleUtil.findCountryOf(locale);
        String countryName = country.getDisplayCountry();
        Console.println("{0} language comes from {1}", locale.getDisplayLanguage(), countryName);

        Character.UnicodeScript script = LocaleUtil.getUnicodeScript(locale);
        String scriptname = toString(script);
        Console.println("{0} language is using {1} script", locale.getDisplayLanguage(), scriptname);

        List<Locale> locales = LocaleUtil.getSubLocales(locale);
        Console.println("{0} language is spoken in : {1}", locale.getDisplayLanguage(), locales);
    }

    private static void displayLocales(Character.UnicodeScript script) {
        List<Locale> locales = LocaleUtil.getLocalesForScript(script);
        Console.println("Languages using {0} script : {1}", toString(script), locales);
    }

    private static void displayLocales(Character.UnicodeScript[] scripts) {
        List<Locale> locales = LocaleUtil.getLocalesForScripts(scripts);
        Console.println("Languages using {0} script : {1}", toString(scripts), locales);
    }

    private static String toString(Character.UnicodeScript script) {
        return script.name().toLowerCase();
    }

    private static String toString(Character.UnicodeScript[] scripts) {
        List<String> strings = new ArrayList();

        for (Character.UnicodeScript script : scripts) {
            strings.add(toString(script));
        }

        return String.join(", ", strings);
    }
}
