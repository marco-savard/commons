package com.marcosavard.commons.util.collection;

import com.marcosavard.commons.util.ListUtil;

import java.util.ArrayList;

public class SafeArrayList<T> extends ArrayList<T> {
  private T defValue;

  public SafeArrayList(T defValue) {
    this.defValue = defValue;
  }

  @Override
  public T get(int idx) {
    ListUtil.ensureSize(this, idx + 1);
    T item = super.get(idx);
    return item;
  }

  @Override
  public T set(int idx, T item) {
    ListUtil.ensureSize(this, idx + 1);
    return super.set(idx, item);
  }
}
