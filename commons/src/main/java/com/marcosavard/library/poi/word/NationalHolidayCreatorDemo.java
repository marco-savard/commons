package com.marcosavard.library.poi.word;

import com.marcosavard.commons.debug.Console;
import com.marcosavard.commons.geog.Country;
import com.marcosavard.commons.geog.io.NationalHolidaysReader;
import com.marcosavard.commons.util.LocaleUtil;
import com.marcosavard.commons.util.TimeZoneUtil;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class NationalHolidayCreatorDemo {

    public static void main(String[] args) {
        //settings
        LocalDate date = LocalDate.now();
        Locale display = Locale.FRENCH;
        int nbDays = 7;

        //set output file
        String outputFilePath = "fetes-nationales.docx";
        File outputFile = new File(outputFilePath);

        //get holidays
        NationalHolidaysReader reader = new NationalHolidaysReader();
        List<NationalHolidaysReader.NationalHoliday> holidays = reader.findNext(date, nbDays);

        try (OutputStream output = new FileOutputStream(outputFile)) {
            XWPFDocument document = new XWPFDocument();
            printTitle(document);
            printHolidays(document, date, holidays, display);
            printCountryDetails(document, holidays.get(0), display);
            printCountryDetails(document, holidays.get(1), display);
            printCountryDetails(document, holidays.get(2), display);

            //write document
            document.write(output);
            Console.println("Success. Output file: {0}", outputFilePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void printTitle(XWPFDocument document) {
        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.setFontSize(16);
        run.setText("Fêtes nationales à venir");
    }

    private static void printHolidays(XWPFDocument document, LocalDate givenDate, List<NationalHolidaysReader.NationalHoliday> holidays, Locale display) {
        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();

        for (NationalHolidaysReader.NationalHoliday holiday : holidays) {
            int dayOfMonth = holiday.getDayOfMonth();
            String month = holiday.getMonth().getDisplayName(TextStyle.FULL, display);
            LocalDate date = holiday.getDate(givenDate.getYear());
            long daysBetween = ChronoUnit.DAYS.between(givenDate, date);
            String inDays = toInDays(daysBetween);
            String country = holiday.getCountryCode();
            Locale locale = LocaleUtil.forCountryTag(country);
            String countryName = locale.getDisplayCountry(display);
            String desc = holiday.getDescription();
            String text = MessageFormat.format("  - {0} {1} ({2}) : {3} ({4})", dayOfMonth, month, inDays, countryName, desc);

            run.setText(text);
            run.addCarriageReturn();
        }
    }

    private static String toInDays(long daysBetween) {
        if (daysBetween == 0) {
            return "aujourd'hui";
        } else if (daysBetween == 1) {
            return "demain";
        } else {
            return MessageFormat.format("dans {0} jours", daysBetween);
        }
    }

    private static void printCountryDetails(XWPFDocument document, NationalHolidaysReader.NationalHoliday holiday, Locale display) {
        String countryCode = holiday.getCountryCode();
        Country country = Country.of(countryCode);
        String countryName = country.getDisplayName(display);
        String text = MessageFormat.format("Fiche technique : {0}", countryName);

        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.setFontSize(14);
        run.setText(text);

        printCountryCapital(paragraph, country, display);
        printCountryCurrency(paragraph, country, display);
        printNationalMottos(paragraph, country, display);
        printTimezone(paragraph, country, display);
        printLanguageByCountry(paragraph, country, display);
        printTranslations(paragraph, country, display);
        run.addCarriageReturn();
    }

    private static void printCountryCapital(XWPFParagraph paragraph, Country country, Locale display) {
        List<String> capitalCities = country.getCapitalCities();
        String title = capitalCities.size() == 1 ? "Capitale" : "Capitales";
        String cities = String.join(", ", capitalCities);

        XWPFRun run = paragraph.createRun();
        run.setText("  Nom officiel : " + country.getDisplayName(display, TextStyle.FULL));
        run.addCarriageReturn();
        run.setText(MessageFormat.format("  {0} : {1}", title, cities));
        run.addCarriageReturn();
    }

    private static void printCountryCurrency(XWPFParagraph paragraph, Country country, Locale display) {
        Currency currency = country.getCurrency();
        String currencyCode = currency.getCurrencyCode();

        XWPFRun run = paragraph.createRun();
        run.setText(MessageFormat.format("  Monnaie : {0} ({1})", currency.getDisplayName(display), currencyCode));
        run.addCarriageReturn();
    }

    private static void printNationalMottos(XWPFParagraph paragraph, Country country, Locale display) {
        List<String> mottoList = country.getNationalMottos();

        if (mottoList != null) {
            String title = mottoList.size() == 1 ? "Devise" : "Devises";
            String mottos = mottoList.size() == 1 ? mottoList.get(0) : String.join(", ", mottoList);
            XWPFRun run = paragraph.createRun();
            run.setText(MessageFormat.format("  {0} : {1}", title, mottos));
            run.addCarriageReturn();
        }
    }

    private static void printTimezone(XWPFParagraph paragraph, Country country, Locale display) {
        List<TimeZone> timezones = country.getTimeZones();

        Comparator<? super TimeZone> comparator = TimeZoneUtil.comparator();
        Collections.sort(timezones, comparator.reversed());

        TimeZone eastern = TimeZoneUtil.getMostEartern(timezones);
        TimeZone western = TimeZoneUtil.getMostWestern(timezones);
        List<String> regions = TimeZoneUtil.extractRegions(timezones, display);

        String offset1 = TimeZoneUtil.toOffset(eastern);
        String offset2 = TimeZoneUtil.toOffset(western);
        String name1 = eastern.getDisplayName(display);
        String name2 = western.getDisplayName(display);
        XWPFRun run = paragraph.createRun();
        run.setText(MessageFormat.format("  Région : {0}", String.join(", ", regions)));
        run.addCarriageReturn();

        if (offset1.equals(offset2)) {
            run.setText(MessageFormat.format("  Fuseau horaire : {0} ({1})", offset1, name1));
        } else {
            run.setText(MessageFormat.format("  Fuseaux horaires : [{0} ({1}) .. {2} ({3})]", offset1, name1, offset2, name2));
        }

        run.addCarriageReturn();
    }

    private static void printLanguageByCountry(XWPFParagraph paragraph, Country country, Locale display) {
        Locale[] officialLanguages = country.getOfficialLanguages();
        Locale[] regionalLanguages = country.getRegionalLanguages();
        String title = officialLanguages.length == 1 ? "Langue officielle" : "Langues officielles";
        XWPFRun run = paragraph.createRun();
        run.setText(MessageFormat.format("  {0}: {1}", title, LocaleUtil.toDisplayName(officialLanguages, display)));
        run.addCarriageReturn();

        if (regionalLanguages.length > 0) {
            title = regionalLanguages.length == 1 ? "Langue régional" : "Langues régionales";
            run.setText(MessageFormat.format("  {0}: {1}", title, LocaleUtil.toDisplayName(regionalLanguages, display)));
            run.addCarriageReturn();
        }
    }

    private static void printTranslations(XWPFParagraph paragraph, Country country, Locale display) {
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
        XWPFRun run = paragraph.createRun();
        run.setText(MessageFormat.format("  Autre(s) nom(s) : {0}.", String.join(", ", translations)));
        run.addCarriageReturn();
    }


}
