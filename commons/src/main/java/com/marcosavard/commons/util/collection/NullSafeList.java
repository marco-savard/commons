package com.marcosavard.commons.util.collection;

import java.util.ArrayList;

public class NullSafeList<T> extends ArrayList<T> {

  @Override
  public boolean add(T element) {
    boolean added = (element != null);

    if (added && element instanceof CharSequence) {
      CharSequence cs = (CharSequence)element;
      added = ! cs.isEmpty();
    }

    if (added) {
      super.add(element);
    }

    return added;
  }
}
