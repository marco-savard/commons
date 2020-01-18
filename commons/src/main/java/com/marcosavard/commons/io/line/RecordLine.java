package com.marcosavard.commons.io.line;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public abstract class RecordLine {
  private static final double DEFAULT_DOUBLE_VALUE = 1.0;
  private static final int DEFAULT_INTEGER_VALUE = 1;
  private static final long DEFAULT_LONG_VALUE = 1L;
  private static final String DEFAULT_STRING_VALUE = " aB ";
  private static final LocalDate DEFAULT_LOCAL_DATE_VALUE = LocalDate.of(1970, 1, 1);
  private List<FieldProperty> properties = new ArrayList<>();

  protected double ofDouble(int beginIdx, int endIdx) {
    FieldProperty property = new FieldProperty(beginIdx, endIdx);
    properties.add(property);
    return DEFAULT_DOUBLE_VALUE;
  }

  protected int ofInteger(int beginIdx, int endIdx) {
    FieldProperty property = new FieldProperty(beginIdx, endIdx);
    properties.add(property);
    return DEFAULT_INTEGER_VALUE;
  }

  protected long ofLong(int beginIdx, int endIdx) {
    FieldProperty property = new FieldProperty(beginIdx, endIdx);
    properties.add(property);
    return DEFAULT_LONG_VALUE;
  }

  protected String ofString(int beginIdx, int endIdx) {
    FieldProperty property = new FieldProperty(beginIdx, endIdx);
    properties.add(property);
    return DEFAULT_STRING_VALUE;
  }

  protected Object ofType(int beginIdx, int endIdx, Class type) {
    FieldProperty property = new TypeFieldProperty(beginIdx, endIdx, type);
    properties.add(property);
    Object instance;

    try {
      Constructor constr = type.getConstructor(String.class);
      instance = constr.newInstance(DEFAULT_STRING_VALUE);
    } catch (InstantiationException | IllegalAccessException | NoSuchMethodException
        | SecurityException | IllegalArgumentException | InvocationTargetException e) {
      instance = null;
    }
    return instance;
  }

  protected LocalDate ofLocalDate(int beginIdx, int endIdx, String dateFormat) {
    FieldProperty property = new LocalDateFieldProperty(beginIdx, endIdx, dateFormat);
    properties.add(property);
    return DEFAULT_LOCAL_DATE_VALUE;
  }

  public void parseLine(String line) {
    Class claz = this.getClass();
    Field[] fields = claz.getDeclaredFields();

    for (int i = 0; i < properties.size(); i++) {
      FieldProperty property = properties.get(i);
      Field field = fields[i];
      parseField(line, field, property);
    }
  }

  private void parseField(String line, Field field, FieldProperty property) {
    try {
      int beginIdx = property.beginIdx;
      int endIdx = property.endIdx;
      String fieldValue = line.substring(beginIdx, endIdx);

      field.setAccessible(true);
      Object originalValue = field.get(this);

      if (originalValue instanceof Double) {
        parseDoubleField(fieldValue, field, (Double) originalValue);
      } else if (originalValue instanceof Integer) {
        parseIntegerField(fieldValue, field);
      } else if (originalValue instanceof Long) {
        parseLongField(fieldValue, field, (Long) originalValue);
      } else if (originalValue instanceof String) {
        parseStringField(fieldValue, field, (String) originalValue);
      } else if (originalValue instanceof LocalDate) {
        parseLocalDateField(fieldValue, field, (LocalDate) originalValue,
            (LocalDateFieldProperty) property);
      } else if (property instanceof TypeFieldProperty) {
        parseTypeField(fieldValue, field, (TypeFieldProperty) property);
      }
    } catch (IllegalArgumentException | IllegalAccessException | NoSuchMethodException
        | SecurityException | InstantiationException | InvocationTargetException e) {
      // ignore
    }
  }

  private void parseDoubleField(String fieldValue, Field field, double originalValue)
      throws IllegalArgumentException, IllegalAccessException {

    double value = Double.parseDouble(fieldValue);
    double ratio = originalValue;
    value *= ratio;
    field.set(this, value);
  }

  private void parseIntegerField(String fieldValue, Field field)
      throws IllegalArgumentException, IllegalAccessException {

    int value = Integer.parseInt(fieldValue);
    field.set(this, value);
  }

  private void parseLongField(String fieldValue, Field field, long originalValue)
      throws IllegalArgumentException, IllegalAccessException {

    long value = Long.parseLong(fieldValue);
    field.set(this, value);
  }

  private void parseStringField(String fieldValue, Field field, String originalValue)
      throws IllegalArgumentException, IllegalAccessException {

    String value = isTrimmed(originalValue) ? fieldValue.trim() : fieldValue;
    field.set(this, value);
  }

  private void parseLocalDateField(String fieldValue, Field field, LocalDate originalValue,
      LocalDateFieldProperty property) throws IllegalArgumentException, IllegalAccessException {
    LocalDate value = LocalDate.parse(fieldValue, property.dateTimeFormatter);
    field.set(this, value);
  }

  private void parseTypeField(String fieldValue, Field field, TypeFieldProperty property)
      throws NoSuchMethodException, SecurityException, InstantiationException,
      IllegalAccessException, IllegalArgumentException, InvocationTargetException {
    Constructor constr = property.claz.getConstructor(String.class);
    Object value = constr.newInstance(fieldValue);
    field.set(this, value);
  }

  private boolean isTrimmed(String originalValue) {
    boolean trimmed = originalValue.charAt(0) != ' ';
    return trimmed;
  }

  private static class FieldProperty {
    private int beginIdx, endIdx;

    public FieldProperty(int beginIdx, int endIdx) {
      this.beginIdx = beginIdx;
      this.endIdx = endIdx;
    }
  }

  private static class LocalDateFieldProperty extends FieldProperty {
    private DateTimeFormatter dateTimeFormatter;

    public LocalDateFieldProperty(int beginIdx, int endIdx, String pattern) {
      super(beginIdx, endIdx);
      dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
    }
  }

  private static class TypeFieldProperty extends FieldProperty {
    private Class claz;

    public TypeFieldProperty(int beginIdx, int endIdx, Class claz) {
      super(beginIdx, endIdx);
      this.claz = claz;
    }
  }

}
