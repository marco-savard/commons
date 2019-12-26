package com.marcosavard.commons.io;

import java.io.IOException;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class JsonWriter {
  private IndentWriter writer;
  private JsonWriterOptions options;
  private DateFormat dateFormat;

  JsonWriter(Writer w, JsonWriterOptions options) {
    this.writer = new IndentWriter(w, options.indentation);
    this.options = options;
    this.dateFormat = new SimpleDateFormat(options.dateFormat);
  }

  public void print(Object o) throws IOException {
    if (o instanceof Map) {
      printMap((Map) o);
    } else if (o instanceof List) {
      printList((List) o);
    } else if (o instanceof Date) {
      String text = (o == null) ? "null" : dateFormat.format((Date) o);
      writer.print(options.valueDelimiter + text + options.valueDelimiter);
    } else {
      String s = (o == null) ? "null" : o.toString();
      writer.print(options.valueDelimiter + s + options.valueDelimiter);
    }
  }

  private void printMap(Map map) throws IOException {
    writer.print("{");

    if (options.indented) {
      writer.indent();
      writer.println();
    }

    Set keys = map.keySet();
    int i = 0, nb = keys.size();

    for (Object key : keys) {
      writer.print(options.keyDelimiter + key.toString() + options.keyDelimiter);
      writer.print(": ");
      Object value = map.get(key);
      print(value);

      if (++i < nb) {
        writer.print(", ");
      }

      if (options.indented) {
        writer.println();
      }
    }

    if (options.indented) {
      writer.unindent();
    }

    writer.print("}");
  }



  private void printList(List list) throws IOException {
    List nonEmptyList = new ArrayList();
    for (Object element : list) {
      if (element != null) {
        nonEmptyList.add(element);
      }
    }

    writer.print("[");
    int i = 0, nb = nonEmptyList.size();

    for (Object element : nonEmptyList) {
      print(element);
      if (++i < nb) {
        writer.print(", ");
      }
    }

    writer.print("]");
  }

  public void close() throws IOException {
    writer.close();
  }

  static class JsonWriterOptions {

    String keyDelimiter = "";
    String valueDelimiter = "";
    String dateFormat = "yyyy/MM/dd";
    boolean indented = false;
    public int indentation = 0;
  }
}
