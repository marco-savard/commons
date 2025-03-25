package com.marcosavard.library.javaparser;

import com.github.javaparser.JavaParser;
import com.marcosavard.commons.debug.Console;
import com.marcosavard.commons.io.FileSystem;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class MetricDemo {

  private static final AbstractMetric[] metrics = new AbstractMetric[] {
          new EncapsulationMetric(),
          new CouplingMetric(),
          new MethodComplexityMetric()
  };

  public static void main(String[] args) {
    try {
      File srcFolder = getSourceFolder();
      List<File> files = FileSystem.getFilesEndingWith(srcFolder, ".java");
      files = files.subList(0, Math.min(700, files.size()));
      JavaParser parser = new JavaParser();

      for (AbstractMetric metric : metrics) {
        metric.iterateFiles(parser, files);
      }

      for (AbstractMetric metric : metrics) {
        List<AbstractMetric.ResultSet> resultSets = metric.getResults();

        for (AbstractMetric.ResultSet resultSet : resultSets) {
          Console.println(resultSet);
        }

        Console.println();
      }

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }


  private static File getSourceFolder() {
    File rootFolder = FileSystem.getRootFolder(MetricDemo.class);
    return new File(rootFolder.getParentFile(), "src");
  }






}
