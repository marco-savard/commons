package com.marcosavard.common.lang.safe;

import java.util.ArrayList;

public class SafeArrayList<T> extends ArrayList<T> {
  private T defValue;

  public SafeArrayList(T defValue) {
    this.defValue = defValue;
  }

  @Override
  public void add(int idx, T item) {
    if (idx >= 0) {
      ensureSize(idx + 1);
      super.add(idx, item);
    }
  }

  @Override
  public T get(int idx) {
    ensureSize(idx + 1);
    T item = super.get(idx);
    return item;
  }

  @Override
  public T set(int idx, T item) {
    ensureSize(idx + 1);
    return super.set(idx, item);
  }

  public void ensureSize(int size) {
    // Prevent excessive copying while we're adding
    super.ensureCapacity(size);

    while (super.size() < size) {
      super.add(defValue);
    }
  }
}
