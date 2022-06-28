package com.marcosavard.commons.io.csv;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class CsvFormatterDemo {

    public static void main(String[] args) {
        //create writer
        Writer writer = new OutputStreamWriter(System.out);

        //format
        CsvFormatter formatter = new LocaleFormatter(Locale.class);
        Locale[] locales = Locale.getAvailableLocales();
        List<Locale> languages = Arrays.asList(locales).stream().filter(l -> ! l.getLanguage().equals("")).toList();
        List<String[]> data = formatter.format(languages);

        //generate
        CsvWriter csvWriter = new CsvWriter(writer);
        csvWriter.writeAll(data);
        csvWriter.close();
    }

    private static class LocaleFormatter extends CsvFormatter<Locale> {

        public LocaleFormatter(Class<Locale> claz) {
            super(claz);
        }

        @Override
        public void addColumns() throws NoSuchMethodException {
            addColumn("Language", "%-3s");
            addColumn("ISO3Language", "%-3s");
            addColumn("DisplayLanguage", "%-16s");
            addColumn("DisplayScript");
            addColumn("DisplayCountry");
        }


    }
}
