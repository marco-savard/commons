package com.marcosavard.library.javaparser.generate;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.marcosavard.commons.debug.Console;
import com.marcosavard.commons.io.FileSystem;
import com.marcosavard.commons.lang.reflect.meta.PojoGenerator;
import com.marcosavard.domain.purchasing.model.PurchaseOrderModel;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class SourceBasedPojoGeneratorDemo {

    public static void main(String[] args) {
        File outputFolder = new File("C:/Users/Marco/IdeaProjects/commons/commons/src/main/java");
        File sourceFile = getSourceFile(PurchaseOrderModel.class);
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
            CompilationUnit cu = parser.parse(sourceFile).getResult().orElse(null);

            //new
            SourceBasedPojoGenerator pojoGenerator = new SourceBasedPojoGenerator(outputFolder, cu);
            List<File> generatedFiles = pojoGenerator.generate(cu);

            for (File file : generatedFiles) {
                Console.println("File {0} generated", file.getName());
            }

            /*

            NodeList<TypeDeclaration<?>> types = cu.getTypes();

            for (TypeDeclaration type : types) {
                List<Node> nodes = type.getChildNodes();

                for (Node node : nodes) {
                    if (node instanceof TypeDeclaration<?> td) {
                        File generatedFile = generateType(outputFolder, cu, td);
                        Console.println("File {0} generated", generatedFile.getName());
                    }
                }
            }
            
            cu.getTypes();

             */

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static File generateType(File outputFolder, CompilationUnit cu, TypeDeclaration typeDeclaration) {
        File generated = null;

       // try {
          //  MetaClass mc = new SourceMetaClass(cu, typeDeclaration);
            PojoGenerator pojoGenerator = new SourceBasedPojoGenerator(outputFolder, cu);
           // generated = pojoGenerator.generateClass(mc);
      //  } catch (IOException e) {
     //       throw new RuntimeException(e);
    //    }

        return generated;
    }
}