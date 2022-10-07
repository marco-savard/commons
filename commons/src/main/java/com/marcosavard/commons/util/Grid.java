package com.marcosavard.commons.util;

import com.marcosavard.commons.lang.StringUtil;
import com.marcosavard.commons.util.collection.SafeArrayList;

import java.util.ArrayList;
import java.util.List;

public class Grid<T> {
  private List<List<T>> cells = new ArrayList<List<T>>();

  private Grid(int width, List<T> items) {
    int height = (int) Math.ceil(items.size() / width);

    for (int i = 0; i <= height; i++) {
      int start = i * width;
      int end = Math.min((i + 1) * width, items.size());
      List<T> row = items.subList(start, end);
      cells.add(row);
    }
  }

  public static <T> Grid<T> of(int width, List<T> items) {
    return new Grid(width, items);
  }

  public List<List<T>> getCells() {
    return cells;
  }

  public List<String> toLines() {
    return nestedListToLines(cells, StringUtil.Alignment.LEFT);
  }

  public List<String> toLines(StringUtil.Alignment alignment) {
    return nestedListToLines(cells, alignment);
  }

  public static <T> List<String> arrayListToLines(List<String[]> list) {
    return arrayListToLines(list, StringUtil.Alignment.LEFT);
  }

  public static <T> List<String> arrayListToLines(
      List<String[]> list, StringUtil.Alignment alignment) {
    List<Integer> widths = findArrayLists(list);
    List<String> lines = new ArrayList<>();

    for (String[] array : list) {
      String line = "";

      for (int i = 0; i < array.length; i++) {
        line += StringUtil.padLeft(array[i], widths.get(i) + 1);
      }

      lines.add(line);
    }

    return lines;
  }

  public static <T> List<String> nestedListToLines(List<List<T>> cells) {
    return nestedListToLines(cells, StringUtil.Alignment.LEFT);
  }

  public static <T> List<String> nestedListToLines(
      List<List<T>> cells, StringUtil.Alignment alignment) {
    List<List<String>> rows = toStringList(cells);
    List<Integer> widths = findListWidths(rows);
    List<String> lines = new ArrayList<>();

    for (List<String> row : rows) {
      String line = "";

      for (int i = 0; i < row.size(); i++) {
        String cell = row.get(i);
        line += StringUtil.pad(cell, widths.get(i) + 1, alignment);
      }

      lines.add(line);
    }

    return lines;
  }

  public static List<Integer> findListWidths(List<List<String>> rows) {
    List<Integer> widths = new SafeArrayList<>(10);

    for (List<String> row : rows) {
      for (int i = 0; i < row.size(); i++) {
        String cell = row.get(i);
        int width = i < widths.size() ? widths.get(i) : 0;
        width = (cell.length() > width) ? cell.length() : width;
        widths.set(i, width);
      }
    }

    return widths;
  }

  public static List<Integer> findArrayLists(List<String[]> lines) {
    List<Integer> widths = new SafeArrayList<>(10);

    for (String[] line : lines) {
      for (int i = 0; i < line.length; i++) {
        String cell = line[i];
        int width = i < widths.size() ? widths.get(i) : 0;
        width = (cell.length() > width) ? cell.length() : width;
        widths.set(i, width);
      }
    }

    return widths;
  }

  private static <T> List<List<String>> toStringList(List<List<T>> cells) {
    List<List<String>> rows = new ArrayList<>();

    for (List<T> row : cells) {
      rows.add(toStrings(row));
    }

    return rows;
  }

  private static <T> List<String> toStrings(List<T> items) {
    List<String> row = new ArrayList<>();

    for (T item : items) {
      row.add(item.toString());
    }

    return row;
  }
}
