package com.marcosavard.library.javaparser.generate;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.marcosavard.commons.debug.Console;
import com.marcosavard.commons.io.FileSystem;
import com.marcosavard.commons.lang.reflect.meta.MetaClass;
import com.marcosavard.commons.lang.reflect.meta.PojoGenerator;
import com.marcosavard.domain.library.model.LibraryModel;
import com.marcosavard.domain.purchasing.model.PurchaseOrderModel;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SourceBasedPojoGeneratorDemo {

    public static void main(String[] args) {
        File outputFolder = new File("C:/Users/Marco/IdeaProjects/commons/commons/src/main/java");
        File sourceFile = getSourceFile(LibraryModel.class);
        generate(outputFolder, sourceFile);
    }

    private static File getSourceFile(Class claz) {
        List<File> sourceFiles = getSourceFiles(claz);
        String filename = claz.getSimpleName() + ".java";
        File sourceFile =
                sourceFiles.stream().filter(f -> f.getName().equals(filename)).findFirst().orElse(null);
        return sourceFile;
    }

    private static List<File> getSourceFiles(Class claz) {
        File sourceFolder = new File("C:/Users/Marco/IdeaProjects/commons/commons/src/main/java");
        Package pack = claz.getPackage();
        List<File> sourceFiles = FileSystem.getSourceFiles(sourceFolder, pack);
        sourceFiles.addAll(sourceFiles);
        return sourceFiles;
    }

    private static void generate(File outputFolder, File sourceFile) {
        try {
            JavaParser parser = new JavaParser();
            Reader r = new FileReader(sourceFile); 
            CompilationUnit cu = parser.parse(r);
            r.close();


            //new
            Map<String, String> codeByFileName = new HashMap<>();
            Reader reader = new FileReader(sourceFile);
            SourceBasedPojoGenerator pojoGenerator = new SourceBasedPojoGenerator(reader, codeByFileName);
            pojoGenerator.generatePojos();




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
