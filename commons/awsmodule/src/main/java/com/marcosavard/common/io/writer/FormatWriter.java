package com.marcosavard.common.io.writer;

import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.Writer;
import java.text.MessageFormat;
import java.util.Arrays;

public class FormatWriter extends IndentWriter {

  public FormatWriter(Writer w, int indentation) {
    super(w, indentation);
  }

  public FormatWriter(Writer w) {
    this(w, DEFAULT_INDENTATION);
  }

    public FormatWriter(PrintStream output) {
        this(new OutputStreamWriter(output));
    }

    public void print(String pattern, Object... items) {
    pattern = pattern.replace("'", "''");
    super.print(MessageFormat.format(pattern, items));
  }

  public void println(String pattern, Object... items) {
    pattern = pattern.replace("'", "''");
    super.println(MessageFormat.format(pattern, toString(items)));
  }

  public void printlnIndented(String line) {
    super.printlnIndented(line);
  }

  public void printlnIndented(String pattern, Object... items) {
    super.printlnIndented(MessageFormat.format(pattern, items));
  }

  private String[] toString(Object[] items) {
    String[] strs = new String[items.length];

    for (int i=0; i<items.length; i++) {
      strs[i] = toString(items[i]);
    }

    return strs;
  }

  private String toString(Object item) {
    boolean array = (item == null) ? false : item.getClass().isArray();
    return array ? arrayToString(item) : String.valueOf(item);
  }

  private String arrayToString(Object item) {
    if (item instanceof short[] array) {
      return Arrays.toString(array);
    } else if (item instanceof int[] array) {
      return Arrays.toString(array);
    } else if (item instanceof long[] array) {
      return Arrays.toString(array);
    } else if (item instanceof float[] array) {
      return Arrays.toString(array);
    } else if (item instanceof double[] array) {
      return Arrays.toString(array);
    } else if (item instanceof boolean[] array) {
      return Arrays.toString(array);
    } else if (item instanceof char[] array) {
      return Arrays.toString(array);
    } else {
       return item.toString();
    }
  }

}
