package com.marcosavard.common.text.format;

import com.marcosavard.common.text.format.en.FullTextFormatterEn;
import com.marcosavard.common.text.format.fr.FullTextFormatterFr;

import java.text.*;
import java.util.Locale;

//cannot inherit from DecimalFormat (DecimalFormat.format() is final)
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
        return format((long)number, toAppendTo, pos);
    }

    @Override
    public Number parse(String source, ParsePosition parsePosition) {
        parsePosition.setIndex(source.length());
        FullTextFormatter formatter;
        Number parsed = null;

        if (style == Style.ORDINAL) {
            if (Locale.FRENCH.getLanguage().equals(display.getLanguage())) {
                formatter = new FrOrdinalFormatter();
            } else {
                formatter = new EnOrdinalFormatter();
            }
        } else if (style == Style.ROMAN_NUMERAL) {
            formatter = new RomanNumeralTextFormatter();
        } else {
            return 0;
        }

        try {
            parsed = formatter.parse(source);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return parsed;
    }

    private String toOrdinalString(long number) {
        if (Locale.FRENCH.getLanguage().equals(display.getLanguage())) {
            return toOrdinalStringFr(number);
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
        FullTextFormatter formatter = new EnOrdinalFormatter();
        return formatter.formatOrdinal(number);
    }

    private String toOrdinalStringFr(long number) {
        FullTextFormatter formatter = new FrOrdinalFormatter();
        return formatter.formatOrdinal(number);
    }

    public static interface FullTextFormatter {
        String format(double number);
        String formatOrdinal(double number);
        Number parse(String source) throws ParseException;
    }

    private static class FrOrdinalFormatter implements FullTextFormatter {
        private Locale display = Locale.FRENCH;

        @Override
        public String format(double number) {
            return formatOrdinal(number);
        }

        @Override
        public String formatOrdinal(double number) {
            String formatted = NumberFormat.getNumberInstance(display).format(number);

            if (number == 1) {
                return formatted + "er";
            } else {
                return formatted + "e";
            }
        }

        @Override
        public Number parse(String source) throws ParseException {
            source = source.replace("er", "");
            source = source.replace("e", "");
            return NumberFormat.getIntegerInstance().parse(source);
        }
    }

    private static class EnOrdinalFormatter implements FullTextFormatter {
        private Locale display = Locale.ENGLISH;

        @Override
        public String format(double number) {
            return formatOrdinal(number);
        }

        @Override
        public String formatOrdinal(double number) {
            String formatted = NumberFormat.getNumberInstance(display).format(number);

            if (((number % 10) == 1) && ((number % 100) != 11)) {
                return formatted + "st";
            } else if (((number % 10) == 2) && ((number % 100) != 12)) {
                return formatted + "nd";
            } else if (((number % 10) == 3) && ((number % 100) != 13)) {
                return formatted + "rd";
            } else {
                return formatted + "th";
            }
        }

        @Override
        public Number parse(String source) throws ParseException {
            source = source.replace("th", "");
            source = source.replace("st", "");
            source = source.replace("nd", "");
            source = source.replace("rd", "");
            return NumberFormat.getNumberInstance().parse(source);
        }
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
        public String format(double number) {
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
        public String formatOrdinal(double number) {
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
}
