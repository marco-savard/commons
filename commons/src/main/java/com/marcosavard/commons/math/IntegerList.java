package com.marcosavard.commons.math;

import java.util.Arrays;
import java.util.List;

public class IntegerList {

    public static List<Integer> cummulate(List<Integer> original) {
        Integer[] arr = original.toArray(Integer[]::new);
        Arrays.parallelPrefix(arr, Integer::sum);
        List<Integer> cummulative = Arrays.asList(arr);
        return cummulative;
    }
}
