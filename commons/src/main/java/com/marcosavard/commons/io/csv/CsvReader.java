package com.marcosavard.commons.io.csv;

import java.io.BufferedReader;
import java.io.IOException;
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
  private int nbHeaders;
  private char separator;
  private boolean hasNext = true;

  /**
   * Builds a CSV reader from a standard Reader instance.
   * 
   * @param reader the input
   * @param nbHeaders number of lines to skip
   * @param separator such as ; , or |
   */
  public CsvReader(Reader reader, int nbHeaders, char separator) {
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
  public List<String> readHeaderColumns() throws IOException {
    return readHeaders().get(0);
  }

  /**
   * Read headers, in the case there are several lines of headers
   * 
   * @return the list of columns
   * @throws IOException when I/O exception occurs
   */
  public List<List<String>> readHeaders() throws IOException {
    List<List<String>> headers = new ArrayList<>();

    for (int h = 0; h < nbHeaders; h++) {
      List<String> columns = readNext();
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
  public List<String> readNext() throws IOException {
    List<String> line = new ArrayList<>();

    do {
      line = readNotEmptyLine();
    } while (line.isEmpty() && hasNext);

    return line;
  }


  // private method
  private List<String> readNotEmptyLine() throws IOException {
    List<String> values = new ArrayList<>();
    StringBuilder sb = new StringBuilder();
    boolean inQuotes = false;

    String line = bf.readLine();

    if (line != null) {
      char[] chars = line.toCharArray();

      for (char ch : chars) {
        if (ch == '\"') {
          inQuotes = !inQuotes;
        } else if ((ch == separator) && !inQuotes) {
          values.add(sb.toString());
          sb.setLength(0);
        } else {
          sb.append(ch);
        }
      }

      if (!sb.toString().isEmpty()) {
        values.add(sb.toString());
      }

    } else {
      hasNext = false;
    }

    return values;
  }
}