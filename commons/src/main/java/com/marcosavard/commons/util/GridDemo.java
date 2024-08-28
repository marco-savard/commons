package com.marcosavard.commons.util;

import com.marcosavard.commons.debug.Console;
import com.marcosavard.commons.lang.StringUtil;
import com.marcosavard.commons.math.Range;
import com.marcosavard.commons.util.collection.ListUtil;

import java.util.List;

public class GridDemo {

  public static void main(String args[]) {
    List<Integer> days = Range.of(30).addTo(1).toList();
    List<String> items = ListUtil.toStrings(days);
    List<String> lines = Grid.of(7, items).toLines(StringUtil.Alignment.RIGHT);

    for (String line : lines) {
      Console.println(line);
    }
  }
}
