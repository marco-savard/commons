package com.marcosavard.commons.time;

import java.time.DayOfWeek;
import java.time.format.TextStyle;
import java.util.Locale;

public class DayOfWeekUtil {
    private static final String LATIN = "la";

    public static String getDisplayName(DayOfWeek dayOfWeek, TextStyle textStyle, Locale locale) {
        if (LATIN.equals(locale.getLanguage())) {
            return getLatinDisplayName(dayOfWeek);
        } else {
            return dayOfWeek.getDisplayName(textStyle, locale);
        }
    }

    private static String getLatinDisplayName(DayOfWeek dayOfWeek) {
        String displayName = switch (dayOfWeek) {
            case MONDAY -> "diēs Lūnae";
            case TUESDAY -> "diēs Mārtis";
            case WEDNESDAY -> "diēs Mercuriī";
            case THURSDAY -> "diēs Iovis";
            case FRIDAY -> "diēs Veneris";
            case SATURDAY -> "diēs Sāturnī";
            case SUNDAY -> "diēs Sōlis";
        };

        return displayName;
    }
}
