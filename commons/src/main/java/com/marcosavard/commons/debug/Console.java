package com.marcosavard.commons.debug;

import com.marcosavard.commons.io.FormatWriter;
import com.marcosavard.commons.util.ToStringBuilder;

import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Arrays;

public class Console {
    private static FormatWriter formatWriter = null;

    public static void println(String pattern, Object... items) {
        getWriter().println(pattern, items);
        getWriter().flush();
    }

    public static void print(String pattern, Object... items) {
        getWriter().print(pattern, items);
    }

    public static void println(double[] array) {
        println(Arrays.toString(array));
    }

    public static void println(int[] array) {
        println(Arrays.toString(array));
    }

    public static void println(long[] array) {
        println(Arrays.toString(array));
    }

    public static void println(double[][] array) {
        println(Arrays.deepToString(array));
    }

    public static void println(int[][] array) {
        println(Arrays.deepToString(array));
    }

    public static void println(long[][] array) {
        println(Arrays.deepToString(array));
    }

    public static void println(double value) {
        getWriter().printf("%.4f", value);
        getWriter().println();
        getWriter().flush();
    }

    public static <T> void println(T[] array) {
        println(Arrays.deepToString(array));
    }

    public static void println(Object object) {
        getWriter().println(ToStringBuilder.build(object));
        getWriter().flush();
    }

    public static void println() {
        getWriter().println();
        getWriter().flush();
    }

    private static FormatWriter getWriter() {
        if (formatWriter == null) {
            Writer w = new OutputStreamWriter(System.out);
            formatWriter = new FormatWriter(w);
        }

        return formatWriter;
    }


}
