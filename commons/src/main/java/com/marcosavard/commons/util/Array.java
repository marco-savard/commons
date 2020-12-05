package com.marcosavard.commons.util;

import java.util.ArrayList;
import java.util.List;

public class Array {
  private List<List<String>> rows = new ArrayList<List<String>>();

  public static Array of(int width, List<String> list) {
    Array array = new Array();
    int height = (int) Math.ceil(list.size() / width);

    for (int i = 0; i <= height; i++) {
      int start = i * width;
      int end = Math.min((i + 1) * width, list.size());
      List<String> row = list.subList(start, end);
      array.add(row);
    }

    return array;
  }

  private void add(List<String> row) {
    rows.add(row);
  }

  private Array() {}

  public List<List<String>> toList() {
    return rows;
  }


}
