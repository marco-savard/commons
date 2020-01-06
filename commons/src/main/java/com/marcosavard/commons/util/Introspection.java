package com.marcosavard.commons.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.TreeMap;

public class Introspection {
  public static String toString(Object object) {
    Field[] fields = object.getClass().getDeclaredFields();
    Map<String, String> valuesByField = new TreeMap<>();

    for (Field f : fields) {
      boolean isTransient = Modifier.isTransient(f.getModifiers());

      if (!isTransient) {
        try {
          f.setAccessible(true);
          String key = f.getName();
          String value = (f.get(object) == null) ? null : f.get(object).toString();
          valuesByField.put(key, value);
        } catch (IllegalArgumentException | IllegalAccessException e) {
          // skip
        }
      }
    }

    String s = valuesByField.toString();
    return s;
  }
}
