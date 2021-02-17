package com.marcosavard.commons.math.type;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SuppressWarnings("serial")
public class IntegerList extends ArrayList<Integer> {

  public static IntegerList of(List<Integer> original) {
    IntegerList list = new IntegerList(original);
    return list;
  }

  public static IntegerList of(String original) {
    String[] splitted = original.split(" +");
    Function<String, Integer> parse = Integer::parseInt;
    List<Integer> list = Arrays.stream(splitted).map(parse).collect(Collectors.toList());

    return of(list);
  }

  public static IntegerList of(int length) {
    IntegerList list = of(IntStream.rangeClosed(1, length).boxed().collect(Collectors.toList()));
    return list;
  }

  private IntegerList(List<Integer> list) {
    super(list);
  }

  public IntegerList addTo(int term) {
    List<Integer> target = new ArrayList<>();

    for (int element : this) {
      target.add(element + term);
    }

    return new IntegerList(target);
  }

  public IntegerList multiplyBy(int factor) {
    List<Integer> target = new ArrayList<>();

    for (int element : this) {
      target.add(element * factor);
    }

    return new IntegerList(target);
  }

  public BigInteger multiply() {
    BigInteger product = BigInteger.ONE;

    for (int element : this) {
      product = product.multiply(BigInteger.valueOf(element));
    }

    return product;
  }

  public List<String> format(String pattern) {
    List<String> formatted = new ArrayList<>();

    for (int element : this) {
      formatted.add(String.format(pattern, element));
    }

    return formatted;
  }

  public List<String> toStrings() {
    List<String> strings = this.stream().map(Object::toString).collect(Collectors.toList());
    return strings;
  }

  public List<Integer> reverse() {
    List<Integer> reversed = (List<Integer>) this.clone();
    Collections.reverse(reversed);
    return reversed;
  }

  public List<Integer> multiplyBy(IntegerList that) {
    int smallest = Math.min(this.size(), that.size());
    List<Integer> products = new ArrayList<>();

    for (int i = 0; i < smallest; i++) {
      int product = this.get(i) * that.get(i);
      products.add(product);
    }

    return products;
  }

  public Integer forAll(int first, BinaryOperator<Integer> oper) {
    int result = this.stream().reduce(first, oper);
    return result;
  }



}
