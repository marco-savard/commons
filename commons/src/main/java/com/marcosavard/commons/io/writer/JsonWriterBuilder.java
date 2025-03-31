package com.marcosavard.commons.io.writer;

import java.io.StringWriter;

public class JsonWriterBuilder {

  private JsonWriter.JsonWriterOptions options = new JsonWriter.JsonWriterOptions();

  public JsonWriterBuilder withKeyDelimitor(String delimitor) {
    options.keyDelimiter = delimitor;
    return this;
  }

  public JsonWriterBuilder withValueDelimitor(String delimitor) {
    options.valueDelimiter = delimitor;
    return this;
  }

  public JsonWriterBuilder withDateFormat(String dateFormat) {
    options.dateFormat = dateFormat;
    return this;
  }

  public JsonWriterBuilder withIndentation() {
    return withIndentation(true);
  }

  public JsonWriterBuilder withIndentation(boolean indented) {
    return withIndentation(indented, 2);
  }

  public JsonWriterBuilder withIndentation(boolean indented, int indentation) {
    options.indented = indented;
    options.indentation = indentation;
    return this;
  }


  public JsonWriter build(StringWriter sw) {
    JsonWriter jsonWriter = new JsonWriter(sw, options);
    return jsonWriter;
  }



}
