package com.marcosavard.commons.lang.reflect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TypeFinder {
  private static final Class[] WRAPPER_TYPES =
      new Class[] {Byte.class, Short.class, Integer.class, Long.class, Float.class, Double.class};
  private List<DateFormat> dateFormats = new ArrayList<>();

  public TypeFinder() {
    dateFormats.add(new SimpleDateFormat());
  }

  public void addDateFormat(String pattern) {
    dateFormats.add(new SimpleDateFormat(pattern));
  }

  public Object findTypeOf(String textValue) {
    Object value = typeOfWrapper(textValue);

    if ((value == null) && (textValue.length() == 1)) {
      value = new Character(textValue.charAt(0));
    }

    if ((value == null) && ("true".equalsIgnoreCase(textValue))) {
      value = Boolean.TRUE;
    }

    if ((value == null) && ("false".equalsIgnoreCase(textValue))) {
      value = Boolean.FALSE;
    }

    if (value == null) {
      value = typeOfDate(textValue);
    }

    if (value == null) {
      value = textValue;
    }

    return value;
  }

  private Date typeOfDate(String textValue) {
    Date date = null;

    for (DateFormat format : dateFormats) {
      try {
        date = format.parse(textValue);

        if (date != null) {
          break;
        }
      } catch (ParseException e) {
        date = null;
      }
    }

    return date;
  }


  private Object typeOfWrapper(String textValue) {
    Object foundValue = null;

    for (Class type : WRAPPER_TYPES) {
      foundValue = valueOf(textValue, type);

      if (foundValue != null) {
        break;
      }
    }

    return foundValue;
  }

  private Object valueOf(String textValue, Class type) {
    Object value;

    try {
      Method method = type.getDeclaredMethod("valueOf", String.class);
      value = (method == null) ? null : method.invoke(null, textValue);
    } catch (NoSuchMethodException e) {
      value = null;
    } catch (SecurityException e) {
      value = null;
    } catch (IllegalAccessException e) {
      value = null;
    } catch (IllegalArgumentException e) {
      value = null;
    } catch (InvocationTargetException e) {
      value = null;
    }

    if (value instanceof Float) {
      value = value.equals(Float.POSITIVE_INFINITY) ? null : value;
    }

    if (value instanceof Float) {
      value = value.equals(Float.NEGATIVE_INFINITY) ? null : value;
    }

    if (value instanceof Double) {
      value = value.equals(Double.POSITIVE_INFINITY) ? null : value;
    }

    if (value instanceof Double) {
      value = value.equals(Double.NEGATIVE_INFINITY) ? null : value;
    }

    return value;
  }


}
