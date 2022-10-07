package com.marcosavard.commons.util;

import com.marcosavard.commons.debug.Console;

import java.util.Arrays;
import java.util.List;

public class ArrayUtilDemo {

  public static void main(String args[]) {
    int[] array = new int[] {1, 2, 3, 4, 5, 6, 7, 8};
    List<Integer> list = ArrayUtil.toList(array);
    int[] array2 = ArrayUtil.toArrayInt(list);

    Console.println("array = " + Arrays.toString(array));
    Console.println("list = " + list);
    Console.println("array2 = " + Arrays.toString(array2));

    int[] concat = ArrayUtil.concat(array, array);
    Console.println("concatenate = " + Arrays.toString(concat));

    int[] removed = ArrayUtil.removeAll(concat, new int[] {1, 2});
    Console.println("remove = " + Arrays.toString(removed));
  }
}
