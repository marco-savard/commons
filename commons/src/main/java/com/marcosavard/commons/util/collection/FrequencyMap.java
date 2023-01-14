package com.marcosavard.commons.util.collection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class FrequencyMap<T> extends TreeValueMap<T, Integer> {
  public void add(T key) {
    if (super.containsKey(key)) {
      super.put(key, super.get(key) + 1);
    } else {
      super.put(key, 1);
    }
  }

  @Override
  public Set<T> keySet() {
    Set<T> keys = super.keySet();
    List<T> list = new ArrayList<>(keys);

    Comparator<T> comparator = new ValueComparator(this);
    list.sort(comparator);
    Collections.reverse(list);

    Set<T> targetSet = new LinkedHashSet<>(list);
    return targetSet;
  }
}
