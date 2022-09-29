package com.marcosavard.commons.text;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;

public class DisplayTextDemo {

    public static void main(String[] args) {
        boolean[] booleans = new boolean[] {Boolean.TRUE, Boolean.FALSE};
        System.out.println(booleans + " -> " + DisplayText.of(booleans));

        byte[] bytes = "Hello".getBytes();
        System.out.println(bytes + " -> " + DisplayText.of(bytes));

        char[] chars = "Hello".toCharArray();
        System.out.println(chars + " -> " + DisplayText.of(chars));

        int[] ints = new int[] {1, 2, 3, 5, 8,13};
        System.out.println(ints + " -> " + DisplayText.of(ints));

        long[] longs = new long[] {1, 2, 3, 5, 8,13};
        System.out.println(longs + " -> " + DisplayText.of(longs));

        float[] floats = new float[] {(float)Math.sqrt(2), (float)Math.sqrt(3)};
        System.out.println(floats + " -> " + DisplayText.of(floats));

        double[] doubles = new double[] {Math.sqrt(2), Math.sqrt(3)};
        System.out.println(doubles + " -> " + DisplayText.of(doubles));

        LocalDate[] today = new LocalDate[] { LocalDate.now(), LocalDate.now() };
        System.out.println(today + " -> " + DisplayText.of(today, "yyyy-MM-dd"));

        String[] array = new String[] {"A", "B", "C"};
        System.out.println(array + " -> " + DisplayText.of(array));

        DayOfWeek[] days = DayOfWeek.values();
        System.out.println(days + " -> " + DisplayText.of(days, TextStyle.SHORT));

        Month[] months = Month.values();
        System.out.println(months + " -> " + DisplayText.of(months, TextStyle.SHORT));




    }
}
