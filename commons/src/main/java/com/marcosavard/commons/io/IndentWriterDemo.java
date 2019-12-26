package com.marcosavard.commons.io;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import com.marcosavard.commons.util.WildcardsToRegex;

public class IndentWriterDemo implements Runnable {

  public static void main(String[] args) {
    IndentWriterDemo demo = new IndentWriterDemo();
    demo.run();
  }

  public IndentWriterDemo() {

  }

  @Override
  public void run() {
    // find packages whose name start with java.*
    List<Package> javaPackages = findPackages("java.*");

    // write Java packages
    IndentWriter iw = new IndentWriter(new PrintWriter(System.out));
    iw.println("Java Packages");
    iw.println();
    iw.indent();

    for (Package p : javaPackages) {
      // print first level packages
      int level = countOccurences(p.getName(), ".");

      if (level == 1) {
        printPackage(iw, javaPackages, p);
      }
    }

    iw.unindent();
    iw.close();
  }

  private static void printPackage(IndentWriter iw, List<Package> javaPackages, Package pack) {
    iw.println(pack.getName());
    List<Package> subPackages = findSubpackagesOf(javaPackages, pack);

    if (!subPackages.isEmpty()) {
      iw.indent();

      for (Package subPackage : subPackages) {
        printPackage(iw, subPackages, subPackage);
      }

      iw.println();
      iw.unindent();
    }
  }

  private static List<Package> findSubpackagesOf(List<Package> javaPackages, Package pack) {
    Predicate<Package> predicate = p -> (p.getName().startsWith(pack.getName())
        && (countOccurences(p.getName(), ".") == countOccurences(pack.getName(), ".") + 1));
    List<Package> subPackages =
        javaPackages.stream().filter(predicate).collect(Collectors.toList());
    return subPackages;
  }

  private static int countOccurences(String str, String substring) {
    int count = str.length() - str.replace(substring, "").length();
    return count;
  }

  private static List<Package> findPackages(String wildcards) {
    String javaHome = System.getProperty("java.home");
    System.out.println(javaHome);;

    String regex = WildcardsToRegex.toRegex(wildcards);
    List<Package> allPackages = Arrays.asList(Package.getPackages());
    Comparator<Package> comparator = Comparator.comparing(Package::getName);
    List<Package> javaPackages = allPackages.stream().filter(p -> p.getName().matches(regex))
        .sorted(comparator).collect(Collectors.toList());
    return javaPackages;
  }

}
