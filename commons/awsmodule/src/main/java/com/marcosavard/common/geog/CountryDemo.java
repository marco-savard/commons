package com.marcosavard.common.geog;

import com.marcosavard.common.ling.Language;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.*;

public class CountryDemo {

    public static void main(String[] args) {
        Locale display = Locale.FRENCH;
      //  displayCountryNames(display);
        displayCountryBasicInfo(display);
      //  displayCountryBasicInfoOld(Country.SPAIN, display);
     //   displayCountryBasicInfoOld(Country.SWITZERLAND_FRENCH, display);
      //  displayCountryDisplayNames(Country.USA, display);
       //displayCountryDisplayNames(Country.UNITED_KINGDOM, display);
        //displayCountryInfo(Country.SPAIN, display);
    }

    private static void displayCountryBasicInfo(Locale display) {
        Country[] countries = new Country[] {Country.SPAIN, Country.SWITZERLAND_FRENCH };

        for (Country country : countries) {
            System.out.println("Info sur : " + country.toLocale().getDisplayCountry(display));
            List<Locale> locales = country.localesOf();
            List<Language> languages = country.langagesOf();
            List<String> languageNames = languages.stream()
                    .map(l -> l.toLocale().getDisplayLanguage(display))
                    .toList();
            Currency currency = country.getCurrency();

            System.out.println("  locales : " + locales);
            System.out.println("  langues : " + languageNames);
            System.out.println("  devise : " + currency.getDisplayName(display));
            System.out.println();
        }
    }

    private static void displayCountryNames(Locale display) {
        printCountry(Locale.US, display);
        printCountry(Locale.UK, display);
        printCountry(Locale.GERMANY, display);
        printCountry(Country.SPAIN.toLocale(), display);
        printCountry(Country.NETHERLANDS.toLocale(), display);
        System.out.println();
    }

    private static void displayCountryBasicInfoOld(Country country, Locale display) {
        List<Locale> locales = country.localesOf();
        List<Language> languages = country.langagesOf();
        List<Character.UnicodeScript> scripts = country.getScripts();
        Currency currency = country.getCurrency();

        System.out.println(country.toLocale().getDisplayCountry(display));
        System.out.println("  locales:   " + locales);
        printScripts(scripts, display);
        printLanguages(languages, display);

        System.out.println("  currency: " + currency.getDisplayName(display));
        System.out.println();
    }

    private static void printScripts(List<Character.UnicodeScript> scripts, Locale display) {
        List<String> scriptNames = new ArrayList<>();

        for (Character.UnicodeScript script : scripts) {
            scriptNames.add(script.name());
        }

        System.out.println("  scripts: " + scriptNames);
    }

    private static void printLanguages(List<Language> languages, Locale display) {
        List<String> languageNames = new ArrayList<>();

        for (Language language : languages) {
            languageNames.add(language.toLocale().getDisplayLanguage(display));
        }

        System.out.println("  languages: " + languageNames);
    }

    private static void printCountry(Locale locale, Locale display) {
        System.out.println(locale.getDisplayCountry(display));
    }

    private static void displayCountryInfo(Country country, Locale display) {
        CountryInfo info = CountryInfo.of(country);
        List<TimeZone> timeZones = info.getTimeZones();
        List<CountryInfo.Capital> capitalCities = info.getCapitalCities();
        List<String> mottos = info.getNationalMottos();
        LocalDate localDate = info.getNationalHolidayDate(LocalDate.now().getYear());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM", display);

        System.out.println(info.getDisplayName(display, TextStyle.FULL));
        printTimeZones(timeZones, display);
        System.out.println("  capitalCities: " + capitalCities);
        System.out.println("  mottos: " + mottos);
        System.out.println("  nationalHoliday: " + formatter.format(localDate));
    }

    private static void printTimeZones(List<TimeZone> timeZones, Locale display) {
        List<String> timeZoneNames = new ArrayList<>();

        for (TimeZone timeZone : timeZones) {
            String timeZoneName = timeZone.getID() + ";" + timeZone.getDisplayName(display);
            timeZoneNames.add(timeZoneName);
        }

        System.out.println("  timezones: " + timeZoneNames);
    }

    private static void displayCountryDisplayNames(Country country, Locale display) {
        System.out.println("Code du pays : " + country.toLocale().getCountry());
        System.out.println("  abréviation : " + CountryInfo.of(country).getDisplayName(display, TextStyle.NARROW));
        System.out.println("  nom court seul : " + CountryInfo.of(country).getDisplayName(display, TextStyle.SHORT_STANDALONE));
        System.out.println("  nom court avec article : " + CountryInfo.of(country).getDisplayName(display, TextStyle.SHORT));
        System.out.println("  nom complet : " + CountryInfo.of(country).getDisplayName(display, TextStyle.FULL));
        System.out.println();
    }
}
