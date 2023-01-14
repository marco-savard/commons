package com.marcosavard.commons.util.collection;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TreeValueMap<K, V extends Comparable> extends LinkedHashMap<K, V> {
  @Override
  public Set<K> keySet() {
    Set<K> keys = super.keySet();
    List<K> list = new ArrayList<>(keys);

    Comparator<K> comparator = new ValueComparator(this);
    list.sort(comparator);

    Set<K> targetSet = new LinkedHashSet<>(list);
    return targetSet;
  }

  protected class ValueComparator implements Comparator<K> {
    private Map<K, V> map;

    public ValueComparator(Map<K, V> map) {
      this.map = map;
    }

    @Override
    public int compare(K key1, K key2) {
      V value1 = map.get(key1);
      V value2 = map.get(key2);
      int comparison = value1.compareTo(value2);
      return comparison;
    }
  }

  @Override
  public String toString() {
    List<String> elements = new ArrayList<>();

    for (K key : keySet()) {
      String element = key + "=" + this.get(key);
      elements.add(element);
    }

    String s = String.join(", ", elements);
    return s;
  }
}
