package com.marcosavard.commons.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArrayUtil {

  public static int[] concat(int[] first, int[]... rest) {
    int totalLength = length(first, rest);
    int[] result = Arrays.copyOf(first, totalLength);
    int offset = first.length;

    for (int[] array : rest) {
      System.arraycopy(array, 0, result, offset, array.length);
      offset += array.length;
    }
    return result;
  }

  public static <T> T[] concat(T[] first, T[]... rest) {
    int totalLength = length(first, rest);
    T[] result = Arrays.copyOf(first, totalLength);
    int offset = first.length;

    for (T[] array : rest) {
      System.arraycopy(array, 0, result, offset, array.length);
      offset += array.length;
    }
    return result;
  }

  public static int length(int[] first, int[]... rest) {
    int totalLength = first.length;
    for (int[] array : rest) {
      totalLength += array.length;
    }

    return totalLength;
  }

  public static <T> int length(T[] first, T[]... rest) {
    int totalLength = first.length;
    for (T[] array : rest) {
      totalLength += array.length;
    }

    return totalLength;
  }

  public static int[] removeAll(int[] array, int[] toRemoved) {
    List<Integer> sourceList = Arrays.stream(array).boxed().toList();
    List<Integer> targetList = new ArrayList<>();
    targetList.addAll(sourceList);
    targetList.removeAll(Arrays.stream(toRemoved).boxed().toList());
    int[] result = targetList.stream().mapToInt(i -> i).toArray();
    return result;
  }

  public static int[] toArrayInt(List<Integer> list) {
    return list.stream().mapToInt(i -> i).toArray();
  }

  public static String[] toArray(List<String> list) {
    return list.toArray(new String[0]);
  }

  public static List<Integer> toList(int[] array) {
    return Arrays.stream(array).boxed().toList();
  }

  public static String toString(int[] array) {
    return Arrays.toString(array);
  }
}
