package com.marcosavard.commons.io.csv;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 * This class reads a CSV input (a file usually) and returns a list of values.
 * 
 * @author Marco
 *
 */
public class CsvReader {
  private BufferedReader bf;
  private int nbHeaders = 1;
  private char headerSeparator = ';', separator = ';';
  private String commentCharacter = "#";
  private boolean hasNext = true;

  public static CsvReader of(Class<?> claz, String filename) {
    InputStream input = claz.getResourceAsStream(filename);
    CsvReader csvReader = of(input);
    return csvReader;
  }

  public static CsvReader of(InputStream input) {
    Reader reader = new InputStreamReader(input);
    CsvReader csvReader = of(reader);
    return csvReader;
  }

  public static CsvReader of(Reader reader) {
    CsvReader csvReader = new CsvReader(reader, 0, ';');
    return csvReader;
  }

  public CsvReader withHeader(int nbHeaders, char headerSeparator) {
    this.nbHeaders = nbHeaders;
    this.headerSeparator = headerSeparator;
    return this;
  }

  public CsvReader withSeparator(char separator) {
    this.separator = separator;
    return this;
  }

  public CsvReader withCommentCharacter(char commentCharacter) {
    this.commentCharacter = Character.toString(commentCharacter);
    return this;
  }

  /**
   * Builds a CSV reader from a standard Reader instance.
   * 
   * @param reader the input
   * @param nbHeaders number of lines to skip
   * @param separator such as ; , or |
   */
  private CsvReader(Reader reader, int nbHeaders, char separator) {
    this.bf = new BufferedReader(reader);
    this.separator = separator;
    this.nbHeaders = nbHeaders;
  }

  /**
   * Read header columns, if the case where there is only one line of header
   * 
   * @return the list of columns
   * @throws IOException when I/O exception occurs
   */
  public String[] readHeaderColumns() throws IOException {
    List<String[]> headers = readHeaders();
    String[] header = headers.size() == 0 ? new String[] {} : headers.get(0);
    return header;
  }

  /**
   * Read headers, in the case there are several lines of headers
   * 
   * @return the list of columns
   * @throws IOException when I/O exception occurs
   */
  public List<String[]> readHeaders() throws IOException {
    List<String[]> headers = new ArrayList<>();

    for (int h = 0; h < nbHeaders; h++) {
      String[] columns = readNext(this.headerSeparator);
      headers.add(columns);
    }

    return headers;
  }

  /**
   * Tells if CSV has next rows to read.
   * 
   * @return true if CSV has more rows to read.
   * 
   */
  public boolean hasNext() {
    return hasNext;
  }

  /**
   * Read a line in the CSV input.
   * 
   * @return a list of cell values.
   * 
   * @throws IOException when I/O exception occurs
   */
  public String[] readNext() throws IOException {
    return readNext(this.separator);
  }

  public String[] readNext(char separator) throws IOException {
    String[] line;

    do {
      line = readNotEmptyLine(separator);
    } while (line.length == 0 && hasNext);

    return line;
  }

  public List<String[]> readAll() throws IOException {
    List<String[]> lines = new ArrayList<>();

    do {
      String[] line = readNext(this.separator);
      if (line.length > 0) {
        lines.add(line);
      }
    } while (hasNext());

    return lines;
  }


  // private method
  private String[] readNotEmptyLine(char separator) throws IOException {
    List<String> values = new ArrayList<>();
    StringBuilder sb = new StringBuilder();
    boolean inQuotes = false;

    String line = bf.readLine();

    if (line != null) {
      boolean comment = line.startsWith(commentCharacter);

      if (!comment) {
        char[] chars = line.toCharArray();

        for (char ch : chars) {
          if (ch == '\"') {
            inQuotes = !inQuotes;
          } else if ((ch == separator) && !inQuotes) {
            values.add(sb.toString().trim());
            sb.setLength(0);
          } else {
            sb.append(ch);
          }
        }

        if (!sb.toString().isEmpty()) {
          values.add(sb.toString().trim());
        }
      }
    } else {
      hasNext = false;
    }

    String[] row = new String[values.size()];
    row = values.toArray(row);
    return row;
  }

  public void close() throws IOException {
    bf.close();
  }



}
