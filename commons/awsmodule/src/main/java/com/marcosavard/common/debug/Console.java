package com.marcosavard.common.debug;

import com.marcosavard.common.io.writer.FormatWriter;
import com.marcosavard.common.util.ToStringBuilder;

import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * Instead of : System.out.println("Hello");
 *
 * <p>use: Console.println("Hello");
 *
 * <p>Advantages : 1) Shorter 2) Print accents in IDE (Eclipse, IntelliJ) 3) Supports string
 * formatting 4) Supports indentation
 */
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

  public static void println(char value) {
    getWriter().print(Character.toString(value));
    getWriter().println();
    getWriter().flush();
  }

  public static void println(int value) {
    getWriter().println(Integer.toString(value));
    getWriter().flush();
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
      PrintStream ps;

      try {
        String encoding = StandardCharsets.UTF_8.name();
        ps = new PrintStream(System.out, true, encoding);
      } catch (UnsupportedEncodingException ex) {
        ps = new PrintStream(System.out, true);
      }

      Writer w = new OutputStreamWriter(ps);
      formatWriter = new FormatWriter(w);
    }

    return formatWriter;
  }

    public static void indent() {
      getWriter().indent();
    }

  public static void unindent() {
    getWriter().unindent();
  }
}
