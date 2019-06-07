package com.marcosavard.commons.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class NullSafe {
  private static final List<Object> EMPTY_COLLECTION =
      Collections.unmodifiableList(new ArrayList<Object>());

  private static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
  private static final char[] EMPTY_CHAR_ARRAY = new char[0];
  private static final double[] EMPTY_DOUBLE_ARRAY = new double[0];
  private static final int[] EMPTY_INT_ARRAY = new int[0];
  private static final long[] EMPTY_LONG_ARRAY = new long[0];
  private static final short[] EMPTY_SHORT_ARRAY = new short[0];

  private NullSafe() {}

  @SuppressWarnings("unchecked")
  public static final <T> Collection<T> nullSafe(Collection<T> collection) {
    return collection != null ? collection : (Collection<T>) EMPTY_COLLECTION;
  }

  @SuppressWarnings("unchecked")
  public static final <T> List<T> nullSafe(List<T> list) {
    return list != null ? list : (List<T>) EMPTY_COLLECTION;
  }

  @SuppressWarnings("unchecked")
  public static final <T extends Object> T[] nullSafe(T[] array, Class<T> type) {
    return array != null ? array : (T[]) Array.newInstance(type, 0);
  }

  public static final byte[] nullSafe(byte[] array) {
    return array != null ? array : EMPTY_BYTE_ARRAY;
  }

  public static final char[] nullSafe(char[] array) {
    return array != null ? array : EMPTY_CHAR_ARRAY;
  }

  public static final double[] nullSafe(double[] array) {
    return array != null ? array : EMPTY_DOUBLE_ARRAY;
  }

  public static final int[] nullSafe(int[] array) {
    return array != null ? array : EMPTY_INT_ARRAY;
  }

  public static final long[] nullSafe(long[] array) {
    return array != null ? array : EMPTY_LONG_ARRAY;
  }

  public static final short[] nullSafe(short[] array) {
    return array != null ? array : EMPTY_SHORT_ARRAY;
  }

  public static <T> boolean equals(T obj1, T obj2) {
    boolean equal = false;

    if ((obj1 == null) && (obj2 == null)) {
      equal = true;
    } else if ((obj1 == null) || (obj2 == null)) {
      equal = false;
    } else if (obj1 instanceof Comparable) {
      Comparable comparable = (Comparable) obj1;
      equal = comparable.compareTo(obj2) == 0;
    } else {
      equal = obj1.equals(obj2);
    }

    return equal;
  }

  public static <T extends Comparable<T>> int compareTo(T obj1, T obj2) {
    int comparison;

    if ((obj1 == null) && (obj2 == null)) {
      comparison = 0;
    } else if (obj1 == null) {
      comparison = -1;
    } else if (obj2 == null) {
      comparison = 1;
    } else {
      comparison = obj1.compareTo(obj2);
    }

    return comparison;
  }

  public static <T> List<T> varargsToList(T[] varargs) {
    List<T> list;

    if (varargs.length != 1 || varargs[0] != null) {
      list = Arrays.asList(varargs);
    } else {
      list = (List<T>) EMPTY_COLLECTION;
    }

    return list;
  }

}
