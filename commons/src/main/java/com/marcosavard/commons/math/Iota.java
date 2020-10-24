package com.marcosavard.commons.math;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Iota {
  private List<Integer> list;

  // if length = 5 returns 1,2,3,4,5
  public static Iota of(int length) {
    List<Integer> list = IntStream.rangeClosed(1, length).boxed().collect(Collectors.toList());
    Iota iota = new Iota(list);
    return iota;
  }

  private Iota(List<Integer> list) {
    this.list = list;
  }

  public List<Integer> toIntegers() {
    return list;
  }

  public List<String> toStrings() {
    List<String> strings = list.stream().map(Object::toString).collect(Collectors.toList());
    return strings;
  }


}
