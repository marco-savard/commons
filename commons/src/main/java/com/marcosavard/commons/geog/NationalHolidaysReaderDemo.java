package com.marcosavard.commons.geog;

import com.marcosavard.commons.debug.Console;
import com.marcosavard.commons.geog.io.NationalHolidaysReader;
import com.marcosavard.commons.util.LocaleUtil;
import com.marcosavard.commons.util.TimeZoneUtil;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.*;

public class NationalHolidaysReaderDemo {

    public static void main(String[] args) {
        Country country = Country.NORWAY;
        Locale display = Locale.FRENCH;

        Console.println(country.getDisplayName(display));
        readCountryCapital(country, display);
        readCountryCurrency(country, display);
        readNationalMottos(country, display);
        readTimezone(country, display);
        readNationalHoliday(country, display);
        readLanguageByCountry(country, display);
        readTranslations(country, display);

        readNationalHolidays();
    }

    private static void readNationalMottos(Country country, Locale display) {
        List<String> mottoList = country.getNationalMottos();

        if (mottoList != null) {
            String title = mottoList.size() == 1 ? "Devise" : "Devises";
            String mottos = mottoList.size() == 1 ? mottoList.get(0) : String.join(", ", mottoList);
            Console.println("  {0} : {1}", title, mottos);
        }

    }

    private static void readTranslations(Country country, Locale display) {
        Locale[] officialLanguages = country.getOfficialLanguages();
        Locale[] regionalLanguages = country.getRegionalLanguages();
        List<Locale> otherLanguages = new ArrayList<>();
        otherLanguages.addAll(Arrays.asList(officialLanguages));
        otherLanguages.addAll(Arrays.asList(regionalLanguages));
        List<String> translations = new ArrayList<>();
        Locale locale = country.getLocale();
        Map<String, List<String>> translationsByLanguages = new HashMap<>();

        for (Locale language : otherLanguages) {
            if (! language.getLanguage().equals(display.getLanguage())) {
                String translation = locale.getDisplayCountry(language);
                String languageName = language.getDisplayLanguage(display);
                List<String> languages = translationsByLanguages.get(translation);

                if (languages == null) {
                    languages = new ArrayList<>();
                    translationsByLanguages.put(translation, languages);
                }

                languages.add(languageName);
            }
        }

        for (String translation : translationsByLanguages.keySet()) {
            List<String> languages = translationsByLanguages.get(translation);
            translations.add(MessageFormat.format("{0} ({1})", translation, String.join(", ", languages)));
        }

        Console.println("  Autres nom(s) : {0}.", String.join(", ", translations));
    }

    private static void readCountryCurrency(Country country, Locale display) {
        Currency currency = country.getCurrency();
        String currencyCode = currency.getCurrencyCode();
        Console.println("  Monnaie : {0} ({1})", currency.getDisplayName(display), currencyCode);
    }

    private static void readTimezone(Country country, Locale display) {
        List<TimeZone> timezones = country.getTimeZones();

        Comparator<? super TimeZone> comparator = TimeZoneUtil.comparator();
        Collections.sort(timezones, comparator.reversed());

        TimeZone eastern = TimeZoneUtil.getMostEartern(timezones);
        TimeZone western = TimeZoneUtil.getMostWestern(timezones);

        List<String> regions = TimeZoneUtil.extractRegions(timezones, display);
        Console.println("  Region : " + String.join(", ", regions));

        String offset1 = TimeZoneUtil.toOffset(eastern);
        String offset2 = TimeZoneUtil.toOffset(western);
        String name1 = eastern.getDisplayName(display);
        String name2 = western.getDisplayName(display);

        if (offset1.equals(offset2)) {
            Console.println("  Fuseau horaire : {0} ({1})", offset1, name1);
        } else {
            Console.println("  Fuseaux horaires : [{0} ({1}) .. {2} ({3})]", offset1, name1, offset2, name2);
        }
    }

    private static void readCountryCapital(Country country, Locale display) {
        List<String> capitals = country.getCapitalCities().stream().map(Country.Capital::name).toList();
        String title = capitals.size() == 1 ? "Capitale" : "Capitales";
        String cities = String.join(", ", capitals);

        Console.println("  Nom officiel : " + country.getDisplayName(display, TextStyle.FULL));
        Console.println("  {0} : {1}", title, cities);
    }

    private static void readNationalHoliday(Country country, Locale display) {
        LocalDate date =  country.getNationalHolidayDate(2000);

        if (date != null) {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("d MMMM", display);
            Console.println("  Fete nationale : {0}", dateFormatter.format(date));
        }
    }

    private static void readLanguageByCountry(Country country, Locale display) {
        Locale[] officialLanguages = country.getOfficialLanguages();
        Locale[] regionalLanguages = country.getRegionalLanguages();
        String title = officialLanguages.length == 1 ? "Langue officielle" : "Langues officielles";
        Console.println("  {0}: {1}", title, LocaleUtil.toDisplayNames(officialLanguages, display));

        if (regionalLanguages.length > 0) {
            title = regionalLanguages.length == 1 ? "Langue regional" : "Langues regionales";
            Console.println("  {0}: {1}", title, LocaleUtil.toDisplayNames(regionalLanguages, display));
        }
    }

    private static void readNationalHolidays() {
        NationalHolidaysReader reader = new NationalHolidaysReader();
        LocalDate date = LocalDate.now();
        int nbDays = 10;
        List<NationalHolidaysReader.NationalHoliday> holidays = reader.findNext(date, nbDays);

        for (NationalHolidaysReader.NationalHoliday holiday : holidays) {
            Console.println(holiday);
        }
    }


}
