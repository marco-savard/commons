package com.marcosavard.common.text.format;

import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Currency;
import java.util.Locale;

public class CurrencyFormatDemo {

    public static void main(String[] args) throws ParseException {
        Locale display = Locale.CANADA_FRENCH;
        double[] numbers = new double[] {1.5};
        Locale[] countries = new Locale[] {null, Locale.CANADA, Locale.US, Locale.FRANCE, Locale.UK};
        NumberFormat numberFormat;

        //Java standard
        for (Locale country : countries) {
            //println("Formatting using standard DecimalFormat.getCurrencyInstance({0}}) and currency={1}", display.toString(), country);
            numberFormat = DecimalFormat.getCurrencyInstance(display);

            if (country != null) {
                numberFormat.setCurrency(Currency.getInstance(country));
            }

            //printFormatted(numberFormat, numbers);
            //System.out.println();
        }

        //classe
        for (Locale country : countries) {
            println("Formatting using standard DecimalFormat.getCurrencyInstance({0}}) and currency={1}", display.toString(), country);
            numberFormat = CurrencyFormat.getCurrencyInstance(display, CurrencyFormat.Style.DISPLAY_NAME);

            if (country != null) {
                numberFormat.setCurrency(Currency.getInstance(country));
            }

            printFormatted(numberFormat, numbers);
            System.out.println();
        }
    }

    private static void printFormatted(NumberFormat numberFormat, double[] numbers) throws ParseException {
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
