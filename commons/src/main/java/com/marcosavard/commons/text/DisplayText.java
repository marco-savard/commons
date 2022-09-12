package com.marcosavard.commons.text;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DisplayText {
    //Generic
    public static <T> String of(T[] array) {
        String[] text = new String[array.length];

        for (int i=0; i<array.length; i++) {
            text[i] = of(array[i]);
        }

        String str = String.join(", ", text);
        return "[" + str + "]";
    }

    public static <T> String of(T item) {
        if (item instanceof DayOfWeek) {
            return of((DayOfWeek) item);
        } else if (item instanceof LocalDate) {
            return of((LocalDate)item);
        } else if (item instanceof Month) {
            return of((Month)item);
        } else {
            return item.toString();
        }
    }

    //Primitives
    public static String of(boolean[] array) {
        String[] text = new String[array.length];

        for (int i=0; i<array.length; i++) {
            text[i] = of(array[i]);
        }

        String str = String.join(", ", text);
        return "[" + str + "]";
    }

    public static String of(byte[] array) {
        String[] text = new String[array.length];

        for (int i=0; i<array.length; i++) {
            text[i] = of(array[i]);
        }

        String str = String.join(", ", text);
        return "[" + str + "]";
    }

    public static String of(char[] array) {
        String[] text = new String[array.length];

        for (int i=0; i<array.length; i++) {
            text[i] = of(array[i]);
        }

        String str = String.join(", ", text);
        return "[" + str + "]";
    }

    public static String of(double[] array) {
        String[] text = new String[array.length];

        for (int i=0; i<array.length; i++) {
            text[i] = of(array[i]);
        }

        String str = String.join(", ", text);
        return "[" + str + "]";
    }

    public static String of(float[] array) {
        String[] text = new String[array.length];

        for (int i=0; i<array.length; i++) {
            text[i] = of(array[i]);
        }

        String str = String.join(", ", text);
        return "[" + str + "]";
    }

    public static String of(short[] array) {
        String[] text = new String[array.length];

        for (int i=0; i<array.length; i++) {
            text[i] = of(array[i]);
        }

        String str = String.join(", ", text);
        return "[" + str + "]";
    }

    public static String of(int[] array) {
        String[] text = new String[array.length];

        for (int i=0; i<array.length; i++) {
            text[i] = of(array[i]);
        }

        String str = String.join(", ", text);
        return "[" + str + "]";
    }

    public static String of(long[] array) {
        String[] text = new String[array.length];

        for (int i=0; i<array.length; i++) {
            text[i] = of(array[i]);
        }

        String str = String.join(", ", text);
        return "[" + str + "]";
    }

    //Specific
    public static String of(DayOfWeek day) {
        return of(day, TextStyle.FULL);
    }

    public static String of(DayOfWeek day, TextStyle textStyle) {
        return day.getDisplayName(textStyle, Locale.getDefault());
    }

    public static String of(DayOfWeek day, TextStyle textStyle, Locale displayLocale) {
        return day.getDisplayName(textStyle, displayLocale);
    }

    public static String of(DayOfWeek[] days, TextStyle textStyle) {
        return of(days, textStyle, Locale.getDefault());
    }

    public static String of(DayOfWeek[] days, TextStyle textStyle, Locale displayLocale) {
        List<String> list = new ArrayList<>();
        for (DayOfWeek day : days) {
            list.add(of(day, textStyle, displayLocale));
        }

        return "[" + String.join(", ", list) + "]";
    }

    public static String of(LocalDate date) {
        return of(date, "yyyy-MM-dd");
    }

    public static String of(LocalDate date, String pattern) {
        return of(date, pattern, Locale.getDefault());
    }

    public static String of(LocalDate[] dates, String pattern) {
        return of(dates, pattern, Locale.getDefault());
    }

    public static String of(LocalDate[] dates, String pattern, Locale displayLocale) {
        List<String> list = new ArrayList<>();
        for (LocalDate date : dates) {
            list.add(of(date, pattern, displayLocale));
        }

        return "[" + String.join(", ", list) + "]";
    }

    public static String of(LocalDate date, String pattern, Locale displayLocale) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern, displayLocale);
        return formatter.format(date);
    }

    public static String of(Month month) {
        return of(month, TextStyle.FULL);
    }

    public static String of(Month month, TextStyle textStyle) {
        return of(month, textStyle, Locale.getDefault());
    }

    public static String of(Month month, TextStyle textStyle, Locale displayLocale) {
        return month.getDisplayName(textStyle, displayLocale);
    }

    public static String of(Month[] months, TextStyle textStyle) {
        return of(months, textStyle, Locale.getDefault());
    }

    public static String of(Month[] months, TextStyle textStyle, Locale displayLocale) {
        List<String> list = new ArrayList<>();
        for (Month month : months) {
            list.add(of(month, textStyle, displayLocale));
        }

        return "[" + String.join(", ", list) + "]";
    }
}
