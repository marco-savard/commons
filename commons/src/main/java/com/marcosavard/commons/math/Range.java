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
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Range extends ArrayList<Integer> {
    private static final BinaryOperator<Integer> ADDITION = (x1, x2) -> x1 + x2;
    private static final BinaryOperator<Integer> MULTIPLICATION = (x1, x2) -> x1 * x2;

    private Range(int min, int max) {
        addAll(IntStream.rangeClosed(min, max-1).boxed().collect(Collectors.toList()));
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
        List<Integer> list = Arrays.stream(splitted).map(parse).collect(Collectors.toList());
        return new Range(list);
    }

    //TODO equals()

    public Range addTo(int term) {
        List<Integer> target = new ArrayList<>();

        for (int element : this) {
            target.add(element + term);
        }

        return new Range(target);
    }

    public List<String> format(String pattern) {
        List<String> formatted = new ArrayList<>();

        for (int element : this) {
            formatted.add(String.format(pattern, element));
        }

        return formatted;
    }

    public Range multiplyBy(int factor) {
        List<Integer> target = new ArrayList<>();

        for (int element : this) {
            target.add(element * factor);
        }

        return new Range(target);
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
        return forAll(0, ADDITION);
    }

    public BigInteger product() {
        BigInteger product = BigInteger.ONE;

        for (int element : this) {
            product = product.multiply(BigInteger.valueOf(element));
        }

        return product;
    }

    public Integer forAll(int first, BinaryOperator<Integer> oper) {
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
