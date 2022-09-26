package com.marcosavard.commons.text.locale;

import com.marcosavard.commons.debug.Console;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class LocaleUtilDemo {

    public static void main(String[] args) {
        Locale fr = Locale.FRENCH;
        displayFacts(fr);
        displayLocales(Character.UnicodeScript.ARABIC);
        displayLocales(Character.UnicodeScript.CYRILLIC);
        displayLocales(Character.UnicodeScript.GREEK);

        Character.UnicodeScript[] caucasians = new Character.UnicodeScript[] {
                Character.UnicodeScript.ARMENIAN, Character.UnicodeScript.GEORGIAN
        };
        displayLocales(caucasians);
        displayLocales(LocaleUtil.FAR_EAST_SCRIPTS);

        Locale[] allLocales = Locale.getAvailableLocales();
        Console.println("{0} available locales : {1}", allLocales.length, Arrays.toString(allLocales));

        Locale[] extraLocales = LocaleUtil.getAdditionalLocales();
        Console.println("{0} additional locales : {1}", extraLocales.length, Arrays.toString(extraLocales));
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
