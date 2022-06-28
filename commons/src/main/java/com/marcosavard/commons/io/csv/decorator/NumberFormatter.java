package com.marcosavard.commons.io.csv.decorator;

import com.marcosavard.commons.io.csv.CsvFormatter;

import java.text.NumberFormat;

public class NumberFormatter extends CsvFormatter.Decorator<Number> {
    private final NumberFormat format;

    public NumberFormatter(NumberFormat format, String... columns) {
        super(columns);
        this.format = format;
    }

    @Override
    public Object decorateValue(Number value) {
        return format.format(value);
    }
}
