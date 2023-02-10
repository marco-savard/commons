package com.marcosavard.commons.util.collection;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class SortedList<T> extends UniqueList<T> {
  private final Comparator<T> comparator;

  public SortedList() {
    this.comparator = (Comparator<T>) Comparator.naturalOrder();
  }

  public SortedList(Comparator<T> comparator) {
    this.comparator = comparator;
  }

  @Override
  public boolean add(T element) {
    boolean added = super.add(element);
    Collections.sort(this, comparator);
    return added;
  }

  @Override
  public boolean addAll(Collection<? extends T> collection) {
    boolean added = super.addAll(collection);
    Collections.sort(this, comparator);
    return added;
  }

  @Override
  public boolean remove(Object o) {
    boolean removed = super.remove(o);
    Collections.sort(this, comparator);
    return removed;
  }

  @Override
  public boolean removeAll(Collection<?> collection) {
    boolean removed = super.removeAll(collection);
    Collections.sort(this, comparator);
    return removed;
  }
}
