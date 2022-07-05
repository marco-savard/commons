package com.marcosavard.commons.io.csv;

import com.marcosavard.commons.io.FileSystem;
import com.marcosavard.commons.io.csv.decorator.BooleanDecorator;
import com.marcosavard.commons.io.csv.decorator.StringFormatter;
import com.marcosavard.commons.io.csv.decorator.StringReplacer;
import com.marcosavard.commons.io.csv.decorator.StringStripper;
import com.marcosavard.commons.res.PropertyLoader;
import com.marcosavard.commons.util.PropertiesConverter;

import java.io.File;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.*;

public class CsvLocaleFormatterDemo {

    public static void main(String[] args) throws ClassNotFoundException {
        formatLocales1();
        formatLocales2();
    }

    private static void formatLocales1() {
        //get data
        Locale[] locales = Locale.getAvailableLocales();
        List<Locale> languages = Arrays.asList(locales).stream().filter(l -> ! l.getLanguage().equals("")).toList().subList(0, 220);

        //format data
        CsvFormatter formatter = new LocaleFormatter(Locale.class);
        List<String[]> data = formatter.format(languages);

        //generate output
        Writer writer = new OutputStreamWriter(System.out);
        CsvWriter csvWriter = new CsvWriter(writer);
        csvWriter.writeAll(data);
        csvWriter.flush();
        System.out.println();
    }

    private static void formatLocales2() throws ClassNotFoundException {
        //get data
        Locale[] locales = Locale.getAvailableLocales();
        List<Locale> languages = Arrays.asList(locales).stream().filter(l -> ! l.getLanguage().equals("")).toList().subList(0, 220);

        //format data
        String resources = "resources/LocaleFormatter.properties";
        Properties properties = PropertyLoader.of(PropertyCsvFormatter.class).load(resources);
        Map<String, Object> propertyMap = PropertiesConverter.of(properties).toMap();
        CsvFormatter formatter = new PropertyCsvFormatter(propertyMap);
        List<String[]> data = formatter.format(languages);

        //generate output
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
