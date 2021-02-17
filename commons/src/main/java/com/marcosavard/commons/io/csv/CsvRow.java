package com.marcosavard.commons.io.csv;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class CsvRow {

  // inner class
  public static class CsvFile<T> {
    private Class resourceClass;
    private String filename;
    private Charset charset;
    private char commentCharacter;
    private int nbHeaders;
    private char delimiter;
    private List<String> columns;

    public static CsvFile of(Class resourceClass, String filename, Charset charset) {
      CsvFile csvFile = new CsvFile(resourceClass, filename, charset);
      return csvFile;
    }

    private CsvFile(Class resourceClass, String filename, Charset charset) {
      this.resourceClass = resourceClass;
      this.filename = filename;
      this.charset = charset;
    }

    public void withCommentCharacter(char ch) {
      this.commentCharacter = ch;
    }

    public void withNbHeaders(int nbHeaders) {
      this.nbHeaders = nbHeaders;
    }

    public void withDelimiter(char delimiter) {
      this.delimiter = delimiter;
    }

    public List<T> loadAll() {
      Class claz = resourceClass.getClass();
      InputStream input = claz.getResourceAsStream(filename);
      List<String[]> rows = null;

      try {
        Reader r = new InputStreamReader(input, charset.name());
        CsvReader cr = CsvReader.of(r).withHeader(nbHeaders, delimiter);
        columns = Arrays.asList(cr.readHeaderColumns());
        rows = cr.readAll();
        cr.close();

      } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      }

      List<T> values = new ArrayList<>();
      for (String[] row : rows) {
        System.out.println(row);
        // resourceClass.
        // CsvRow csvRow = new CsvRow();
      }

      return values;

    }

  }

}
