package com.marcosavard.commons.util.sort;

import org.apache.poi.ss.formula.functions.T;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SimpleSorting<T extends Comparable> implements Sorting<T> {

    @Override
    public List<T> sort(List<T> items) {
        List<T> sorted = new ArrayList<>();

        if (items.size() > 0) {
            T pivot = items.get(0);
            List<T> smallest = items.stream().filter(i -> i.compareTo(pivot) < 0).collect(Collectors.toList());
            List<T> highest = items.stream().filter(i -> i.compareTo(pivot) > 0).collect(Collectors.toList());
            sorted.addAll(sort(smallest));
            sorted.add(pivot);
            sorted.addAll(sort(highest));
        }

        return sorted;
    }
}
