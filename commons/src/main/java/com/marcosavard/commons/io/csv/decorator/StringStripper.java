package com.marcosavard.commons.io.csv.decorator;

import com.marcosavard.commons.io.csv.CsvFormatter;

import java.text.Normalizer;
import java.util.stream.Stream;


public class StringStripper extends CsvFormatter.Decorator<String> {
    public static final int STRIP_LEADING = 1;
    public static final int STRIP_TRAILING = 2;
    public static final int STRIP_ACCENT = 4;
    public static final int TO_LOWERCASE = 8;
    public static final int TO_UPPERCASE = 16;
    public static final int TO_TITLECASE = 32;

    private int flags;

    private boolean stripLeading;
    private boolean stripTrailing;


    public StringStripper(int flags, String... columns) {
        super(columns);
        this.flags = flags;
    }

    @Override
    public String decorateValue(String value) {
        if ((flags & STRIP_LEADING) != 0) {
            value = value.stripLeading();
        }

        if ((flags & STRIP_TRAILING) != 0) {
            value = value.stripTrailing();
        }

        if ((flags & STRIP_ACCENT) != 0) {
            value = stripAccents(value);
        }

        if ((flags & TO_LOWERCASE) != 0) {
            value = value.toLowerCase();
        }

        if ((flags & TO_UPPERCASE) != 0) {
            value = value.toUpperCase();
        }

        if ((flags & TO_TITLECASE) != 0) {
            value = toTitleCase(value);
        }

        return value;
    }

    public static String stripAccents(CharSequence original) {
        String stripped = Normalizer.normalize(original, Normalizer.Form.NFD);
        return stripped.replaceAll("[^\\p{ASCII}]", "");
    }

    public static String toTitleCase(String word) {
        return Stream.of(word.split(" "))
                .map(w -> capitalize(w))
                .reduce((s, s2) -> s + " " + s2)
                .orElse("");
    }

    private static String capitalize(String word) {
        return (word.length() <= 1) ? word : word.toUpperCase().charAt(0) + word.toLowerCase().substring(1);
    }
}
