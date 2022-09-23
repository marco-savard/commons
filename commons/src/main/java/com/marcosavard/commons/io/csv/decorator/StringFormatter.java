package com.marcosavard.commons.io.csv.decorator;

public class StringFormatter extends Decorator<String>  {
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
