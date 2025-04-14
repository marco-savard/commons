package com.marcosavard.commons.text.format;

import com.marcosavard.commons.debug.Console;

import java.text.ParseException;
import java.util.Locale;

public class TextNumberFormatDemo {

    public static void main(String[] args) throws ParseException {
        Locale display = Locale.ENGLISH;
        printUpTo(110, display);
       // printFibbonacci(display);
    }

    private static void printUpTo(long number, Locale display) throws ParseException {
        // NumberFormat numberFormat = NumberFormat.getIntegerInstance(display);
        TextNumberFormat numberFormat = TextNumberFormat.getFullTextOrdinalInstance(display);

        for (int i=0; i<=110; i++) {
            String formatted = numberFormat.format(i);
            Number parsed = numberFormat.parse(formatted);
            Console.println("{0} -> {1} -> {2}", i, formatted, parsed);
        }
    }

    private static void printFibbonacci(Locale display) {
        TextNumberFormat numberFormat = TextNumberFormat.getFullTextInstance(display);

        int a=0, b = 1;
        for (int i=0; i<46; i++) {
            int c = a + b;
            a = b;
            b = c;
            Console.println(numberFormat.format(c));
        }
    }



}
