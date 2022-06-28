package com.marcosavard.commons.io.csv;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.List;

//A CsvWriter, compatible w/ OpenCsv API
public class CsvWriter {
    private static final String DEFAULT_DELIMITER = ", ";
    private static final String DEFAULT_QUOTE_DELIMITER = "\"";

    private PrintWriter printWriter;
    private String delimiter;
    private String quote;

    public CsvWriter(Writer writer) {
        this(writer, DEFAULT_DELIMITER);
    }

    public CsvWriter(Writer writer, char delimiter) {
        this(writer, Character.toString(delimiter), DEFAULT_QUOTE_DELIMITER);
    }

    public CsvWriter(Writer writer, String delimiter) {
        this(writer, delimiter, DEFAULT_QUOTE_DELIMITER);
    }

    public CsvWriter(Writer writer, char delimiter, char quote) {
        this(writer, Character.toString(delimiter), Character.toString(quote));
    }

    public CsvWriter(Writer writer, String delimiter, String quote) {
        printWriter = new PrintWriter(writer);
        this.delimiter = delimiter;
        this.quote = quote;
    }

    public void writeAll(List<String[]> rows) {
        for (String[] row : rows) {
            write(row);
        }
    }

    private void write(String[] row) {
        for (int i=0; i<row.length; i++) {
            printWriter.print(quote + row[i] + quote);

            if (i < row.length - 1) {
                printWriter.print(delimiter);
            }
        }

        printWriter.println();
    }

    public void flush() {
        printWriter.flush();
    }

    public void close() {
        printWriter.close();
    }

}
