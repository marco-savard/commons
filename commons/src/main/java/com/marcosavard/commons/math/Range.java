package com.marcosavard.commons.math;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

public class Range implements Iterable<Integer> {

  public static final BinaryOperator<Integer> ADDITION = (x, y) -> x + y;
  public static final BinaryOperator<Integer> MULTIPLICATION = (x, y) -> x * y;
  private final RangeIterator iterator;
  private final List<Integer> integerList;

  public static Range of(int lower, int upper) {
    return new Range(lower, upper, 1, 0, (x) -> x, lower, (x, y) -> y);
  }

  public static Range of(int upper) {
    return new Range(0, upper, 1, 0, (x) -> x, 0, (x, y) -> y);
  }

  public static Range of(List<Integer> list) {
    return new Range(list);
  }

  public static Range of(Integer... items) {
    return new Range(Arrays.asList(items));
  }

  public static List<Integer> listOf(String str) {
    String replaced = str.replace("[", "").replace("]", "");
    replaced = replaced.replace(",", " ");
    String[] splitted = replaced.split(" +");
    Function<String, Integer> parse = Integer::parseInt;
    List<Integer> list = Arrays.stream(splitted).map(parse).collect(Collectors.toList());
    return list;
  }

  public Range multiplyBy(int factor) {
    int lower = iterator.lower;
    return new Range(lower, iterator.upper, factor, iterator.term, (x) -> x, lower, (x, y) -> y);
  }

  public Range multiplyBy(Range thatRange) {
    List<Integer> thisList = this.toList();
    List<Integer> thatList = thatRange.toList();
    int smallest = Math.min(thisList.size(), thatList.size());
    List<Integer> products = new ArrayList<>();

    for (int i = 0; i < smallest; i++) {
      int product = thisList.get(i) * thatList.get(i);
      products.add(product);
    }

    return new Range(products);
  }

  public Range addTo(int term) {
    int lower = iterator.lower;
    return new Range(lower, iterator.upper, iterator.factor, term, (x) -> x, 0, (x, y) -> y);
  }

  public BigInteger product() {
    BigInteger product = BigInteger.ONE;

    for (int element : this) {
      product = product.multiply(BigInteger.valueOf(element));
    }

    return product;
  }

  public int sum() {
    List<Integer> list = forEach(0, ADDITION).toList();
    return list.get(list.size() - 1);
  }

  public Range reverse() {
    if (integerList != null) {
      List<Integer> reversed = new ArrayList<>(integerList);
      Collections.reverse(reversed);
      return new Range(reversed);
    } else {
      int factor = iterator.factor;
      int term = (iterator.upper * factor) + iterator.term - factor;
      return new Range(iterator.lower, iterator.upper, -factor, term, (x) -> x, 0, (x, y) -> y);
    }
  }

  public Range forEach(UnaryOperator<Integer> unary) {
    if (integerList != null) {
      List<Integer> list = new ArrayList<>();

      for (int i : integerList) {
        list.add(unary.apply(i));
      }

      return new Range(list);
    } else {
      int lower = iterator.lower;
      int upper = iterator.upper;
      return new Range(lower, upper, iterator.factor, iterator.term, unary, 0, (x, y) -> y);
    }
  }

  public List<String> ofEach(UnaryOperator<Integer> unary) {
    List<String> list = new ArrayList<>();
    return list;
  }

  public Range forEach(int first, BinaryOperator<Integer> binary) {
    if (integerList != null) {
      List<Integer> list = new ArrayList<>();
      int value = 0;

      for (int i = 0; i < integerList.size(); i++) {
        int term = (i == 0) ? first : value;
        value = binary.apply(term, integerList.get(i));
        list.add(value);
      }

      return new Range(list);
    } else {
      int lower = iterator.lower;
      int upper = iterator.upper;
      return new Range(lower, upper, iterator.factor, iterator.term, (x) -> x, first, binary);
    }
  }

  public Collection<List<Integer>> partitionBy(int size) {
    final AtomicInteger counter = new AtomicInteger(0);
    List<Integer> list = toList();
    return list.stream()
        .collect(Collectors.groupingBy(s -> counter.getAndIncrement() / size))
        .values();
  }

  public List<String> format(String pattern) {
    List<String> formatted = new ArrayList<>();
    List<Integer> list = toList();

    for (int element : list) {
      formatted.add(String.format(pattern, element));
    }

    return formatted;
  }

  private Range(
      int lower,
      int upper,
      int factor,
      int term,
      UnaryOperator<Integer> unary,
      int first,
      BinaryOperator<Integer> binary) {
    iterator = new RangeIterator(lower, upper, factor, term, unary, first, binary);
    integerList = null;
  }

  private Range(List<Integer> list) {
    integerList = list;
    iterator = null;
  }

  @Override
  public Iterator<Integer> iterator() {
    return (iterator != null) ? iterator : integerList.iterator();
  }

  @Override
  public String toString() {
    return toList().toString();
  }

  public List<String> toString(Function<Integer, String> unary) {
    List<Integer> source = toList();
    List<String> target = new ArrayList<>();

    for (int i : source) {
      target.add(unary.apply(i));
    }

    return target;
  }

  public List<Integer> toList() {
    if (integerList != null) {
      return integerList;
    } else {
      List<Integer> list = new ArrayList<>();
      iterator.counter = 0;

      while (iterator.hasNext()) {
        list.add(iterator.next());
      }

      return list;
    }
  }

  public Range addAll(Range thatRange) {
    List<Integer> list1 = toList();
    List<Integer> list2 = thatRange.toList();
    list1.addAll(list2);
    return new Range(list1);
  }

  private static class RangeIterator implements Iterator<Integer> {
    private final int lower, upper, factor, term, first;
    private final UnaryOperator<Integer> unary;
    private final BinaryOperator<Integer> binary;
    private int counter;
    private Integer previousValue;

    public RangeIterator(
        int lower,
        int upper,
        int factor,
        int term,
        UnaryOperator<Integer> unary,
        int first,
        BinaryOperator<Integer> binary) {
      this.lower = lower;
      this.upper = upper;
      this.factor = factor;
      this.term = term;
      this.unary = unary;
      this.first = first;
      this.binary = binary;
      counter = lower;
    }

    @Override
    public boolean hasNext() {
      return (counter < upper);
    }

    @Override
    public Integer next() {
      int value = counter++ * factor + term;
      value = unary.apply(value);
      value = (previousValue == null) ? value : binary.apply(previousValue, value);
      previousValue = value;
      return value;
    }
  }
}
