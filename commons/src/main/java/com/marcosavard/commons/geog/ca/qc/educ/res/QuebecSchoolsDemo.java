package com.marcosavard.commons.geog.ca.qc.educ.res;

import java.util.List;

public class QuebecSchoolsDemo {

  public static void main(String[] args) {
    // get data
    QuebecSchools quebecSchools = new QuebecSchools();
    quebecSchools.loadAll();
    List<String> columns = quebecSchools.getColumns();
    List<String[]> rows = quebecSchools.getRows();

    // display data
    List<String> displayColumns = columns.subList(0, Math.min(3, columns.size()));
    List<String[]> displayRows = rows.subList(0, Math.min(8, rows.size()));

    for (String col : displayColumns) {
      System.out.print(col + "|");
    }
    System.out.println();


    for (String[] row : displayRows) {
      for (int i = 0; i < displayColumns.size(); i++) {
        String value = row[i];
        System.out.print(value + "|");
      }

      System.out.println();
    }

  }

}
