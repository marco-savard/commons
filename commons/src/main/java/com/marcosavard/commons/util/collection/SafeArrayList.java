package com.marcosavard.commons.util.collection;

import java.util.ArrayList;

public class SafeArrayList<T> extends ArrayList<T> {
    private T defValue;

    public SafeArrayList(T defValue) {
        this.defValue = defValue;
    }

    @Override
    public T get(int idx) {
        while (this.size() <= idx) {
            add(defValue);
        }

        T item = super.get(idx);
        return item;
    }
}
