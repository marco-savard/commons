package com.marcosavard.commons.io.writer;

import java.io.PrintWriter;
import java.util.List;
import java.util.Locale;

public class TableWriterDemo {

  public static void main(String[] args) {
    String[] str = Locale.getISOCountries();

    PrintWriter pw = new PrintWriter(System.out);
    TableWriter<String> tw = new TableWriter<>();

    List list;

    // TreeWriter<String> tw = new TreeWriter<>(pw);
    // tw.write(packageNames);
  }
}
