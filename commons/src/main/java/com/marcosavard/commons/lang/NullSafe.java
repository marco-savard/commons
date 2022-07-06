package com.marcosavard.commons.lang;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class NullSafe {
  private static final String NULL_STRING = String.valueOf((String)null);
  private static final List<Object> EMPTY_LIST =
      Collections.unmodifiableList(new ArrayList<Object>());
  private static final Object NULL_SAFE_OBJECT = new Object();

  private static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
  private static final char[] EMPTY_CHAR_ARRAY = new char[0];
  private static final double[] EMPTY_DOUBLE_ARRAY = new double[0];
  private static final int[] EMPTY_INT_ARRAY = new int[0];
  private static final long[] EMPTY_LONG_ARRAY = new long[0];
  private static final short[] EMPTY_SHORT_ARRAY = new short[0];

  private static final NullValue[] NULL_VALUES = new NullValue[] {
          NullValue.of(Boolean.class, Boolean.FALSE),
          NullValue.of(Byte.class, (byte)0),
          NullValue.of(Character.class, '\0'),
          NullValue.of(CharSequence.class, NULL_STRING),
          NullValue.of(Double.class, Double.NaN),
          NullValue.of(Float.class, Float.NaN),
          NullValue.of(Integer.class, 0),
          NullValue.of(Long.class, 0L),
          NullValue.of(Short.class, (short)0),
  };
  
  private NullSafe() {}

  public static Byte of(Byte b) {
	  b = (b == null) ? (byte)0 : b;
	  return b;
  }
  
  public static Character of(Character c) {
	  c = (c == null) ? '\0' : c;
	  return c;
  }
  
  public static CharSequence of(CharSequence original) {
      return (original == null) ? (String)getNull(String.class) : original;
  }
  
  public static Double of(Double d) {
	  d = (d == null) ? (Double)getNull(Double.class) : d;
	  return d;
  }
  
  public static Float of(Float f) {
	  f = (f == null) ? (Float)getNull(Float.class) : f;
	  return f;
  }
  
  public static Integer of(Integer i) {
	  i = (i == null) ? (Integer)getNull(Integer.class) : i;
	  return i;
  }
  
  public static Short of(Short s) {
	  s = (s == null) ? (Short)getNull(Short.class) : s;
	  return s;
  }
  
  public static Long of(Long l) {
	  l = (l == null) ? (Long)getNull(Long.class) : l;
	  return l;
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
  public static final <T> List<T> of(List<T> list, Class<T> type) {
    return (list != null) ? nullsafe(list, type) : (List<T>) EMPTY_LIST;
  }

  public static <T> T of(T obj, Class type) {
      return (obj == null) ? (T)createObject(type) : obj;
    }

  public static String coalesce(String givenString, String defaultValue) {
    return (givenString == null) ? defaultValue : givenString;
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
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            o = (T)getNull(type);
        }
        return o;
    }


    private static Object getNull(Class<?> type) {
       List<NullValue> nullValues = Arrays.asList(NULL_VALUES);
       NullValue nullValue = nullValues.stream().filter(n -> n.type.isAssignableFrom(type)).findFirst().orElse(null);
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
