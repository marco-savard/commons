package com.marcosavard.library.javaparser.generate;

import com.marcosavard.commons.lang.reflect.meta.MetaClass;
import com.marcosavard.commons.lang.reflect.meta.PojoGenerator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

public class SourceBasedPojoGeneratorMain {

    public static void main(String[] args) {
        System.out.println(System.getProperty("java.ext.dirs"));

        if (args.length != 1) {
            System.out.println("Needs one argument");
        } else {
            String arg = args[0];
            if (! arg.endsWith(".java")) {
                System.out.println("Needs a .java as argument");
            } else {
               // generate(arg);
            }
        }
    }

    private static void generate(String filename) {
        try {
            File file = new File(filename);
            Reader r = new FileReader(file);
            Map< MetaClass, String> codeByClassName = new HashMap<>();
            PojoGenerator pojoGenerator = new SourceBasedPojoGenerator(r, codeByClassName);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
