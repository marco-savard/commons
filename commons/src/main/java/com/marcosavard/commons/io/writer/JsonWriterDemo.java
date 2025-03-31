package com.marcosavard.commons.io.writer;

import com.marcosavard.commons.util.collection.NestedMap;

import java.io.IOException;
import java.io.StringWriter;

public class JsonWriterDemo {

  public static void main(String[] args) {
    try {
      // build a complex object
      NestedMap map = new NestedMap();
      map.put("Canada/Quebec/City[0]", "Quebec City");
      map.put("Canada/Quebec/City[1]", "Montreal");
      map.put("Canada/Ontario/City[0]", "Toronto");
      map.put("Canada/Ontario/City[1]", "Ottawa");

      // write in json
      JsonWriterBuilder builder = new JsonWriterBuilder();
      builder = builder.withIndentation();
      StringWriter sw = new StringWriter();
      JsonWriter jw = builder.build(sw);
      jw.print(map);
      jw.close();
      System.out.println(sw.toString());

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
