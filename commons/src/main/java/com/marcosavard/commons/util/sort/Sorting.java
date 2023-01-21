package com.marcosavard.commons.util.sort;

import java.util.List;

public interface Sorting<T extends Comparable> {
    List<T> sort(List<T> items);
}
