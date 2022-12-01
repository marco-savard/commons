package com.marcosavard.library.javaparser.generate;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.marcosavard.commons.debug.Console;
import com.marcosavard.commons.io.FileSystem;
import com.marcosavard.commons.lang.reflect.meta.MetaClass;
import com.marcosavard.commons.lang.reflect.meta.PojoGenerator;
import com.marcosavard.domain.library.model.LibraryModel;
import com.marcosavard.domain.mountain.model.MountainModel1;
import com.marcosavard.domain.purchasing.model.PurchaseOrderModel;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SourceBasedPojoGeneratorDemo {
    private static final String SOURCE_FOLDER = "C:/Users/Marco/IdeaProjects/commons/commons/src/main/java";
    private static final String OUTPUT_FOLDER = "C:/Users/Marco/IdeaProjects/commons/commons/src/main/java";

    public static void main(String[] args) {
        Class model = MountainModel1.class;
       // Class model = PurchaseOrderModel.class;
        File sourceFile = getSourceFile(model);
        File outputFolder = new File(OUTPUT_FOLDER);
        generate(sourceFile, outputFolder);
    }

    private static File getSourceFile(Class claz) {
        List<File> sourceFiles = getSourceFiles(claz);
        String filename = claz.getSimpleName() + ".java";
        File sourceFile =
                sourceFiles.stream().filter(f -> f.getName().equals(filename)).findFirst().orElse(null);
        return sourceFile;
    }

    private static List<File> getSourceFiles(Class claz) {
        File sourceFolder = new File(SOURCE_FOLDER);
        Package pack = claz.getPackage();
        List<File> sourceFiles = FileSystem.getSourceFiles(sourceFolder, pack);
        sourceFiles.addAll(sourceFiles);
        return sourceFiles;
    }

    private static void generate(File sourceFile, File outputFolder) {
        try {
            //generate POJOs
            Map<MetaClass, String> codeByClassName = new HashMap<>();
            Reader reader = new FileReader(sourceFile);
            SourceBasedPojoGenerator pojoGenerator = new SourceBasedPojoGenerator(reader, codeByClassName);
            pojoGenerator
                    .withParameterlessConstructor()
                    .generatePojos();
            reader.close();

            //Write POJOs
            for (MetaClass mc : codeByClassName.keySet()) {
                String packName = mc.getPackage().getName().replace('.', '/');
                File packageFolder = new File(outputFolder, packName);
                String fileName = mc.getSimpleName() + ".java";
                File generatedFile = new File(packageFolder, fileName);
                Writer w = new FileWriter(generatedFile);
                String pojo = codeByClassName.get(mc);
                w.write(pojo);
                w.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static File generateType(File outputFolder, CompilationUnit cu, TypeDeclaration typeDeclaration) {
        File generated = null;

       // try {
          //  MetaClass mc = new SourceMetaClass(cu, typeDeclaration);
          //  PojoGenerator pojoGenerator = new SourceBasedPojoGenerator(outputFolder, cu);
           // generated = pojoGenerator.generateClass(mc);
      //  } catch (IOException e) {
     //       throw new RuntimeException(e);
    //    }

        return generated;
    }
}
