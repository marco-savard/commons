package com.marcosavard.commons.lang.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.LinkedHashMap;
import java.util.Map;

public class Introspection {
  public static String toString(Object object) {
    Field[] fields = object.getClass().getDeclaredFields();
    Map<String, String> valuesByField = new LinkedHashMap<>();

    for (Field f : fields) {
      boolean isStatic = Modifier.isStatic(f.getModifiers());
      boolean isTransient = Modifier.isTransient(f.getModifiers());
      boolean printable = (!isStatic) && (!isTransient);

      if (printable) {
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
