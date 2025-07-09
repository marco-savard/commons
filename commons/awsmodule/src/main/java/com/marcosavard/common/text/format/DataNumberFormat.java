package com.marcosavard.common.text.format;

import java.text.*;
import java.util.Locale;

public class DataNumberFormat extends NumberFormat {
    private static final String[] COMPACT_PATTERNS_EN = {"", "", "", "0 KB", "00 KB", "000 KB",
            "0 MB", "00 MB", "000 MG", "0 GB", "00 GB", "000 GB", "0 TB", "00 TB", "000 TB"};
    private static final String[] COMPACT_PATTERNS_FR = {"", "", "", "0 Ko", "00 Ko", "000 Ko",
            "0 Mo", "00 Mo", "000 Mo", "0 Go", "00 Go", "000 Go", "0 To", "00 To", "000 To"};

    private final NumberFormat compactNumberFormat;

    public static NumberFormat getInstance(Locale display) {
        return new DataNumberFormat(display);
    }

    private DataNumberFormat(Locale display) {
        DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getNumberInstance(display);
        String pattern = decimalFormat.toPattern();
        DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance(display);
        String[] compactPatterns = getCompactPatterns(display);


        compactNumberFormat = new CompactNumberFormat(pattern, symbols, compactPatterns);
    }

    private String[] getCompactPatterns(Locale display) {
        String[] compactPatterns;

        if ("fr".equals(display.getLanguage())) {
            compactPatterns = COMPACT_PATTERNS_FR;
        } else {
            compactPatterns = COMPACT_PATTERNS_EN;
        }

        return compactPatterns;
    }

    @Override
    public StringBuffer format(double number, StringBuffer toAppendTo, FieldPosition pos) {
        return format((long)number, toAppendTo, pos);
    }

    @Override
    public StringBuffer format(long number, StringBuffer toAppendTo, FieldPosition pos) {
        return toAppendTo.append(formatNumber(number));
    }

    @Override
    public Number parse(String source, ParsePosition parsePosition) {
        return null;
    }

    private String formatNumber(long number) {
        String formatted = compactNumberFormat.format(number);
        return formatted;
    }
}
