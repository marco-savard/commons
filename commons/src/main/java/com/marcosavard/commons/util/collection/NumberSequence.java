package com.marcosavard.commons.util.collection;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class NumberSequence {
    private List<Integer> list;

    public static NumberSequence of(List<List<Integer>> nested) {
        List<Integer> list = flatten(nested);
        return new NumberSequence(list);
    }

    private NumberSequence(List<Integer> list) {
        this.list = list;
    }


    public List<Integer> toList() {
        return list;
    }

    public static <T> List<T> flatten(List<List<T>> list) {
        return list.stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    public int sum() {
        int sum = list.stream().mapToInt(Integer::intValue).sum();
        return sum;
    }

    public double average() {
        double average = list.stream().mapToInt(Integer::intValue).average().orElse(0.0);
        return average;
    }
}
