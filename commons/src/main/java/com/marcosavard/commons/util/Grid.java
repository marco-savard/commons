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
    List<List<String>> rows = toStrings();
    List<Integer> widths = findListWidths(rows);
    List<String> lines = new ArrayList<>();

    for (List<String> row : rows) {
      String line = "";

      for (int i = 0; i < row.size(); i++) {
        String cell = row.get(i);
        line += StringUtil.padRight(cell, widths.get(i)) + " ";
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

  private List<List<String>> toStrings() {
    List<List<String>> rows = new ArrayList<>();

    for (List<T> row : cells) {
      rows.add(toStrings(row));
    }

    return rows;
  }

  private List<String> toStrings(List<T> items) {
    List<String> row = new ArrayList<>();

    for (T item : items) {
      row.add(item.toString());
    }

    return row;
  }
}
