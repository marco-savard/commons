package com.marcosavard.library.javaparser.generate;

import com.marcosavard.commons.lang.reflect.meta.MetaClass;
import com.marcosavard.commons.lang.reflect.meta.PojoGenerator;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class SourceBasedPojoGeneratorMain {

    public static void main(String[] args) {
        System.out.println("POJO generator v02");

        if (args.length == 0) {
            //generate("C:/Users/Marco/IdeaProjects/commons/commons/run/MountainModel1.java");
        }

        if (args.length != 1) {
            System.out.println("Syntax: java -cp <path> com.marcosavard.library.javaparser.generate.SourceBasedPojoGeneratorMain <ModelClass>.java");
        } else {
            String arg = args[0];
            if (! arg.endsWith(".java")) {
                System.out.println("Needs a .java as argument");
            } else {
                generate(arg);
            }
        }
    }

    private static void generate(String input) {
        try {
            System.out.println("Generating POJOs");
            File file = new File(input);
            Reader r = new FileReader(file);
            Map<MetaClass, String> codeByMetaName = new HashMap<>();
            PojoGenerator pojoGenerator = new SourceBasedPojoGenerator(r, codeByMetaName);
            pojoGenerator.generatePojos();
            r.close();

            for (MetaClass mc : codeByMetaName.keySet()) {
                String output = mc.getSimpleName() + ".java";
                File f = new File(file.getParentFile(), output);
                Writer w = new FileWriter(f);
                w.write(codeByMetaName.get(mc));
                w.close();
                String path = f.getAbsolutePath();
                System.out.println("  " + path + " generated");
            }

            int count = codeByMetaName.keySet().size();
            System.out.println(count + " POJOs generated");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
