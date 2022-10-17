package com.marcosavard.commons.util.collection;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SortedList<T> extends UniqueList<T> {
    private final Comparator<T> comparator;

    public SortedList(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    @Override
    public boolean add(T element) {
        boolean added = super.add(element);
        Collections.sort(this, comparator);
        return added;
    }
}
