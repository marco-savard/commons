package com.marcosavard.commons.geog.io;

import com.marcosavard.commons.io.reader.ResourceReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.TextStyle;
import java.util.*;

public class NationalHolidaysReader {
    private List<NationalHoliday> allNationalHolidays;

    public List<NationalHoliday> readAll() {
        if (allNationalHolidays == null) {
            Reader reader = new ResourceReader(NationalHolidaysReader.class, "nationalHolidays.txt", StandardCharsets.UTF_8);
            BufferedReader lineReader = new BufferedReader(reader);
            allNationalHolidays = new ArrayList<>();
            Month currentMonth = Month.JANUARY;
            String line;

            try {
                do {
                    line = lineReader.readLine();

                    if (line != null) {
                        Month month = extractMonth(line);
                        currentMonth = (month == null) ? currentMonth : month;
                        NationalHoliday holiday = extractHoliday(currentMonth, line);

                        if (holiday != null) {
                            allNationalHolidays.add(holiday);
                        }
                    }
                } while (line != null);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return allNationalHolidays;
    }

    public NationalHoliday read(String countryCode) {
        List<NationalHoliday> allNationalHolidays = readAll();
        String code = countryCode.toUpperCase();
        return allNationalHolidays.stream().filter(h -> code.equals(h.countryCode)).findFirst().orElse(null);
    }

    public List<NationalHoliday> findNext(LocalDate date, int nbDays) {
        List<NationalHoliday> allNationalHoliday = readAll();
        List<NationalHoliday> nextHolidays = new ArrayList<>();

        for (NationalHoliday holiday : allNationalHoliday) {
            int dayOfYear = holiday.getDayOfYear(date.getYear());
            if (dayOfYear >= date.getDayOfYear() && (dayOfYear <= date.getDayOfYear() + nbDays)) {
                nextHolidays.add(holiday);
            }
        }

        Comparator<NationalHoliday> comparator = new NationalHolidayComparator(date.getYear());
        Collections.sort(nextHolidays, comparator);
        return nextHolidays;
    }

    private Month extractMonth(String line) {
        String textDate = line.trim().toLowerCase() + "-01-2000";
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMMM-dd-yyyy", Locale.FRENCH);
        Month month;

        try {
            LocalDate date = LocalDate.parse(textDate, dateFormatter);
            month = date.getMonth();
        } catch (DateTimeParseException e) {
            month = null;
        }

        return month;
    }

    private NationalHoliday extractHoliday(Month currentMonth, String line) {
        String[] values = line.split(";");
        NationalHoliday holiday = null;

        if (values.length == 3) {
            String code = extractCountry(values[1]);
            holiday = NationalHoliday.of(currentMonth, Integer.valueOf(values[0]), code, values[1], values[2]);
        }

        return holiday;
    }

    private String extractCountry(String value) {
        String[] isoCountries = Locale.getISOCountries();
        Map<String, String> codeByCountryName = new HashMap<>();

        for (String code : isoCountries) {
            Locale country = new Locale("", code);
            String name = country.getDisplayName(Locale.FRENCH);
            codeByCountryName.put(name, code);
        }

        codeByCountryName.put("Myanmar", "MM");
        codeByCountryName.put("Brunéi-Darussalam", "BN");
        codeByCountryName.put("Bélarus", "BY");
        codeByCountryName.put("Iraq", "IQ");
        codeByCountryName.put("Libéria", "LR");
        codeByCountryName.put("Côte d'Ivoire", "CI");
        codeByCountryName.put("République démocratique du Congo", "CD");
        codeByCountryName.put("République du Congo", "CG");
        codeByCountryName.put("République de Corée", "KR");
        codeByCountryName.put("République populaire démocratique de Corée", "KP");
        codeByCountryName.put("Kirghizistan", "KG");
        codeByCountryName.put("Macédoine", "MK");
        codeByCountryName.put("Moldova", "MD");
        codeByCountryName.put("Nigéria", "NG");
        codeByCountryName.put("République tchèque", "CZ");
        codeByCountryName.put("Sao Tomé et Principe", "ST");
        codeByCountryName.put("Swaziland", "SZ");
        codeByCountryName.put("Saint-Siège", "VA");
        codeByCountryName.put("Trinité et Tobago", "TT");
        codeByCountryName.put("Viet Nam", "VN");
        codeByCountryName.put("Saint-Vincent et les Grenadines", "VC");
        codeByCountryName.put("Saint-Kitts-et-Nevis", "KN");
        String code = codeByCountryName.get(value);
        return code;
    }

    public static class NationalHoliday {
        private Month month;
        private int dayOfMonth;
        private String countryCode, countryName;
        private String desc;

        public static NationalHoliday of(Month month, int day, String countryCode, String countryName, String desc) {
            return new NationalHoliday(month, day, countryCode, countryName, desc);
        }

        private NationalHoliday(Month month, int day, String countryCode, String countryName, String desc) {
            this.month = month;
            this.dayOfMonth = day;
            this.countryCode = countryCode;
            this.countryName = countryName;
            this.desc = desc;
        }

        @Override
        public String toString() {
            String countryName = (countryCode == null) ? this.countryName : new Locale("", countryCode).getDisplayName(Locale.FRENCH);
            return MessageFormat.format("{0} {1} : {2}", month.getDisplayName(TextStyle.FULL, Locale.FRENCH), dayOfMonth, countryName);
        }

        public int getDayOfYear(int year) {
            LocalDate date = LocalDate.of(year, month, dayOfMonth);
            return date.getDayOfYear();
        }

        public Month getMonth() {
            return month;
        }

        public int getDayOfMonth() {
            return dayOfMonth;
        }

        public LocalDate getDate(int year) {
            return LocalDate.of(year, month, dayOfMonth);
        }

        public String getCountryCode() {
            return countryCode;
        }

        public String getDescription() {
            return desc;
        }
    }

    private static class NationalHolidayComparator implements Comparator<NationalHoliday> {
        private int year;

        public NationalHolidayComparator(int year) {
            this.year = year;
        }

        @Override
        public int compare(NationalHoliday hol1, NationalHoliday hol2) {
            return hol1.getDayOfYear(year) - hol2.getDayOfYear(year);
        }
    }

}
