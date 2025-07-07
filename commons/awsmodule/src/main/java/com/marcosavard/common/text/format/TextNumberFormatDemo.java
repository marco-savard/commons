package com.marcosavard.common.text.format;

import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class TextNumberFormatDemo {

    public static void main(String[] args) throws ParseException {
        Locale display = Locale.FRENCH;
        double[] numbers = new double[] {0, 0.5, 1, 2, 3, 5, 9, 10, 20, 100, 1000, 2000, 10_000, 1_000_000, 2_000_000};
        NumberFormat numberFormat;

        //STANDARD JAVA
        System.out.println("Formatting using standard NumberFormat.getNumberInstance(FRENCH)");
        numberFormat = NumberFormat.getNumberInstance(Locale.FRENCH);
        printFormatted(numberFormat, numbers);
        System.out.println();

        System.out.println("Formatting using standard NumberFormat.getCompactNumberInstance(FRENCH, SHORT)");
        numberFormat = NumberFormat.getCompactNumberInstance(display, NumberFormat.Style.SHORT);
        printFormatted(numberFormat, numbers);
        System.out.println();

        System.out.println("Formatting using standard NumberFormat.getCompactNumberInstance(FRENCH, LONG)");
        numberFormat = NumberFormat.getCompactNumberInstance(display, NumberFormat.Style.LONG);
        printFormatted(numberFormat, numbers);
        System.out.println();

        System.out.println("Formatting using standard NumberFormat.getPercentInstance(FRENCH)");
        numberFormat = NumberFormat.getPercentInstance(Locale.FRENCH);
        printFormatted(numberFormat, numbers);
        System.out.println();

        System.out.println("Formatting using standard new DecimalFormat(\"\"0.###E0\")");
        numberFormat = new DecimalFormat("0.###E0");
        printFormatted(numberFormat, numbers);
        System.out.println();

        //EXTENDED JAVA
        System.out.println("Formatting using extended TextNumberFormat.getOrdinalInstance(FRENCH)");
        numberFormat = TextNumberFormat.getOrdinalInstance(display);
        printFormatted(numberFormat, numbers);
        System.out.println();

        System.out.println("Formatting using extended TextNumberFormat.getOrdinalInstance(FRENCH)");
        numberFormat = TextNumberFormat.getFullTextInstance(display);
        printFormatted(numberFormat, numbers);
        System.out.println();

        System.out.println("Formatting using extended TextNumberFormat.getOrdinalInstance(FRENCH)");
        numberFormat = TextNumberFormat.getFullTextOrdinalInstance(display);
        printFormatted(numberFormat, numbers);
        System.out.println();
    }

    private static void printFormatted(NumberFormat numberFormat, double numbers[]) throws ParseException {
        for (double number : numbers) {
            String formatted = numberFormat.format(number);
            Number parsed = numberFormat.parse(formatted);
            println("  {0} -> {1} -> {2}", Double.toString(number), formatted, parsed);
        }
    }

    private static void println(String pattern, Object... parameters) {
        System.out.println(MessageFormat.format(pattern, parameters));
    }
}
