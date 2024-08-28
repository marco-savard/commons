package com.marcosavard.commons.math;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Table {
  private final Collection<List<String>> rows;

  public static Table of(List<String> list, int columns) {
    return new Table(list, columns);
  }

  private Table(List<String> list, int columns) {
    final AtomicInteger counter = new AtomicInteger(0);
    rows =
        list.stream()
            .collect(Collectors.groupingBy(s -> counter.getAndIncrement() / columns))
            .values();
  }

  public Collection<List<String>> getRows() {
    return rows;
  }
}
