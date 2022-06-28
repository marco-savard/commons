package com.marcosavard.commons.io.csv.decorator;

import com.marcosavard.commons.io.csv.CsvFormatter;

public class StringReplacer extends CsvFormatter.Decorator<String> {
    private final String oldString, newString;

    public StringReplacer(String oldString, String newString, String... columns) {
        super(columns);
        this.oldString = oldString;
        this.newString = newString;
    }

    @Override
    public String decorateValue(String value) {
        value = value.replace(oldString, newString);
        return value;
    }
}
