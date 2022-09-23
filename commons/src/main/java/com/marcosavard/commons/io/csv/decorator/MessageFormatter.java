package com.marcosavard.commons.io.csv.decorator;

import java.text.MessageFormat;

public class MessageFormatter extends Decorator<Object>  {
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
