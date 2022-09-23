package com.marcosavard.commons.io.csv.decorator;

import java.util.Arrays;
import java.util.List;

public abstract class Decorator<T> {
    private List<String> columns;

    protected Decorator(String[] columns) {
        this.columns = Arrays.asList(columns);
    }

    public Object decorate(String column, T value) {
        Object decorated = value;

        if (columns.contains(column)) {
            decorated = decorateValue(value);
        }

        return decorated;
    }

    public abstract Object decorateValue(T value);
}
