package com.marcosavard.commons.io.csv.decorator;

public class BooleanDecorator extends Decorator<Boolean> {
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
