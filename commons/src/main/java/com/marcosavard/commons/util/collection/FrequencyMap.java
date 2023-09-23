package com.marcosavard.commons.util.collection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class FrequencyMap<T> extends TreeValueMap<T, Integer> {

  public static <T> FrequencyMap of(List<T> items) {
    FrequencyMap<T> map = new FrequencyMap<T>();
    for (T item : items) {
      map.add(item);
    }

    return map;
  }

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

  public T getMostFrequent() {
    Set<T> keys = super.keySet();
    int higherFrequency = 0;
    T mostFrequent = null;

    for (T t : keys) {
      int frequency = super.get(t);
      if (frequency > higherFrequency) {
        mostFrequent = t;
        higherFrequency = frequency;
      }
    }

    return mostFrequent;
  }
}
