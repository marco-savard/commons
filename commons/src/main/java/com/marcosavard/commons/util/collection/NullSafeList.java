package com.marcosavard.commons.util.collection;

import java.util.ArrayList;

public class NullSafeList<T> extends ArrayList<T> {

  @Override
  public boolean add(T element) {
    boolean added = false;

    if (element != null) {
      super.add(element);
      added = true;
    }

    return added;
  }
}
