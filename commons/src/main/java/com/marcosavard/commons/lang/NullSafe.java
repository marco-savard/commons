package com.marcosavard.commons.lang;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class NullSafe {
	private static final String EMPTY = "";
  private static final List<Object> EMPTY_LIST =
      Collections.unmodifiableList(new ArrayList<Object>());

  private static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
  private static final char[] EMPTY_CHAR_ARRAY = new char[0];
  private static final double[] EMPTY_DOUBLE_ARRAY = new double[0];
  private static final int[] EMPTY_INT_ARRAY = new int[0];
  private static final long[] EMPTY_LONG_ARRAY = new long[0];
  private static final short[] EMPTY_SHORT_ARRAY = new short[0];
  
  private NullSafe() {}

  public static CharSequence of(CharSequence original) {
	  CharSequence nullSafe = (original == null) ? EMPTY : original;
	  return nullSafe;
  }
  
  public static final byte[] of(byte[] array) {
    return (array != null) ? array : EMPTY_BYTE_ARRAY;
  }

  public static final char[] of(char[] array) {
    return (array != null) ? array : EMPTY_CHAR_ARRAY;
  }

  public static final double[] of(double[] array) {
    return (array != null) ? array : EMPTY_DOUBLE_ARRAY;
  }

  public static final int[] of(int[] array) {
    return (array != null) ? array : EMPTY_INT_ARRAY;
  }

  public static final long[] of(long[] array) {
    return (array != null) ? array : EMPTY_LONG_ARRAY;
  }

  public static final short[] of(short[] array) {
    return (array != null) ? array : EMPTY_SHORT_ARRAY;
  }

  @SuppressWarnings("unchecked")
  public static final <T> Collection<T> of(Collection<T> collection) {
    return (collection != null) ? collection : (Collection<T>) EMPTY_LIST;
  }

  @SuppressWarnings("unchecked")
  public static final <T> List<T> of(List<T> list) {
    return (list != null) ? list : (List<T>) EMPTY_LIST;
  }

  @SuppressWarnings("unchecked")
  public static final <T extends Object> T[] of(T[] array, Class<T> type) {
    return (array != null) ? array : (T[]) Array.newInstance(type, 0);
  }


  // a equals() method that is both robust and efficient
  public static <T> boolean equals(T obj1, T obj2) {
    boolean equal;

    if ((obj1 == null) && (obj2 == null)) {
      equal = true; // by definition
    } else if ((obj1 == null) || (obj2 == null)) {
      equal = false;
    } else if (obj1 instanceof Comparable) {
      Comparable<T> comparable = (Comparable) obj1;
      equal = comparable.compareTo(obj2) == 0;
    } else if (obj1.hashCode() != obj2.hashCode()) {
      equal = false;
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

  public static <T> boolean indexSafe(Collection<T> collection, int idx) {
    boolean safe = (collection != null) && (idx >= 0) && (idx < collection.size());
    return safe;
  }

  public static <T> boolean indexSafe(List<T> list, int idx) {
    boolean safe = (list != null) && (idx >= 0) && (idx < list.size());
    return safe;
  }

  public static <T> boolean indexSafe(T[] array, int idx) {
    boolean safe = (array != null) && (idx >= 0) && (idx < array.length);
    return safe;
  }

  // verify if safe before calling String.charAt()
  public static <T> boolean indexSafe(String s, int idx) {
    boolean safe = (s != null) && (idx >= 0) && (idx < s.length());
    return safe;
  }

  // verify if safe before calling String.sublist()
  public static <T> boolean indexSafe(String s, int startIdx, int endIdx) {
    boolean safe = (s != null) && (startIdx >= 0) && (endIdx < s.length() && (startIdx < endIdx));
    return safe;
  }



}
