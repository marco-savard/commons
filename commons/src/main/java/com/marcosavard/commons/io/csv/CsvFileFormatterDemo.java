package com.marcosavard.commons.io.csv;

import com.marcosavard.commons.io.FileSystem;
import com.marcosavard.commons.io.csv.decorator.*;
import com.marcosavard.commons.res.PropertyLoader;
import com.marcosavard.commons.util.PropertiesConverter;

import java.io.*;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

public class CsvFileFormatterDemo {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Writer writer = new StringWriter();
        //formatFiles1(writer);
        formatFiles2(writer);
        writer.close();

        System.out.println(writer.toString());
    }

    private static void formatFiles1(Writer writer) throws IOException {
        //format data
        CsvFormatter formatter = new FileFormatter(File.class);
        File[] files = FileSystem.getUserDocumentFolder().listFiles();
        List<String[]> data = formatter.format(files);

        //generate
        CsvWriter csvWriter = new CsvWriter(writer, "|", "");
        csvWriter.writeAll(data);
        System.out.println();
    }

    private static void formatFiles2(Writer writer) throws IOException, ClassNotFoundException {
        //format data
        String resources = "resources/FileFormatter.properties";
        Properties properties = PropertyLoader.of(PropertyCsvFormatter.class).load(resources);
        Map<String, Object> propertyMap = PropertiesConverter.of(properties).toMap();
        CsvFormatter formatter = new PropertyCsvFormatter(propertyMap);
        File[] files = FileSystem.getUserDocumentFolder().listFiles();
        List<String[]> data = formatter.format(files);

        //generate
        CsvWriter csvWriter = new CsvWriter(writer, "|", "");
        csvWriter.writeAll(data);
        System.out.println();
    }

    private static class FileFormatter extends CsvFormatter<File> {

        public FileFormatter(Class<File> claz) {
            super(claz);
        }

        @Override
        public void addColumns() {
            addColumn("Directory", "Type");
            addColumn("length", "Size");
            addColumn("lastModified", "Date Modified");
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
            addDecorator(new DateTimeDecorator("yyyy MMM dd", Locale.FRENCH.getLanguage(),"lastModified"));
            addDecorator(new StringStripper(StringStripper.STRIP_ACCENT, "lastModified"));
            addDecorator(new StringFormatter("%-8s", "Directory", "length", "lastModified"));
            addDecorator(new StringFormatter("%-14s", "Directory", "length", "lastModified"));
        }
    }
}

