package com.marcosavard.commons.io.csv.decorator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateTimeDecorator extends Decorator<LocalDate> {
    private final DateTimeFormatter dateTimeFormatter;

    public DateTimeDecorator(String pattern, String localeText, String... columns) {
        super(columns);
        Locale locale = new Locale(localeText);
        dateTimeFormatter = DateTimeFormatter.ofPattern(pattern).withLocale(locale);
    }

    @Override
    public Object decorateValue(LocalDate value) {
        return dateTimeFormatter.format(value);
    }
}
