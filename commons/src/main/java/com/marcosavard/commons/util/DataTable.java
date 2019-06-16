package com.marcosavard.commons.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public class DataTable {
  private enum JoinType {
    INNER, LEFT
  };

  private JoinType joinType;
  private final List<String[]> primary;
  private final List<String[]> secondary;

  public static DataTable of(List<String[]> rows) {
    return new DataTable(trim(rows), null, JoinType.INNER);
  }

  public DataTable innerJoin(DataTable rows) {
    return new DataTable(this.primary, trim(rows.getValues()), JoinType.INNER);
  }

  public DataTable leftJoin(DataTable rows) {
    return new DataTable(this.primary, trim(rows.getValues()), JoinType.LEFT);
  }

  public DataTable on(BiFunction<String[], String[], Boolean> function) {
    List<String[]> joins = new ArrayList<>();

    for (String[] p : primary) {
      boolean joined = false;

      for (String[] s : secondary) {
        if (function.apply(p, s)) {
          joins.add(merge(p, s));
          joined = true;
          break;
        }
      }

      if ((joinType == JoinType.LEFT) && !joined) {
        String[] s = new String[secondary.get(0).length];
        joins.add(merge(p, s));
      }
    }

    return DataTable.of(joins);
  }

  public DataTable select(Function<String[], String[]> function) {
    List<String[]> selected = new ArrayList<>();

    for (String[] line : primary) {
      selected.add(function.apply(line));
    }

    return DataTable.of(selected);
  }

  public List<String[]> getValues() {
    return primary;
  }

  public Iterable<String[]> iterable() {
    return primary;
  }

  //
  // private
  //

  private DataTable(List<String[]> primary, List<String[]> secondary, JoinType joinType) {
    this.primary = primary;
    this.secondary = secondary;
    this.joinType = joinType;
  }

  private static List<String[]> trim(List<String[]> rows) {
    List<String[]> trimmed = new ArrayList<>();

    for (String[] row : rows) {
      trimmed.add(trim(row));
    }

    return trimmed;
  }

  private static String[] trim(String[] row) {
    String[] trimmed = new String[row.length];

    for (int i = 0; i < row.length; i++) {
      trimmed[i] = (row[i] == null) ? null : row[i].trim();
    }

    return trimmed;
  }

  private String[] merge(String[] first, String[] second) {
    String[] merged = Arrays.copyOf(first, first.length + second.length);
    System.arraycopy(second, 0, merged, first.length, second.length);
    return merged;
  }


}
