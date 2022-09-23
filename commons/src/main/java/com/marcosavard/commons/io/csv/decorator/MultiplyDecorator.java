package com.marcosavard.commons.io.csv.decorator;

public class MultiplyDecorator extends Decorator<Number> {
    private final double factor;

    public MultiplyDecorator(String factor, String... columns) {
        this(Double.parseDouble(factor), columns);
    }


    public MultiplyDecorator(double factor, String... columns) {
        super(columns);
        this.factor = factor;
    }

    @Override
    public Object decorateValue(Number value) {
        return value.doubleValue() * factor;
    }
}
