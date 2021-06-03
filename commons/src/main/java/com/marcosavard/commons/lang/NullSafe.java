package com.marcosavard.commons.lang;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

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
	  CharSequence nullSafe = (original == null) ? (String)getNull(String.class) : original;
	  return nullSafe;
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
  
  public static Object of(Object obj) {
	  obj = (obj == null) ? NULL_SAFE_OBJECT: obj;
	  return obj;
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

 
  private static Object getNull(Class<?> claz) {
	  Object obj; 
	  
	  if (claz.equals(Byte.class)) { 
		  obj = (byte)0;
	  } else if (claz.equals(Character.class)) {
		  obj = '\0'; 
	  } else if (claz.equals(CharSequence.class)) {
		  obj = "";
	  } else if (claz.equals(Double.class)) {
		  obj = 0.0;
	  } else if (claz.equals(Float.class)) {
		  obj = 0.0f;
	  } else if (claz.equals(Integer.class)) {
		  obj = 0;
	  } else if (claz.equals(Long.class)) {
		  obj = 0L;
	  } else if (claz.equals(Short.class)) {
		  obj = (short)0;
	  } else if (claz.equals(String.class)) {
		  obj = NULL_STRING;
	  } else {
		  obj = NULL_SAFE_OBJECT; 
	  }
	  
	  return obj;
  }



}
