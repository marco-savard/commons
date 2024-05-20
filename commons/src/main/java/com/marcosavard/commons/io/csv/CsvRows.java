package com.marcosavard.commons.io.csv;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class CsvRows<T> {
  private String filename;
  private Charset charset;
  private int nbHeaders = 1;
  private char delimiter = ';';
  private char commentCharacter = '#';
  private List<String> columns;
  private List<T> rows;

  protected CsvRows(String filename, Charset charset) {
    this.filename = filename;
    this.charset = charset;
  }

  protected void withNbHeaders(int nbHeaders) {
    this.nbHeaders = nbHeaders;
  }

  protected void withDelimiter(char delimiter) {
    this.delimiter = delimiter;
  }

  protected void withCommentCharacter(char commentCharacter) {
    this.commentCharacter = commentCharacter;
  }

  public List<T> getRows() {
    if (rows == null) {
      rows = loadAll();
    }

    return rows;
  }

  private List<T> loadAll() {
    Class claz = getClass();
    InputStream input = claz.getResourceAsStream(filename);
    List<String[]> lines = null;

    try {
      Reader r = new InputStreamReader(input, charset.name());
      CsvReader cr =
          CsvReader.of(r) //
              .withHeader(nbHeaders, delimiter) //
              .withSeparator(delimiter) //
              .withCommentCharacter(commentCharacter);
      columns = Arrays.asList(cr.readHeaderColumns());
      lines = cr.readAll();
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

    Class rowClass = getRowClass();
    List<T> rows = new ArrayList<>();
    for (String[] line : lines) {
      T row = createRow(rowClass, line);
      rows.add(row);
    }
    return rows;
  }

  private Class getRowClass() {
    Class compositeClaz = getClass();
    Type superclass = compositeClaz.getGenericSuperclass();
    ParameterizedType ptype = (ParameterizedType) superclass;
    Type[] types = ptype.getActualTypeArguments();
    Class type = (Class) types[0];
    return type;
  }

  protected T createRow(Class rowClass, String[] line) {
    T row;

    try {
      row = (T) rowClass.newInstance();
      setValues(rowClass, row, line);
    } catch (InstantiationException | IllegalAccessException e) {
      row = null;
    }

    return row;
  }

  private void setValues(Class claz, T row, String[] line) {
    Field[] fields = claz.getDeclaredFields();

    if (line.length < fields.length) {
      throw new RuntimeException("" + row);
    }

    for (int i = 0; i < fields.length; i++) {
      Field field = fields[i];

      try {
        Class type = field.getType();
        Object value = readValue(line[i], type);
        field.set(row, value);

      } catch (IllegalArgumentException | IllegalAccessException e) {
        e.printStackTrace();
      }
    }
  }

  private Object readValue(String raw, Class type) {
    Object value;

    if (double.class.equals(type)) {
      value = Double.valueOf(raw);
    } else if (int.class.equals(type)) {
      value = Integer.valueOf(raw);
    } else {
      value = raw;
    }

    return value;
  }

  public abstract static class CvsRow {

    @Override
    public String toString() {
      Field[] fields = getClass().getDeclaredFields();
      int nb = fields.length;
      String str = "";

      try {
        for (int i = 0; i < nb; i++) {
          Field field = fields[i];
          str += field.get(this).toString();
          str += (i < nb - 1) ? ";" : "";
        }

      } catch (IllegalArgumentException | IllegalAccessException e) {
        str = "?";
      }
      return str;
    }
  }
}
