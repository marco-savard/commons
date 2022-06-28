package com.marcosavard.commons.io.csv;

import com.marcosavard.commons.io.FileSystem;
import com.marcosavard.commons.io.csv.decorator.*;

import java.io.*;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class CsvFileFormatterDemo {

    public static void main(String[] args) {
        formatFiles();
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
            addDecorator(new DateTimeDecorator("yyyy MMM dd", "lastModified"));
            addDecorator(new StringFormatter("%-8s", "Directory", "length", "lastModified"));
            addDecorator(new StringFormatter("%-14s", "Directory", "length", "lastModified"));
        }
    }
}
