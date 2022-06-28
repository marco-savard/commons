package com.marcosavard.commons.io.csv.decorator;

import com.marcosavard.commons.io.csv.CsvFormatter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateTimeDecorator extends CsvFormatter.Decorator<LocalDate> {
    private final DateTimeFormatter dateTimeFormatter;

    public DateTimeDecorator(String pattern, String... columns) {
        super(columns);
        dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
    }

    @Override
    public Object decorateValue(LocalDate value) {
        return dateTimeFormatter.format(value);
    }
}
