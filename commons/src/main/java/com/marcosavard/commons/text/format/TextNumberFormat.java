package com.marcosavard.commons.text.format;

import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.Locale;

public class TextNumberFormat extends NumberFormat {
    private enum Style {
        CARDINAL,
        ORDINAL,
        ROMAN_NUMERAL,
        FULL_TEXT,
        FULL_TEXT_ORDINAL
    }

    private final Style style;
    private final Locale display;

    public static TextNumberFormat getInstance(Locale display) {
        return new TextNumberFormat(Style.CARDINAL, display);
    }

    public static TextNumberFormat getOrdinalInstance(Locale display) {
        return new TextNumberFormat(Style.ORDINAL, display);
    }

    public static TextNumberFormat getRomanNumeralInstance() {
        return new TextNumberFormat(Style.ROMAN_NUMERAL, null);
    }

    public static TextNumberFormat getFullTextInstance(Locale display) {
        return new TextNumberFormat(Style.FULL_TEXT, display);
    }

    public static TextNumberFormat getFullTextOrdinalInstance(Locale display) {
        return new TextNumberFormat(Style.FULL_TEXT_ORDINAL, display);
    }

    private TextNumberFormat(Style style, Locale display) {
        this.style = style;
        this.display = display;
    }

    @Override
    public StringBuffer format(long number, StringBuffer toAppendTo, FieldPosition pos) {
        if (style == Style.ORDINAL) {
            return toAppendTo.append(toOrdinalString(number));
        } else if (style == Style.ROMAN_NUMERAL) {
            return toAppendTo.append(toRomanNumeral(number));
        } else if (style == Style.FULL_TEXT) {
            return toAppendTo.append(toFullText(number));
        } else if (style == Style.FULL_TEXT_ORDINAL) {
            return toAppendTo.append(toFullTextOrdinal(number));
        } else {
            return toAppendTo.append(Long.toString(number));
        }
    }

    @Override
    public StringBuffer format(double number, StringBuffer toAppendTo, FieldPosition pos) {
        return toAppendTo.append(Double.toString(number));
    }

    @Override
    public Number parse(String source, ParsePosition parsePosition) {
        parsePosition.setIndex(source.length());

        if (style == Style.ROMAN_NUMERAL) {
            FullTextFormatter formatter = new RomanNumeralTextFormatter();
            return formatter.parse(source);
        } else {
            return 0;
        }
    }

    private String toOrdinalString(long number) {
        if (Locale.FRENCH.getLanguage().equals(display.getLanguage())) {
            return toOrdinalStringEn(number);
        } else {
            return toOrdinalStringEn(number);
        }
    }

    private String toRomanNumeral(long number) {
        FullTextFormatter formatter = new RomanNumeralTextFormatter();
        return formatter.format(number);
    }

    private String toFullText(long number) {
        FullTextFormatter formatter;

        if (Locale.FRENCH.getLanguage().equals(display.getLanguage())) {
            formatter = new FullTextFormatterFr();
        } else {
            formatter = new FullTextFormatterEn();
        }

        return formatter.format(number);
    }

    private String toFullTextOrdinal(long number) {
        FullTextFormatter formatter;

        if (Locale.FRENCH.getLanguage().equals(display.getLanguage())) {
            formatter = new FullTextFormatterFr();
        } else {
            formatter = new FullTextFormatterEn();
        }

        return formatter.formatOrdinal(number);
    }

    /**
     * Format the ordinal of this number. For instance format(2)
     * returns "2nd".
     *
     * @param number to format
     * @return a textual representation of the ordinal number
     */
    private String toOrdinalStringEn(long number) {
        if (((number % 10) == 1) && ((number % 100) != 11)) {
            return number + "st";
        } else if (((number % 10) == 2) && ((number % 100) != 12)) {
            return number + "nd";
        } else if (((number % 10) == 3) && ((number % 100) != 13)) {
            return number + "rd";
        } else {
            return number + "th";
        }
    }

    private String toOrdinalStringFr(long number) {
        if (number == 1) {
            return number + "er";
        } else {
            return number + "e";
        }
    }

    private static interface FullTextFormatter {
        String format(long number);
        String formatOrdinal(long number);
        Number parse(String source);

    }

    private static class RomanNumeralTextFormatter implements FullTextFormatter {
        private static final String NIL = "NIL";
        private static final int[] NUMBERS = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        private static final String[] LETTERS = {
                "M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"
        };

        /**
         * Create a roman numeral whose toString() is its textual representation. For instance,
         * System.out.println(romanNumeral.getDisplayName(10)) prints "X".
         *
         * @param number between 0 and 3999
         */
        @Override
        public String format(long number) {
            if (number < 0) throw new IllegalArgumentException("must be positive : " + number);
            if (number > 3999) throw new IllegalArgumentException("must be 3999 or less : " + number);
            String displayName = "";

            if (number == 0) {
                displayName = NIL;
            } else {
                for (int i = 0; i < NUMBERS.length; i++) {
                    while (number >= NUMBERS[i]) {
                        displayName += LETTERS[i];
                        number -= NUMBERS[i];
                    }
                }
            }

            return displayName;
        }

        @Override
        public String formatOrdinal(long number) {
            return "";
        }

        @Override
        public Number parse(String source) {
            long value = 0, prevValue = 0;

            if (NIL.equals(source)) {
                return 0L;
            } else {
                for (int i=source.length()-1; i>=0; i--) {
                    char ch = source.charAt(i);
                    int idx = indexOf(LETTERS, Character.toString(ch));
                    int currentValue = NUMBERS[idx];
                    int mult = currentValue < prevValue ? -1 : 1;
                    value = value + currentValue * mult;
                    prevValue = currentValue;
                }

                return value;
            }
        }

        private static int indexOf(String[] array, String item) {
            int idx = -1;

            for (int i=0; i<array.length; i++) {
                if (array[i].equals(item)) {
                    idx = i;
                    break;
                }
            }

            return idx;
        }
    }

    private static class FullTextFormatterEn implements FullTextFormatter {
        private static final String[] UNITS = new String[] { //
                "zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine",
                "ten", "eleven", "twelve", "thirteen", "forteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen"
        };

        private static final String[] TENS = new String[] { //
                "", "ten", "twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety"
        };

        @Override
        public String format(long number) {
            int millions = (int)((number / 1000_000) % 1000_000);
            int thousands = (int)((number / 1000) % 1000);
            int units = (int)(number % 1000);

            String text = (millions == 0) ? "" : lessThan1000(millions) + " " + "million" + " ";
            text += (thousands == 0) ? "" : lessThan1000(thousands) + " " + "thousand" + " ";
            text += lessThan1000(units);

            return text;
        }

        @Override
        public String formatOrdinal(long number) {
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

    private static class FullTextFormatterFr implements FullTextFormatter {
        private static final String[] UNITS = new String[] { //
                "", "un", "deux", "trois", "quatre", "cinq", "six", "sept", "huit", "neuf",
                "dix", "onze", "douze", "treize", "quatorze", "quinze", "seize", "dix-sept", "dix-huit", "dix-neuf"
        };

        private static final String[] TENS = new String[] { //
                "", "dix", "vingt", "trente", "quarante", "cinquante", "soixante", "soixante-dix", "quatre-vingts", "quatre-vingts-dix"
        };

        @Override
        public String format(long number) {
            return (number == 0) ? "zero" : nonZero(number);
        }

        @Override
        public String formatOrdinal(long number) {
            String numeral;

            if (number == 1) {
                numeral = "premier";
            } else {
                numeral = format(number);
                numeral = addIeme(numeral);
            }

            return numeral;
        }

        private String addIeme(String numeral) {
            String start = numeral.substring(0, numeral.length() - 1);
            numeral = numeral.endsWith("q") ? numeral + "u" : numeral;
            numeral = numeral.endsWith("f") ? start + "v" : numeral;
            numeral = numeral.endsWith("e") ? start + "ième" : numeral + "ième";
            return numeral;
        }

        @Override
        public Number parse(String source) {
            return 0;
        }

        private String nonZero(long number) {
            long positive = Math.abs(number);
            double signum = Math.signum((double)number);

            int bilions = (int)((positive / 1000_000_000) % 1000);
            int milions = (int)((positive / 1000_000) % 1000);
            int thousands = (int)((positive / 1000) % 1000);
            int units = (int)(positive % 1000);

            String text = (bilions == 0) ? "" : lessThan1000(bilions, true) + " " + bilionString(bilions) + " ";
            text += (milions == 0) ? "" : lessThan1000(milions, true) + " " + milionString(milions) + " ";
            text += (thousands == 0) ? "" : lessThan1000(thousands, false) + " " + thousandString(thousands) + " ";
            text += lessThan1000(units, true);
            text = (signum < 0) ? "moins " + text : text;
            return text.trim().replaceAll(" +", " "); //remove duplicated blanks
        }

        private String lessThan1000(int value, boolean returnOne) {
            int hundreds = (value / 100) % 100;
            int units = value % 100;

            String text = (hundreds == 0) ? "" : lessThan100(hundreds, false) + " " + hundredString(hundreds);
            text += " " + lessThan100(units, returnOne);

            return text;
        }

        private String lessThan100(int value, boolean returnOne) {
            String units, tens, text = "";
            value = (value == 1) && ! returnOne ? 0 : value;

            if ((value < 20) || (value > 60)) {
                units = UNITS[value % 20];
                tens = (value < 20) ? "" : TENS[(value / 20) * 2];
            } else {
                units = UNITS[value % 10];
                tens = TENS[value / 10];
            }

            text = tens;
            text += (!tens.isEmpty() && !units.isEmpty() ) ? "-" : "";
            text += (! tens.isEmpty() && units.equals("un") ) ? "et-" : "";
            text += units;

            return text;
        }

        private String bilionString(int bilions) {
            String str = (bilions <= 1) ? "milliard" : "milliards";
            return str;
        }

        private String milionString(int milions) {
            String str = (milions <= 1) ? "million" : "millions";
            return str;
        }

        private String thousandString(int thousands) {
            String str = (thousands <= 1) ? "mille" : "milles";
            return str;
        }

        private String hundredString(int hundreds) {
            String str = (hundreds <= 1) ? "cent" : "cents";
            return str;
        }
    }
}
