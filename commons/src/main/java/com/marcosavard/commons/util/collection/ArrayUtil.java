package com.marcosavard.commons.util.collection;

import java.util.Arrays;

public class ArrayUtil {

    public static <T> boolean contains(T[] array, T item) {
        return Arrays.asList(array).contains(item);
    }

    public static <T> int indexOf(T[] array, T item) {
        return Arrays.asList(array).indexOf(item);
    }


}
