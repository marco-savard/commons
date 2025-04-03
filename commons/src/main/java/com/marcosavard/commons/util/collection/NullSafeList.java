package com.marcosavard.commons.util.collection;

import org.apache.poi.xwpf.usermodel.XWPFStyle;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class NullSafeList<T> extends ArrayList<T> {

  public static <T> List<T> of(List<T> originalList) {
      return (originalList == null) ? new NullSafeList<>() : originalList;
   }

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



  @Override
  public boolean addAll(Collection<? extends T> elements) {
    boolean added = false;

    for (T element : elements) {
      added = added | add(element);
    }

    return added;
  }
}
