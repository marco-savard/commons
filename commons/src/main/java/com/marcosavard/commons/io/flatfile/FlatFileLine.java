package com.marcosavard.commons.io.flatfile;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public abstract class FlatFileLine {

  protected void init(String line) {
    Field[] fields = getClass().getDeclaredFields();

    for (Field f : fields) {
      boolean isStatic = Modifier.isStatic(f.getModifiers());
      boolean isTransient = Modifier.isTransient(f.getModifiers());
      boolean isPersistent = (!isStatic) && (!isTransient);

      if (isPersistent) {
        Class type = f.getType();
        boolean isFlatField = type.isAssignableFrom(FlatFileField.class);

        if (isFlatField) {
          try {
            FlatFileField flatField = (FlatFileField) f.get(this);
            int beginIndex = flatField.getBeginIndex();
            int endIndex = flatField.getEndIndex();
            String value = line.substring(beginIndex, endIndex);
            flatField.setValue(value);
          } catch (IllegalArgumentException | IllegalAccessException e) {
            // skip
          }
        }
      }
    }
  }

}
