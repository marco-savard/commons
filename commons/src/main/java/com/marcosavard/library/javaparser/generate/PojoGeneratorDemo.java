package com.marcosavard.library.javaparser.generate;

import com.marcosavard.commons.debug.Console;
import com.marcosavard.domain.model.Model8;
import com.marcosavard.domain.purchasing.model.PurchaseOrderModel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PojoGeneratorDemo {

  public static void main(String[] args) {
    File outputFolder = new File("C:/Users/Marco/IdeaProjects/commons/commons/src/main/java");
    //File outputFolder = new File("C:/Users/User/IdeaProjects/commons/commons/src/main/java");
    generate(outputFolder, PurchaseOrderModel.class.getClasses());
  }

  private static void generate(File outputFolder, Class<?> claz) {
    generate(outputFolder, new Class[] {claz});
  }

  private static void generate(File outputFolder, Class<?>[] classes) {
    try {
      PojoGenerator generator = new PojoGenerator(outputFolder, classes);
      List<File> generatedFiles = generator.generate();

      for (File generatedFile : generatedFiles) {
        Console.println("File {0} generated", generatedFile);
      }

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
