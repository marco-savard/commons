package com.marcosavard.commons.io.csv.decorator;

import com.marcosavard.commons.io.csv.CsvFormatter;

public class StringFormatter extends CsvFormatter.Decorator<String>  {
    private final String format;

    public StringFormatter(String format, String... columns) {
        super(columns);
        this.format = format;
    }

    @Override
    public String decorateValue(String value) {
        return String.format(format, value);
    }
}
