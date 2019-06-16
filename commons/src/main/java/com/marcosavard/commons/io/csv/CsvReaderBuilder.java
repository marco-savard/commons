package com.marcosavard.commons.io.csv;

import java.io.Reader;

public class CsvReaderBuilder {
  private final Reader reader;
  private char separator;
  private int nbHeader = 0;

  public CsvReaderBuilder(Reader reader) {
    this.reader = reader;
  }

  public CsvReaderBuilder withSeparator(char separator) {
    this.separator = separator;
    return this;
  }

  public CsvReader build() {
    CsvReader csvReader = new CsvReader(reader, nbHeader, separator);
    return csvReader;
  }

}
