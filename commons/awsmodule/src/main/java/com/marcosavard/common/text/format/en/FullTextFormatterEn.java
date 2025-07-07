package com.marcosavard.common.text.format.en;

import com.marcosavard.common.text.format.TextNumberFormat;

public class FullTextFormatterEn implements TextNumberFormat.FullTextFormatter  {
    private static final String[] UNITS = new String[] { //
            "zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine",
            "ten", "eleven", "twelve", "thirteen", "forteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen"
    };

    private static final String[] TENS = new String[] { //
            "", "ten", "twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety"
    };

    @Override
    public String format(double number) {
        int millions = (int)((number / 1000_000) % 1000_000);
        int thousands = (int)((number / 1000) % 1000);
        int units = (int)(number % 1000);

        String text = (millions == 0) ? "" : lessThan1000(millions) + " " + "million" + " ";
        text += (thousands == 0) ? "" : lessThan1000(thousands) + " " + "thousand" + " ";
        text += lessThan1000(units);

        return text;
    }

    @Override
    public String formatOrdinal(double number) {
        String cardinal = format(number);
        String ordinal = cardinal.replace("one", "first");
        ordinal = ordinal.replace("two", "second");
        ordinal = ordinal.replace("three", "third");
        ordinal = (cardinal.equals(ordinal)) ? ordinal + "th" : ordinal;
        ordinal = ordinal.replace("veth", "fth");
        ordinal = ordinal.replace("tth", "th");
        return ordinal;
    }

    @Override
    public Number parse(String source) {
        return 0;
    }

    private String lessThan1000(int number) {
        int hundreds = (number / 100) % 100;
        int units = number % 100;

        String text = (hundreds == 0) ? "" : lessThan100(hundreds) + " " + "hundred";
        text += " " + lessThan100(units);

        return text;
    }

    private String lessThan100(int value) {
        String text = "";

        if (value < 20) {
            text = UNITS[value];
        } else {
            int tens = value / 10;
            text = TENS[tens];
            text += (value % 10 == 0) ? "" : "-" + UNITS[value % 10];
        }

        return text;
    }
}
