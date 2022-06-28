package com.marcosavard.commons.io.csv;

import com.marcosavard.commons.io.csv.decorator.BooleanDecorator;
import com.marcosavard.commons.io.csv.decorator.StringFormatter;
import com.marcosavard.commons.io.csv.decorator.StringReplacer;
import com.marcosavard.commons.io.csv.decorator.StringStripper;

import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class CsvLocaleFormatterDemo {

    public static void main(String[] args) {
        formatLocales();
    }

    private static void formatLocales() {
        //format data
        CsvFormatter formatter = new LocaleFormatter(Locale.class);
        Locale[] locales = Locale.getAvailableLocales();
        List<Locale> languages = Arrays.asList(locales).stream().filter(l -> ! l.getLanguage().equals("")).toList().subList(0, 50);
        List<String[]> data = formatter.format(languages);

        //generate
        Writer writer = new OutputStreamWriter(System.out);
        CsvWriter csvWriter = new CsvWriter(writer);
        csvWriter.writeAll(data);
        csvWriter.flush();
        System.out.println();
    }

    private static class LocaleFormatter extends CsvFormatter<Locale> {

        public LocaleFormatter(Class<Locale> claz) {
            super(claz);
        }

        @Override
        public void addColumns() {
            addColumn("Language");
            addColumn("ISO3Language");
            addColumn("hasExtensions");
            addColumn("DisplayLanguage");
            addColumn("DisplayScript");
            addColumn("DisplayCountry");
        }

        @Override
        public void addDecorators() {
            addDecorator(new StringFormatter("%-3s", "Language", "ISO3Language"));
            addDecorator(new StringFormatter("%-15s", "DisplayLanguage"));
            addDecorator(new BooleanDecorator("Yes", "No", "hasExtensions"));
            addDecorator(new StringStripper(StringStripper.STRIP_ACCENT, "DisplayLanguage", "DisplayCountry"));
            addDecorator(new StringReplacer("&", "and", "DisplayCountry"));
        }
    }
}
