package com.marcosavard.commons.io.csv;

import com.marcosavard.commons.io.FileSystem;
import com.marcosavard.commons.io.csv.decorator.*;

import java.io.*;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class CsvFormatterDemo {

    public static void main(String[] args) {
        formatLocales();
        formatFiles();
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

    private static void formatFiles() {
        //format data
        CsvFormatter formatter = new FileFormatter(File.class);
        File[] files = FileSystem.getUserDocumentFolder().listFiles();
        List<String[]> data = formatter.format(files);

        //generate
        Writer writer = new OutputStreamWriter(System.out);
        CsvWriter csvWriter = new CsvWriter(writer, "|", "");
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

    private static class FileFormatter extends CsvFormatter<File> {

        public FileFormatter(Class<File> claz) {
            super(claz);
        }

        @Override
        public void addColumns() {
            addColumn("Directory");
            addColumn("length");
            addColumn("lastModified");
            addColumn("Name");
        }

        @Override
        public void addDecorators() {
            NumberFormat nf = NumberFormat.getInstance(Locale.ENGLISH);
            nf.setGroupingUsed(true);
            nf.setMaximumFractionDigits(1);

            addDecorator(new BooleanDecorator("Folder", "File", "Directory"));
            addDecorator(new MultiplyDecorator(0.001, "length"));
            addDecorator(new NumberFormatter(nf, "length"));
            addDecorator(new MessageFormatter("{0} KB", "length"));
            addDecorator(new LocalDateConverter("lastModified"));
            addDecorator(new DateTimeDecorator("yyyy MMM dd", "lastModified"));
            addDecorator(new StringFormatter("%-8s", "Directory", "length", "lastModified"));
        }
    }
}
