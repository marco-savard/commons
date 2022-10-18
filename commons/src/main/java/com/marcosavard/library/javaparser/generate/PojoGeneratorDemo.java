package com.marcosavard.library.javaparser.generate;

import com.marcosavard.commons.debug.Console;
import com.marcosavard.commons.io.FileSystem;
import com.marcosavard.domain.model.Model3;
import com.marcosavard.domain.model.Model4;
import com.marcosavard.domain.model.Model8;

import java.io.File;
import java.io.IOException;

public class PojoGeneratorDemo {

    public static void main(String[] args) {
        File folder = new File("C:/Users/Marco/IdeaProjects/commons/commons/src/main/java");

        try {
            //File folder = FileSystem.getUserDocumentFolder();
            PojoGenerator generator = new PojoGenerator(folder);
            Class[] classes = Model8.class.getClasses();

            for (Class claz : classes) {
                Console.println("File {0} generated", generator.generate(claz));
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
