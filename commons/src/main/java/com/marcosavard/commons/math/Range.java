package com.marcosavard.commons.math;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Range extends ArrayList<Integer> {
  public static final BinaryOperator<Integer> ADDITION = (x, y) -> x + y;
  public static final BinaryOperator<Integer> MULTIPLICATION = (x, y) -> x * y;

  private Range(int min, int max) {
    addAll(IntStream.rangeClosed(min, max - 1).boxed().toList());
  }

  private Range(List<Integer> list) {
    super(list);
  }

  public static Range of(int max) {
    return of(0, max);
  }

  public static Range of(int min, int max) {
    return new Range(min, max);
  }

  public static Range of(Integer... items) {
    return new Range(Arrays.asList(items));
  }

  public static Range of(String str) {
    String replaced = str.replace("[", "").replace("]", "");
    replaced = replaced.replace(",", " ");
    String[] splitted = replaced.split(" +");
    Function<String, Integer> parse = Integer::parseInt;
    List<Integer> list = Arrays.stream(splitted).map(parse).toList();
    return new Range(list);
  }

  public Range addTo(int term) {
    return this.forAll(x -> x + term);
  }

  public Range multiplyBy(int factor) {
    return this.forAll(x -> x * factor);
  }

  public List<String> format(String pattern) {
    List<String> formatted = new ArrayList<>();

    for (int element : this) {
      formatted.add(String.format(pattern, element));
    }

    return formatted;
  }

  public Range multiplyBy(Range that) {
    int smallest = Math.min(this.size(), that.size());
    List<Integer> products = new ArrayList<>();

    for (int i = 0; i < smallest; i++) {
      int product = this.get(i) * that.get(i);
      products.add(product);
    }

    return new Range(products);
  }

  public Range reverse() {
    List<Integer> reversed = (List<Integer>) this.clone();
    Collections.reverse(reversed);
    return new Range(reversed);
  }

  public int sum() {
    Range range = forAll(0, ADDITION);
    return range.get(range.size()-1);
  }

  public BigInteger product() {
    BigInteger product = BigInteger.ONE;

    for (int element : this) {
      product = product.multiply(BigInteger.valueOf(element));
    }

    return product;
  }

  public Range forAll(UnaryOperator<Integer> oper) {
    List<Integer> target = new ArrayList<>();

    for (int item : this) {
      target.add(oper.apply(item));
    }

    return new Range(target);
  }

  public Range forAll(int first, BinaryOperator<Integer> oper) {
    List<Integer> target = new ArrayList<>();

    for (int i = 0; i < size(); i++) {
      int result = (i == 0) ? oper.apply(first, get(i)) : oper.apply(target.get(i - 1), get(i));
      target.add(result);
    }

    return new Range(target);
  }

  public Integer forAllOld(int first, BinaryOperator<Integer> oper) {
    int result = this.stream().reduce(first, oper);
    return result;
  }

  public Collection<List<Integer>> partitionBy(int size) {
    final AtomicInteger counter = new AtomicInteger(0);
    return this.stream()
        .collect(Collectors.groupingBy(s -> counter.getAndIncrement() / size))
        .values();
  }
}
