package com.marcosavard.commons.io.csv;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A class that binds each row of a CSV input to an instance of T.
 * 
 * @author Marco
 *
 * @param <T>
 */
public class CsvBinder<T> {
  private CsvReader csvReader;
  private Class<T> claz;
  private Map<String, Integer> columnIndexByFieldName = new HashMap<>();

  public CsvBinder(Reader reader, char separator, Class<T> claz) {
    this.csvReader = CsvReader.of(reader).withHeader(1, separator).withSeparator(separator);
    this.claz = claz;
  }

  public List<T> readAll() throws IOException {
    List<T> rows = new ArrayList<>();
    readHeader();

    do {
      try {
        String[] values = csvReader.readNext();

        if (values.length > 0) {
          T row = readRow(values);
          rows.add(row);
        }
      } catch (InstantiationException | IllegalAccessException e) {
        throw new RuntimeException(e);
      }
    } while (csvReader.hasNext());

    return rows;
  }

  private void readHeader() throws IOException {
    String[] cols = csvReader.readHeaderColumns();

    for (int i = 0; i < cols.length; i++) {
      columnIndexByFieldName.put(cols[i], i);
    }
  }

  private T readRow(String[] values) throws InstantiationException, IllegalAccessException {
    T row = (T) claz.newInstance();

    for (Field f : claz.getDeclaredFields()) {
      String fieldName = f.getName();
      int idx = columnIndexByFieldName.get(fieldName);
      String value = values[idx];
      f.setAccessible(true);
      f.set(row, value);
    }

    return row;
  }

}
