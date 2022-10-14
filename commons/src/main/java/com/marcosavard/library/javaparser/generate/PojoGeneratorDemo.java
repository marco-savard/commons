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
        try {
            File folder = FileSystem.getUserDocumentFolder();
            PojoGenerator generator = new PojoGenerator(folder);
            Console.println("File {0} generated", generator.generate(Model8.Country.class));
            Console.println("File {0} generated", generator.generate(Model8.PhoneQualifier.class));
            Console.println("File {0} generated", generator.generate(Model8.Person.class));
            Console.println("File {0} generated", generator.generate(Model8.Address.class));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
