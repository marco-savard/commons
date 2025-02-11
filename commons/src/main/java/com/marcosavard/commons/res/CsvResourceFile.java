package com.marcosavard.commons.res;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public abstract class CsvResourceFile<T> {
  private static final Class[] PRIMITIVES =
      new Class[] {
        boolean.class,
        byte.class,
        char.class,
        short.class,
        int.class,
        long.class,
        float.class,
        double.class
      };
  private static final Class[] WRAPPERS =
      new Class[] {
        Boolean.class,
        Byte.class,
        Character.class,
        Short.class,
        Integer.class,
        Long.class,
        Float.class,
        Double.class
      };

  private String resourceName;
  private Charset charset;
  private Class<T> type;
  private CsvReader csvReader;
  private String[] header;
  private char separator = ';';
  private char headerSeparator = ';';
  private char quoteChar = '\"';
  private String commentPrefix = "#";
  private Map<Field, String> columnNameByField = new HashMap<>();

  protected CsvResourceFile(String resourceName, Charset charset, Class<T> type) {
    this.resourceName = resourceName;
    this.type = type;
    this.charset = charset;
  }

  public void withSeparator(char separator) {
    this.separator = separator;
  }

  public void withHeaderSeparator(char separator) {
    this.headerSeparator = separator;
  }

  public void withQuoteChar(char quoteChar) {
    this.quoteChar = quoteChar;
  }

  public void withCommentPrefix(String commentPrefix) {
    this.commentPrefix = commentPrefix;
  }

  public List<String> getColumnNames() {
    Field[] fields = type.getDeclaredFields();
    List<String> columnNames = new ArrayList<>();

    for (Field field : fields) {
      String columnName = columnNameByField.get(field);
      columnNames.add(columnName);
    }

    return columnNames;
  }

  public List<T> read(int nbLines) {
    List<T> entries = new ArrayList<>();
    CsvReader csvReader = getCsvReader();

    try {
      for (int i = 0; i < nbLines; i++) {
        String[] line = csvReader.readNext();
        T entry = createEntry(line);
        entries.add(entry);
      }
    } catch (IOException ex) {
      ex.printStackTrace();
    } catch (InstantiationException
        | IllegalAccessException
        | IllegalArgumentException
        | InvocationTargetException
        | NoSuchMethodException
        | SecurityException e) {
      e.printStackTrace();
    }

    return entries;
  }

  public List<T> readAll() {
    List<T> entries = new ArrayList<>();
    CsvReader csvReader = getCsvReader();

    try {
      List<String[]> lines = csvReader.readAll();

      for (String[] line : lines) {
        T entry = createEntry(line);
        entries.add(entry);
      }

    } catch (IOException ex) {
      ex.printStackTrace();
    } catch (InstantiationException
        | IllegalAccessException
        | IllegalArgumentException
        | InvocationTargetException
        | NoSuchMethodException
        | SecurityException e) {
      e.printStackTrace();
    }

    return entries;
  }

  private CsvReader getCsvReader() {
    if (csvReader == null) {
      CsvResourceFile<T> resourceFile = this;
      Class<? extends CsvResourceFile> claz = resourceFile.getClass();
      InputStream input = claz.getResourceAsStream(resourceName);

      try {
        Reader reader = new InputStreamReader(input, charset.name());
        CsvReaderImpl csvReaderImpl =
            new CsvReaderImpl(reader, separator, quoteChar, headerSeparator, commentPrefix);
        header = csvReaderImpl.getHeader();
        csvReader = csvReaderImpl;
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    return csvReader;
  }

  private T createEntry(String[] line)
      throws InstantiationException, IllegalAccessException, IllegalArgumentException,
          InvocationTargetException, NoSuchMethodException, SecurityException {
    T t = type.getDeclaredConstructor().newInstance();

    for (int i = 0; i < line.length; i++) {
      String fieldName = getFieldName(header[i]);

      try {
        Field declaredField = type.getDeclaredField(fieldName);
        setValue(t, declaredField, line[i]);
        columnNameByField.put(declaredField, header[i]);
      } catch (NoSuchFieldException ex) {
        // ignore field
      }
    }

    return t;
  }

  private String getFieldName(String columnName) {
    // remove apostrophe, blanks
    columnName = columnName.replaceAll("-", " ");
    columnName = columnName.replaceAll("'", " ");
    columnName = stripAccents(columnName);
    columnName = toTitleCase(columnName);
    columnName = columnName.replaceAll(" ", "");
    String fieldName = Character.toLowerCase(columnName.charAt(0)) + columnName.substring(1);
    return fieldName;
  }

  public String stripAccents(String original) {
    String stripped = Normalizer.normalize(original, Normalizer.Form.NFD);
    stripped = stripped.replaceAll("[^\\p{ASCII}]", "");
    return stripped;
  }

  public String toTitleCase(String word) {
    return Stream.of(word.split("\\s+"))
        .map(w -> w.toUpperCase().charAt(0) + w.toLowerCase().substring(1))
        .reduce((s, s2) -> s + " " + s2)
        .orElse("");
  }

  private void setValue(Object instance, Field declaredField, String string)
      throws IllegalArgumentException, IllegalAccessException {
    Class<?> type = declaredField.getType();
    type = isPrimitive(type) ? getWrapper(type) : type;
    String[] methodNames = new String[] {"decode", "valueOf"};
    Class[] parameterTypes = new Class[] {Object.class, String.class};
    Object value = null;

    // find value
    for (String methodName : methodNames) {
      for (Class parameterType : parameterTypes) {
        try {
          Method valueOf = type.getDeclaredMethod(methodName, parameterType);
          value = valueOf.invoke(instance, (Object) string);
          break;
        } catch (NoSuchMethodException e) {
          // not found, iterate
        } catch (SecurityException | InvocationTargetException e) {
          //  e.printStackTrace();
        }
      }

      if (value != null) {
        break;
      }
    }

    // set value
    if (value != null) {
      declaredField.setAccessible(true);
      declaredField.set(instance, value);
    }
  }

  private boolean isPrimitive(Class<?> type) {
    return Arrays.asList(PRIMITIVES).contains(type);
  }

  private Class<?> getWrapper(Class<?> type) {
    int idx = Arrays.asList(PRIMITIVES).indexOf(type);
    return WRAPPERS[idx];
  }

  public void close() {
    try {
      csvReader.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
