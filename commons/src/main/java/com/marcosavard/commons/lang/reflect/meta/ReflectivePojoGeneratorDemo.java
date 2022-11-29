package com.marcosavard.commons.lang.reflect.meta;

import com.marcosavard.commons.debug.Console;
import com.marcosavard.domain.library.model.LibraryModel;
import com.marcosavard.domain.mountain.model.MountainModel1;
import com.marcosavard.domain.purchasing.model.PurchaseOrderModel;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReflectivePojoGeneratorDemo {

  public static void main(String[] args) {
    File outputFolder = new File("C:/Users/Marco/IdeaProjects/commons/commons/src/main/java");
    //File outputFolder = new File("C:/Users/User/IdeaProjects/commons/commons/src/main/java");

    Map<String, String> codeByFileName = new HashMap<>();
    generate(codeByFileName, MountainModel1.class.getClasses());
    generate(codeByFileName, LibraryModel.class.getClasses());
    generate(codeByFileName, PurchaseOrderModel.class.getClasses());

    //TODO write in file
  }

  //private static void generate(File outputFolder, Class<?> claz) {
 //   generate(outputFolder, new Class[] {claz});
  //}

  private static void generate(Map<String, String> codeByFileName, Class<?>[] classes) {
      ReflectivePojoGenerator generator = new ReflectivePojoGenerator(codeByFileName, classes);
      generator.withParameterlessConstructor();
      generator.generatePojos();
  }
}
