package com.marcosavard.common.lang;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

public class NullSafe {
  private static final String NULL_STRING = String.valueOf((String) null);
  private static final Object NULL_SAFE_OBJECT = new Object();

  private static final Object[] EMPTY_OBJECT_ARRAY = new Object[] {};

  private static final boolean[] EMPTY_BOOLEAN_ARRAY = new boolean[0];
  private static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
  private static final char[] EMPTY_CHAR_ARRAY = new char[0];
  private static final float[] EMPTY_FLOAT_ARRAY = new float[0];

  private static final double[] EMPTY_DOUBLE_ARRAY = new double[0];

  private static final int[] EMPTY_INT_ARRAY = new int[0];
  private static final long[] EMPTY_LONG_ARRAY = new long[0];
  private static final short[] EMPTY_SHORT_ARRAY = new short[0];

  private static final NullValue[] NULL_VALUES =
      new NullValue[] {
        NullValue.of(Boolean.class, Boolean.FALSE),
        NullValue.of(Byte.class, (byte) 0),
        NullValue.of(Character.class, '\0'),
        NullValue.of(CharSequence.class, NULL_STRING),
        NullValue.of(Double.class, Double.NaN),
        NullValue.of(Float.class, Float.NaN),
        NullValue.of(Integer.class, 0),
        NullValue.of(Long.class, 0L),
        NullValue.of(Short.class, (short) 0),
      };

  private NullSafe() {}

  @SuppressWarnings("unchecked")
  public static <T> Collection<T> of(Collection<T> collection) {
    return (collection != null) ? collection : (Collection<T>) Collections.EMPTY_LIST;
  }

  @SuppressWarnings("unchecked")
  public static <K, V> Map<K, V> of(Map<K, V> map) {
    return (map != null) ? map : (Map<K, V>) Collections.EMPTY_MAP;
  }

  public static CharSequence of(CharSequence value) {
    return (value == null) ? "" : value;
  }

  public static Number of(Number value) {
    return (value != null) ? value : BigInteger.ZERO.doubleValue();
  }

  public static Boolean of(Boolean value) {
    return (value != null) ? value : Boolean.FALSE;
  }

  public static Character of(Character value) {
    return (value != null) ? value : Character.MIN_VALUE;
  }

  public static Object[] of(Object[] array) {
    return (array != null) ? array : EMPTY_OBJECT_ARRAY;
  }

  public static final boolean[] of(boolean[] array) {
    return (array != null) ? array : EMPTY_BOOLEAN_ARRAY;
  }

  public static final byte[] of(byte[] array) {
    return (array != null) ? array : EMPTY_BYTE_ARRAY;
  }

  public static final char[] of(char[] array) {
    return (array != null) ? array : EMPTY_CHAR_ARRAY;
  }

  public static final float[] of(float[] array) {
    return (array != null) ? array : EMPTY_FLOAT_ARRAY;
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

  /*


    @SuppressWarnings("unchecked")
    public static final <T> List<T> of(List<T> list, Class<T> type) {
      return (list != null) ? nullsafe(list, type) : (List<T>) Collections.EMPTY_LIST;
    }

    public static <T> T of(T obj, Class type) {
        return (obj == null) ? (T)createObject(type) : obj;
      }
  */

  // return first not-null value
  public static <T> T coalesce(T... values) {
    return Stream.of(values).filter(Objects::nonNull).findFirst().orElse(null);
  }

  private static <T> List<T> nullsafe(List<T> list, Class<T> type) {
    return list.contains(null) ? buildNullSafeList(list, type) : list;
  }

  private static <T> List<T> buildNullSafeList(List<T> list, Class<T> type) {
    List<T> nullSafeList = new ArrayList<>();

    for (T item : list) {
      T safeItem = (item == null) ? createObject(type) : item;
      nullSafeList.add(safeItem);
    }

    return nullSafeList;
  }

  @SuppressWarnings("unchecked")
  public static final <T extends Object> T[] of(T[] array, Class<T> type) {
    return (array != null) ? array : (T[]) Array.newInstance(type, 0);
  }

  // an equals() method that is both robust and efficient
  @SuppressWarnings("unchecked")
  public static <T> boolean equals(T obj1, T obj2) {
    boolean equal;

    if ((obj1 == null) && (obj2 == null)) {
      equal = true; // by definition
    } else if ((obj1 == null) || (obj2 == null)) {
      equal = false;
    } else if (obj1 instanceof Comparable) {
      Comparable<T> comparable = (Comparable<T>) obj1;
      equal = comparable.compareTo(obj2) == 0;
    } else if (obj1.hashCode() != obj2.hashCode()) {
      equal = false;
    } else {
      equal = obj1.equals(obj2);
    }

    return equal;
  }

  public static <T> int hashCode(T obj) {
    int hashCode = (obj == null) ? getNull(Object.class).hashCode() : obj.hashCode();
    return hashCode;
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

  private static <T> T createObject(Class<T> type) {
    T o = null;
    try {
      o = type.getDeclaredConstructor().newInstance();
    } catch (InstantiationException
        | IllegalAccessException
        | InvocationTargetException
        | NoSuchMethodException e) {
      o = (T) getNull(type);
    }
    return o;
  }

  private static Object getNull(Class<?> type) {
    List<NullValue> nullValues = Arrays.asList(NULL_VALUES);
    NullValue nullValue =
        nullValues.stream().filter(n -> n.type.isAssignableFrom(type)).findFirst().orElse(null);
    return (nullValue == null) ? NULL_SAFE_OBJECT : nullValue.nullValue;
  }

  private static class NullValue<T> {
    private Class type;
    private Object nullValue;

    public static <T> NullValue of(Class<T> type, T nullValue) {
      return new NullValue(type, nullValue);
    }

    private NullValue(Class<T> type, T nullValue) {
      this.type = type;
      this.nullValue = nullValue;
    }
  }
}
