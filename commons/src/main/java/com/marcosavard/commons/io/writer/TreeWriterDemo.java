package com.marcosavard.commons.io.writer;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TreeWriterDemo {

  public static void main(String[] args) {
    List<Package> javaPackages = findJavaPackages();
    List<String> packageNames = toNames(javaPackages);
    List result = unflatten(packageNames, "\\.");

    PrintWriter pw = new PrintWriter(System.out);
    TreeWriter<String> tw = new TreeWriter<>(pw);
    tw.write(packageNames);
  }

  private static List<String> unflatten(List<String> flatList, String sep) {
    List<String> list = new ArrayList<>();

    for (String item : flatList) {
        String[] parts = item.split(sep);

        for (String part : parts) {

        }
    }

    return list;
  }

  // find packages whose name start with java.*
  private static List<Package> findJavaPackages() {
    List<Package> allPackages = Arrays.asList(Package.getPackages());
    Comparator<Package> comparator = Comparator.comparing(Package::getName);
    List<Package> javaPackages =
        allPackages.stream()
            .filter(p -> p.getName().startsWith("java"))
            .sorted(comparator)
            .collect(Collectors.toList());
    return javaPackages;
  }

  private static List<String> toNames(List<Package> packages) {
    List<String> names = new ArrayList<>();

    for (Package p : packages) {
      names.add(p.getName());
    }

    return names;
  }
}
