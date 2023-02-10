package com.marcosavard.commons.util.collection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ListUtil {

  public static <T> void ensureSize(List<T> list, int size) {
    // Prevent excessive copying while we're adding
    if (list instanceof ArrayList) {
      ((ArrayList) list).ensureCapacity(size);
    }

    while (list.size() < size) {
      list.add(null);
    }
  }

  /**
   * Converts a list of arbitrary elements into [element1, element2.. ]
   *
   * @param list of arbitrary elements
   * @return string formatted as [element1, element2.. ]
   */
  public static <T> String toString(Collection<T> list) {
    return toString(list, "[", ", ", "]");
  }

  public static <T> String toString(
      Collection<T> list, String prefix, String separator, String suffix) {
    List<String> strings = toStrings(list);
    String joined = prefix + String.join(separator, strings) + suffix;
    return joined;
  }

  /**
   * Converts a list of arbitrary elements into a list of strings
   *
   * @param collection of arbitrary elements
   * @return list of strings
   */
  public static <T> List<String> toStrings(Collection<T> collection) {
    List<String> strings = new ArrayList<>();
    Collection<T> safeCollection =
        (collection != null) ? collection : (Collection<T>) Collections.EMPTY_LIST;

    for (Object element : safeCollection) {
      strings.add(element.toString());
    }

    return strings;
  }

  // Convert a varargs to a list of objects. Returns empty list if varargs is null.
  public static <T> List<T> toList(T[] varargs) {
    List<T> list;

    if (varargs.length != 1 || varargs[0] != null) {
      list = Arrays.asList(varargs);
    } else {
      list = new ArrayList<>();
    }

    return list;
  }
}
