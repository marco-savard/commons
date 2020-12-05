package com.marcosavard.commons.math;

import java.util.ArrayList;
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

  public static List<String> format(List<Integer> list, String format) {
    List<String> formatted = new ArrayList<>();

    for (int i : list) {
      formatted.add(String.format(format, i));
    }

    return formatted;
  }

  public List<String> toStrings() {
    List<String> strings = list.stream().map(Object::toString).collect(Collectors.toList());
    return strings;
  }

  public Iota addTo(int term) {
    List<Integer> multiplied = new ArrayList<>();

    for (int element : list) {
      multiplied.add(element + term);
    }

    return new Iota(multiplied);
  }

  public Iota multiplyBy(int factor) {
    List<Integer> multiplied = new ArrayList<>();

    for (int element : list) {
      multiplied.add(element * factor);
    }

    return new Iota(multiplied);
  }

  public List<String> format(String format) {
    List<String> formatted = new ArrayList<>();

    for (int i : list) {
      formatted.add(String.format(format, i));
    }

    return formatted;
  }



}
