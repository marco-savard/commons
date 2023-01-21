package com.marcosavard.commons.util.sort;

import java.util.Arrays;
import java.util.List;

public class SortingDemo {

    public static void main(String args[]) {
        List<Integer> numbers = Arrays.asList(12, 45, 97, 43, 7, 2, 67);
        Sorting<Integer> sorting = new SimpleSorting<>();
        List<Integer> sorted = sorting.sort(numbers);
        System.out.println(sorted);
    }
}
