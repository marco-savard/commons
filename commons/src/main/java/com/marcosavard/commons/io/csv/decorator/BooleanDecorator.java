package com.marcosavard.commons.io.csv.decorator;

import com.marcosavard.commons.io.csv.CsvFormatter;

public class BooleanDecorator extends CsvFormatter.Decorator<Boolean> {
    private final String trueValue, falseValue;

    public BooleanDecorator(String trueValue, String falseValue, String... columns) {
        super(columns);
        this.trueValue = trueValue;
        this.falseValue = falseValue;
    }

    @Override
    public Object decorateValue(Boolean value) {
        return value ? trueValue : falseValue;
    }
}
