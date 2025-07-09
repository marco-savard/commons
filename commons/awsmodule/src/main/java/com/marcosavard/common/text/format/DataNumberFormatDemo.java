package com.marcosavard.common.text.format;

import java.text.CompactNumberFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class DataNumberFormatDemo {

    public static void main(String[] args) {
        Locale display = Locale.FRENCH;

        //standard Java
        NumberFormat dataNumberFormat = NumberFormat.getCompactNumberInstance(display, NumberFormat.Style.SHORT);
        System.out.println("Format using CompactNumberFormat : " + dataNumberFormat.format(20_000L));
        System.out.println("Format using CompactNumberFormat : " + dataNumberFormat.format(20_000_000_000L));
        System.out.println();

        //extended Java
        dataNumberFormat = DataNumberFormat.getInstance(display);
        System.out.println("Format using DataNumberFormat : " + dataNumberFormat.format(20_000L));
        System.out.println("Format using CompactNumberFormat : " + dataNumberFormat.format(20_000_000_000L));
        System.out.println();
    }
}
