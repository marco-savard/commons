package com.marcosavard.library.javaparser;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.NodeList;
import com.marcosavard.commons.io.FileSystem;
import com.marcosavard.commons.lang.CharArray;
import com.marcosavard.commons.lang.CharString;
import com.marcosavard.commons.lang.CharUtil;
import com.marcosavard.commons.lang.IndexSafe;
import com.marcosavard.commons.lang.NullSafe;
import com.marcosavard.commons.lang.StringUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MetricDemo {
    private static final File sourceFolder = new File("C:/Users/User/IdeaProjects/commons/commons/src/main/java");

    public static void main(String[] args) {
        List<File> sourceFiles = getSourceFiles();
        Map<File, CompilationUnit> parsedFiles = parseFiles(sourceFiles);

        for (File file : parsedFiles.keySet()) {
            computeMetric(file, parsedFiles.get(file));
        }
    }

    private static void computeMetric(File file, CompilationUnit compilationUnit) {
        NodeList<ImportDeclaration> list = compilationUnit.getImports();
        int count = list.size();
        int genericCount = 0;

        for (int i = 0; i < count; i++) {
            ImportDeclaration importDeclaration = list.get(i);
            String s = importDeclaration.getNameAsString();
            boolean generic = s.startsWith("java.");
            genericCount += generic ? 1 : 0;
        }

        String name = compilationUnit.getType(0).getNameAsString();
        double percent = (genericCount * 100) / count;
        String msg = MessageFormat.format("{0} is {1} % generic ({2} of {3}).", name, percent, genericCount, count);
        System.out.println(msg);
    }

    private static List<File> getSourceFiles() {
        List<File> sourceFiles = new ArrayList<>();
        sourceFiles.addAll(FileSystem.getSourceFiles(sourceFolder, CharArray.class.getPackage()));

        /*
        sourceFiles.add(FileSystem.getSourceFile(sourceFolder, CharArray.class));
        sourceFiles.add(FileSystem.getSourceFile(sourceFolder, CharString.class));
        sourceFiles.add(FileSystem.getSourceFile(sourceFolder, CharUtil.class));
        sourceFiles.add(FileSystem.getSourceFile(sourceFolder, IndexSafe.class));
        sourceFiles.add(FileSystem.getSourceFile(sourceFolder, NullSafe.class));
        sourceFiles.add(FileSystem.getSourceFile(sourceFolder, StringUtil.class));

         */
        return sourceFiles;
    }

    private static Map<File, CompilationUnit> parseFiles(List<File> sourceFiles) {
        Map<File, CompilationUnit> parsedFiles = new HashMap<>();
        JavaParser parser = new JavaParser();

        for (File file : sourceFiles) {
            try {
                ParseResult<CompilationUnit> result = parser.parse(file);
                CompilationUnit unit = result.getResult().orElse(null);
                parsedFiles.put(file, unit);
            } catch (FileNotFoundException e) {
                //ignore
            }
        }

        return parsedFiles;
    }


}
