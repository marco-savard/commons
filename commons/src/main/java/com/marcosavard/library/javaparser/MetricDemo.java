package com.marcosavard.library.javaparser;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import com.marcosavard.commons.App;
import com.marcosavard.commons.io.FileSystem;
import com.marcosavard.commons.util.ArrayUtil;
import com.marcosavard.commons.util.Grid;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class MetricDemo {
  private static final File sourceFolder =
      new File("C:/Users/User/IdeaProjects/commons/commons/src/main/java");

  public static void main(String[] args) {
    Metric[] metrics =
        new Metric[] {
          // new GenericityMetric(),
          new MethodLengthComplexity()
        };

    List<File> sourceFiles = getSourceFiles();
    List<File> selection =
        sourceFiles.stream().filter(f -> !f.getName().endsWith("Demo.java")).toList();
    selection = selection.stream().filter(f -> !f.getPath().contains("meta")).toList();

    for (Metric metric : metrics) {
      computeMetric(metric, selection);
    }
  }

  private static void computeMetric(Metric metric, List<File> sourceFiles) {
    Map<File, CompilationUnit> parsedFiles = parseFiles(sourceFiles);
    List<String[]> rows = new ArrayList<>();

    for (File file : parsedFiles.keySet()) {
      List<Metric.Stat> stats = metric.compute(file, parsedFiles.get(file));

      for (Metric.Stat stat : stats) {
        String[] row = ArrayUtil.concat(new String[] {stat.name}, stat.results);
        rows.add(row);
      }
    }

    Metric.Stat total = metric.getTotal();
    String[] row = ArrayUtil.concat(new String[] {total.name}, total.results);
    rows.add(row);
    List<String> lines = Grid.arrayListToLines(rows);

    for (String line : lines) {
      System.out.println(line);
    }

    System.out.println();
  }

  private static List<File> getSourceFiles() {
    Package pack = App.class.getPackage();
    List<File> sourceFiles = FileSystem.getSourceFiles(sourceFolder, pack);
    sourceFiles.addAll(sourceFiles);

    return sourceFiles;
  }

  private static Map<File, CompilationUnit> parseFiles(List<File> sourceFiles) {
    Map<File, CompilationUnit> parsedFiles = new TreeMap<>();
    JavaParser parser = new JavaParser();

    for (File file : sourceFiles) {
      try {
        ParseResult<CompilationUnit> result = parser.parse(file);
        CompilationUnit unit = result.getResult().orElse(null);
        parsedFiles.put(file, unit);
      } catch (FileNotFoundException e) {
        // ignore
      }
    }

    return parsedFiles;
  }
}
