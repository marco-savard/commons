package com.marcosavard.commons.lang.reflect.meta;

import com.marcosavard.commons.debug.Console;
import com.marcosavard.domain.library.model.LibraryModel;
import com.marcosavard.domain.mountain.model.MountainModel1;
import com.marcosavard.domain.purchasing.model.PurchaseOrderModel;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ReflectivePojoGeneratorDemo {

  public static void main(String[] args) {
    File outputFolder = new File("C:/Users/Marco/IdeaProjects/commons/commons/src/main/java");
    //File outputFolder = new File("C:/Users/User/IdeaProjects/commons/commons/src/main/java");
    generate(outputFolder, MountainModel1.class.getClasses());
    generate(outputFolder, LibraryModel.class.getClasses());
    generate(outputFolder, PurchaseOrderModel.class.getClasses());
  }

  private static void generate(File outputFolder, Class<?> claz) {
    generate(outputFolder, new Class[] {claz});
  }

  private static void generate(File outputFolder, Class<?>[] classes) {
    try {
      ReflectivePojoGenerator generator = new ReflectivePojoGenerator(outputFolder, classes);
      generator.withParameterlessConstructor();
      List<File> generatedFiles = generator.generate();

      for (File generatedFile : generatedFiles) {
        Console.println("File {0} generated", generatedFile);
      }

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
