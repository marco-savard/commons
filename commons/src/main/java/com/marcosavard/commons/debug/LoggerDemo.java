package com.marcosavard.commons.debug;

public class LoggerDemo {
    private static final Logger logger = Logger.getLogger(LoggerDemo.class);

    public static void main(String[] args) {
        LoggerDemo demo = new LoggerDemo();
        double value = demo.computeValue();
        String text = demo.formatValue(value);
        demo.displayValue(text);
    }

    private double computeValue() {
        double value = 42;
        logger.info("value = " + value);
        return value;
    }

    private String formatValue(double value) {
        logger.info("value = " + value);
        String formmatted = Double.toString(value);
        return formmatted;
    }

    private void displayValue(String text) {
        logger.info("text = " + text);
        System.out.println(text);
    }
}
