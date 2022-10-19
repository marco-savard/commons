package com.marcosavard.library.javaparser.generate;

import com.marcosavard.commons.debug.Console;
import com.marcosavard.domain.model.Model8;

import java.io.File;
import java.io.IOException;

public class PojoGeneratorDemo {

  public static void main(String[] args) {
    // File folder = new File("C:/Users/Marco/IdeaProjects/commons/commons/src/main/java");
    File folder = new File("C:/Users/User/IdeaProjects/commons/commons/src/main/java");
    generate(folder, Model8.class.getClasses());
  }

  private static void generate(File folder, Class<?> claz) {
    generate(folder, new Class[] {claz});
  }

  private static void generate(File folder, Class<?>[] classes) {
    PojoGenerator generator = new PojoGenerator(folder);

    for (Class claz : classes) {
      try {
        Console.println("File {0} generated", generator.generate(claz));
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
  }
}
