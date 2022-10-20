package com.marcosavard.library.javaparser.generate;

import com.marcosavard.commons.debug.Console;
import com.marcosavard.domain.model.Model8;
import com.marcosavard.domain.purchasing.model.PurchaseOrderModel;

import java.io.File;
import java.io.IOException;

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
    PojoGenerator generator = new PojoGenerator(outputFolder);

    for (Class claz : classes) {
      try {
        Console.println("File {0} generated", generator.generate(claz));
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
  }
}
