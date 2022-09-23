package com.marcosavard.commons.io.csv.decorator;

public class StringReplacer extends Decorator<String> {
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
