package com.marcosavard.library.javaparser.generate;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.marcosavard.commons.io.FileSystem;
import com.marcosavard.commons.lang.reflect.meta.MetaClass;
import com.marcosavard.commons.lang.reflect.meta.PojoGenerator;
import com.marcosavard.domain.library.model.LibraryModel;
import com.marcosavard.domain.mountain.model.MountainModel1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class ParsingPojoGeneratorDemo {

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
            CompilationUnit cu = parser.parse(sourceFile).getResult().orElse(null);
            NodeList<TypeDeclaration<?>> types = cu.getTypes();

            for (TypeDeclaration type : types) {
                List<Node> nodes = type.getChildNodes();

                for (Node node : nodes) {
                    if (node instanceof TypeDeclaration<?> td) {
                        generateType(outputFolder, td);
                    }
                }
            }
            
            cu.getTypes(); 

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static void generateType(File outputFolder, TypeDeclaration type) {
        try {
            MetaClass mc = new ParsedMetaClass(type);
            PojoGenerator pojoGenerator = new ParsingPojoGenerator(outputFolder);
            File generated = pojoGenerator.generateClass(mc);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}