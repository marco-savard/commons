package com.marcosavard.commons.io.csv.decorator;

import com.marcosavard.commons.io.csv.CsvFormatter;

import java.text.MessageFormat;

public class MessageFormatter extends CsvFormatter.Decorator<Object>  {
    private final String pattern;

    public MessageFormatter(String pattern, String... columns) {
        super(columns);
        this.pattern = pattern;
    }

    @Override
    public Object decorateValue(Object value) {
        return MessageFormat.format(pattern, value);
    }
}
